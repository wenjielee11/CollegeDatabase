import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Performs all application specific computation of College Search App (CSearchApp). Key features include 
 * loading in data, searching by college name, and searching by college tuition.
 * @author Kiet Pham
 *
 */
public class CSearchBackendBD implements CSearchBackendInterface {
	private RBTCollegeInterface<CollegeInterface> collegeListsByName, collegeListsByTuition;
	private CollegeReaderInterface collegeReader; 
	
	/**
	 * Constructor for the CSearchBackendBD class.
	 * Initialize backend to make use of the enhanced red-black tree of collegeLists called RBTCollegeDataInterface
	 * and the college reader
	 * @param collegeLists - the collection of colleges
	 * 						 Placeholder, implemented by AE
	 * @param collegeReader - the college reader
	 * 						  Placeholder, implemented by DW
	 */
	public CSearchBackendBD(RBTCollegeInterface<CollegeInterface> collegeListsByName,
			RBTCollegeInterface<CollegeInterface> collegeListsByTuition,
			CollegeReaderInterface collegeReader) {
		this.collegeReader = collegeReader;
		this.collegeListsByName = collegeListsByName; 
		this.collegeListsByTuition = collegeListsByTuition; 
	}
	
	/**
	 * Using CollegeReaderInterface to load posts from specific files and add Colleges to the 
	 * Red-black tree container. 
	 * @param filename - The path of file which we load the post from. 
	 * @throws FileNotFoundException - if the file is not found
	 */
	@Override
	public void loadData(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		List<CollegeInterface> colleges = collegeReader.readCollegesFromFile(filename); 
		for (CollegeInterface college : colleges) {
			addOneData(college); 
		}
	}

	/**
	 * This method add one college instance into the red-black trees, which is implemented by AE.
	 * @param dataToAdd - the college instance that will be added 
	 */
	@Override
	public void addOneData(CollegeInterface dataToAdd) {
		try {
			CollegeInterface dataToAddByName = dataToAdd.deepCopy("college");
			CollegeInterface dataToAddByTuition = dataToAdd.deepCopy("tuition");
			collegeListsByName.insert(dataToAddByName);
			collegeListsByTuition.insert(dataToAddByTuition);
		}
		catch (IllegalArgumentException e) {
			
		}
	}

	/**
	 * This method use the Red-black tree implemented by AE to search for the college with the exact name as the
	 * requested name. If there is no colleges then it will return a blank string
	 * @param exactName - the name of the college we need to find
	 * @return the String representation (name and information) of the college we need to find.
	 */
	@Override
	public String findCollegesByExactName(String exactName) throws NoSuchElementException{
		CollegeInterface toReturn = collegeListsByName.search("college", exactName); 
		if (toReturn == null) throw new NoSuchElementException(); 
		return toReturn.toString(); 
	}

	/**
	 * This method use the Red-black tree implemented by AE to search for the college with the starting name is
	 * the same as the requested name. 
	 * @param startingName - the starting name of the colleges we need to find
	 * @return the String representation of the list of colleges we need to find. 
	 */
	@Override
	public String findCollegesByStartingName(String startingName) {
		return collegeListsByName.searchInRange("college", startingName, startingName + '~');
	}

	/**
	 * This method use the Red-black tree implemented by AE to search for the college with the requested tuition
	 * @param tuition - the requested tuition
	 * @return the String representation of the list of colleges we need to find. 
	 */
	@Override
	public String findCollegesByTuitionRates(int tuition) {
		return collegeListsByTuition.searchInRange("tuition", tuition, tuition);
	}

	/**
	 * This method use the Red-black tree implemented by AE to search for the college with the requested tuition
	 * range
	 * @param tuitionMin - the lowerbound of requested tuition
	 * @param tuitionMax - the upperbound of requested tuition
	 * @return the String representation of the list of colleges we need to find. 
	 */
	@Override
	public String findCollegesByTuitionRatesRange(int tuitionMin, int tuitionMax) {
		// TODO Auto-generated method stub
		return collegeListsByTuition.searchInRange("tuition", tuitionMin, tuitionMax);
	}

	/**
	 * This method use remove the college instances from the red-black tree implemented by AE
	 * @param the name of the college we need to remove
	 * @return true if the removal is successful and false if otherwise
	 */
	@Override
	public boolean removeOneCollege(String exactName) {
		// TODO Auto-generated method stub
		CollegeInterface toRemove = collegeListsByName.search("college", exactName);
		if (toRemove == null) {
			return false; 
		}
		if (collegeListsByName.remove(toRemove)) {
			toRemove = toRemove.deepCopy("tuition"); 
			collegeListsByTuition.remove(toRemove); 
			return true; 
		}
		return false; 
	}
	
	// Accessor methods for testing - This can be deleted in the integration
	/**
	 * Accessor method to get the reference of collegeListsByName instance
	 * @return the reference to the instance of collegeListsByName
	 */
	public RBTCollegeInterface<CollegeInterface> getCollegeListsByName() {
		return this.collegeListsByName; 
	}
	
	/**
	 * Accessor method to get the reference of collegeListsByTuition instance
	 * @return the reference to the instance of collegeListsByTuition
	 */
	public RBTCollegeInterface<CollegeInterface> getCollegeListsByTuition() {
		return this.collegeListsByTuition; 
	}
}
