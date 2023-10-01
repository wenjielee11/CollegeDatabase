import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollegeReaderDW implements CollegeReaderInterface{
  /**
   * Reads the csv file and puts it into an array list
   * @param filename file to be read
   * @return list of colleges from the csv
   * @throws FileNotFoundException if the file is not found
   */
  @Override
  public List<CollegeInterface> readCollegesFromFile(String filename) throws FileNotFoundException {
    List<CollegeInterface> colleges = new ArrayList<CollegeInterface>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filename));
      reader.readLine();
      String line = null;
      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        String name = fields[0].replaceAll("\"", "");
        String isPrivate = fields[1].replaceAll("\"", "");
        int apps = Integer.parseInt(fields[2]);
        int accept = Integer.parseInt(fields[3]);
        int enroll = Integer.parseInt(fields[4]);
        int top10perc = Integer.parseInt(fields[5]);
        int top25perc = Integer.parseInt(fields[6]);
        int fUndergrad = Integer.parseInt(fields[7]);
        int pUndergrad = Integer.parseInt(fields[8]);
        int outState = Integer.parseInt(fields[9]);
        int roomBoard = Integer.parseInt(fields[10]);
        int books = Integer.parseInt(fields[11]);
        int personal = Integer.parseInt(fields[12]);
        int phd = Integer.parseInt(fields[13]);
        int terminal = Integer.parseInt(fields[14]);
        double sfratio = Double.parseDouble(fields[15]);
        int percAlumni = Integer.parseInt(fields[16]);
        int expend = Integer.parseInt(fields[17]);
        int gradRate = Integer.parseInt(fields[18]);
        CollegeInterface college = new CollegeDW(name, isPrivate, apps, accept, enroll, top10perc, top25perc, fUndergrad, pUndergrad, outState, roomBoard, books, personal, phd, terminal, sfratio, percAlumni, expend, gradRate);
        colleges.add(college);
      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found: " + filename);
    } catch (IOException e) {
      throw new RuntimeException("Error reading file: " + filename, e);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          // Ignore
        }
      }
    }
    return colleges;
  }
}
