public class Itemset {

	String[] itemlist;
	Itemset friend;
	int supp;
	public Itemset(String concat) {

		this.itemlist = concat.split(";");
		this.friend = null;
	}
	public Itemset() {

		System.out.print("ObjectInstaciationError : the object <Itemsets>");
		System.out.println("with an argument must be instanciated.");
	}

	// Defintion of the methods
	public void pritems() {
		for(String s : itemlist)
			System.out.println("item : " + s);
	}
	// joining : joining the two itemsets with
	// the laws respected ie the k-1 elements must be the same
	public String[] join(String[] freqcandi) {
	
		// Don't forget that you need the preserve the
		// order of the items, to reduce operations
		int attsize = this.itemlist.length, counter = 0;
		String[] rstring = new String[attsize + 1];
		if(freqcandi.length == attsize) {

			for(String scmp : freqcandi) {

				if(counter != (attsize - 1)) {
					if(!scmp.equals(this.itemlist[counter])) 
						return rstring;
					else
						rstring[counter] = scmp;
				}else { break; }
				counter++;
			}
			rstring[counter] = this.itemlist[attsize - 1];
			rstring[++counter] = freqcandi[attsize - 1];
			return rstring;
		}
		return rstring;
	}
}
