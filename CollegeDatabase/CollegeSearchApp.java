import java.util.Scanner;
public class CollegeSearchApp {
    public static void main(String[] args) {
        CollegeReaderDW collegeReader = new CollegeReaderDW();
        RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
        RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
        CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
        Scanner scanner = new Scanner(System.in);
        CSearchFrontendInterface frontend = new CSearchFrontend(scanner, backend);
        frontend.runCommandLoop();
    }
}
