import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.junit.*;

import static org.junit.Assert.*;

public class DWTests {
  List<CollegeInterface> colleges;

  /**
   * This method sets up the testing environment by instantiating a CollegeReaderDW object and
   * reading from a CSV file. Tests to make sure the CollegeReaderDW is working properly as well.
   */
  @Before
  public void setupAndTestCollegeReader() {
    CollegeReaderInterface collegeReader = new CollegeReaderDW();
    String filename = "College_Data 3.csv";
    colleges = null;
    try {
      colleges = collegeReader.readCollegesFromFile(filename);
    } catch (FileNotFoundException e) {
      System.out.println("file not found: " + filename);
    }
  }

  /**
   * This method tests the CollegeDW constructor by checking that each attribute of a CollegeDW
   * object is initialized correctly.
   */
  @Test
  public void testConstructor() {
    assertEquals("Abilene Christian University", colleges.get(0).getCollege().replaceAll("\"", ""));
    assertEquals("Yes", colleges.get(0).isPrivate().replaceAll("\"", ""));
    assertEquals(1660, colleges.get(0).getNumApps());
    assertEquals(1232, colleges.get(0).getNumAccepted());
    assertEquals(721, colleges.get(0).getNumEnrolled());
    assertEquals(23, colleges.get(0).getTop10Pct());
    assertEquals(52, colleges.get(0).getTop25Pct());
    assertEquals(2885, colleges.get(0).getNumFullUndergrads());
    assertEquals(537, colleges.get(0).getNumPartUndergrads());
    assertEquals(7440, colleges.get(0).getTuition());
    assertEquals(3300, colleges.get(0).getBoardCost());
    assertEquals(450, colleges.get(0).getBooksCost());
    assertEquals(2200, colleges.get(0).getSpending());
    assertEquals(70, colleges.get(0).getPhDPct());
    assertEquals(78, colleges.get(0).getTerminalPct());
    assertEquals(18.1, colleges.get(0).getSFRatio(), 0.0);
    assertEquals(12, colleges.get(0).getAlumniDonationPct());
    assertEquals(7041, colleges.get(0).getExpenditurePerStudent());
    assertEquals(60, colleges.get(0).getGraduationRate());
  }

  /**
   * This method tests the toString() method of the CollegeDW class by comparing the output of each
   * CollegeDW object in the colleges List with the expected output.
   */

  @Test
  public void testToString() {
    for (CollegeInterface college : colleges) {
      assertEquals(college.toString(), "College: " + college.getCollege()
          .replaceAll("\"", "") + "\n" + "Private school: " + college.isPrivate().replaceAll("\"",
          "") + "\n" + "Number of applications received: " + college.getNumApps() + "\n" + "Number of applications accepted: " + college.getNumAccepted() + "\n" + "Number of new students enrolled: " + college.getNumEnrolled() + "\n" + "Percentage of new students from top 10% of H.S. class: " + college.getTop10Pct() + "\n" + "Percentage of new students from top 25% of H.S. class: " + college.getTop25Pct() + "\n" + "Number of full-time undergraduates: " + college.getNumFullUndergrads() + "\n" + "Number of part-time undergraduates: " + college.getNumPartUndergrads() + "\n" + "Out-of-state tuition: $" + college.getTuition() + "\n" + "Room and board costs: $" + college.getBoardCost() + "\n" + "Estimated books costs: $" + college.getBooksCost() + "\n" + "Estimated personal spending per student: $" + college.getSpending() + "\n" + "Percentage of faculty with Ph.D.'s: " + college.getPhDPct() + "\n" + "Percentage of faculty with terminal degree: " + college.getTerminalPct() + "\n" + "Student to faculty ratio: " + college.getSFRatio() + "\n" + "Alumni donation percentage: " + college.getAlumniDonationPct() + "\n" + "Instructional expenditure per student: $" + college.getExpenditurePerStudent() + "\n" + "Graduation rate: " + college.getGraduationRate() + "%" + "\n" + "\n");
    }
  }

  /**
   * Test for content equality of CollegeDW objects. Compares whether two CollegeDW objects are
   * equal in terms of their contents.
   */
  @Test
  public void testEquals() {
    CollegeInterface college1 = colleges.get(0);
    CollegeInterface college2 = colleges.get(1);

    // Test for object equality
    assertEquals(college1, college1);
    assertNotEquals(null, college1);
    assertNotEquals(college1, new Object());

    // Test for content equality
    assertNotEquals(college1, college2);
    CollegeInterface college1Copy =
        new CollegeDW(college1.getCollege(), college1.isPrivate(), college1.getNumApps(),
            college1.getNumAccepted(), college1.getNumEnrolled(), college1.getTop10Pct(),
            college1.getTop25Pct(), college1.getNumFullUndergrads(),
            college1.getNumPartUndergrads(), college1.getTuition(), college1.getBoardCost(),
            college1.getBooksCost(), college1.getSpending(), college1.getPhDPct(),
            college1.getTerminalPct(), college1.getSFRatio(), college1.getAlumniDonationPct(),
            college1.getExpenditurePerStudent(), college1.getGraduationRate());
    assertEquals(college1, college1Copy);
  }

  /**
   * Tests the comparison of two CollegeDW objects by name, tuition, and the qualifications of the
   * compareTo() method.
   */
  @Test
  public void testCompareTo() {
    CollegeInterface college1 = colleges.get(0);
    CollegeInterface college2 = colleges.get(1);
    CollegeInterface college3 = colleges.get(2);
    CollegeInterface college4 = colleges.get(8);
    CollegeInterface college5 = colleges.get(13);
    CollegeInterface college6 = colleges.get(102);
    CollegeInterface college7 = colleges.get(103);
    college1.setFilter("college");
    college3.setFilter("college");
    college4.setFilter("college");
    college5.setFilter("college");
    college6.setFilter("college");
    college7.setFilter("college");
    assertTrue(((CollegeDW) college1).compareByName(college2) < 0);
    assertTrue(((CollegeDW) college2).compareByTuition(college1) > 0);
    assertEquals(-1, college1.compareTo(college3));
    assertEquals(-1, college4.compareTo(college5));
    assertEquals(-1, college6.compareTo(college7));

  }

  /**
   * Tests the getter methods of the CollegeDW class.
   */
  @Test
  public void testGetters() {
    CollegeInterface college = colleges.get(0);
    assertEquals("Abilene Christian University", colleges.get(0).getCollege().replaceAll("\"", ""));
    assertEquals("Yes", college.isPrivate().replaceAll("\"", ""));
    assertEquals(1660, college.getNumApps());
    assertEquals(1232, college.getNumAccepted());
    assertEquals(721, college.getNumEnrolled());
    assertEquals(23, college.getTop10Pct());
    assertEquals(52, college.getTop25Pct());
    assertEquals(2885, college.getNumFullUndergrads());
    assertEquals(537, college.getNumPartUndergrads());
    assertEquals(7440, college.getTuition());
    assertEquals(3300, college.getBoardCost());
    assertEquals(450, college.getBooksCost());
    assertEquals(2200, college.getSpending());
    assertEquals(70, college.getPhDPct());
    assertEquals(78, college.getTerminalPct());
    assertEquals(18.1, college.getSFRatio(), 0.0);
    assertEquals(12, college.getAlumniDonationPct());
    assertEquals(7041, college.getExpenditurePerStudent());
    assertEquals(60, college.getGraduationRate());
  }

  /**
   * Tests the output of searching for a tuition range
   */
  @Test
  public void codeReviewOfFrontend1() {
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CSearchBackendInterface backend =
        new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
    TextUITester tester = new TextUITester("F\nCollege_Data 3.csv\nT\n1\n20000\n25000\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), backend);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    System.out.println(output);
    assertEquals(true, output.contains(
        "Colleges with tuition in the range $20000 - $25000:\n" + "College: Massachusetts Institute of Technology\n" + "Private school: Yes\n" + "Number of applications received: 6411\n" + "Number of applications accepted: 2140\n" + "Number of new students enrolled: 1078\n" + "Percentage of new students from top 10% of H.S. class: 96\n" + "Percentage of new students from top 25% of H.S. class: 99\n" + "Number of full-time undergraduates: 4481\n" + "Number of part-time undergraduates: 28\n" + "Out-of-state tuition: $20100\n" + "Room and board costs: $5975\n" + "Estimated books costs: $725\n" + "Estimated personal spending per student: $1600\n" + "Percentage of faculty with Ph.D.'s: 99\n" + "Percentage of faculty with terminal degree: 99\n" + "Student to faculty ratio: 10.1\n" + "Alumni donation percentage: 35\n" + "Instructional expenditure per student: $33541\n" + "Graduation rate: 94%\n" + "\n" + "\n" + "College: Bennington College\n" + "Private school: Yes\n" + "Number of applications received: 519\n" + "Number of applications accepted: 327\n" + "Number of new students enrolled: 114\n" + "Percentage of new students from top 10% of H.S. class: 25\n" + "Percentage of new students from top 25% of H.S. class: 53\n" + "Number of full-time undergraduates: 457\n" + "Number of part-time undergraduates: 2\n" + "Out-of-state tuition: $21700\n" + "Room and board costs: $4100\n" + "Estimated books costs: $600\n" + "Estimated personal spending per student: $500\n" + "Percentage of faculty with Ph.D.'s: 35\n" + "Percentage of faculty with terminal degree: 59\n" + "Student to faculty ratio: 10.1\n" + "Alumni donation percentage: 33\n" + "Instructional expenditure per student: $16364\n" + "Graduation rate: 55%"));
  }

  /**
   * Tests the output of searching for stats about the University of Wisconsin at Madison
   */
  @Test
  public void codeReviewOfFrontend2() {
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CSearchBackendInterface backend =
        new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
    TextUITester tester =
        new TextUITester("F\nCollege_Data 3.csv\nN\nUniversity of Wisconsin at Madison\nQ\n");
    CSearchFrontend frontend = new CSearchFrontend(new Scanner(System.in), backend);
    frontend.runCommandLoop();
    String output = tester.checkOutput();
    System.out.println(output);
    assertEquals(true, output.contains(
        "College: University of Wisconsin at Madison\n" + "Private school: No\n" + "Number of applications received: 14901\n" + "Number of applications accepted: 10932\n" + "Number of new students enrolled: 4631\n" + "Percentage of new students from top 10% of H.S. class: 36\n" + "Percentage of new students from top 25% of H.S. class: 80\n" + "Number of full-time undergraduates: 23945\n" + "Number of part-time undergraduates: 2200\n" + "Out-of-state tuition: $9096\n" + "Room and board costs: $4290\n" + "Estimated books costs: $535\n" + "Estimated personal spending per student: $1545\n" + "Percentage of faculty with Ph.D.'s: 93\n" + "Percentage of faculty with terminal degree: 96\n" + "Student to faculty ratio: 11.5\n" + "Alumni donation percentage: 20\n" + "Instructional expenditure per student: $11006\n" + "Graduation rate: 72%"));
  }

  /**
   * Tests the RBT insert, size, contains, isEmpty, and remove methods
   */
  @Test
  public void integrationTestOfRBT() {
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CollegeDW madison =
        new CollegeDW("University of Wisconsin-Madison", "Yes", 100, 80, 10000, 23, 50, 4500, 3000,
            3000, 323, 100, 850, 21, 12, 53.0, 9, 32, 71);
    madison.setFilter("college");
    CSearchBackendInterface backend =
        new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
    assertEquals(collegeListsByName.size(), 0);
    collegeListsByName.insert(madison);
    assertEquals(collegeListsByName.size(), 1);
    assertTrue(collegeListsByName.contains(madison));
    collegeListsByName.remove(madison);
    assertEquals(collegeListsByName.size(), 0);
    assertTrue(collegeListsByName.isEmpty());
  }

  @Test
  public void integrationTestOfBackend() {
    // Instantiate the necessary objects
    CollegeReaderDW collegeReader = new CollegeReaderDW();
    RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
    RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
    CollegeDW madison =
        new CollegeDW("University of Wisconsin-Madison", "Yes", 100, 80, 10000, 23, 50, 4500, 3000,
            3000, 323, 100, 850, 21, 12, 53.0, 9, 32, 71);
    madison.setFilter("college");
    CSearchBackendInterface backend =
        new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);

    try {
      backend.loadData("wrong_file.txt");
    } catch (FileNotFoundException e) {
    }
    try {
    backend.loadData("College_Data 3.csv");
    } catch (FileNotFoundException e) {
      fail();
    }
    String startingWithCal = backend.findCollegesByStartingName("California");
    assertTrue(startingWithCal.contains("California Lutheran University"));
    assertTrue(startingWithCal.contains("California Polytechnic-San Luis"));
    assertTrue(startingWithCal.contains("California State University at Fresno"));

    String tuition10000 = backend.findCollegesByTuitionRates(10000);
    assertTrue(tuition10000.contains("Immaculata College"));
    assertTrue(tuition10000.contains("Warren Wilson College"));

    String tuitionRange3000035000 = backend.findCollegesByTuitionRatesRange(20000, 21000);
    System.out.println(tuitionRange3000035000);
    assertTrue(tuitionRange3000035000.contains("Massachusetts Institute of Technology"));

  }

}



