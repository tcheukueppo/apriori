import java.io.*;

public class FAgenerate {

	// attributs : using fake hashmap hashmaps :)
	// the size of the itemset array will be determined
	// by the number of item found in the items arguments.
	

	// Somthing to note About the size of the hashmap `hash`
	//	- Technically, the size of the hashmap is as a function
	//	  of the cardinality of the set of frequent itemsets each 
	//	  of length `1`, but decided, to guess a big value which 
	//	  is very bad because it might present a `nullpointerexception`
	//	  in case it's not sufficient to determine other frequent itemset
	Hashelement[] hash = new Hashelement[100];

	String transacs; 
	int ssl;
	float csl, fssl;
	public FAgenerate(String ts, int x, float y) {
		this.transacs = ts;
		this.ssl = x; this.csl = y;
	}
	public FAgenerate() { this(80, 80); }
	public FAgenerate(int x, int y) { 
		this.transacs = "transactions";
		this.ssl = x; this.csl = y;
	}
	private boolean initial() {
	
		int linenumber = 0;
		try {
			//////////////////////////////////////////////////////
			// put the api use to read excel files line by line //
			//////////////////////////////////////////////////////
			FileReader file = new FileReader(this.transacs);
			BufferedReader buff = new BufferedReader(file);

			String[] transaction, toget = new String[1];
			Hashelement freqone = new Hashelement(null);
			Itemset newnode = null;
			System.out.println("\nGenerating frequent itemsets ...\n");
			while(buff.ready())	{

				transaction = buff.readLine().split(";");
				newnode = freqone.firtemset;
				while(newnode != null) {

					for(int x = 0;x < transaction.length;x++)
						if(transaction[x] != null)
							if(transaction[x].equals(newnode.itemlist[0])) {
								newnode.supp++;
								transaction[x] = null;
							} 
					newnode = newnode.friend;
				}
				for(String s : transaction)
					if(s != null) {
						toget[0] = s;
						freqone.add(toget, 0);
						freqone.lastemset.supp++;
					}
				linenumber++;
			}
			// setting the real value of the minsup for further test
			this.fssl = ((((float)linenumber) * ((float)this.ssl))/100);
			System.out.println("Absolute Minsup is " + this.fssl + "\n");
			// filtering the non-sense before adding to hashmap
			// mark has as function of enabling and disabling insertion
			freqone = this.filterandprint(freqone.firtemset, -1);
			hash[0] = freqone;
		}catch(FileNotFoundException e) {
			System.out.println("Error : DatasetNotFound");
			return false;
		}catch(IOException e) {
			e.printStackTrace();  
		}
		return true;
	}
	public boolean generateFreq() {
		
		int hashindex = 0;
		boolean fuckingbool = this.initial();
		if(fuckingbool == false)
			return false;
		
		while(this.hash[hashindex].length != 0) {

			this.hash[hashindex + 1] = new Hashelement(hash[hashindex].firtemset);
			this.hash[hashindex + 1].joinall();
			this.hash[hashindex + 1] = this.freqAssistance(this.hash[hashindex + 1]);
			// get the frequent itemsets
			hash[hashindex + 1] = this.filterandprint(hash[hashindex + 1].firtemset, hashindex);
			hashindex++;
		}
		return true;
	}
	private Hashelement freqAssistance(Hashelement aghdoit) {
		
		////////////////////////////////////////////////////////
		/// put the api use to read excel files line by line ///
		////////////////////////////////////////////////////////
		if(aghdoit.firtemset == null)
			return aghdoit;
		try {  

			FileReader fileread = new FileReader(this.transacs);
			BufferedReader reader = new BufferedReader(fileread);
			String[] transaction;
			Itemset gothrough = null, getit = aghdoit.firtemset;
			int size = 0;
			
			while(reader.ready()) {  

				transaction = reader.readLine().split(";");
				gothrough = aghdoit.firtemset;
				// break if transaction size is less than itemlist size
				// this makes sense 'cause no one can try to determine
				// inclusion of A in B if card(B) < card(A).
				if(transaction.length < gothrough.itemlist.length)
					continue;
				while(gothrough != null) {
					
					size = 0;
					for(String s : gothrough.itemlist)
						for(String k : transaction)
							if(s.equals(k)) size++;
					if(gothrough.itemlist.length <= size)
						gothrough.supp++;
					gothrough = gothrough.friend;
				}
			}  
		}catch(IOException e) {
			e.printStackTrace();  
		}
		return aghdoit;
	}
	private Hashelement filterandprint(Itemset gothrough, int hashindex) {

		Hashelement realreturn = new Hashelement(null);
		boolean todo = true;
		while(gothrough != null) {
				
			if(((float)gothrough.supp) >= this.fssl) {
				if(todo) {
					System.out.println("Frequent Itemsets of length " + (hashindex + 2) + "\n" + "{\n");
					todo = false;
				}
				realreturn.add(gothrough.itemlist, gothrough.supp);
				System.out.println("Support = " + gothrough.supp);
				for(String s : gothrough.itemlist)
					System.out.print(" °° " + s + " °° ");
				System.out.println("\n");
			}
			gothrough = gothrough.friend;
			if(todo == false && gothrough == null)
				System.out.println("\n} ");
		}
		return realreturn;
	}
	public boolean generateAss() {

		//gothrough all frequent itemset in the hashmap
		int index = 1;
		if(this.hash[index] == null || this.hash[index - 1] == null) {

			System.out.println("Sorry, but there are NO association rules in the transaction set.");
			return false;
		}
		Hashelement flitemset = new Hashelement(null);
		Itemset itis = this.hash[index].firtemset;
		System.out.println("\nGenerating association rules ...");
		while(this.hash[index] != null) {

			itis = this.hash[index].firtemset;
			while(itis != null) {
		
				flitemset = this.getSubsetOne(itis);
				flitemset = this.getRule(itis, flitemset);
				itis = itis.friend;
			}
			if(itis != null)
				this.recurGenerate(itis, flitemset, 1);
			index++;
		}
		return true;
	}
	private void recurGenerate(Itemset argone, Hashelement argtwo, int edge) {
		
		Hashelement surplus = new Hashelement(argtwo.firtemset);

		if(argone.itemlist.length > edge) {
			
			surplus.joinall();
			surplus = this.getRule(argone, surplus);
			this.recurGenerate(argone, surplus, ++edge);
		}
	}
	private Hashelement getSubsetOne(Itemset togenerate) {

		Hashelement toreturn = new Hashelement(null);
		Itemset togetsupp =  hash[0].firtemset;
		String[] block = new String[1];
		
		for(String s : togenerate.itemlist)
			while(togetsupp != null) {
				if(togetsupp.itemlist[0].equals(s)) {
					block[0] = s;
					toreturn.add(block, togetsupp.supp);
				}
				togetsupp = togetsupp.friend;	
			}
		return toreturn;
	}
	private Hashelement getRule(Itemset bigset, Hashelement calfrom) {

		Itemset again, calfromit = calfrom.firtemset;
		Hashelement toreturn = new Hashelement(calfrom.prevhash);
		boolean toknow = true;
		int leftlength = bigset.itemlist.length - 1, unionsup,
			rightlength = calfromit.itemlist.length - 1, index = 0;
		while(calfromit != null) {

			again = hash[rightlength].firtemset;
			while(toknow) {
				
				toknow = true; index = 0;
				for(index = 0;index < again.itemlist.length;index++)
					if(again.itemlist[index].equals(calfromit.itemlist[index]))
						break;
				if((index + 1) == again.itemlist.length) {
					toknow = false;
					calfromit.supp = again.supp;
				}
				again = again.friend;
			}
			toknow = true;
			unionsup = bigset.supp;
			bigset = this.doMath(bigset, calfromit.itemlist);
			if((unionsup/calfromit.supp) >= this.csl) {

				System.out.println("\n-----------------------------------------------");
				System.out.print(" *==    { °° ");
				for(String k : bigset.itemlist)
					System.out.print(k + " °° ");
				System.out.println("}\n |");
				System.out.println(" |");
				System.out.print(" *==||> { °° ");
				for(String s : calfromit.itemlist)
					System.out.print(s + " °° ");
				System.out.println("}");
				toreturn.add(calfromit.itemlist, calfromit.supp);
			}
			calfromit = calfromit.friend;
		}
		return toreturn;
	}
	private Itemset doMath(Itemset dumb, String[] defil) {

		boolean escape = false;
		String toreturn = "";
		Itemset	filtered;
		for(String s : dumb.itemlist) {
			
			for(String k : defil)
				if(s.equals(k))
					escape = true;
			if(escape) { escape = false; continue; }
			toreturn = toreturn + ";" + s;
		}
		toreturn = toreturn.substring(1, toreturn.length());
		filtered = new Itemset(toreturn);
		return filtered;
	}
}
