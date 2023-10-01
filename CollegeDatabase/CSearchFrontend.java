// --== CS400 Spring 2023 File Header Information ==--
// Name: Noah Boeder
// Email: nboeder@wisc.edu
// Team: BK
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class models the user interface for a college search application that allows user to store
 * college data and search by certain parameters
 * 
 * @author Noah Boeder
 */
public class CSearchFrontend implements CSearchFrontendInterface {

  private Scanner userInput; // Scanner to get user input
  private CSearchBackendInterface backend; // backend object used to store and search colleges

  /**
   * Creates a new frontend object using a given scanner and backend
   * 
   * @param userInput, provided Scanner object for getting user input
   * @param backend,   provided backend object
   */
  public CSearchFrontend(Scanner userInput, CSearchBackendInterface backend) {
    this.userInput = userInput;
    this.backend = backend;
  }

  /**
   * Runs the user interface using a loop. Methods are called based on user input until the user
   * stops the program with a specific command
   */
  @Override
  public void runCommandLoop() {
    // introduction to the program
    line();
    System.out.println("Welcome to CSearch, the College Search App.");
    line();
    // loop the command prompt until they choose to quit with Q
    char command = '\0';
    while (command != 'Q') {
      // give command prompt message
      command = mainMenuPrompt();
      // call a method in this class based on the command
      switch (command) {
        case 'F':
          loadFileCommand();
          break;
        case 'N':
          searchNameCommand();
          break;
        case 'K':
          searchKeywordCommand();
          break;
        case 'T':
          searchTuitionCommand();
          break;
        case 'R':
          removeCollegeCommand();
          break;
        case 'Q':
          System.out.println();
          break;
        // default case for invalid inputs
        default:
          System.out.println("Error: Invalid command given."
              + " Please type one of the letters within []s to choose a command.\n");
      }
    }
    // exit message
    line();
    System.out.println("Thank you for using CSearch!");
    line();
  }

  /**
   * Prints a row of dashes
   */
  private void line() {
    System.out
        .println("-------------------------------------------------------------------------------");
  }

  /**
   * Provides the user with a list of command options and gets their input
   * 
   * @return the char command made by the user
   */
  @Override
  public char mainMenuPrompt() {
    // print options
    System.out.println("Choose a command from the list below:\n"
        + "    Load college data from a [F]ile\n" + "    Search for a college by [N]ame\n"
        + "    List all colleges starting with a given [K]eyword\n"
        + "    List all coleges within a [T]uition range\n"
        + "    [R]emove a college from the database\n" + "    [Q]uit program\n"
        + "Choose a command: ");
    // get user input
    String input = userInput.nextLine().trim();
    // return the null character if input is empty
    if (input.length() == 0) {
      return '\0';
    }
    // return capitalized first character from the input
    return (Character.toUpperCase(input.charAt(0)));
  }

  /**
   * Loads colleges from a file provided by the user
   */
  @Override
  public void loadFileCommand() {
    // get the file name from the user
    System.out.println("Enter the name of the file to load: ");
    String fileName = userInput.nextLine().trim();
    try {
      // try to load data from that file
      backend.loadData(fileName);
      // success message
      System.out.println("Your file was successfully loaded!\n");
    } catch (FileNotFoundException e) {
      // failure message
      System.out.println("Error: " + fileName + " could not be found/loaded\n");
    }
  }

  /**
   * Searches for a college by name
   */
  @Override
  public void searchNameCommand() {
    // get the college name from the user
    System.out.println("Enter the name of the college you want to find: ");
    String collegeName = userInput.nextLine().trim();
    try {
      // try to find the college
      String collegeInfo = backend.findCollegesByExactName(collegeName);
      // provide info about it if found
      System.out.println("College found! Here is some information about it:");
      System.out.println(collegeInfo + "\n");
    } catch (NoSuchElementException e) {
      // otherwise give error message
      System.out.println("Error: College named " + collegeName + " could not be found\n");
    }
  }

  /**
   * Searches for colleges with a certain keyword starting their name
   */
  @Override
  public void searchKeywordCommand() {
    // get the keyword from the user
    System.out.println("Enter a keyword to search for colleges starting with it: ");
    String keyword = userInput.nextLine().trim();
    // print out all colleges with that keyword
    System.out.println("Here are all colleges starting with the keyword \"" + keyword + "\":");
    System.out.println(backend.findCollegesByStartingName(keyword) + "\n");
  }

  /**
   * Searches for colleges by an exact tuition or tuition range
   */
  @Override
  public void searchTuitionCommand() {
    // let the user choose to search by exact tuition or a range
    System.out
        .println("Enter 0 to search by exact tutition or 1 to search using a tuition range: ");
    String searchChoice = userInput.nextLine().trim();
    if (searchChoice.equals("0")) {
      // get the tuition from the user
      System.out.println("Enter the exact tuition to be searched: ");
      String tuitionStr = userInput.nextLine().trim();
      try {
        // try to print colleges with that tuition
        Integer tuition = Integer.valueOf(tuitionStr);
        System.out.println("Colleges with $" + tuition + " tuition:");
        System.out.println(backend.findCollegesByTuitionRates(tuition) + "\n");
      } catch (NumberFormatException e) {
        // otherwise print error message
        System.out.println("Error: Invalid tuition given\n");
      }
    } else if (searchChoice.equals("1")) {
      // get the tuition range from the user
      System.out.println(
          "Enter the lower then upper end of the tuition range being searched (separated by returns): ");
      String lowerStr = userInput.nextLine().trim();
      String upperStr = userInput.nextLine().trim();
      try {
        // try to print colleges within that tuition range
        Integer tuitionMin = Integer.valueOf(lowerStr);
        Integer tuitionMax = Integer.valueOf(upperStr);
        System.out.println(
            "Colleges with tuition in the range $" + tuitionMin + " - $" + tuitionMax + ":");
        System.out.println(backend.findCollegesByTuitionRatesRange(tuitionMin, tuitionMax) + "\n");
      } catch (NumberFormatException e) {
        // otherwise print error message
        System.out.println("Error: Invalid tuition range given\n");
      }
    } else {
      System.out.println("Error: Invalid input given\n");
    }
  }

  /**
   * Removes a college of the user's choice from the database
   */
  @Override
  public void removeCollegeCommand() {
    // get the name of the college being removed
    System.out.println("Enter the name of the college you want to remove: ");
    String removeName = userInput.nextLine().trim();
    // notify the user on whether the removal has succeeded or failed
    if (backend.removeOneCollege(removeName)) {
      System.out.println(removeName + " was successfully removed from the list of colleges\n");
    } else {
      System.out.println("Error: College named " + removeName + " could not be found\n");
    }
  }


}
