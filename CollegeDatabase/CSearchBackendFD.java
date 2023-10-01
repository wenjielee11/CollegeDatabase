import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class CSearchBackendFD implements CSearchBackendInterface {
  
  @Override
  public void loadData(String filename) throws FileNotFoundException {
  }

  @Override
  public void addOneData(CollegeInterface dataToAdd) {
  }

  @Override
  public String findCollegesByExactName(String exactName) throws NoSuchElementException {
    return("Name - University of Wisconsin-Madison\nAverage GPA - 3.4\nAdmission rate - 40%");
  }

  @Override
  public String findCollegesByStartingName(String startingName) {
    return("University of Wisconsin-Madison\nUniversity of Iowa");
  }

  @Override
  public String findCollegesByTuitionRates(int tuition) {
    return("Michigan University");
  }

  @Override
  public String findCollegesByTuitionRatesRange(int tuitionMin, int tuitionMax) {
    return("Lafayette University\nPrinceton University");
  }

  @Override
  public boolean removeOneCollege(String exactName) {
    return true;
  }

}
