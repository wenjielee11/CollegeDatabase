// --== CS400 Spring 2023 File Header Information ==--
// Name: Noah Boeder
// Email: nboeder@wisc.edu
// Team: BK
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: none

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * This class tests the functionality of methods in the CSearchFrontend class
 * 
 * @author Noah Boeder
 */
public class FrontendDeveloperTests {

  /**
   * Tests the correctness of CSearchFrontend's runCommandLoop and mainMenuPrompt
   */
  @Test
  public void frontendTest1() {
    // run the program and simulate user input
    TextUITester tester = new TextUITester("W\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), new CSearchBackendFD());
    frontend.runCommandLoop();
    // check the output
    String output = tester.checkOutput();
    assertEquals(true,
        output.contains(
            "Choose a command from the list below:\n" + "    Load college data from a [F]ile\n"
                + "    Search for a college by [N]ame\n"
                + "    List all colleges starting with a given [K]eyword\n"
                + "    List all coleges within a [T]uition range\n"
                + "    [R]emove a college from the database\n" + "    [Q]uit program\n"
                + "Choose a command: "),
        "Command prompt was not correctly given");
    assertEquals(true,
        output.contains("Error: Invalid command given."
            + " Please type one of the letters within []s to choose a command.\n"),
        "Error message was not correctly given for bad input");
    assertEquals(true, output.contains("Thank you for using CSearch!"),
        "Exit message was not given");
  }

  /**
   * Tests the correctness of CSearchFrontend's loadFileCommand
   */
  @Test
  public void frontendTest2() {
    // run the program and simulate user input
    TextUITester tester =
        new TextUITester("F\ncolleges.txt\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), new CSearchBackendFD());
    frontend.runCommandLoop();
    // check the output
    String output = tester.checkOutput();
    assertEquals(true, output.contains("Enter the name of the file to load:"),
        "loadFileCommand prompts were not properly printed");
    assertEquals(true, output.contains("Your file was successfully loaded!"),
        "loadFileCommand prompts were not properly printed");
  }

  /**
   * Tests the correctness of CSearchFrontend's searchNameCommand and searchKeywordCommand
   */
  @Test
  public void frontendTest3() {
    // run the program and simulate user input
    TextUITester tester =
        new TextUITester("N\nUniversity of Wisconsin-Madison\nK\nUniversity\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), new CSearchBackendFD());
    frontend.runCommandLoop();
    // check the output
    String output = tester.checkOutput();
    assertEquals(true, output.contains("College found! Here is some information about it:"),
        "searchNameCommand did not provide the correct output");
    assertEquals(true,
        output.contains(
            "Name - University of Wisconsin-Madison\nAverage GPA - 3.4\nAdmission rate - 40%"),
        "searchNameCommand did not provide the correct output");
    assertEquals(true,
        output.contains("Here are all colleges starting with the keyword \"University\":"),
        "searchKeywordCommand did not provide the correct output");
    assertEquals(true, output.contains("University of Wisconsin-Madison\nUniversity of Iowa"),
        "searchKeywordCommand did not provide the correct output");
  }

  /**
   * Tests the correctness of CSearchFrontend's searchTuitionCommand
   */
  @Test
  public void frontendTest4() {
    // run the program and simulate user input
    TextUITester tester = new TextUITester("T\n0\n72k\nT\n0\n66000\nT\n1\n64000\n124000\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), new CSearchBackendFD());
    frontend.runCommandLoop();
    // check the output
    String output = tester.checkOutput();
    assertEquals(true, output.contains("Enter the exact tuition to be searched:"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains("Error: Invalid tuition given"));
    assertEquals(true, output.contains("Colleges with $66000 tuition:"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains("Michigan University"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains(
        "Enter the lower then upper end of the tuition range being searched (separated by returns):"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains("Colleges with tuition in the range $64000 - $124000:"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains("Lafayette University"),
        "searchTuitionCommand messages were not correctly printed");
    assertEquals(true, output.contains("Princeton University"),
        "searchTuitionCommand messages were not correctly printed");
  }

  /**
   * Tests the correctness of CSearchFrontend's removeCollegeCommand
   */
  @Test
  public void frontendTest5() {
    // run the program and simulate user input
    TextUITester tester = new TextUITester("R\nUniversity of Iowa\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), new CSearchBackendFD());
    frontend.runCommandLoop();
    // check the output
    String output = tester.checkOutput();
    assertEquals(true, output.contains("Enter the name of the college you want to remove: "),
        "removeTest did not provide the correct output");
    assertEquals(true,
        output.contains("University of Iowa was successfully removed from the list of colleges\n"),
        "removeTest did not provide the correct output");
  }

  /**
   * Tests interaction between CSearchFrontend and CSearchBackendBD
   */
  @Test
  public void integrationTest1() {
    // run the program and simulate user input
    TextUITester tester = new TextUITester("F\nCollege_Data 3.csv\nN\nAlma College\nT\n0\n7440\nK\nUniversity\nQ\n");
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
    Scanner scanner = new Scanner(System.in);
    CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
    frontend.runCommandLoop();
    // compare the frontend and backend output
    String output = tester.checkOutput();
    assertEquals(true, output.contains(backend.findCollegesByExactName("Alma College")),
            "CSearchFrontend did not provide the same results as CSearchBackendBD's findCollegesByExactName");
    assertEquals(true, output.contains(backend.findCollegesByTuitionRates(7440)),
            "CSearchFrontend did not provide the same results as CSearchBackendBD's findCollegesByTuitionRates");
    assertEquals(true, output.contains(backend.findCollegesByStartingName("University")),
            "CSearchFrontend did not provide the same results as CSearchBackendBD's findCollegesByTuitionRates");
  }

  /**
   * Tests correctness of RBT methods on a tree of CollegeDW objects
   */
  @Test
  public void integrationTest2() throws FileNotFoundException {
    // use backend methods to construct red-black tree
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
    backend.loadData("College_Data 3.csv");
    // test red-black tree methods
    CollegeInterface college = new CollegeDW("Name", "Yes", 0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    college.setFilter("college");
    assertEquals(true, collegeListsByName.insert(college), "Red-black trees did not function properly given a tree of CollegeDW objects");
    assertEquals(college, collegeListsByName.search("college", "Name"), "Red-black trees did not function properly given a tree of CollegeDW objects");
    assertEquals(true, collegeListsByName.remove(college), "Red-black trees did not function properly given a tree of CollegeDW objects");
    assertEquals(false, collegeListsByName.contains(college), "Red-black trees did not function properly given a tree of CollegeDW objects");
  }

  /**
   * Tests the correctness of CollegeReaderDW's readCollegesFromFile along with CollegeDW's constructor, set, and get
   * methods
   */
  @Test
  public void codeReviewofDataWrangler1() throws FileNotFoundException {
    // get a list of CollegeDW objects using CollegeReaderDW's readCollegesFromFile
    CollegeReaderInterface reader = new CollegeReaderDW();
    List<CollegeInterface> colleges = reader.readCollegesFromFile("College_Data 3.csv");
    // check field values
    assertEquals("Abilene Christian University", colleges.get(0).getCollege().replaceAll("\"", ""), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals("Yes", colleges.get(0).isPrivate().replaceAll("\"", ""), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(1660, colleges.get(0).getNumApps(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(1232, colleges.get(0).getNumAccepted(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(721, colleges.get(0).getNumEnrolled(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(23, colleges.get(0).getTop10Pct(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(52, colleges.get(0).getTop25Pct(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(2885, colleges.get(0).getNumFullUndergrads(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(537, colleges.get(0).getNumPartUndergrads(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(7440, colleges.get(0).getTuition(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(3300, colleges.get(0).getBoardCost(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(450, colleges.get(0).getBooksCost(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(2200, colleges.get(0).getSpending(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(70, colleges.get(0).getPhDPct(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(78, colleges.get(0).getTerminalPct(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(18.1, colleges.get(0).getSFRatio(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(12, colleges.get(0).getAlumniDonationPct(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(7041, colleges.get(0).getExpenditurePerStudent(), "Data from the given file was not correctly stored in CollegeDW objects");
    assertEquals(60, colleges.get(0).getGraduationRate(), "Data from the given file was not correctly stored in CollegeDW objects");
    // test setting and getting a filter
    colleges.get(0).setFilter("tuition");
    assertEquals("tuition", colleges.get(0).getFilter(), "CollegeDW's setFilter or getFilter did not function correctly");
    // test CollegeDW's toString
    for (CollegeInterface college : colleges) {
      assertEquals("College: " + college.getCollege().replaceAll("\"", "")
              + "\n" + "Private school: " + college.isPrivate().replaceAll("\"", "") + "\n"
              + "Number of applications received: " + college.getNumApps() + "\n"
              + "Number of applications accepted: " + college.getNumAccepted() + "\n" + "Number of new students enrolled: "
              + college.getNumEnrolled() + "\n" + "Percentage of new students from top 10% of H.S. class: " + college.getTop10Pct()
              + "\n" + "Percentage of new students from top 25% of H.S. class: " + college.getTop25Pct() + "\n"
              + "Number of full-time undergraduates: " + college.getNumFullUndergrads() + "\n" + "Number of part-time undergraduates: "
              + college.getNumPartUndergrads() + "\n" + "Out-of-state tuition: $" + college.getTuition() + "\n"
              + "Room and board costs: $" + college.getBoardCost() + "\n" + "Estimated books costs: $" + college.getBooksCost()
              + "\n" + "Estimated personal spending per student: $" + college.getSpending() + "\n"
              + "Percentage of faculty with Ph.D.'s: " + college.getPhDPct() + "\n" + "Percentage of faculty with terminal degree: "
              + college.getTerminalPct() + "\n" + "Student to faculty ratio: " + college.getSFRatio() + "\n"
              + "Alumni donation percentage: " + college.getAlumniDonationPct() + "\n" + "Instructional expenditure per student: $"
              + college.getExpenditurePerStudent() + "\n" + "Graduation rate: " + college.getGraduationRate() + "%" + "\n" + "\n", college.toString(), "CollegeDW's toString did not function properly");
    }
  }

  /**
   * Tests the correctness of CollegeDW's equals, compareTo, and deepCopy methods
   */
  @Test
  public void codeReviewofDataWrangler2() throws FileNotFoundException {
    // get a list of CollegeDW objects using CollegeReaderDW's readCollegesFromFile
    CollegeReaderInterface reader = new CollegeReaderDW();
    List<CollegeInterface> colleges = reader.readCollegesFromFile("College_Data 3.csv");
    // test CollegeDW's equals
    CollegeInterface college1 = colleges.get(0);
    college1.setFilter("college");
    CollegeInterface college2 = colleges.get(1);
    CollegeDW college1copy = new CollegeDW(college1.getCollege(), college1.isPrivate(), college1.getNumApps(), college1.getNumAccepted(), college1.getNumEnrolled(), college1.getTop10Pct(), college1.getTop25Pct(), college1.getNumFullUndergrads(), college1.getNumPartUndergrads(), college1.getTuition(), college1.getBoardCost(), college1.getBooksCost(), college1.getSpending(), college1.getPhDPct(), college1.getTerminalPct(), college1.getSFRatio(), college1.getAlumniDonationPct(), college1.getExpenditurePerStudent(), college1.getGraduationRate());
    college1copy.setFilter(college1.getFilter());
    assertEquals(true, college1.equals(college1copy), "CollegeDW's equals did not function correctly");
    assertEquals(false, college1.equals(college2), "CollegeDW's equals did not function correctly");
    // test CollegeDW's deepCopy
    assertEquals(college1, college1.deepCopy("college"), "CollegeDW's deepCopy did not function correctly");
    // test CollegeDW's compareTo
    // comparing by name
    assertEquals(-1, college1.compareTo(college2), "CollegeDW's compareTo did not correctly compare college names");
    // comparing by tuition
    college1.setFilter("tuition");
    assertEquals((Integer.valueOf(7440)).compareTo(12280), college1.compareTo(college2), "CollegeDW's compareTo did not correctly compare tuitions");
  }
}
