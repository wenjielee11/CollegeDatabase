import java.util.Comparator;
import java.util.Objects;

/**
 * CollegeDW is a class that implements CollegeInterface. It stores various attributes of a college
 * and provides getter methods for accessing these attributes. It also provides a toString() method
 * for printing the attributes in a readable format.
 */
public class CollegeDW implements CollegeInterface, Comparable<CollegeInterface> {
  public String college;
  public String isPrivate;
  public int numApps;
  public int numAccepted;
  public int numEnrolled;
  public int top10Pct;
  public int top25Pct;
  public int numFullUndergrads;
  public int numPartUndergrads;
  public int tuition;
  public int boardCost;
  public int booksCost;
  public int spending;
  public int phDPct;
  public int terminalPct;
  public double sFratio;
  public int alumniDonationPct;
  public int expenditurePerStudent;
  public int graduationRate;
  public String filter;

  /**
   * Constructor for a College object
   *
   * @param college               name of the college
   * @param isPrivate             yes if college is private, no if public
   * @param numApps               number of applications received
   * @param numAccepted           number of applications accepted
   * @param numEnrolled           number of new students enrolled
   * @param top10Pct              number of students from the top 10% of their high school class
   * @param top25Pct              number of students from the top 25% of their high school class
   * @param numFullUndergrads     number of full time undergraduates
   * @param numPartUndergrads     number of part time undergraduates
   * @param tuition               out of state tuition cost
   * @param boardCost             room and board cost
   * @param booksCost             average books cost
   * @param spending              average student spending
   * @param phDPct                percent of faculty with pHD
   * @param terminalPct           percent of faculty with terminal degree
   * @param sFratio               student faculty ratio
   * @param alumniDonationPct     percent of alumni who make donations
   * @param expenditurePerStudent average expenditure per student
   * @param graduationRate        graduation rate
   */
  public CollegeDW(String college, String isPrivate, int numApps, int numAccepted, int numEnrolled,
      int top10Pct, int top25Pct, int numFullUndergrads, int numPartUndergrads, int tuition,
      int boardCost, int booksCost, int spending, int phDPct, int terminalPct, double sFratio,
      int alumniDonationPct, int expenditurePerStudent, int graduationRate) {
    this.college = college;
    this.isPrivate = isPrivate;
    this.numApps = numApps;
    this.numAccepted = numAccepted;
    this.numEnrolled = numEnrolled;
    this.top10Pct = top10Pct;
    this.top25Pct = top25Pct;
    this.numFullUndergrads = numFullUndergrads;
    this.numPartUndergrads = numPartUndergrads;
    this.tuition = tuition;
    this.boardCost = boardCost;
    this.booksCost = booksCost;
    this.spending = spending;
    this.phDPct = phDPct;
    this.terminalPct = terminalPct;
    this.sFratio = sFratio;
    this.alumniDonationPct = alumniDonationPct;
    this.expenditurePerStudent = expenditurePerStudent;
    this.graduationRate = graduationRate;
  }

  /**
   * Getter for name of college
   *
   * @return name of college
   */
  @Override
  public String getCollege() {
    return college;
  }

  /**
   * Getter for status of private/public school
   *
   * @return yes if private, no if public
   */
  @Override
  public String isPrivate() {
    return isPrivate;
  }

  /**
   * Getter for number of applications
   *
   * @return number of applications
   */
  @Override
  public int getNumApps() {
    return numApps;
  }

  /**
   * Getter for number of applications accepted
   *
   * @return number of applications accepted
   */
  @Override
  public int getNumAccepted() {
    return numAccepted;
  }

  /**
   * Getter for number of new students enrolled
   *
   * @return number of new students enrolled
   */
  @Override
  public int getNumEnrolled() {
    return numEnrolled;
  }

  /**
   * Getter for number of students enrolled in top 10% of high school class
   *
   * @return number of students enrolled in top 10% of high school class
   */
  @Override
  public int getTop10Pct() {
    return top10Pct;
  }

  /**
   * Getter for number of students enrolled in top 25% of high school class
   *
   * @return number of students enrolled in top 25% of high school class
   */
  @Override
  public int getTop25Pct() {
    return top25Pct;
  }

  /**
   * Getter for number of full time undergraduate students
   *
   * @return number of full time undergraduate students
   */
  @Override
  public int getNumFullUndergrads() {
    return numFullUndergrads;
  }

  /**
   * Getter for number of part time undergraduate students
   *
   * @return number of part time undergraduate students
   */
  @Override
  public int getNumPartUndergrads() {
    return numPartUndergrads;
  }

  /**
   * Getter for out of state tuition
   *
   * @return out of state tuition
   */
  @Override
  public int getTuition() {
    return tuition;
  }

  /**
   * Getter for room and board cost
   *
   * @return room and board cost
   */
  @Override
  public int getBoardCost() {
    return boardCost;
  }

  /**
   * Getter for average books cost
   *
   * @return average books cost
   */
  @Override
  public int getBooksCost() {
    return booksCost;
  }

  /**
   * Getter for average student spending cost
   *
   * @return average student spending cost
   */
  @Override
  public int getSpending() {
    return spending;
  }

  /**
   * Getter for percent of faculty with pHD
   *
   * @return percent of faculty with pHD
   */
  @Override
  public int getPhDPct() {
    return phDPct;
  }

  /**
   * Getter for percent of faculty with terminal degree
   *
   * @return percent of faculty with terminal degree
   */
  @Override
  public int getTerminalPct() {
    return terminalPct;
  }

  /**
   * Getter for student to faculty ratio
   *
   * @return student to faculty ratio
   */
  @Override
  public double getSFRatio() {
    return sFratio;
  }

  /**
   * Getter for percent of alumni who make donations
   *
   * @return percent of alumni who make donations
   */
  @Override
  public int getAlumniDonationPct() {
    return alumniDonationPct;
  }

  /**
   * Getter for average expenditure per student
   *
   * @return average expenditure per student
   */
  @Override
  public int getExpenditurePerStudent() {
    return expenditurePerStudent;
  }

  /**
   * Getter for graduation rate
   *
   * @return graduation rate
   */
  @Override
  public int getGraduationRate() {
    return graduationRate;
  }

  /**
   * Mutator for filter
   */
  public void setFilter(String filter) {
    this.filter = filter;
  }

  /**
   * Getter for filter
   *
   * @return filter
   */
  public String getFilter() {
    return this.filter;
  }

  /**
   * Returns the college object as a string formatted with each of the data fields
   *
   * @return formatted string of college
   */
  @Override
  public String toString() {
    return "College: " + college.replaceAll("\"",
        "") + "\n" + "Private school: " + isPrivate.replaceAll("\"",
        "") + "\n" + "Number of applications received: " + numApps + "\n" + "Number of applications accepted: " + numAccepted + "\n" + "Number of new students enrolled: " + numEnrolled + "\n" + "Percentage of new students from top 10% of H.S. class: " + top10Pct + "\n" + "Percentage of new students from top 25% of H.S. class: " + top25Pct + "\n" + "Number of full-time undergraduates: " + numFullUndergrads + "\n" + "Number of part-time undergraduates: " + numPartUndergrads + "\n" + "Out-of-state tuition: $" + tuition + "\n" + "Room and board costs: $" + boardCost + "\n" + "Estimated books costs: $" + booksCost + "\n" + "Estimated personal spending per student: $" + spending + "\n" + "Percentage of faculty with Ph.D.'s: " + phDPct + "\n" + "Percentage of faculty with terminal degree: " + terminalPct + "\n" + "Student to faculty ratio: " + sFratio + "\n" + "Alumni donation percentage: " + alumniDonationPct + "\n" + "Instructional expenditure per student: $" + expenditurePerStudent + "\n" + "Graduation rate: " + graduationRate + "%" + "\n" + "\n";
  }

  /**
   * Determines if two college objects are equal
   *
   * @param obj college object to be compared
   * @return true if colleges are the same, false if not
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CollegeDW collegeDW = (CollegeDW) obj;
    return numApps == collegeDW.numApps && numAccepted == collegeDW.numAccepted && numEnrolled == collegeDW.numEnrolled && numFullUndergrads == collegeDW.numFullUndergrads && numPartUndergrads == collegeDW.numPartUndergrads && Double.compare(
        collegeDW.top10Pct, top10Pct) == 0 && Double.compare(collegeDW.top25Pct,
        top25Pct) == 0 && Double.compare(collegeDW.tuition, tuition) == 0 && Double.compare(
        collegeDW.boardCost, boardCost) == 0 && Double.compare(collegeDW.booksCost,
        booksCost) == 0 && Double.compare(collegeDW.spending, spending) == 0 && Double.compare(
        collegeDW.phDPct, phDPct) == 0 && Double.compare(collegeDW.terminalPct,
        terminalPct) == 0 && Double.compare(collegeDW.sFratio, sFratio) == 0 && Double.compare(
        collegeDW.alumniDonationPct, alumniDonationPct) == 0 && Double.compare(
        collegeDW.expenditurePerStudent, expenditurePerStudent) == 0 && Double.compare(
        collegeDW.graduationRate, graduationRate) == 0 && Objects.equals(college,
        collegeDW.college) && Objects.equals(isPrivate, collegeDW.isPrivate);
  }

  /**
   * Compares two college objects by their pHD percent, then their terminal degree percent, then
   * their student to faculty ratio.
   * @param college the object to be compared.
   * @return
   */
  @Override
  public int compareTo(CollegeInterface college) {

    if (filter.equals("college")) {
      return compareByName(college);
    }
    if (filter == "tuition") {
      return compareByTuition(college);
    }
    return 0;

  }

  /**
   * Compares two colleges by their name.
   *
   * @param other college to be compared
   * @return -1 if the name of the other comes later alphabetically, 1 if before alphabetically, 0
   * if equal
   */
  public int compareByName(CollegeInterface other) {
    if (college.compareTo(other.getCollege()) < 0) {
      return -1;
    }
    if (college.compareTo(other.getCollege()) > 0) {
      return 1;
    }
    return 0;
  }

  /**
   * Compares two college objects by their tuition.
   *
   * @param other college to be compared
   * @return -1 if other college has a higher tuition, 1 if lower tuition, 0 if equal tuition.
   */
  public int compareByTuition(CollegeInterface other) {
      int result =  ((Integer)tuition).compareTo(other.getTuition());
      if(result ==0){
        return this.compareByName(other);
      }
      return result;
  }

  /**
   * Creates a deepcopy
   *
   * @param filter
   * @return
   */
  @Override
  public CollegeInterface deepCopy(String filter) {
    CollegeInterface toReturn =
        new CollegeDW(this.college, this.isPrivate, this.numApps,
            this.numAccepted, this.numEnrolled, this.top10Pct, this.top25Pct, this.numFullUndergrads, this.numPartUndergrads, this.tuition, this.boardCost, this.booksCost, this.spending, this.phDPct, this.terminalPct, this.sFratio, this.alumniDonationPct, this.expenditurePerStudent, this.graduationRate);
    toReturn.setFilter(filter);
    return toReturn;
  }
}
