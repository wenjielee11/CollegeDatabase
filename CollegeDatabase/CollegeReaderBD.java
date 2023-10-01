import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Placeholder class for CollegeReaderInterface
 * @author Kiet Pham
 *
 */

public class CollegeReaderBD implements CollegeReaderInterface{

	@Override
	public List<CollegeInterface> readCollegesFromFile(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if (filename.equals("Unis")) {
			ArrayList<CollegeInterface> uniData = new ArrayList<CollegeInterface>(); 
			uniData.add(new CollegeBD("University", 10));
			uniData.add(new CollegeBD("Univer", 0)); 
			uniData.add(new CollegeBD("Una", 1)); 
			uniData.add(new CollegeBD("University ", 100)); 
			return uniData; 
		}
		else if (filename.equals("Real")) {
			ArrayList<CollegeInterface> uniData = new ArrayList<CollegeInterface>(); 
			uniData.add(new CollegeBD("University of Wisconsin - Madison", 57000));
			uniData.add(new CollegeBD("University of Wisconsin - Milwaukee", 50000)); 
			uniData.add(new CollegeBD("University of Wisconsin - Whitewater", 50000)); 
			uniData.add(new CollegeBD("University of Wisconsin", 1000000)); 
			return uniData; 
		}
		else throw new FileNotFoundException("This file is not found and not found"); 
	}

}
