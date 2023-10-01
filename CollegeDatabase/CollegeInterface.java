public interface CollegeInterface extends Comparable<CollegeInterface>{
  // public CollegeInterface(String college, int numApps, int numEnrolled, double top10Pct, double top25Pct, int numFullUndergrads, int numPartUndergrads, double tuition, double boardCost, double booksCost, double spending, double phDPct, double terminalPct, double sFratio, double alumniDonationPct, double expenditurePerStudent, double graduationRate);
  public String getCollege();
  public String isPrivate();
  public int getNumApps();
  public int getNumAccepted();
  public int getNumEnrolled();
  public int getTop10Pct();
  public int getTop25Pct();
  public int getNumFullUndergrads();
  public int getNumPartUndergrads();
  public int getTuition();
  public int getBoardCost();
  public int getBooksCost();
  public int getSpending();
  public int getPhDPct();
  public int getTerminalPct();
  public double getSFRatio();
  public int getAlumniDonationPct();
  public int getExpenditurePerStudent();
  public int getGraduationRate();
  public String toString();
  public CollegeInterface deepCopy(String tuition);
  public String getFilter();
  public void setFilter(String filter);
}
