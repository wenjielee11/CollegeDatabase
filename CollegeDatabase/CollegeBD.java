/**
 * Placeholder class for CollegeInterface
 * @author Kiet Pham
 */

public class CollegeBD implements CollegeInterface{
	public String college; 
	public int tuition; 
	public String filter = ""; 

	public CollegeBD(String college, int tuition) {
		this.college = college; 
		this.tuition = tuition; 
	}
	
	@Override
	public int compareTo(CollegeInterface o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCollege() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumApps() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumEnrolled() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTop10Pct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTop25Pct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumFullUndergrads() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumPartUndergrads() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTuition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBoardCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBooksCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpending() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPhDPct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTerminalPct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSFRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAlumniDonationPct() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExpenditurePerStudent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getGraduationRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public String toString() {
		return this.college + ": " + this.tuition; 
	}

	@Override
	public CollegeInterface deepCopy(String filter) {
		CollegeInterface toReturn = new CollegeBD(this.college, this.tuition); 
		toReturn.setFilter(filter); 
		return toReturn; 
	}

	@Override
	public String getFilter() {
		return this.filter; 
	}

	@Override
	public void setFilter(String filter) {
		this.filter = filter; 
		
	}

	@Override
	public String isPrivate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumAccepted() {
		// TODO Auto-generated method stub
		return 0;
	}

}
