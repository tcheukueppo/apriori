public class Hashelement {

	// this class is composed of linked itemsets
	// which is important for Hashmap class.
	
	// contructors
	public Itemset firtemset;
	public Itemset lastemset;
	public Itemset prevhash; 
	int length = 0;
	public Hashelement(Itemset behind) { 
		prevhash = behind; 
		firtemset = null;
		lastemset = null;
	}
	// ATTENTION : you need to know that order counts 
	// now, ie the order of the items in the array.
	// if it's not taken into consideration, then a 
	// problem may arise on 'Itemset.java' implemented methods.

	// definition of the methods
	public String[] gotemset(int index) {

		Itemset tmpnode = this.firtemset;
		int gothrough = 1;
		while(gothrough != index) {
			tmpnode = tmpnode.friend;
			gothrough++;
		}
		return tmpnode.itemlist;
	}
	public void add(String[] toadd, int supp) {
	
		String concate = "";
		for(String s : toadd)
			concate = concate + ";" +  s;
		// deleting the last ';' character
		concate = concate.substring(1, concate.length());
		Itemset nelement = new Itemset(concate);
		nelement.supp = supp;
		if(this.length == 0) {
			lastemset = nelement;
			firtemset = nelement;
		}else {
			lastemset.friend =  nelement;
			lastemset = nelement;
		}
		this.length++;
	}
	private boolean subset(String[] tover) {

		String[] sub = new String[tover.length - 1];
		int counter = 0,result = 0, scounter;
		Itemset tmpitem;

		for(String k : tover) {
			for(String j : tover)
				if(!j.equals(k)) {
					sub[counter] = j; // or j
					counter++;
				}
			tmpitem = this.prevhash;
			while(tmpitem != null) {
				scounter = 0;
				for(String s : tmpitem.itemlist) {
					if(!s.equals(sub[scounter]))
						break;
					scounter++;
				}
				if(scounter == tmpitem.itemlist.length) break;
				tmpitem = tmpitem.friend; 
			}
			if(tmpitem == null)
				return false;	
			counter = 0;
		}
		this.add(tover, 0);
		return true;
	}
	public void joinall() {
		
		String[] carryall;
		int x = 0;
		Itemset tmpitem = this.prevhash, secondtmp;
		while(tmpitem.friend != null) {
			
			secondtmp = tmpitem.friend;
			while(secondtmp != null) {

				carryall = tmpitem.join(secondtmp.itemlist);
				x = (secondtmp.itemlist.length - 1);
				if(carryall[x] != null)
					this.subset(carryall);
				secondtmp = secondtmp.friend;
			}
			tmpitem = tmpitem.friend;
		}
	}

}
