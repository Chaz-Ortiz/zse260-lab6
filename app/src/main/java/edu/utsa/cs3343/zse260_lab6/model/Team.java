package edu.utsa.cs3343.zse260_lab6.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Represents a team of Avengers.
 * The team can be loaded with data from a CSV file, and it can update the locations of its Avengers.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class Team {

    /**
     * The list of Avengers in the team.
     */
    private ArrayList<Avenger> avengers;

    /**
     * Constructs a new Team with an empty list of Avengers.
     */
    public Team() {
        this.avengers = new ArrayList<>();
    }  // constructor

    /**
     * Method to load Avengers data from a CSV file and add them to the team.
     * @param csvFileName The name of the CSV file containing Avengers data.
     */
    public void loadAvengers(String csvFileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvFileName)) {
            if (inputStream != null) {
                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                     BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                    String line;
                    // Read each line from the CSV file
                    while ((line = bufferedReader.readLine()) != null) {
                        // Split the line into tokens using comma as the delimiter
                        String[] tokens = line.split(",");

                        // Assuming the structure of your CSV, create and add Avenger objects
                        try {
                            Avenger avenger = new Avenger(
                                    tokens[0], // realName
                                    tokens[1], // alias
                                    tokens[2], // gender
                                    Integer.parseInt(tokens[3]), // height
                                    Integer.parseInt(tokens[4]), // colorCode
                                    Integer.parseInt(tokens[5]), // weight
                                    Boolean.parseBoolean(tokens[6]), // Boolean for super powers or not
                                    Integer.parseInt(tokens[7]), // latitude
                                    Integer.parseInt(tokens[8])  // longitude
                            );
                            avengers.add(avenger);
                        } catch (NumberFormatException e) {
                            // Handle the exception, e.g., log it, skip the line, or take appropriate action
                            System.err.println("Error parsing CSV line: " + line);
                        }
                    }
                }
            } else {
                // Handle the case where the resource is not found
                System.err.println("CSV file not found: " + csvFileName);
            }
        } catch (IOException e) {
            // Handle IOException, e.g., print stack trace or log the error
            e.printStackTrace();
        }
    }

    /**
     * Updates the locations of all Avengers in the team.
     */
    public void updateAvengerLocations() {
        for (Avenger avenger : avengers) {
            avenger.updateLocation();
        }
    }

    /**
     * Method to retrieve an Avenger from the team based on the alias.
     * @param alias The alias of the Avenger to retrieve.
     * @return The Avenger with the specified alias, or null if not found.
     */
    public Avenger getAvenger(String alias) {
        for (Avenger avenger : avengers) {
            if (avenger.getAlias().equals(alias)) {
                return avenger;
            }
        }
        return null; // Return null if the Avenger is not found
    }

    /**
     * Retrieves an array of all Avengers in the team.
     * @return An array containing all Avengers in the team.
     */
    public Avenger[] getAvengers() {
        return avengers.toArray(new Avenger[0]);
    }
}