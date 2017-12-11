package Database;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to read data from outside txt file into a arraylist. The
 * element for arraylist is a string array
 * 
 * @author zoe
 *
 */
public class Readtxt {
	public static ArrayList<String[]> read(String classpath) {
		ArrayList<String[]> storedata = new ArrayList<String[]>();
		String csvFile = classpath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
		
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] country = line.split(cvsSplitBy);
                               
				storedata.add(country);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return storedata;

	}
}
