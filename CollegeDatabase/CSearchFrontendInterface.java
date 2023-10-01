import java.util.List;

public interface CSearchFrontendInterface {

  // public CSearchFrontendXX(Scanner userInput, CSearchBackendInterface backend);

  public void runCommandLoop();

  public char mainMenuPrompt();

  public void loadFileCommand();

  public void searchNameCommand();

  public void searchKeywordCommand();

  public void searchTuitionCommand();

  public void removeCollegeCommand();
  
}

