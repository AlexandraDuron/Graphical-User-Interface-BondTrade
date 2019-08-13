import java.io.FileNotFoundException;

public class Main {

	/*  BondTrade is an individual project. It's an interactive application that processes fictional financial data. 
	 * 
	 * • The objective of this application is to implement the Model-View-Controller (MVC) design pattern. 
	 * The MVC classifies objects according to their role. Controller uses Model, Controller updates View.
	 * 
	 * The data describes 100 foreign exchange bond trades for a department of a Bank, over a period of a
       few weeks in 1996. The meanings of these columns are as follows:
       • YIELD: The percentage return to be made on the trade. High yield bonds are riskier
         than low yields.
       • DAYS_TO_MATURITY: Time until the bond matures - when the owner get their
         money back.
       • AMOUNT_CHF(000): The amount of the trade in Swiss Francs x 1000. 
       (YIELD x AMOUNT_CHF(000) gives the amount of profit that will be made on the
         trade.)

	 * The program reads an input .csv file, and then stores data in different ArrayLists. 
	 *  The application displays the information in a scatterplot utilising 
	 * Swing components.  */

	
	
	//Main class instantiates Controller, View and Model classes, and initialises methods.
	public static void main(String[] args) throws FileNotFoundException {	
		Model theModel = new Model();
		theModel.Read_File();	
		View theView = new View();
		theView.ViewBonTrade();	
		Controller theController = new Controller(theView,theModel);
	}
}
