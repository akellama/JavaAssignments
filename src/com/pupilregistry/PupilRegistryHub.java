package com.pupilregistry;

import java.util.Scanner;

/**
 * Pupil Registry Hub
 * Core Java Integrated Assignment
 * <p>
 * Demonstrates: variables, operators, if/else, switch, type casting,
 * Scanner, for/while/do-while loops, nested loops, 1D & 2D arrays,
 * methods, method overloading, classes, objects, constructors, this keyword.
 * <p>
 * No ArrayList / Collections / Streams / Lambdas / external libraries used.
 */
public class PupilRegistryHub {

    // ---------- Shared Scanner and learner storage ----------
    static Scanner inputReader = new Scanner(System.in);

    static final int MAX_PUPILS = 10;
    static Learner[] pupilRoster = new Learner[MAX_PUPILS];
    static int rosterSize = 0; // how many slots in 'pupilRoster' are filled

    public static void main(String[] args) {

        int menuSelection = 0;

        // ----- WHILE LOOP: keep running until user selects Exit -----
        while (menuSelection != 8) {

            showMenu();
            menuSelection = readInteger("Enter your choice: ");

            switch (menuSelection) {
                case 1:
                    enrollPupil();
                    break;
                case 2:
                    listAllPupils();
                    break;
                case 3:
                    findPupil();
                    break;
                case 4:
                    showReport();
                    break;
                case 5:
                    reviseScores();
                    break;
                case 6:
                    showClassStats();
                    break;
                case 7:
                    showScoresBySubject();
                    break;
                case 8:
                    System.out.println("\nExiting Pupil Registry Hub. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid choice. Please enter a number between 1 and 8.");
            }
        }

        inputReader.close();
    }

    // =========================================================
    // MENU
    // =========================================================
    static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("         PUPIL REGISTRY HUB");
        System.out.println("========================================\n");
        System.out.println("1. Enroll Pupil");
        System.out.println("2. List All Pupils");
        System.out.println("3. Find Pupil");
        System.out.println("4. Show Pupil Report");
        System.out.println("5. Revise Pupil Scores");
        System.out.println("6. Class Statistics");
        System.out.println("7. Subject-wise Scores");
        System.out.println("8. Exit\n");
    }

    // =========================================================
    // 1. ENROLL PUPIL
    // =========================================================
    static void enrollPupil() {

        // Array capacity check
        if (rosterSize >= MAX_PUPILS) {
            System.out.println("\nCannot enroll more pupils. Maximum limit (" + MAX_PUPILS + ") reached.");
            return;
        }

        System.out.println();
        int code = readInteger("Enter Pupil Code: ");

        // Pupil Code must be positive
        if (code <= 0) {
            System.out.println("Invalid Pupil Code. Code must be a positive number.");
            return;
        }

        // Pupil Code must not be duplicated
        if (locatePupilPosition(code) != -1) {
            System.out.println("A pupil with code " + code + " already exists.");
            return;
        }

        System.out.print("Enter Pupil Name: ");
        String fullName = inputReader.nextLine();

        int yearsOld = readInteger("Enter Age: ");

        // Age should be reasonable
        if (yearsOld <= 0 || yearsOld > 100) {
            System.out.println("Invalid age. Age must be between 1 and 100.");
            return;
        }

        // DO-WHILE LOOP: repeated validation for each subject's score
        double s1 = promptForValidScore("Enter Subject 1 Score: ");
        double s2 = promptForValidScore("Enter Subject 2 Score: ");
        double s3 = promptForValidScore("Enter Subject 3 Score: ");

        Learner freshLearner = new Learner(code, fullName, yearsOld, s1, s2, s3);
        pupilRoster[rosterSize] = freshLearner;
        rosterSize += 1; // assignment + arithmetic operator usage

        System.out.println("\nPupil enrolled successfully.");
    }

    /**
     * Uses a do-while loop to keep asking until score is within 0-100.
     */
    static double promptForValidScore(String question) {
        double scoreValue;
        do {
            scoreValue = readDecimal(question);
            if (scoreValue < 0 || scoreValue > 100) {
                System.out.println("Invalid score. Please enter a value between 0 and 100.");
            }
        } while (scoreValue < 0 || scoreValue > 100); // relational + logical operators
        return scoreValue;
    }

    // =========================================================
    // 2. LIST ALL PUPILS
    // =========================================================
    static void listAllPupils() {

        if (rosterSize == 0) {
            System.out.println("\nNo pupils to display.");
            return;
        }

        System.out.println("\n--------------------------------------------------------");
        System.out.printf("%-8s%-14s%-8s%-8s%-8s%-8s%n", "Code", "Name", "Age", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------------");

        // FOR LOOP: traverse the pupil array
        for (int i = 0; i < rosterSize; i++) {
            Learner pupil = pupilRoster[i];
            System.out.printf("%-8d%-14s%-8d%-8.1f%-8.1f%-8.1f%n",
                    pupil.pupilCode, pupil.fullName, pupil.yearsOld,
                    pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree);
        }

        System.out.println("--------------------------------------------------------");
    }

    // =========================================================
    // 3. FIND PUPIL
    // =========================================================
    static void findPupil() {
        System.out.println();
        int code = readInteger("Enter Pupil Code: ");

        int position = locatePupilPosition(code);

        if (position == -1) {
            System.out.println("\nPupil not found.");
        } else {
            System.out.println();
            pupilRoster[position].showPupilInfo();
        }
    }

    /**
     * Uses a for loop and conditional statements to locate a pupil by code.
     * Returns the array position, or -1 if not found.
     */
    static int locatePupilPosition(int code) {
        for (int i = 0; i < rosterSize; i++) {
            if (pupilRoster[i].pupilCode == code) { // relational operator
                return i;
            }
        }
        return -1;
    }

    // =========================================================
    // 4. SHOW PUPIL REPORT
    // =========================================================
    static void showReport() {
        System.out.println();
        int code = readInteger("Enter Pupil Code: ");

        int position = locatePupilPosition(code);
        if (position == -1) {
            System.out.println("\nPupil not found.");
            return;
        }

        Learner pupil = pupilRoster[position];

        double aggregate = computeAggregate(pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree);
        double meanValue = computeMean(pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree);
        double scorePercent = meanValue; // out of 100 per subject, so mean == percent
        String letterGrade = determineGrade(scorePercent);
        boolean hasPassed = hasQualified(pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree);

        System.out.println("\n====================================");
        System.out.println("          PUPIL REPORT");
        System.out.println("====================================\n");
        System.out.println("Pupil Code     : " + pupil.pupilCode);
        System.out.println("Name           : " + pupil.fullName);
        System.out.println("Age            : " + pupil.yearsOld);
        System.out.println();
        System.out.println("Subject 1      : " + pupil.scoreOne);
        System.out.println("Subject 2      : " + pupil.scoreTwo);
        System.out.println("Subject 3      : " + pupil.scoreThree);
        System.out.println();
        System.out.printf("Aggregate      : %.2f%n", aggregate);
        System.out.printf("Mean           : %.2f%n", meanValue);
        System.out.printf("Percentage     : %.2f%%%n", scorePercent);
        System.out.println("Grade          : " + letterGrade);
        System.out.println("Status         : " + (hasPassed ? "PASS" : "FAIL"));
        System.out.println("\n====================================");
    }

    // =========================================================
    // 5. REVISE SCORES
    // =========================================================
    static void reviseScores() {
        System.out.println();
        int code = readInteger("Enter Pupil Code: ");

        int position = locatePupilPosition(code);
        if (position == -1) {
            System.out.println("\nPupil not found.");
            return;
        }

        Learner pupil = pupilRoster[position];

        double s1 = promptForValidScore("Enter new Subject 1 Score: ");
        double s2 = promptForValidScore("Enter new Subject 2 Score: ");
        double s3 = promptForValidScore("Enter new Subject 3 Score: ");

        pupil.scoreOne = s1;
        pupil.scoreTwo = s2;
        pupil.scoreThree = s3;

        System.out.println("\nPupil scores revised successfully.");
    }

    // =========================================================
    // 6. CLASS STATISTICS
    // =========================================================
    static void showClassStats() {

        if (rosterSize == 0) {
            System.out.println("\nNo pupils available for statistics.");
            return;
        }

        double totalPercent = 0;
        double topScore = -1;
        double bottomScore = 101;
        int passTally = 0;
        int failTally = 0;

        // FOR LOOP: calculate statistics across all pupils
        for (int i = 0; i < rosterSize; i++) {
            Learner pupil = pupilRoster[i];
            double scorePercent = computeMean(pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree);

            totalPercent += scorePercent; // compound assignment operator

            if (scorePercent > topScore) {
                topScore = scorePercent;
            }
            if (scorePercent < bottomScore) {
                bottomScore = scorePercent;
            }

            if (hasQualified(pupil.scoreOne, pupil.scoreTwo, pupil.scoreThree)) {
                passTally++;
            } else {
                failTally++;
            }
        }

        double meanPercent = totalPercent / rosterSize; // type-cast-safe: both doubles

        System.out.println("\n====================================");
        System.out.println("          CLASS STATISTICS");
        System.out.println("====================================\n");
        System.out.println("Number of Pupils   : " + rosterSize);
        System.out.printf("Mean Percentage    : %.2f%%%n", meanPercent);
        System.out.printf("Top Percentage     : %.2f%%%n", topScore);
        System.out.printf("Bottom Percentage  : %.2f%%%n", bottomScore);
        System.out.println("Pass Tally         : " + passTally);
        System.out.println("Fail Tally         : " + failTally);
        System.out.println("\n====================================");
    }

    // =========================================================
    // 7. SUBJECT-WISE SCORES  (uses a 2D array + nested loops)
    // =========================================================
    static void showScoresBySubject() {

        if (rosterSize == 0) {
            System.out.println("\nNo pupils available.");
            return;
        }

        // Build a 2D array: rows = pupils, columns = subjects
        double[][] scoreGrid = new double[rosterSize][3];

        // NESTED LOOP: fill the 2D matrix from pupil objects
        for (int i = 0; i < rosterSize; i++) {
            double[] rowScores = {pupilRoster[i].scoreOne, pupilRoster[i].scoreTwo, pupilRoster[i].scoreThree};
            for (int j = 0; j < rowScores.length; j++) {
                scoreGrid[i][j] = rowScores[j];
            }
        }

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-9s%-14s%-10s%-10s%-10s%n", "Code", "Name", "Sub1", "Sub2", "Sub3");
        System.out.println("--------------------------------------------------");

        double[] subjectAggregates = new double[3];

        // NESTED LOOP: display matrix and accumulate subject aggregates
        for (int i = 0; i < rosterSize; i++) {
            System.out.printf("%-9d%-14s", pupilRoster[i].pupilCode, pupilRoster[i].fullName);
            for (int j = 0; j < 3; j++) {
                System.out.printf("%-10.1f", scoreGrid[i][j]);
                subjectAggregates[j] += scoreGrid[i][j];
            }
            System.out.println();
        }

        System.out.println("--------------------------------------------------");

        // Type casting used to safely compute a mean from a sum
        double avgOne = (double) subjectAggregates[0] / rosterSize;
        double avgTwo = (double) subjectAggregates[1] / rosterSize;
        double avgThree = (double) subjectAggregates[2] / rosterSize;

        System.out.printf("Subject 1 Mean : %.2f%n", avgOne);
        System.out.printf("Subject 2 Mean : %.2f%n", avgTwo);
        System.out.printf("Subject 3 Mean : %.2f%n", avgThree);
    }

    // =========================================================
    // CALCULATION METHODS (with overloading)
    // =========================================================

    /**
     * Overload 1: aggregate of two int scores
     */
    static int computeAggregate(int scoreA, int scoreB) {
        return scoreA + scoreB;
    }

    /**
     * Overload 2: aggregate of three double scores
     */
    static double computeAggregate(double scoreA, double scoreB, double scoreC) {
        return scoreA + scoreB + scoreC;
    }

    /**
     * Overload 1: mean of two int scores
     */
    static double computeMean(int scoreA, int scoreB) {
        return (double) (scoreA + scoreB) / 2; // type casting to avoid integer division
    }

    /**
     * Overload 2: mean of three int scores
     */
    static double computeMean(int scoreA, int scoreB, int scoreC) {
        int aggregate = scoreA + scoreB + scoreC;
        return (double) aggregate / 3; // type casting requirement
    }

    /**
     * Overload 3: mean of three double scores
     */
    static double computeMean(double scoreA, double scoreB, double scoreC) {
        double aggregate = scoreA + scoreB + scoreC;
        return aggregate / 3;
    }

    /**
     * Determines letter grade from percentage using if/else.
     */
    static String determineGrade(double scorePercent) {
        String letterGrade;
        if (scorePercent >= 90) {
            letterGrade = "A+";
        } else if (scorePercent >= 80) {
            letterGrade = "A";
        } else if (scorePercent >= 70) {
            letterGrade = "B";
        } else if (scorePercent >= 60) {
            letterGrade = "C";
        } else if (scorePercent >= 50) {
            letterGrade = "D";
        } else if (scorePercent >= 40) {
            letterGrade = "E";
        } else {
            letterGrade = "F";
        }
        return letterGrade;
    }

    /**
     * A pupil qualifies only if all three subject scores are >= 40.
     */
    static boolean hasQualified(double s1, double s2, double s3) {
        return s1 >= 40 && s2 >= 40 && s3 >= 40; // relational + logical AND
    }

    // =========================================================
    // INPUT HELPER METHODS (Scanner handling + validation loops)
    // =========================================================

    /**
     * Reads an integer safely, re-prompting on invalid (non-numeric) input.
     */
    static int readInteger(String question) {
        int parsed;
        while (true) {
            System.out.print(question);
            String rawInput = inputReader.nextLine().trim();
            try {
                parsed = Integer.parseInt(rawInput);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
        return parsed;
    }

    /**
     * Reads a double safely, re-prompting on invalid (non-numeric) input.
     */
    static double readDecimal(String question) {
        double parsed;
        while (true) {
            System.out.print(question);
            String rawInput = inputReader.nextLine().trim();
            try {
                parsed = Double.parseDouble(rawInput);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
        return parsed;
    }
}
