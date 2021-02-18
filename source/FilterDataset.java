import java.io.*;

public class FilterDataset {

	Transaction head, end; 
	String filename;
	FilterDataset(String file) { this.filename = file; this.head = null; } 
	private boolean Append(String transid, String item) {
		
		Transaction work = this.head;
		Transaction newtransc = new Transaction(transid, item);
		if(work == null) {
			this.head = new Transaction(transid, item);
			this.end = this.head;
			return true;
		}
		while(work != null) {
			if(work.transactionid.equals(transid)) {
				
				String[] broken = work.items.split(";");
				for(String s : broken)
					if(broken.equals(item)) return false;
				work.items = work.items + ";" + item;
				return true;
			}
			work = work.next;
		}
		end.next = newtransc;
		end = end.next;
		return true;
	}
	public void Itistime() {

		try {
			FileReader filereader = new FileReader(this.filename);
			BufferedReader buffer = new BufferedReader(filereader);
			while(buffer.ready()) {
			
				String[] faketransaction = buffer.readLine().split(";");
				this.Append(faketransaction[0], faketransaction[2]);
			} 
		}catch(FileNotFoundException e) {
			System.out.println("Error : DatasetNotFound");
		}catch(IOException e) {
			System.out.println("Error : FileformatError");
		}
		this.Makedb();
	}
	private void Makedb() {

		try {
			FileWriter writer = new FileWriter("sdataset");
			Transaction enough = head;
			while(enough != null) {
				writer.write(enough.items + "\n");
				enough = enough.next;
			}
			writer.close();
		}catch(FileNotFoundException e) {
			System.out.println("Error : DerivedDatasetNotFound");
		}catch(IOException e) {
			System.out.println("Error : FileformatError");
		}
	}
		
}
