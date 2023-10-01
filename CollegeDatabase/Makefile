run: CollegeSearchApp.class
		java CollegeSearchApp

runTests: 
	make runDWTests
	make runBDTests
	make runAETests
	make runFDTests

runAETests: RBTCollegeAETest.class RedBlackTreeAE.class RBTCollegeAE.class RBTCollegeAEDummy.class CollegeAE.class CollegeDW.class CSearchBackendBD.class CollegeReaderDW.class
		java -jar junit5.jar -cp . -c RBTCollegeAETest -n ".*"
runDWTests: DWTests.class
		java -jar junit5.jar -cp . --select-class=DWTests
runBDTests: BackendDeveloperTests.class CSearchBackendBD.class CollegeBD.class CollegeReaderBD.class RBTCollegeBD.class CollegeDW.class CollegeReaderDW.class RBTCollegeAE.class CSearchFrontend.class
		java -jar junit5.jar -cp . -c BackendDeveloperTests -n ".*"
runFDTests: FrontendDeveloperTests.class CollegeSearchApp.class TextUITester.class CSearchBackendFD.class
		java -jar junit5.jar -cp . -c FrontendDeveloperTests -n ".*"


DWTests.class: DWTests.java CollegeReaderDW.class
		javac -cp .:junit5.jar DWTests.java
TextUITester.class: TextUITester.java
		javac TextUITester.java
CSearchBackendFD.class: CSearchBackendInterface.class CSearchBackendFD.java
		javac CSearchBackendFD.java
RBTCollegeAEDummy.class: RBTCollegeAEDummy.java
		javac RBTCollegeAEDummy.java
RBTCollegeAETest.class: RBTCollegeAETest.java
		javac -cp .:junit5.jar RBTCollegeAETest.java
RedBlackTreeAE.class: RedBlackTreeAE.java
		javac RedBlackTreeAE.java
		javac RedBlackTreeAEDummy.java
RBTCollegeAE.class: RBTCollegeAE.java
		javac RBTCollegeAE.java
CollegeAE.class: CollegeAE.java
		javac CollegeAE.java
CollegeSearchApp.class: CollegeSearchApp.java CollegeDW.class CollegeReaderDW.class RBTCollegeAE.class CSearchBackendBD.class CSearchFrontend.class
		javac CollegeSearchApp.java
CollegeDW.class: CollegeDW.java CollegeInterface.class
		javac CollegeDW.java
CollegeInterface.class: CollegeInterface.java
		javac CollegeInterface.java
CollegeReaderDW.class: CollegeReaderDW.java CollegeReaderInterface.class
		javac CollegeReaderDW.java
CollegeReaderInterface.class: CollegeReaderInterface.java
		javac CollegeReaderInterface.java
CSearchBackendBD.class: CSearchBackendBD.java CSearchBackendInterface.class
		javac CSearchBackendBD.java
CSearchBackendInterface.class: CSearchBackendInterface.java
		javac CSearchBackendInterface.java
CSearchFrontend.class: CSearchFrontend.java CSearchFrontendInterface.class
		javac CSearchFrontend.java
CSearchFrontendInterface.class: CSearchFrontendInterface.java
		javac CSearchFrontendInterface.java

BackendDeveloperTests.class: BackendDeveloperTests.java
	javac -cp .:junit5.jar BackendDeveloperTests.java

CollegeBD.class: CollegeBD.java
	javac CollegeBD.java

CollegeReaderBD.class: CollegeReaderBD.java
	javac CollegeReaderBD.java

RBTCollegeBD.class: RBTCollegeBD.java
	javac RBTCollegeBD.java

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac -cp .:junit5.jar FrontendDeveloperTests.java

clean:
	rm *.class
