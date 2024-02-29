package edu.utsa.cs3343.zse260_lab6.model;

import java.util.Random;
import edu.utsa.cs3343.zse260_lab6.MainActivity;

/**
 * Represents a task for updating Avenger positions at regular intervals.
 * Implements the {@link Runnable} interface to be executed in a separate thread.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class AvengerTask implements Runnable {

    private MainActivity mainActivity;

    /**
     * Constructs an AvengerTask with the specified mainActivity and team.
     *
     * @param mainActivity The main activity where the updates will be displayed.
     * @param team         The team of Avengers.
     */
    public AvengerTask(MainActivity mainActivity, edu.utsa.cs3343.zse260_lab6.model.Team team) {
        this.mainActivity = mainActivity;
    }

    /**
     * Executes the AvengerTask, updating positions of all dots and displaying timestamps.
     * It repeats the updateDotPositions method every specified interval.
     */
    @Override
    public void run() {
        // Update positions of all dots
        updateDotPositions();

        // Display timestamp in the timestampText
        mainActivity.displayTimestamp();

        // Repeat the updateDotPositions method every 10 seconds
        mainActivity.getHandler().postDelayed(this, 3000); // 10000 for 10 seconds. (edited)
    }

    /**
     * Updates the positions of dots randomly based on a chance.
     */
    private void updateDotPositions() {
        Random random = new Random();

        // Update each dot's position with a 10% chance
        for (int i = 0; i < mainActivity.getDots().length; i++) {
            if (random.nextDouble() <= 0.10) { // 10% chance
                int x = random.nextInt(mainActivity.getMapWidth());
                int y = random.nextInt(mainActivity.getMapHeight());
                mainActivity.updateDotPosition(mainActivity.getDots()[i], x, y);
            }
        }
    }
}
