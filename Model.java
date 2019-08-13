
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


/* Model is responsible for data handling.	
 * In the model section a .csv file is read. The first row of the file is deleted, and columns are stored in ArrayLists. */

public class Model {

	//Attributes  
	private static List<Double> YIELD_A = new ArrayList<>();
	private static List<Double> DAYS_TO_MATURITY = new ArrayList<>();
	private static List<Double> AMOUNT_CHF = new ArrayList<>();

	public void Read_File() throws FileNotFoundException {

		String fileName = "bondData100reals.csv";
		File file = new File(fileName);
		Scanner inputStream = new Scanner(file);
		// ignore the first line
		inputStream.next(); 
		while(inputStream.hasNext()) {
			String data = inputStream.next();
			String[] values = data.split(",");
			//Get the file's columns and make data " Double ".
			YIELD_A.add(Double.valueOf(values[0]));
			DAYS_TO_MATURITY.add(Double.valueOf(values[1]));
			AMOUNT_CHF.add(Double.valueOf(values[2]));
		}
		inputStream.close();
	}

	// Getters for the data
	public static List<Double> get_YIELD_A_double() 
	{
		return YIELD_A;
	}

	public static List<Double> get_DAYS_TO_MATURITY_double() 
	{
		return DAYS_TO_MATURITY;
	}

	public static List<Double> get_AMOUNT_CHF_double() 
	{
		return AMOUNT_CHF;
	}
}





