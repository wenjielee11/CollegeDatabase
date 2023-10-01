import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * This class implements the testers for the RedBlackTreeAE and RBTCollegeAEDummyDummy
 */
public class RBTCollegeAETest {
    @Test
    void testConstructor() {
        try {
            // No exceptions should occur
            RBTCollegeAEDummy test = new RBTCollegeAEDummy("ranking");
            RBTCollegeAEDummy test2 = new RBTCollegeAEDummy("name");
            RBTCollegeAEDummy test3 = new RBTCollegeAEDummy("points");
            assertTrue(test.size == 0);
            assertTrue(test2.size == 0);
            assertTrue(test3.size == 0);
            assertTrue(test.isEmpty());
            assertTrue(test2.isEmpty());
            assertTrue(test3.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * This method tests if the college nodes exist after insertion
     */
    @Test
    void testInsert(){
        RBTCollegeAEDummy test = new RBTCollegeAEDummy(("ranking"));
        for (int i = 0; i <= 100; i++) {
            char b = (char) i;
            CollegeInterface node = new CollegeAE("Name" + b, i, (double) i / 10, i * 1000);
            test.insert(node);
            assertTrue(test.findNodeWithData(node).data == node);
        }
        assertTrue(test.size() == 101);
    }

    /**
     * This method tests the implementation of remove, and it should adhere to all deletion cases. Example was obtained
     * from Florian's lecture notes.
     */
    @Test
    void testInsertAndRemove() {
        RedBlackTreeAE<Integer> test = new RedBlackTreeAE<>();
        test.insert(14);
        test.insert(7);
        test.insert(20);
        test.insert(5);
        test.insert(11);
        test.insert(1);
        test.insert(6);
        test.insert(9);
        test.insert(13);
        test.insert(18);
        test.insert(23);
        test.insert(15);
        test.insert(19);
        test.insert(29);

        test.findNodeWithData(7).blackHeight = 1;
        test.findNodeWithData(20).blackHeight = 1;
        test.findNodeWithData(5).blackHeight = 0;
        test.findNodeWithData(11).blackHeight = 0;
        test.findNodeWithData(1).blackHeight = 1;
        test.findNodeWithData(6).blackHeight = 1;
        test.findNodeWithData(9).blackHeight = 1;
        test.findNodeWithData(13).blackHeight = 1;
        test.findNodeWithData(18).blackHeight = 1;
        test.findNodeWithData(23).blackHeight = 1;
        test.findNodeWithData(15).blackHeight = 0;
        test.findNodeWithData(19).blackHeight = 0;
        test.findNodeWithData(29).blackHeight = 0;
        /**
         level order: [ 14 (1), 7 (1), 20 (1), 5 (0), 11 (0), 18 (1), 23 (1), 1 (1), 6 (1), 9 (1), 13 (1), 15 (0), 19 (0), 29 (0) ]
         in order: [ 1 (1), 5 (0), 6 (1), 7 (1), 9 (1), 11 (0), 13 (1), 14 (1), 15 (0), 18 (1), 19 (0), 20 (1), 23 (1), 29 (0) ]
         **/
        // Removing a red leaf node
        test.remove(15);
        //System.out.println(test.toStringBlackHeight());
        // Removing a node with a right red leaf child
        test.remove(23);
        String expected1 = "level order: [ 14 (1), 7 (1), 20 (1), 5 (0), 11 (0), 18 (1), 29 (1), 1 (1), 6 (1), 9 (1), 13 (1), 19 (0) ]" +
                "\nin order: [ 1 (1), 5 (0), 6 (1), 7 (1), 9 (1), 11 (0), 13 (1), 14 (1), 18 (1), 19 (0), 20 (1), 29 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        // Removing a node with 2 child
        test.remove(20);
        expected1 = "level order: [ 14 (1), 7 (1), 19 (1), 5 (0), 11 (0), 18 (1), 29 (1), 1 (1), 6 (1), 9 (1), 13 (1) ]" +
                "\nin order: [ 1 (1), 5 (0), 6 (1), 7 (1), 9 (1), 11 (0), 13 (1), 14 (1), 18 (1), 19 (1), 29 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        test.remove(13);
        expected1 = "level order: [ 14 (1), 7 (1), 19 (1), 5 (0), 11 (1), 18 (1), 29 (1), 1 (1), 6 (1), 9 (0) ]" +
                "\nin order: [ 1 (1), 5 (0), 6 (1), 7 (1), 9 (0), 11 (1), 14 (1), 18 (1), 19 (1), 29 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        test.remove(29);
        expected1 = "level order: [ 7 (1), 5 (1), 14 (1), 1 (1), 6 (1), 11 (1), 19 (1), 9 (0), 18 (0) ]" +
                "\nin order: [ 1 (1), 5 (1), 6 (1), 7 (1), 9 (0), 11 (1), 14 (1), 18 (0), 19 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        // Cascades to root
        test.remove(1);
        expected1 = "level order: [ 7 (1), 5 (1), 14 (0), 6 (0), 11 (1), 19 (1), 9 (0), 18 (0) ]" +
                "\nin order: [ 5 (1), 6 (0), 7 (1), 9 (0), 11 (1), 14 (0), 18 (0), 19 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        test.remove(5);
        expected1 = "level order: [ 7 (1), 6 (1), 14 (0), 11 (1), 19 (1), 9 (0), 18 (0) ]" +
                "\nin order: [ 6 (1), 7 (1), 9 (0), 11 (1), 14 (0), 18 (0), 19 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());
        test.remove(6);
        expected1 = "level order: [ 14 (1), 9 (0), 19 (1), 7 (1), 11 (1), 18 (0) ]" +
                "\nin order: [ 7 (1), 9 (0), 11 (1), 14 (1), 18 (0), 19 (1) ]";
        assertEquals(expected1, test.toStringBlackHeight());

    }



    /**
     * This method tests if the search() method returns a specific college node that matches the given filter and criteria.
     */
    @Test
    void search() {

        try {
            RBTCollegeAEDummy test = new RBTCollegeAEDummy(("ranking"));
            for (int i = 0; i <= 100; i++) {
                char b = (char) i;
                test.insert(new CollegeAE("Name" + b, i, (double) i / 10, i * 1000));
            }
            //System.out.println("search:");
            //System.out.println(test.toInOrderString());
            CollegeInterface result = test.search("ranking", 34);
            String actual = result.toString().trim();
            String expected = "Name: Name\"\n" + "Ranking: 34\n" + "tuitionFees: 34000\n" + "points: 3.4\n".trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when ranking is 34");

            result = test.search("name", "Name+");
            actual = result.toString().trim();
            expected = "Name: Name+\n" + "Ranking: 43\n" + "tuitionFees: 43000\n" + "points: 4.3\n".trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when name is Name+");


            result = test.search("points", 5.5);
            actual = result.toString().trim();
            expected = "Name: Name7\n" + "Ranking: 55\n" + "tuitionFees: 55000\n" + "points: 5.5\n".trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when points is 5.5");

            //Searching for largest value in the tree
            result = test.search("name", "Named");
            actual = result.toString().trim();
            expected = "Name: Named\n" + "Ranking: 100\n" + "tuitionFees: 100000\n" + "points: 10.0\n".trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when name is Named");

            result = test.search("ranking", 100);
            actual = result.toString().trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when ranking is 100");

            result = test.search("points", 10.0);
            actual = result.toString().trim();
            assertEquals(expected, actual, "Error, search did not return the correct college when points is 10.0");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method tests if searchInRange returns a correct result range for any given filter and range.
     * Do note that the tree is inserted by ranking in default. This can be changed in the compareTo method within CollegeAE.java
     * For example, when searching for tuition fees, it will visit elements that were sorted by ranking first. However, the string should only contain the college
     * if its tuition fees are within the specified range
     */
    @Test
    void searchInRange() {
        try {
            RBTCollegeInterface test = new RBTCollegeAEDummy("ranking");
            for (int i = 0; i <= 100; i++) {
                char b = (char) i;
                test.insert(new CollegeAE("Name" + b, i, (double) i / 10, i * 1000));

            }

            // Testing by searching for tuition fees
            String actual = test.searchInRange("tuitionFees", 1000, 9000);
            for (int i = 0; i < 100; i++) {
                int tuitionFees = i * 1000;
                if (tuitionFees < 1000 || tuitionFees > 9000) {
                    assertFalse(actual.contains("tuitionFees: " + tuitionFees), "Error, contains a tuition fee not in range: " + tuitionFees);
                } else {
                    assertTrue("Error, does not contain a tuition fee in range: " + tuitionFees, actual.contains("tuitionFees: " + tuitionFees));
                }
            }

            //Testing by searching for ranking
            actual = test.searchInRange("ranking", 5, 20);

            for (int i = 0; i < 100; i++) {

                if (i < 5 || i > 20) {
                    assertFalse(actual.contains("Ranking: " + i + "\n"), "Error, contains a ranking not in range :" + i);
                } else {
                    assertTrue("Error, does not contain a ranking in range: " + i, actual.contains("Ranking: " + i + "\n"));
                }
            }
            actual = test.searchInRange("ranking", 0, 100);

            for (int i = 0; i <= 100; i++) {

                if (i < 0 || i > 100) {
                    assertFalse(actual.contains("Ranking: " + i + "\n"), "Error, contains a ranking not in range :" + i);
                } else {
                    assertTrue("Error, does not contain a ranking in range: " + i, actual.contains("Ranking: " + i + "\n"));
                }
            }

            // Testing by searching for doubles (points)
            actual = test.searchInRange("points", 3.3, 4.6);
            //System.out.println(actual);
            for (int i = 0; i < 100; i++) {
                double points = (double) i / 10;
                if (points < 3.3 || points > 4.6) {
                    assertFalse(actual.contains("points: " + points + "\n"), "Error, contains a pointnot in range :" + points + "\n");
                } else {
                    assertTrue("Error, does not contain a point in range: " + points, actual.contains("points: " + points + "\n"));
                }
            }

            actual = test.searchInRange("name", "Name#", "Name+");
            // Testing for name. Note: A string with alphabet and numbers make the compareTo wonky, so use a char instead
            for (int i = 0; i < 100; i++) {
                char a = (char) i;
                String name = "Name" + a;
                if (i < 35 || i > 43) {
                    assertFalse(actual.contains("Name: " + name), "Error, contains a name not in range :" + name + " at i=" + i);
                } else {
                    assertTrue("Error, contains a name not in range :" + name + " at i=" + i, actual.contains("Name: " + name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method tests the insertion and removal method after integration
     */
    @Test
    void insertRemoveIntegration() {
        CollegeReaderDW collegeReader = new CollegeReaderDW();
        List<CollegeInterface> colleges = null;
        RBTCollegeAE test = new RBTCollegeAE("college");
        RBTCollegeAE test2 = new RBTCollegeAE("tuition");
        try {
            colleges = collegeReader.readCollegesFromFile("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (CollegeInterface college : colleges) {
            college.setFilter("college");
            test.insert(college);
            college.setFilter("tuition");
            test2.insert(college);
        }
        assertEquals(24, test.size(), "Error, expected size for college tree is 24, got: " + test.size);
        assertEquals(24, test2.size(), "Error, expected size is tuition tree is 24, got: " + test2.size);
        String actual = test.toString();
        String actual2 = test2.toString();
        for (CollegeInterface college : colleges) {
            // If colleges exist
            assertThat(actual, containsString(college.getCollege()));
            assertThat(actual2, containsString(college.getCollege()));
        }
        int i = 0;
        int size = test.size();
        // Test the remove method
        for (CollegeInterface college : colleges) {
            college.setFilter("college");
            test.remove(college);
            college.setFilter("tuition");
            test2.remove(college);
            i++;
            // Test if size is updated accordingly
            assertEquals(test.size(), size - i, "actual size is: " + test.size());
            assertEquals(test2.size(), size - i, "actual size is: " + test2.size());
            actual = test.toString();
            actual2 = test2.toString();
            // Test if college is still present
            assertFalse(actual.contains(college.getCollege()));
            assertFalse(actual2.contains(college.getCollege()));
        }
        actual = test.toString();
        actual2 = test2.toString();
        assertEquals("level order: [  ]\n" +
                "in order: [  ]", actual);
        assertEquals("level order: [  ]\n" +
                "in order: [  ]", actual2);

    }

    /**
     * This method tests if search and searchInRange works after integration
     */
    @Test
    void searchInRangeIntegration() {
        CollegeReaderDW collegeReader = new CollegeReaderDW();
        List<CollegeInterface> colleges = null;
        RBTCollegeAE test1 = new RBTCollegeAE("college");
        RBTCollegeAE test2 = new RBTCollegeAE("tuition");
        try {
            colleges = collegeReader.readCollegesFromFile("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (CollegeInterface college : colleges) {
            try {
                college.setFilter("college");
                test1.insert(college);
                college.setFilter("tuition");
                test2.insert(college);
                college.setFilter("college");
                assertEquals(college.getCollege(), test1.search("college", college.getCollege()).getCollege());
                // Search by tuition fees
                college.setFilter("tuition");
                assertEquals(college.getTuition(), test2.search("tuition", college.getTuition()).getTuition());
                // Search if college exists after insertion
            }catch(Exception e){
                System.out.println(college.getCollege());
                e.printStackTrace();

            }
        }
        // test search after insertion
        for(CollegeInterface college: colleges){
                // Search by name
                college.setFilter("college");
                assertEquals(college.getCollege(), test1.search("college", college.getCollege()).getCollege());
                // Search by tuition fees
                college.setFilter("tuition");
                assertEquals(college.getTuition(), test2.search("tuition", college.getTuition()).getTuition());
        }
        // Should contain everything
        String actual1 = test1.searchInRange("college", "Ab", "Arizona State University Main campus");
        String actual2 = test2.searchInRange("tuition", 0 , 1000000);

        for (CollegeInterface college : colleges) {
            // If colleges exist
            assertThat(actual1, containsString(college.getCollege()));
            assertThat(actual2, containsString(String.valueOf(college.getTuition())));
        }
        //All colleges beginning from Al to Alz
        actual1 = test1.searchInRange("college", "Al", "Alz");
        //All colleges within 7000 to 10000 tuition fees
        actual2 = test2.searchInRange("tuition",  7000, 10000);
        for (CollegeInterface college : colleges) {
            if(college.getCollege().contains("Al")) {
                assertThat(actual1, containsString(college.getCollege()));
            }
            if(college.getTuition() >=7000 && college.getTuition()<=10000) {
                assertThat(actual2, containsString(college.getCollege()));
            }
        }

    }

    /**
     * This method tests the implementation of the BD's load data, addOneData, removeOneCollege
     */
    @Test
    void testCodeReviewOfBackendDeveloper1(){
        CollegeReaderDW collegeReader = new CollegeReaderDW();
        RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
        RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
        CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
        try {
            backend.loadData("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<CollegeInterface> colleges = null;
        try {
            colleges = collegeReader.readCollegesFromFile("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(24, collegeListsByName.size(), "Error, expected size for college tree is 24, got: " + collegeListsByName.size());
        assertEquals(24, collegeListsByTuition.size(), "Error, expected size is tuition tree is 24, got: " + collegeListsByTuition.size());
        String actual1 = collegeListsByName.toString();
        String actual2 = collegeListsByTuition.toString();
        for (CollegeInterface college : colleges) {
            // If colleges exist
            assertThat(actual1, containsString(college.getCollege()));
            assertThat(actual2, containsString(college.getCollege()));
        }
        int size = collegeListsByTuition.size();
        int i =0;
        for (CollegeInterface college : colleges) {
            backend.removeOneCollege(college.getCollege());
            i++;
            // Test if size is updated accordingly
            assertEquals(collegeListsByName.size(), size - i, "actual size is: " + collegeListsByName.size());
            assertEquals(collegeListsByTuition.size(), size - i, "actual size is: " + collegeListsByTuition.size());
            actual1 = collegeListsByName.toString();
            actual2 = collegeListsByTuition.toString();
            // Test if college is still present
            assertFalse(actual1.contains(college.getCollege()));
            assertFalse(actual2.contains(college.getCollege()));
        }

    }

    /**
     * This test method tests the implementation of findCollegesByXXX.
     */
    @Test
    void testCodeReviewForBackendDeveloper2(){
        CollegeReaderDW collegeReader = new CollegeReaderDW();
        RBTCollegeInterface collegeListsByName = new RBTCollegeAE("college");
        RBTCollegeInterface collegeListsByTuition = new RBTCollegeAE("tuition");
        CSearchBackendInterface backend = new CSearchBackendBD(collegeListsByName, collegeListsByTuition, collegeReader);
        try {
            backend.loadData("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<CollegeInterface> colleges = null;
        try {
            colleges = collegeReader.readCollegesFromFile("College_Data_SmallAE.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (CollegeInterface college : colleges) {
            try {
                //search by name
                assertThat(backend.findCollegesByExactName(college.getCollege()), containsString(college.getCollege()));
            }catch(Exception e){
                System.out.println(college.getCollege());
                e.printStackTrace();

            }
        }
        // test search after insertion
        for(CollegeInterface college: colleges){
            // Search by name

            assertThat(backend.findCollegesByExactName(college.getCollege()), containsString(college.getCollege()));
        }
        // Should contain everything
        String actual1 = backend.findCollegesByStartingName("A");
        String actual2 = backend.findCollegesByTuitionRatesRange(0, 1000000);

        for (CollegeInterface college : colleges) {
            // If colleges exist
            assertThat(actual1, containsString(college.getCollege()));
            assertThat(actual2, containsString(String.valueOf(college.getTuition())));
        }

        //All colleges beginning from An
        actual1 = backend.findCollegesByStartingName("An");
        //All colleges within 7000 to 10000 tuition fees
        actual2 = backend.findCollegesByTuitionRatesRange(7000, 10000);
        // Should only contain one college
        String actual3 = backend.findCollegesByTuitionRates(7440);
        assertThat(actual3, containsString("Abilene Christian University"));
        for (CollegeInterface college : colleges) {
            if(college.getCollege().contains("An")) {
                assertThat(actual1, containsString(college.getCollege()));
            }
            if(college.getTuition() >=7000 && college.getTuition()<=10000) {
                assertThat(actual2, containsString(college.getCollege()));
            }
            //Should only contain one college with 7440
            if(college.getTuition() != 7440){
                assertFalse(actual3.contains(college.getCollege()), actual3);
            }
        }

    }


}
