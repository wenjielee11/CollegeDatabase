import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Placeholder for RBTCollegeInterface 
 * @author Kiet Pham
 */

public class RBTCollegeBD implements RBTCollegeInterface{
	private String filter; 
	private Field field; 
	// Placeholder array to test the loadData() and addOneData() method
	public ArrayList<CollegeInterface> placeholderArray = new ArrayList<CollegeInterface>(); 
	// Two placeholder arrays to test remove(), findCollegesByExactName(), findCollegesByStartingName(), 
	// findCollegeByTuitionRates(), and findCollegeByTuitionRange(). 
	public String[] collegeNamePlaceholderArray = new String[] 
			{"University of Wisconsin - Madison", 
			 "University of Wisconsin - Manilla", 
			 "University of Wisconsin - Madagascar", 
			 "University of Wisconsin - Man in black", 
			 "University of Wisconsin - Milwaukee", 
			 "University of Wisconsin - Millionaire", 
			 "University of Winnipeg",
			 "University of Washington", 
			 "College of A", 
			 "College of B"
			};
	
	public CollegeBD[] collegePlaceholderArray = new CollegeBD[] 
			{new CollegeBD("University of Wisconsin - Madison", 0), 
			 new CollegeBD("University of Wisconsin - Manilla", 1), 
			 new CollegeBD("University of Wisconsin - Madagascar", 2),
			 new CollegeBD("University of Wisconsin - Man in black", 3), 
			 new CollegeBD("University of Wisconsin - Milwaukee", 4),
			 new CollegeBD("University of Wisconsin - Millionaire", 5),
			 new CollegeBD("University of Winnipeg", 6),
			 new CollegeBD("University of Washington", 7), 
			 new CollegeBD("College of A", 10), 
			 new CollegeBD("College of B", 20)
			};
	
	public int hasRemove = 0; // A variable which track how many time an element is remove if the filter of the 
								// node is tuition.
	
	public RBTCollegeBD(String filter) {
		this.filter = filter;
        try {
            field = CollegeBD.class.getField(filter);
        } catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        placeholderArray.add(null); 
	}

	@Override
	/**
	 * This method is the placeholder for insert. 
	 * Insert college by name: Insert at the front of placeholderArray, so that the last object is null.
	 * Insert college by tuition: Insert at the back of placeholderArray, so that the first object is null. 
	 * The test method will call the placeholderArray and check if the null is in the correct position. 
	 */
	public boolean insert(Comparable data) {
		CollegeInterface newData = (CollegeInterface) data; 
		if (newData.getFilter().equals("college")) {
			placeholderArray.add(0, (CollegeInterface) data); 
			return true;
		}
		else if (newData.getFilter().equals("tuition")) {
			placeholderArray.add((CollegeInterface) data); 
			return true; 
		}
		return false;
	}

	@Override
	/**
	 * This method is placeholder for remove. 
	 */
	public boolean remove(Comparable data) {
		// TODO Auto-generated method stub
		if (data == null) return false; 
		CollegeBD dataToRemove = (CollegeBD) data; 
		for (int i = 0; i < 10; ++i) {
			CollegeBD toCheck = collegePlaceholderArray[i]; 
			if (toCheck != null && toCheck.college.equals(dataToRemove.college) && toCheck.tuition == dataToRemove.tuition) {
				collegePlaceholderArray[i] = null; 
				collegeNamePlaceholderArray[i] = null; 
				if (dataToRemove.getFilter().equals("tuition")) ++hasRemove;
				return true; 
			}
		}
		return false;
	}

	@Override
	public boolean contains(Comparable data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		if (this.filter.equals("college")) {
			return placeholderArray.size() - 1; 
		}
		if (this.filter.equals("tuition")) {
			return placeholderArray.size() - 1; 
		}
		return 0; 
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Placeholder method for search. Use the collegePlaceholderArray(); 
	 */
	@Override
	public CollegeInterface search(String filter, Comparable value) {
		for (int i = 0; i < 10; ++i) {
			if (filter.equals("college")) {
				if (collegePlaceholderArray[i] != null && collegePlaceholderArray[i].college.equals(value)) {
					return collegePlaceholderArray[i]; 
				}
			}
			if (collegePlaceholderArray[i] != null && filter.equals("tuition")) {
				if (value.equals(collegePlaceholderArray[i].tuition)) {
					return collegePlaceholderArray[i]; 
				}
			}
		}
		return null;
	}

	@Override
	public String searchInRange(String filter, Comparable min, Comparable max) {
		if (filter.equals("college")) {
			min = (String) min; 
			max = (String) max; 
		}
		if (filter.equals("tuition")) {
			min = (Integer) min; 
			max = (Integer) max; 
		}
		String result = ""; 
		for (int i = 0; i < 10; ++i) {
			if (collegePlaceholderArray[i] == null) continue; 
			if (filter.equals("college")) {
				String toCheck = collegePlaceholderArray[i].college; 
				if (min.compareTo(toCheck) <= 0 && max.compareTo(toCheck) >= 0) {
					result = result + collegePlaceholderArray[i] + '\n';
				}
			}
			else if (filter.equals("tuition")) {
				double toCheck = collegePlaceholderArray[i].tuition; 
				if (((Integer) min).doubleValue() <= toCheck && ((Integer) max).doubleValue() >= toCheck) {
					result = result + collegePlaceholderArray[i] + '\n';
				}
			}
		}
		return result.trim();
	}


}
