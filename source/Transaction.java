public class Transaction {

	String transactionid;
	String items; 
	Transaction next;

	Transaction(String id, String item) { 

		this.transactionid = id;
		this.items = item;
		this.next = null; 
	}
	Transaction() { System.out.println("Not more than one interface!!"); } 
}
