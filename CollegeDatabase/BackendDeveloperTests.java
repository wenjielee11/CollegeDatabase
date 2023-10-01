import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Tester classes for BackendDeveloper method using JUnit 5
 * @author Kiet Pham
 */

public class BackendDeveloperTests {
	
	public CSearchBackendBD backend; 
	public CollegeReaderBD collegeReader; 
	public RBTCollegeBD collegeListsByName; 
	public RBTCollegeBD collegeListsByTuition; 
	
	// public CollegeReaderDW collegeReaderMain; 
	// public RBTCollegeAE collegeListsByNameMain; 
	// public RBTCollegeAE collegeListsByTuitionMain; 
	public CSearchBackendBD backendMain; 
	// public CSearchFrontend frontendMain; 
	
	public RBTCollegeAE collegeListsByNameReview; 
	public RBTCollegeAE collegeListsByTuitionReview; 
	
	@SuppressWarnings("unchecked")
	@BeforeEach
	public void createInstance() {
		collegeReader = new CollegeReaderBD(); 
		collegeListsByName = new RBTCollegeBD("college");
		collegeListsByTuition = new RBTCollegeBD("tuition");
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader); 

		
		// collegeReaderMain = new CollegeReaderDW(); 
		// collegeListsByNameMain = new RBTCollegeAE("name"); 
		// collegeListsByTuitionMain = new RBTCollegeAE("tuition"); 
		// backendMain = new CSearchBackendBD(collegeListsByNameMain, collegeListsByTuitionMain, collegeReaderMain); 
		// frontendMain = new CSearchFrontend(scanner, backendMain); 
		
		collegeListsByNameReview = new RBTCollegeAE("college"); 
		collegeListsByTuitionReview = new RBTCollegeAE("tuition");
	}
	
	/**
	 * Test the addOneData method of the Backend. 
	 * This method ensures that: 
	 * 1) The data being added into a container (placeholderArray in RBTCollegeBD)
	 * 2) Each data is being added into each individual container (the first element is null for tuition 
	 * and the last element is null for name)
	 */
	@Test
	public void BackendDeveloperTest0() {
		backend.addOneData(new CollegeBD("Uni", 10));
		assertEquals(1, collegeListsByName.size(), "The backend insert does not work properly"); 
		assertEquals(1, collegeListsByTuition.size(), "The backend insert does not work properly");
		assertEquals(null, collegeListsByName.placeholderArray.get(1), "The backend does not insert in red-black tree for college name correctly");
		assertEquals(null, collegeListsByTuition.placeholderArray.get(0), "The backend does not insert in red-black tree for college tuition correctly");
		
		backend.addOneData(new CollegeBD("Unissss", 30));
		assertEquals(2, collegeListsByName.size(), "The backend insert does not work properly"); 
		assertEquals(2, collegeListsByTuition.size(), "The backend insert does not work properly");
		assertEquals(null, collegeListsByName.placeholderArray.get(2), "The backend does not insert in red-black tree for college name correctly");
		assertEquals(null, collegeListsByTuition.placeholderArray.get(0), "The backend does not insert in red-black tree for college tuition correctly");
	}
	
	/**
	 * Test the loadData method of the Backend. This method test for 3 case
	 * 1) A FileNotFoundException was thrown in invalid filename. 
	 * 2) The data is loaded in valid filename, which are "Real" and "Unis", 4 college data for each filename.
	 * 3) Addition after load file is still valid. 
	 */
	@Test
	public void BackendDeveloperTest1() {
		// Case 1: Check invalid case 
		try {
			backend.loadData("Fake");
			assertEquals(0, 1, "The method does not throw exception on invalid case"); 
		}
		catch (Exception e) {
			assertEquals("This file is not found and not found", e.getMessage(), "The method throw the wrong exception");
			assertEquals(0, collegeListsByName.size(), "The backend insert when it is invalid"); 
			assertEquals(0, collegeListsByTuition.size(), "The backend insert when it is invalid");
		}
		
		// Case 2: Check valid case
		try {
			backend.loadData("Real");
			assertEquals(4, collegeListsByName.size(), "The backend insert does not work properly"); 
			assertEquals(4, collegeListsByTuition.size(), "The backend insert does not work properly");
			assertEquals(null, collegeListsByName.placeholderArray.get(4), "The backend does not insert in red-black tree for college name correctly");
			assertEquals(null, collegeListsByTuition.placeholderArray.get(0), "The backend does not insert in red-black tree for college tuition correctly");
		} catch (FileNotFoundException e) {
			assertEquals(0, 1, "This method throws an exception on valid case"); 
		}
		try {
			backend.loadData("Unis");
			assertEquals(8, collegeListsByName.size(), "The backend insert does not work properly"); 
			assertEquals(8, collegeListsByTuition.size(), "The backend insert does not work properly");
			assertEquals(null, collegeListsByName.placeholderArray.get(8), "The backend does not insert in red-black tree for college name correctly");
			assertEquals(null, collegeListsByTuition.placeholderArray.get(0), "The backend does not insert in red-black tree for college tuition correctly");
		} catch (FileNotFoundException e) {
			assertEquals(0, 1, "This method throws an exception on valid case"); 
		}
		
		backend.addOneData(new CollegeBD("Uni", 10));
		backend.addOneData(new CollegeBD("Unissss", 30));
		assertEquals(10, collegeListsByName.size(), "The backend insert does not work properly"); 
		assertEquals(10, collegeListsByTuition.size(), "The backend insert does not work properly");
		assertEquals(null, collegeListsByName.placeholderArray.get(10), "The backend does not insert in red-black tree for college name correctly");
		assertEquals(null, collegeListsByTuition.placeholderArray.get(0), "The backend does not insert in red-black tree for college tuition correctly");
		
		try {
			backend.loadData("Fake");
			assertEquals(0, 1, "The method does not throw exception on invalid case"); 
		}
		catch (Exception e) {
			assertEquals("This file is not found and not found", e.getMessage(), "The method throw the wrong exception");
			assertEquals(10, collegeListsByName.size(), "The backend insert when it is invalid"); 
			assertEquals(10, collegeListsByTuition.size(), "The backend insert when it is invalid");
		}
	}
	
	/**
	 * Test findingCollegeByExactName method of the Backend. 
	 * This method make use the already implemented data called collegeNamePlaceholderArray and collegePlaceholderArray
	 */
	@Test
	public void BackendDeveloperTest2() {
		assertEquals("University of Wisconsin - Madison: 0", backend.findCollegesByExactName("University of Wisconsin - Madison"), "The search works incorrectly");
		assertEquals("University of Wisconsin - Manilla: 1", backend.findCollegesByExactName("University of Wisconsin - Manilla"), "The search works incorrectly");
		assertEquals("University of Wisconsin - Madagascar: 2", backend.findCollegesByExactName("University of Wisconsin - Madagascar"), "The search works incorrectly");
		assertEquals("University of Wisconsin - Man in black: 3", backend.findCollegesByExactName("University of Wisconsin - Man in black"), "The search works incorrectly");
		assertEquals("University of Wisconsin - Milwaukee: 4", backend.findCollegesByExactName("University of Wisconsin - Milwaukee"), "The search works incorrectly");
		assertEquals("University of Wisconsin - Millionaire: 5", backend.findCollegesByExactName("University of Wisconsin - Millionaire"), "The search works incorrectly");
		assertEquals("University of Winnipeg: 6", backend.findCollegesByExactName("University of Winnipeg"), "The search works incorrectly");
		assertEquals("University of Washington: 7", backend.findCollegesByExactName("University of Washington"), "The search works incorrectly");
		try {
			backend.findCollegesByExactName("University of Wisconsin"); 
			assertEquals(0, 1, "This code doesnt throw a NSEE when it is supposed to"); 
		}
		catch (NoSuchElementException e) {
			
		}
		try {
			backend.findCollegesByExactName("University of Washington - Seattle"); 
			assertEquals(0, 1, "This code doesnt throw a NSEE when it is supposed to"); 
		}
		catch (NoSuchElementException e) {
			
		}
		assertEquals("College of A: 10", backend.findCollegesByExactName("College of A"), "The search works incorrectly");
		assertEquals("College of B: 20", backend.findCollegesByExactName("College of B"), "The search works incorrectly");
	}
	
	/**
	 * Test findingCollegeByTuition and findingCollegeByTuitionRange method of the Backend.
	 * In the Red-black tree placeholder class, the search in range of tuition will return a string which is 
	 * created by combining all strings in the tuitionPlaceholderArray[tuitionMin..tuitionMax]
	 */
	@Test
	public void BackendDeveloperTest3() {
		assertEquals("University of Wisconsin - Madison: 0", backend.findCollegesByTuitionRates(0), "The tuition does not correspond with the correct object");
		assertEquals("University of Wisconsin - Manilla: 1", backend.findCollegesByTuitionRates(1), "The tuition does not correspond with the correct object");
		assertEquals("University of Wisconsin - Madagascar: 2", backend.findCollegesByTuitionRates(2), "The tuition does not correspond with the correct object");
		assertEquals("University of Wisconsin - Man in black: 3", backend.findCollegesByTuitionRates(3), "The tuition does not correspond with the correct object");
		assertEquals("University of Wisconsin - Milwaukee: 4", backend.findCollegesByTuitionRates(4), "The tuition does not correspond with the correct object");
		assertEquals("University of Wisconsin - Millionaire: 5", backend.findCollegesByTuitionRates(5), "The tuition does not correspond with the correct object");
		assertEquals("University of Winnipeg: 6", backend.findCollegesByTuitionRates(6), "The tuition does not correspond with the correct object");
		assertEquals("University of Washington: 7", backend.findCollegesByTuitionRates(7), "The tuition does not correspond with the correct object");
		assertEquals("College of A: 10", backend.findCollegesByTuitionRates(10), "The tuition does not correspond with the correct object");
		assertEquals("College of B: 20", backend.findCollegesByTuitionRates(20), "The tuition does not correspond with the correct object");
		assertEquals("", backend.findCollegesByTuitionRates(300), "When tuition cannot be found in RBT, the return is not a blank string");
		assertEquals("", backend.findCollegesByTuitionRates(1000), "When tuition cannot be found in RBT, the return is not a blank string");
		assertEquals("University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3", backend.findCollegesByTuitionRatesRange(2, 3), "The result does not correspond with the correct tuition range");
		assertEquals("University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4", backend.findCollegesByTuitionRatesRange(2, 4), "The result does not correspond with the correct tuition range");
		assertEquals("University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5", backend.findCollegesByTuitionRatesRange(2, 5), "The result does not correspond with the correct tuition range");
		assertEquals("University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByTuitionRatesRange(2, 6), "The result does not correspond with the correct tuition range");
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6\n"
				+ "University of Washington: 7", backend.findCollegesByTuitionRatesRange(1, 8), "The result does not correspond with the correct tuition range");
		assertEquals("College of A: 10", backend.findCollegesByTuitionRatesRange(10, 15), "The result does not correspond with the correct tuition range");
		assertEquals("College of A: 10", backend.findCollegesByTuitionRatesRange(9,11), "The result does not correspond with the correct tuition range");
		assertEquals("College of B: 20", backend.findCollegesByTuitionRatesRange(15, 25), "The result does not correspond with the correct tuition range");
		assertEquals("", backend.findCollegesByTuitionRatesRange(11, 15), "The result does not correspond with the correct tuition range");
		assertEquals("", backend.findCollegesByTuitionRatesRange(19, 19), "The result does not correspond with the correct tuition range");
	}

	/**
	 * Test removeOneCollege method of the Backend.
	 * This method make use the already implemented data called collegeNamePlaceholderArray and collegePlaceholderArray
	 */
	@Test
	public void BackendDeveloperTest4() {
		assertEquals(true,backend.removeOneCollege("University of Wisconsin - Madison")); 
		assertEquals(null, collegeListsByName.collegePlaceholderArray[0], "The removal is incorrect"); 
		for (int i = 0; i < 10; ++i) {
			assertNotEquals("University of Wisconsin - Madison", collegeListsByName.collegeNamePlaceholderArray[i], "The removal is incorrect"); 
			assertNotEquals("University of Wisconsin - Madison", collegeListsByTuition.collegeNamePlaceholderArray[i], "The removal is incorrect"); 
		}
		assertNotEquals(null, collegeListsByName.collegePlaceholderArray[4]);
		assertEquals(0, collegeListsByName.hasRemove);
		assertEquals(1, collegeListsByTuition.hasRemove);
		
		assertEquals(true, backend.removeOneCollege("University of Wisconsin - Milwaukee"));
		assertEquals(null, collegeListsByName.collegePlaceholderArray[4], "The removal is incorrect"); 
		for (int i = 0; i < 10; ++i) {
			assertNotEquals("University of Wisconsin - Milwaukee", collegeListsByName.collegeNamePlaceholderArray[i], "The removal is incorrect"); 
			assertNotEquals("University of Wisconsin - Milwaukee", collegeListsByTuition.collegeNamePlaceholderArray[i], "The removal is incorrect"); 
			assertNotEquals("University of Wisconsin - Madison", collegeListsByName.collegeNamePlaceholderArray[i], "The removal is incorrect after another removal"); 
			assertNotEquals("University of Wisconsin - Madison", collegeListsByTuition.collegeNamePlaceholderArray[i], "The removal is incorrect after another removal"); 
		}
		assertEquals(0, collegeListsByName.hasRemove);
		assertEquals(2, collegeListsByTuition.hasRemove);
		// hasRemove is supposed to only count the removal operation of tuition tree. 
		
		boolean hasRemove = backend.removeOneCollege("La Crosse");
		assertEquals(false, hasRemove,"The removal returns true when it cannot remove");
		assertEquals(0, collegeListsByName.hasRemove);
		assertEquals(2, collegeListsByTuition.hasRemove);
		
		backend.removeOneCollege("College of A"); 
		backend.removeOneCollege("College of of A"); 
		backend.removeOneCollege("Citizen of A"); 
		int nullName = 0, nullTuition = 0; 
		for (int i = 0; i < 10; ++i) {
			if (collegeListsByName.collegeNamePlaceholderArray[i] == null) ++nullName; 
			if (collegeListsByTuition.collegeNamePlaceholderArray[i] == null) ++nullTuition; 
		}
		assertEquals(3, nullName); 
		assertEquals(3, nullTuition); 
		assertEquals(3, collegeListsByTuition.hasRemove); 
	}
	
	/**
	 * Test findingCollegeByStartingName method of the Backend.
	 * This method make use the already implemented data called collegeNamePlaceholderArray and collegePlaceholderArray
	 */
	@Test
	public void BackendDeveloperTest5() {
		assertEquals("University of Wisconsin - Madison: 0\n"
				+ "University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6\n"
				+ "University of Washington: 7", backend.findCollegesByStartingName("University"), "The search by starting name method does not work");
		
		assertEquals("University of Wisconsin - Madison: 0\n"
				+ "University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3" , backend.findCollegesByStartingName("University of Wisconsin - Ma"), "The search by starting name method does not work");
		
		assertEquals("University of Wisconsin - Madison: 0\n"
				+ "University of Wisconsin - Madagascar: 2"
				 , backend.findCollegesByStartingName("University of Wisconsin - Mad"), "The search by starting name method does not work");
		
		assertEquals(
				"University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Man in black: 3" , backend.findCollegesByStartingName("University of Wisconsin - Man"), "The search by starting name method does not work");
		
		assertEquals("University of Wisconsin - Madison: 0\n"
				+ "University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByStartingName("University of Wi"), "The search by starting name method does not work");
		
		assertEquals(
				 "University of Washington: 7", backend.findCollegesByStartingName("University of Wa"), "The search by starting name method does not work");
		
		assertEquals("", backend.findCollegesByStartingName("University of Wisconsin - La Crosse"), "The search by starting does not return blank on non-existent case");
		assertEquals("", backend.findCollegesByStartingName("Another Random College"), "The search by starting does not return blank on non-existent case");
	}
	
	/**
	 * This method test the correctness of remove() and find() when they work together.
	 * This test is possible since all the find() and remove() use the same data reference
	 */
	@Test
	public void BackendDeveloperTest6() {
		assertEquals(true, backend.removeOneCollege("University of Wisconsin - Madison"));
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByStartingName("University of Wi"), "The search by starting name method does not work");
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Man in black: 3\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByTuitionRatesRange(0, 6), "The search by starting name method does not work");
		try {
			backend.findCollegesByExactName("University of Wisconsin - Madison"); 
			assertEquals(0, 1, "This code doesnt throw a NSEE when it is supposed to or the removal fails to do its job"); 
		}
		catch (NoSuchElementException e) {
			
		}
		assertEquals("", backend.findCollegesByTuitionRates(0));
		
		assertEquals(true, backend.removeOneCollege("University of Wisconsin - Man in black"));
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByStartingName("University of Wi"), "The search by starting name method does not work");
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByTuitionRatesRange(0, 6), "The search by starting name method does not work");
		
		assertEquals(false, backend.removeOneCollege("University of Wisconsin"));
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByStartingName("University of Wi"), "The search by starting name method does not work");
		assertEquals("University of Wisconsin - Manilla: 1\n"
				+ "University of Wisconsin - Madagascar: 2\n"
				+ "University of Wisconsin - Milwaukee: 4\n"
				+ "University of Wisconsin - Millionaire: 5\n"
				+ "University of Winnipeg: 6", backend.findCollegesByTuitionRatesRange(0, 6), "The search by starting name method does not work");
		
		assertEquals(false, backend.removeOneCollege(null));
		assertEquals(true, backend.removeOneCollege("College of A"));
		assertEquals(true, backend.removeOneCollege("College of B")); 
		assertEquals("", backend.findCollegesByTuitionRatesRange(10, 100)); 
	}
	
	/**
	 * This methods runs the app. Used for testing only.
	 */
	private String runApp(String command) {
		TextUITester input = new TextUITester(command); 
		// Use data wrangler's code to load post data
		CollegeReaderInterface collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college"); 
		RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		Scanner scanner = new Scanner(System.in);
		CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		String output = input.checkOutput();
		return output; 
	}
	
	/**
	 * Load data set test.
	 */
	@Test
	public void IntegrationTest1() {
		// Load data
		// 1.1 - Successfully
		TextUITester input = new TextUITester("F\nsmallBD.csv\nQ\n"); 
		// Use data wrangler's code to load post data
		CollegeReaderInterface collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college"); 
		RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		Scanner scanner = new Scanner(System.in);
		CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		String output = input.checkOutput();
		
		assertEquals(3, collegeListsByName.size(), "Integration failed - True data is not loaded correctly to RBT-Name");
		assertEquals(3, collegeListsByTuition.size(), "Integration failed - True data is not loaded correctly to RBT-Tuition");
		assertTrue(output.contains("successfully"));
		
		// 1.2 - Failed because No File Found
		input = new TextUITester("F\nfailedBDBD.csv\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();		
		assertEquals(0, collegeListsByName.size(), "Integration failed - True data is not loaded correctly to RBT-Name");
		assertEquals(0, collegeListsByTuition.size(), "Integration failed - True data is not loaded correctly to RBT-Tuition");
		
		// 1.3 - Real file loaded
		input = new TextUITester("F\nCollege_Data 3.csv\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();		
		assertEquals(777, collegeListsByName.size(), "Integration failed - True data is not loaded correctly to RBT-Name");
		assertEquals(777, collegeListsByTuition.size(), "Integration failed - True data is not loaded correctly to RBT-Tuition");
		
		// 1.4 - Real life with small file 
		input = new TextUITester("F\nCollege_Data 3.csv\nF\nsmallBD.csv\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();		
		assertEquals(777, collegeListsByName.size(), "Integration failed - True data is not loaded correctly to RBT-Name");
		assertEquals(777, collegeListsByTuition.size(), "Integration failed - True data is not loaded correctly to RBT-Tuition");
	}
	
	/**
	 * Test search for a college through college name, keyword, tuition range, individually
	 */
	@Test
	public void IntegrationTest2() {
		// 1 - Remove
		// 1-1 - Small file
		TextUITester input = new TextUITester("F\nsmallBD.csv\nR\nAdelphi University\nQ\n"); 
		// Use data wrangler's code to load post data
		CollegeReaderInterface collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college"); 
		RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		Scanner scanner = new Scanner(System.in);
		CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		String output = input.checkOutput();
		
		assertEquals(2, collegeListsByName.size(), "Integration failed - RBT-Name has no removal");
		assertEquals(2, collegeListsByTuition.size(), "Integration failed - RBT-Tuition has no removal");
		
		// 1-2 Big file
		input = new TextUITester("F\nCollege_Data 3.csv\nR\nAdelphi University\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		
		assertEquals(776, collegeListsByName.size(), "Integration failed - RBT-Name has no removal");
		assertEquals(776, collegeListsByTuition.size(), "Integration failed - RBT-Tuition has no removal");
		
		// 1-3 Small file with an invalid removal
		input = new TextUITester("F\nsmallBD.csv\nR\nAdelphi University\nR\nNot a college\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		
		assertEquals(2, collegeListsByName.size(), "Integration failed - RBT-Name removal failed");
		assertEquals(2, collegeListsByTuition.size(), "Integration failed - RBT-Tuition removal failed");
		
		// 1-4 Small file with a repeated removal
		input = new TextUITester("F\nsmallBD.csv\nR\nAdelphi University\nR\nAdelphi University\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		
		assertEquals(2, collegeListsByName.size(), "Integration failed - RBT-Name removal failed");
		assertEquals(2, collegeListsByTuition.size(), "Integration failed - RBT-Tuition removal failed");
		
		// 2 - Search through college name
		input = new TextUITester("F\nsmallBD.csv\nN\nAdelphi University\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Adelphi University"}, extractOutput(output), "Find failed");
		assertTrue(output.contains("College: "));
		
		input = new TextUITester("F\nsmallBD.csv\nN\nAbilene Christian University\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Abilene Christian University"}, extractOutput(output), "Find failed"); 
		assertTrue(output.contains("College: "));
		
		input = new TextUITester("F\nsmallBD.csv\nN\nAdrian College\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Adrian College"}, extractOutput(output), "Find failed"); 
		assertTrue(output.contains("College: "));
		
		// 2-2 Failed search
		input = new TextUITester("F\nsmallBD.csv\nN\nUniversity of Wisconsin - Madison\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {}, extractOutput(output), "Find failed");
		
		// 3 - Search by keywords
		input = new TextUITester("F\nCollege_Data 3.csv\nK\nUniversity of Wisconsin at\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"University of Wisconsin at Green Bay", "University of Wisconsin at Madison", 
				"University of Wisconsin at Milwaukee"}, extractOutput(output), "Keyword search failed");
		
		input = new TextUITester("F\nCollege_Data 3.csv\nK\nWashington\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Washington College", "Washington State University", "Washington University", 
				"Washington and Jefferson College", "Washington and Lee University"}, extractOutput(output), "Keyword search failed");
		
		// 4 - Test tuition and tuition range
		input = new TextUITester("F\nCollege_Data 3.csv\nT\n0\n17926\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Barnard College"}, extractOutput(output), "Exact tuition search failed");
		
		input = new TextUITester("F\nCollege_Data 3.csv\nT\n0\n6550\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertEquals(13, extractOutput(output).length, "Exact tuition search failed");
		
		input = new TextUITester("F\nCollege_Data 3.csv\nT\n1\n19950\n100000\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Reed College", "Gettysburg College", "Massachusetts Institute of Technology", "Bennington College"}, extractOutput(output), "Tuition range search failed");
		
		input = new TextUITester("F\nCollege_Data 3.csv\nT\n1\n0\n4299\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertEquals(20, extractOutput(output).length, "Tuition range search failed");
	}
	
	/**
	 * Test for the app works with multiple commands
	 */
	public void IntegrationTest3() {
		TextUITester input = new TextUITester("F\nCollege_Data 3.csv\nR\nUniversity of Wisconsin at Madison\nK\nUniversity of Wisconsin atQ\n"); 
		// Use data wrangler's code to load post data
		CollegeReaderInterface collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college"); 
		RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		Scanner scanner = new Scanner(System.in);
		CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		String output = input.checkOutput();
		
		assertEquals(776, collegeListsByName.size(), "Integration failed - RBT-Name has no removal");
		assertEquals(776, collegeListsByTuition.size(), "Integration failed - RBT-Tuition has no removal");
		assertArrayEquals(new String[] {"University of Wisconsin at Green Bay", 
		"University of Wisconsin at Milwaukee"}, extractOutput(output), "Keyword search failed");
		
		input = new TextUITester("F\nCollege_Data 3.csv\nT\n1\n19950\n100000\nR\nGettysburg College\nT\n1\n20000\n100000\nQ\n"); 
		// Use data wrangler's code to load post data
		collegeReader = new CollegeReaderDW();
		// Use algorithm engineer's code to store and search for data
		collegeListsByName = new RBTCollegeAE("college"); 
		collegeListsByTuition = new RBTCollegeAE("tuition"); 
		// Use the backend developer's code to manage all app specific processing
		backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, 
				collegeReader);
		// Use the frontend developer's code to drive the text-base user interface
		scanner = new Scanner(System.in);
		frontend = new CSearchFrontend(scanner, backend);
		frontend.runCommandLoop();	
		output = input.checkOutput();
		assertArrayEquals(new String[] {"Reed College", "Gettysburg College", "Massachusetts Institute of Technology", "Bennington College", "Massachusetts Institute of Technology", "Bennington College"}, extractOutput(output), "Tuition range search failed");
	}
	
	private String[] extractOutput(String toExtract) {
		String[] extractedResult;
		ArrayList<String> workingArray = new ArrayList<String>();
		int firstIndex = -1, secondIndex = -1; 
		while (true) {
			String toAdd = "";
			firstIndex = toExtract.indexOf("College: ", secondIndex + 1); 
			secondIndex = toExtract.indexOf("\n", firstIndex); 
			if (firstIndex >= toExtract.length() || secondIndex >= toExtract.length() || firstIndex < 0 || secondIndex < 0) {
				break; 
			}
			for (int j = firstIndex + 9; j < secondIndex; ++j) {
				toAdd = toAdd + toExtract.charAt(j); 
			}
			workingArray.add(toAdd); 
		}
		extractedResult = new String[workingArray.size()] ;
		for (int i = 0; i < workingArray.size(); ++i) {
			extractedResult[i] = workingArray.get(i); 
		}

		return extractedResult; 
	}
	
	/**
	 * This method tests and reviews the insert(), remove(), and all the methods that change and checks the tree
	 * size of the RBTCollege class implemented by AE.  
	 */
	@Test
	public void CodeReviewOfAlgorithmEngineer1() {
		assertEquals(0, collegeListsByNameReview.size(), "Empty RBT does not return 0 or the newly initialized tree is not empty"); 
		assertEquals(0, collegeListsByTuitionReview.size(), "Empty RBT does not return 0 or the newly initialized tree is not empty"); 
		assertTrue(collegeListsByNameReview.isEmpty(), "The newly initialized tree is not empty"); 
		assertTrue(collegeListsByTuitionReview.isEmpty(), "The newly initialized tree is not empty");
		
		CollegeDW data1 = new CollegeDW("A", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data2 = new CollegeDW("B", "Yes", 0, 0, 0, 0, 0, 0, 0, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data3 = new CollegeDW("C", "Yes", 0, 0, 0, 0, 0, 0, 0, 102, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data4 = new CollegeDW("D", "Yes", 0, 0, 0, 0, 0, 0, 0, 105, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data5 = new CollegeDW("E", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data6 = new CollegeDW("F", "Yes", 0, 0, 0, 0, 0, 0, 0, 108, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		collegeListsByNameReview.insert(data1.deepCopy("college")); 
		assertEquals(1, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"A"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.insert(data2.deepCopy("college")); 
		assertEquals(2, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed");
		assertArrayEquals(new String[] {"A", "B"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A", "B"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.insert(data3.deepCopy("college")); 
		assertEquals(3, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"B", "A", "C"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A", "B", "C"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.insert(data4.deepCopy("college")); 
		assertEquals(4, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"B", "A", "C", "D"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A", "B", "C", "D"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.insert(data5.deepCopy("college")); 
		assertEquals(5, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"B", "A", "D", "C", "E"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A", "B", "C", "D", "E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.insert(data6.deepCopy("college")); 
		assertEquals(6, collegeListsByNameReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"A", "B", "C", "D", "E", "F"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong priority");
		assertArrayEquals(new String[] {"B", "A", "D", "C", "E", "F"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		
		collegeListsByNameReview.remove(data6.deepCopy("college")); 
		assertEquals(5, collegeListsByNameReview.size(), "Removal failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"B", "A", "D", "C", "E"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A", "B", "C", "D", "E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong prioirity");

		
		collegeListsByNameReview.remove(data1.deepCopy("college")); 
		assertEquals(4, collegeListsByNameReview.size(), "Removal failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"D", "B", "E", "C"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "D", "E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong prioirity");
		
		collegeListsByNameReview.remove(data4.deepCopy("college")); 
		assertEquals(3, collegeListsByNameReview.size(), "Removal failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"C", "B", "E"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong prioirity");
		
		collegeListsByNameReview.remove(data2.deepCopy("college")); 
		assertEquals(2, collegeListsByNameReview.size(), "Removal failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"C", "E"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"C", "E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong prioirity");
		
		collegeListsByNameReview.remove(data3.deepCopy("college")); 
		assertEquals(1, collegeListsByNameReview.size(), "Removal failed"); 
		assertFalse(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"E"}, extractOutput(collegeListsByNameReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"E"}, extractOutput(collegeListsByNameReview.toInOrderString()), "Wrong prioirity");
		
		collegeListsByNameReview.remove(data5.deepCopy("college")); 
		assertEquals(0, collegeListsByNameReview.size(), "Removal failed"); 
		assertTrue(collegeListsByNameReview.isEmpty(), "Removal failed"); 
		
		// collegeListsByTuitionReview
		
		collegeListsByTuitionReview.insert(data1.deepCopy("tuition")); 
		assertEquals(1, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"A"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"A"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");
		
		collegeListsByTuitionReview.insert(data2.deepCopy("tuition")); 
		assertEquals(2, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"A", "B"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "A"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.insert(data3.deepCopy("tuition")); 
		assertEquals(3, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"C", "B", "A"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "A"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.insert(data4.deepCopy("tuition")); 
		assertEquals(4, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed");  
		assertArrayEquals(new String[] {"C", "B", "A", "D"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "A", "D"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.insert(data5.deepCopy("tuition")); 
		assertEquals(5, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed"); 
		assertArrayEquals(new String[] {"C", "B", "E", "A", "D"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "A", "E", "D"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.insert(data6.deepCopy("tuition")); 
		assertEquals(6, collegeListsByTuitionReview.size(), "Insertion failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Insertion failed"); 
		
		assertArrayEquals(new String[] {"B", "C", "A", "E", "D", "F"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");
		assertArrayEquals(new String[] {"C", "B", "E", "A", "D", "F"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		
		collegeListsByTuitionReview.remove(data6.deepCopy("tuition")); 
		assertEquals(5, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Removal failed");
		assertArrayEquals(new String[] {"C", "B", "E", "A", "D"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "A", "E", "D"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.remove(data1.deepCopy("tuition")); 
		assertEquals(4, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"C", "B", "E", "D"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "E", "D"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.remove(data4.deepCopy("tuition")); 
		assertEquals(3, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"C", "B", "E"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"B", "C", "E"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");
		
		collegeListsByTuitionReview.remove(data2.deepCopy("tuition")); 
		assertEquals(2, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"C", "E"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"C", "E"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.remove(data3.deepCopy("tuition")); 
		assertEquals(1, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertFalse(collegeListsByTuitionReview.isEmpty(), "Removal failed"); 
		assertArrayEquals(new String[] {"E"}, extractOutput(collegeListsByTuitionReview.toLevelOrderString()), "Wrong RBT order");
		assertArrayEquals(new String[] {"E"}, extractOutput(collegeListsByTuitionReview.toInOrderString()), "Wrong priority");

		collegeListsByTuitionReview.remove(data5.deepCopy("tuition")); 
		assertEquals(0, collegeListsByTuitionReview.size(), "Removal failed"); 
		assertTrue(collegeListsByTuitionReview.isEmpty(), "Removal failed"); 

	}
	
	/**
	 * This method tests and reviews all search methods of the RBTCollege class implemented by AE. 
	 */
	@Test
	public void CodeReviewOfAlgorithmEngineer2() {
		CollegeDW data1 = new CollegeDW("College of A", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data2 = new CollegeDW("College of AB", "Yes", 0, 0, 0, 0, 0, 0, 0, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data3 = new CollegeDW("College of AC", "Yes", 0, 0, 0, 0, 0, 0, 0, 102, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data4 = new CollegeDW("D College of Engineering", "Yes", 0, 0, 0, 0, 0, 0, 0, 105, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data5 = new CollegeDW("D College of E", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data6 = new CollegeDW("F", "Yes", 0, 0, 0, 0, 0, 0, 0, 108, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		CollegeDW data7 = new CollegeDW("A", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data8 = new CollegeDW("B", "Yes", 0, 0, 0, 0, 0, 0, 0, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data9 = new CollegeDW("College ", "Yes", 0, 0, 0, 0, 0, 0, 0, 102, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data10 = new CollegeDW("D", "Yes", 0, 0, 0, 0, 0, 0, 0, 105, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data11 = new CollegeDW("E", "Yes", 0, 0, 0, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 0, 0, 0); 
		CollegeDW data12 = new CollegeDW("F College", "Yes", 0, 0, 0, 0, 0, 0, 0, 108, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		collegeListsByNameReview.insert(data1.deepCopy("college"));
		collegeListsByNameReview.insert(data2.deepCopy("college"));
		collegeListsByNameReview.insert(data3.deepCopy("college"));
		
		assertEquals(data1, collegeListsByNameReview.search("college", "College of A"));
		assertEquals(data2, collegeListsByNameReview.search("college", "College of AB"));
		assertEquals(data3, collegeListsByNameReview.search("college", "College of AC"));
		assertArrayEquals(new String[] {"College of A"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College of A", "College of A")));
		assertArrayEquals(new String[] {"College of A", "College of AB", "College of AC"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College of A", "College of B")));
		assertArrayEquals(new String[] {"College of A"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College of A", "College of AAAAAAA")));
		assertArrayEquals(new String[] {"College of AB"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College of AAAA", "College of ABC")));
		assertArrayEquals(new String[] {"College of AB", "College of AC"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College of AAAB", "College of B")));


		collegeListsByNameReview.insert(data4.deepCopy("college"));
		collegeListsByNameReview.insert(data5.deepCopy("college"));
		collegeListsByNameReview.insert(data6.deepCopy("college"));
		collegeListsByNameReview.insert(data7.deepCopy("college"));
		collegeListsByNameReview.insert(data8.deepCopy("college"));
		collegeListsByNameReview.insert(data9.deepCopy("college"));
		collegeListsByNameReview.insert(data10.deepCopy("college"));
		collegeListsByNameReview.insert(data11.deepCopy("college"));
		collegeListsByNameReview.insert(data12.deepCopy("college"));
		assertEquals(data10, collegeListsByNameReview.search("college", "D"));
		assertEquals(data9, collegeListsByNameReview.search("college", "College "));
		assertEquals(data6, collegeListsByNameReview.search("college", "F"));
		assertArrayEquals(new String[] {"College ", "College of A", "College of AB", "College of AC"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College", "College of Z")));
		assertArrayEquals(new String[] {"D", "D College of E", "D College of Engineering", "E"}, extractOutput(collegeListsByNameReview.searchInRange("college", "D", "E")));
		assertArrayEquals(new String[] {"A", "B"}, extractOutput(collegeListsByNameReview.searchInRange("college", "A", "B")));
		
		collegeListsByNameReview.remove(data1.deepCopy("college")); 
		collegeListsByNameReview.remove(data10.deepCopy("college")); 

		assertArrayEquals(new String[] {"College ", "College of AB", "College of AC"}, extractOutput(collegeListsByNameReview.searchInRange("college", "College", "College of Z")));
		assertArrayEquals(new String[] {"D College of E", "D College of Engineering", "E"}, extractOutput(collegeListsByNameReview.searchInRange("college", "D", "E")));

		collegeListsByTuitionReview.insert(data1.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data2.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data3.deepCopy("tuition"));
		
		assertArrayEquals(new String[] {"College of A"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 104, 104)));
		assertArrayEquals(new String[] {"College of AB"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 101, 101)));
		assertArrayEquals(new String[] {"College of AC"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 102, 102)));
		assertArrayEquals(new String[] {"College of AB", "College of AC", "College of A"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 100, 200)));

		
		collegeListsByTuitionReview.insert(data4.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data5.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data6.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data7.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data8.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data9.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data10.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data11.deepCopy("tuition"));
		collegeListsByTuitionReview.insert(data12.deepCopy("tuition"));
		assertArrayEquals(new String[] {"A", "College of A", "D College of E", "E"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 104, 104)));
		assertArrayEquals(new String[] {"College ", "College of AC"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 102, 102)));
		
		collegeListsByTuitionReview.remove(data1.deepCopy("tuition")); 
		collegeListsByTuitionReview.remove(data2.deepCopy("tuition")); 
		collegeListsByTuitionReview.remove(data3.deepCopy("tuition"));
		collegeListsByTuitionReview.remove(data4.deepCopy("tuition")); 
		
		assertArrayEquals(new String[] {"College ", "A", "D College of E", "E", "D"}, extractOutput(collegeListsByTuitionReview.searchInRange("tuition", 102, 105)));
	}
}
