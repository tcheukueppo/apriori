import java.util.Scanner;
import java.io.*;

public class Apriori_and_AssociationRules {

	public static void main(String[] args) {

		float fconf;
		boolean anotherfuckingbool = false;
		String transactions;
		int minsup = 3, minconf = 3;
		
		System.out.println("		       _______  °Association Rules°                       ");
		System.out.println("	              /       \\                        ");
		System.out.println("	             /  _ _ _  \\                       "); 
		System.out.println("		    /  |     |  \\                      ");
		System.out.println("                   /   |     |   \\    . -------.      ");
		System.out.println("		  /     - - -     \\_ /    ...    \\    ");
		System.out.println("                 /    . - - - .          |   |   /    ");
		System.out.println("		/     /       \\           ...   /     ");
		System.out.println("	       /     |         |	  --    \\");
		System.out.println("	      /_ _ _/          \\__ _  ___/  \\___ \\   gkuttix@gmail.com");
		System.out.print("\n****NOTE****\nThe data set should be in the directory containing the classes,");
		System.out.print(" also the data set most be \nstructured so as to reduce redundancy(ie ");
		System.out.println("additional space in between items, ... etc).\n");
		System.out.println("Possible data set format : CSV file.");
		System.out.println("CSV file is better than the Horrible excel file full of spaces between items.");
		System.out.println("Data set description : each line contains a comma seperated list of items");
		System.out.println("Please, contact me through <gkuttix@gmail.com> to report bugs.");

		transactions = args[0];
		minsup = obtainint("% min-support value (that's as a percentage, Range = [1, 100])");
		while(minsup > 100 || minsup < 1) {
			System.out.println("Please enter a percentage value within the given range [1, 100]");
			minsup = obtainint("% min-support");
		}
		minconf = obtainint("% min-confidence value (that's as a percentage, Range = [1, 100])");
		while(minconf > 100 || minconf < 1) {
			System.out.println("Please enter a percentage value within the given range [1, 100]");
			minconf = obtainint("% min-confidence");
		}
		fconf = ((int)minconf/100);
		// base on the structure of the data set, we have the following
		FilterDataset instance = new FilterDataset(transactions);
		instance.Itistime();
		FAgenerate nightmare = new FAgenerate("sdataset", minsup, fconf);
		anotherfuckingbool = nightmare.generateFreq();
		if(anotherfuckingbool == true) {
			nightmare.generateAss();
			System.out.println("\nThanks for waiting");
		}
	}
	public static int obtainint(String which) {
		
		int toreturn = 0; 
		boolean toknow = true;
		while(toknow) {
			try {
				System.out.println("Enter the " + which);
				System.out.print(": ");
				Scanner scanit = new Scanner(System.in);
				toreturn = scanit.nextInt();
				toknow = false;
			}catch(Exception e) {
				System.out.println("Error : integer only, please try again");
			}
		}
		return toreturn;
	}
}
