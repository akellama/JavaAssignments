package com.pupilregistry;

/**
 * Represents a single learner record.
 *
 * Supports two usage styles:
 *  1. Original style - three subject scores (scoreOne, scoreTwo, scoreThree)
 *  2. PupilRegistryHubAdvanced style - a faculty + single overall rating value
 */
public class Learner {

    int pupilCode;
    String fullName;
    int yearsOld;
    double scoreOne;
    double scoreTwo;
    double scoreThree;

    // Extra fields needed by PupilRegistryHubAdvanced
    private String faculty;
    private double rating;

    // ---------------------------------------------------------------
    // Original constructor (subject-scores style)
    // ---------------------------------------------------------------
    public Learner(int pupilCode, String fullName, int yearsOld,
                   double scoreOne, double scoreTwo, double scoreThree) {
        this.pupilCode = pupilCode;
        this.fullName = fullName;
        this.yearsOld = yearsOld;
        this.scoreOne = scoreOne;
        this.scoreTwo = scoreTwo;
        this.scoreThree = scoreThree;

        // Keep "rating" in sync so getRating()/toTabularLine() etc. still work
        this.rating = (scoreOne + scoreTwo + scoreThree) / 3;
    }

    // ---------------------------------------------------------------
    // New constructor (faculty + single rating style) - used by
    // PupilRegistryHubAdvanced's enrollNewPupil()
    // ---------------------------------------------------------------
    public Learner(int pupilCode, String fullName, int yearsOld, String faculty, double rating) {
        this.pupilCode = pupilCode;
        this.fullName = fullName;
        this.yearsOld = yearsOld;
        this.faculty = faculty;
        this.rating = rating;
    }

    /** Displays this learner's full details. */
    void showPupilInfo() {
        double aggregate = this.scoreOne + this.scoreTwo + this.scoreThree;
        double meanValue = aggregate / 3;

        System.out.println("Pupil Code     : " + this.pupilCode);
        System.out.println("Full Name      : " + this.fullName);
        System.out.println("Years Old      : " + this.yearsOld);
        System.out.println("Score 1        : " + this.scoreOne);
        System.out.println("Score 2        : " + this.scoreTwo);
        System.out.println("Score 3        : " + this.scoreThree);
        System.out.printf("Aggregate      : %.2f%n", aggregate);
        System.out.printf("Mean Score     : %.2f%n", meanValue);
    }

    // ---------------------------------------------------------------
    // Getters / setters needed by PupilRegistryHubAdvanced
    // ---------------------------------------------------------------
    public int getPupilCode() {
        return pupilCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearsOld() {
        return yearsOld;
    }

    public void setYearsOld(int yearsOld) {
        this.yearsOld = yearsOld;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Row format used for the "List All Pupils" table.
     */
    public String toTabularLine() {
        return String.format("%-8d %-11s %-6d %-15s %-6.1f",
                pupilCode, fullName, yearsOld, faculty, rating);
    }

    /**
     * Compact row format used for faculty-wise listing.
     */
    public String toFacultyLine() {
        return String.format("%-5d %-8s %-7s %-6.1f",
                pupilCode, fullName, faculty, rating);
    }

    /**
     * Detailed multi-line format used for search results / top learner.
     */
    public String toVerboseDescription() {
        StringBuilder builder = new StringBuilder();
        builder.append("Code       : ").append(pupilCode).append("\n");
        builder.append("Name       : ").append(fullName).append("\n");
        builder.append("Age        : ").append(yearsOld).append("\n");
        builder.append("Faculty    : ").append(faculty).append("\n");
        builder.append("Rating     : ").append(rating);
        return builder.toString();
    }
}
