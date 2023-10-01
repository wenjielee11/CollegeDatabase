import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public interface CSearchBackendInterface {   
    // public CSearchBackendInterface(RBTCollegeDataInterface collegeLists, CollegeReaderInterface collegeReader);
    public void loadData(String filename) throws FileNotFoundException;
    public void addOneData(CollegeInterface dataToAdd);
    public String findCollegesByExactName(String exactName) throws NoSuchElementException; 
    public String findCollegesByStartingName(String startingName); 
    public String findCollegesByTuitionRates(int tuition);
    public String findCollegesByTuitionRatesRange(int tuitionMin, int tuitionMax);
    // public List<String> findCollegesByXXX()
    // Additional implemsentation of finding colleges
    public boolean removeOneCollege(String exactName) throws NoSuchElementException; 
}
