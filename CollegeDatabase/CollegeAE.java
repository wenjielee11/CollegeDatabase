/**
 * Minimal hardcoded class, an object that represents a college
 */
public class CollegeAE implements CollegeInterface{
    public String name;
    public int ranking;
    public int tuitionFees;
    public double points;

    public CollegeAE(String name, int ranking, double pts, int tuitionFees){
        this.name = name;
        this.ranking = ranking;
        this.points = pts;
        this.tuitionFees = tuitionFees;
    }
    public String getName(){
        return name;
    }


    @Override
    public int compareTo(CollegeInterface o) {
        return ((Integer) ranking).compareTo(((CollegeAE)o).ranking);
    }

    @Override
    public String getCollege() {
        return null;
    }

    @Override
    public String isPrivate() {
        return null;
    }

    @Override
    public int getNumApps() {
        return 0;
    }

    @Override
    public int getNumAccepted() {
        return 0;
    }

    @Override
    public int getNumEnrolled() {
        return 0;
    }

    @Override
    public int getTop10Pct() {
        return 0;
    }

    @Override
    public int getTop25Pct() {
        return 0;
    }

    @Override
    public int getNumFullUndergrads() {
        return 0;
    }

    @Override
    public int getNumPartUndergrads() {
        return 0;
    }

    @Override
    public int getTuition() {
        return 0;
    }

    @Override
    public int getBoardCost() {
        return 0;
    }

    @Override
    public int getBooksCost() {
        return 0;
    }

    @Override
    public int getSpending() {
        return 0;
    }

    @Override
    public int getPhDPct() {
        return 0;
    }

    @Override
    public int getTerminalPct() {
        return 0;
    }

    @Override
    public double getSFRatio() {
        return 0;
    }

    @Override
    public int getAlumniDonationPct() {
        return 0;
    }

    @Override
    public int getExpenditurePerStudent() {
        return 0;
    }

    @Override
    public int getGraduationRate() {
        return 0;
    }

    @Override
    public String toString(){
        return "\nName: " + name+"\nRanking: "+ ranking+"\ntuitionFees: "+ tuitionFees+ "\npoints: "+points;
    }

    @Override
    public CollegeInterface deepCopy(String tuition) {
        return null;
    }

    @Override
    public String getFilter() {
        return null;
    }

    @Override
    public void setFilter(String filter) {

    }
}
