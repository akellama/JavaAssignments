package com.pupilregistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Assignment 2
 * Console-based Pupil Registry Hub demonstrating usage of the
 * Java Collection Framework: ArrayList, HashMap and LinkedHashMap.
 */
public class PupilRegistryHubAdvanced {

    // Primary storage - ArrayList maintains insertion order and allows easy iteration.
    private static final List<Learner> pupilCollection = new ArrayList<>();

    // HashMap for fast code-based lookup (no ordering guarantee).
    private static final Map<Integer, Learner> pupilDirectory = new HashMap<>();

    // LinkedHashMap to demonstrate insertion-order-preserving map traversal.
    private static final Map<Integer, Learner> pupilOrderedDirectory = new LinkedHashMap<>();

    private static final Scanner consoleInput = new Scanner(System.in);

    public static void main(String[] args) {
        boolean isActive = true;

        while (isActive) {
            printMenu();
            String menuSelection = consoleInput.nextLine().trim();

            switch (menuSelection) {
                case "1":
                    enrollNewPupil();
                    break;
                case "2":
                    listRegisteredPupils();
                    break;
                case "3":
                    findPupilById();
                    break;
                case "4":
                    reviseEntry();
                    break;
                case "5":
                    discardPupil();
                    break;
                case "6":
                    listPupilsByFaculty();
                    break;
                case "7":
                    showTopPerformer();
                    break;
                case "8":
                    revealDirectoryContents();
                    break;
                case "9":
                    findPupilViaDirectory();
                    break;
                case "10":
                    listPupilsOrdered();
                    break;
                case "0":
                    isActive = false;
                    System.out.println("Exiting Pupil Registry Hub. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        consoleInput.close();
    }

    private static void printMenu() {
        System.out.println("\n====================================");
        System.out.println("        PUPIL REGISTRY HUB");
        System.out.println("====================================");
        System.out.println("1. Enroll Pupil");
        System.out.println("2. List All Pupils");
        System.out.println("3. Find Pupil");
        System.out.println("4. Revise Pupil");
        System.out.println("5. Discard Pupil");
        System.out.println("6. List Pupils by Faculty");
        System.out.println("7. Show Top Performer");
        System.out.println("8. Reveal Pupil Directory (HashMap)");
        System.out.println("9. Find Pupil via Directory");
        System.out.println("10. List Pupils Using LinkedHashMap");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    // ---------------------------------------------------------------
    // 1. Enroll Pupil
    // ---------------------------------------------------------------
    private static void enrollNewPupil() {
        try {
            System.out.print("Enter Pupil Code: ");
            int code = Integer.parseInt(consoleInput.nextLine().trim());

            if (code <= 0) {
                System.out.println("Invalid Pupil Code.");
                return;
            }

            if (pupilDirectory.containsKey(code)) {
                System.out.println("Pupil Code already exists.");
                return;
            }

            System.out.print("Enter Pupil Name: ");
            String fullName = consoleInput.nextLine().trim();
            if (fullName.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter Age: ");
            int yearsOld = Integer.parseInt(consoleInput.nextLine().trim());
            if (yearsOld <= 0) {
                System.out.println("Invalid Age.");
                return;
            }

            System.out.print("Enter Faculty: ");
            String faculty = consoleInput.nextLine().trim();
            if (faculty.isEmpty()) {
                System.out.println("Faculty cannot be empty.");
                return;
            }

            System.out.print("Enter Rating: ");
            double rating = Double.parseDouble(consoleInput.nextLine().trim());
            if (rating < 0 || rating > 100) {
                System.out.println("Rating should be between 0 and 100.");
                return;
            }

            Learner learner = new Learner(code, fullName, yearsOld, faculty, rating);
            pupilCollection.add(learner);
            pupilDirectory.put(code, learner);
            pupilOrderedDirectory.put(code, learner);

            System.out.println("Pupil enrolled successfully.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    // ---------------------------------------------------------------
    // 2. List All Pupils
    // ---------------------------------------------------------------
    private static void listRegisteredPupils() {
        if (pupilCollection.isEmpty()) {
            System.out.println("No pupil records found.");
            return;
        }

        System.out.println("---------------------------------------------------------");
        System.out.printf("%-8s %-11s %-6s %-15s %-6s%n", "Code", "Name", "Age", "Faculty", "Rating");
        System.out.println("---------------------------------------------------------");

        for (Learner pupil : pupilCollection) {
            System.out.println(pupil.toTabularLine());
        }

        System.out.println("---------------------------------------------------------");
    }

    // ---------------------------------------------------------------
    // 3. Find Pupil (linear search through ArrayList)
    // ---------------------------------------------------------------
    private static void findPupilById() {
        try {
            System.out.print("Enter Pupil Code: ");
            int code = Integer.parseInt(consoleInput.nextLine().trim());

            for (Learner pupil : pupilCollection) {
                if (pupil.getPupilCode() == code) {
                    System.out.println("Pupil Found\n");
                    System.out.println(pupil.toVerboseDescription());
                    return;
                }
            }

            System.out.println("Pupil not found.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Pupil Code must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 4. Revise Pupil
    // ---------------------------------------------------------------
    private static void reviseEntry() {
        try {
            System.out.print("Enter Pupil Code: ");
            int code = Integer.parseInt(consoleInput.nextLine().trim());

            Learner learner = pupilDirectory.get(code);
            if (learner == null) {
                System.out.println("Pupil not found.");
                return;
            }

            System.out.print("Enter New Name: ");
            String fullName = consoleInput.nextLine().trim();
            if (fullName.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter New Age: ");
            int yearsOld = Integer.parseInt(consoleInput.nextLine().trim());
            if (yearsOld <= 0) {
                System.out.println("Invalid Age.");
                return;
            }

            System.out.print("Enter New Faculty: ");
            String faculty = consoleInput.nextLine().trim();
            if (faculty.isEmpty()) {
                System.out.println("Faculty cannot be empty.");
                return;
            }

            System.out.print("Enter New Rating: ");
            double rating = Double.parseDouble(consoleInput.nextLine().trim());
            if (rating < 0 || rating > 100) {
                System.out.println("Rating should be between 0 and 100.");
                return;
            }

            learner.setFullName(fullName);
            learner.setYearsOld(yearsOld);
            learner.setFaculty(faculty);
            learner.setRating(rating);

            // pupilCollection, pupilDirectory and pupilOrderedDirectory all hold
            // references to the same Learner object, so all three stay in sync.

            System.out.println("Pupil updated successfully.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    // ---------------------------------------------------------------
    // 5. Discard Pupil
    // ---------------------------------------------------------------
    private static void discardPupil() {
        try {
            System.out.print("Enter Pupil Code: ");
            int code = Integer.parseInt(consoleInput.nextLine().trim());

            Learner learner = pupilDirectory.get(code);
            if (learner == null) {
                System.out.println("Pupil not found.");
                return;
            }

            pupilCollection.remove(learner);
            pupilDirectory.remove(code);
            pupilOrderedDirectory.remove(code);

            System.out.println("Pupil discarded successfully.");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Pupil Code must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 6. List Pupils by Faculty
    // ---------------------------------------------------------------
    private static void listPupilsByFaculty() {
        System.out.print("Enter Faculty: ");
        String faculty = consoleInput.nextLine().trim();

        System.out.println("--------- " + faculty.toUpperCase() + " PUPILS ---------\n");

        boolean found = false;
        for (Learner pupil : pupilCollection) {
            if (pupil.getFaculty().equalsIgnoreCase(faculty)) {
                System.out.println(pupil.toFacultyLine());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pupils found in this faculty.");
        }
    }

    // ---------------------------------------------------------------
    // 7. Show Top Performer (highest rating)
    // ---------------------------------------------------------------
    private static void showTopPerformer() {
        if (pupilCollection.isEmpty()) {
            System.out.println("No pupil records found.");
            return;
        }

        Learner topPerformer = pupilCollection.get(0);
        for (Learner pupil : pupilCollection) {
            if (pupil.getRating() > topPerformer.getRating()) {
                topPerformer = pupil;
            }
        }

        System.out.println("====================================");
        System.out.println("          TOP PERFORMER");
        System.out.println("====================================\n");
        System.out.println("Pupil Code : " + topPerformer.getPupilCode());
        System.out.println("Name       : " + topPerformer.getFullName());
        System.out.println("Faculty    : " + topPerformer.getFaculty());
        System.out.println("Rating     : " + topPerformer.getRating());
        System.out.println("\n====================================");
    }

    // ---------------------------------------------------------------
    // 8. Reveal Pupil Directory (HashMap)
    // ---------------------------------------------------------------
    private static void revealDirectoryContents() {
        if (pupilDirectory.isEmpty()) {
            System.out.println("No pupil records found.");
            return;
        }

        System.out.println("====================================");
        System.out.println("         PUPIL DIRECTORY");
        System.out.println("====================================\n");

        for (Map.Entry<Integer, Learner> entry : pupilDirectory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getFullName());
        }
    }

    // ---------------------------------------------------------------
    // 9. Find Pupil via Directory
    // ---------------------------------------------------------------
    private static void findPupilViaDirectory() {
        try {
            System.out.print("Enter Pupil Code: ");
            int code = Integer.parseInt(consoleInput.nextLine().trim());

            Learner learner = pupilDirectory.get(code);
            if (learner == null) {
                System.out.println("Pupil not found.");
                return;
            }

            System.out.println("Pupil Found\n");
            System.out.println("Code       : " + learner.getPupilCode());
            System.out.println("Name       : " + learner.getFullName());
            System.out.println("Faculty    : " + learner.getFaculty());
            System.out.println("Rating     : " + learner.getRating());

        } catch (NumberFormatException ex) {
            System.out.println("Invalid input. Pupil Code must be numeric.");
        }
    }

    // ---------------------------------------------------------------
    // 10. List Pupils Using LinkedHashMap
    // ---------------------------------------------------------------
    private static void listPupilsOrdered() {
        if (pupilOrderedDirectory.isEmpty()) {
            System.out.println("No pupil records found.");
            return;
        }

        System.out.println("Insertion Order Maintained\n");

        for (Map.Entry<Integer, Learner> entry : pupilOrderedDirectory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue().getFullName());
        }
    }
}
