package edu.utsa.cs3343.zse260_lab6.model;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import edu.utsa.cs3343.zse260_lab6.R;

/**
 * Represents an Avenger, a superhero character with specific attributes.
 * Extends the {@link Person} class.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
/**
 * Represents an Avenger, a superhero character with specific attributes.
 * Extends the {@link Person} class.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class Avenger extends Person {

    private String alias;
    private boolean hasPowers;
    private int colorCode;
    private int latitude;
    private int longitude;

    // Constants for maximum latitude and longitude values
    private static final int MAX_LATITUDE = 10;
    private static final int MAX_LONGITUDE = 20;

    private TextView timestampText;
    /**
     * Constructs an Avenger with specified attributes.
     * @param realName   The real name of the Avenger.
     * @param alias      The alias or superhero name of the Avenger.
     * @param hasPowers  A boolean indicating whether the Avenger has superpowers.
     * @param colorCode  An integer representing the color code associated with the Avenger.
     * @param latitude   The latitude coordinate of the Avenger's location.
     * @param longitude  The longitude coordinate of the Avenger's location.
     */
    public Avenger(String realName, String alias, boolean hasPowers, int colorCode, int latitude, int longitude) {
        super(realName);
        // Existing code...
    }

    /**
     * Alternate constructor with parameters used in CSV data processing.
     * @param token       Tokens representing Avenger attributes from CSV data.
     * @param colorCode   An integer representing the color code associated with the Avenger.
     * @param latitude    The latitude coordinate of the Avenger's location.
     * @param longitude   The longitude coordinate of the Avenger's location.
     * @param parseBoolean A boolean parsed from CSV data indicating whether the Avenger has superpowers.
     * @param parseInt     The height of the Avenger parsed from CSV data.
     * @param parseInt1    The weight of the Avenger parsed from CSV data.
     */
    public Avenger(String token, String token1, String token2, int colorCode, int latitude, int longitude, boolean parseBoolean, int parseInt, int parseInt1) {
    }

    /**
     * Updates the views in the provided {@link Activity} based on CSV data and dot index.
     * @param dotIndex         The index of the dot representing the Avenger.
     * @param executorService  The executor service for background tasks.
     * @param activity         The activity containing the views to be updated.
     */
    public void updateViewsFromCSVData(int dotIndex, ExecutorService executorService, Activity activity) {
        // Assuming the CSV file is in the assets folder with the name "data.csv"
        String csvFileName = "data2.csv";

        try {
            InputStream inputStream = activity.getAssets().open(csvFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            // Read the desired line from CSV based on dotIndex
            for (int i = 0; i <= dotIndex; i++) {
                line = bufferedReader.readLine(); // Read lines until the desired line
            }

            if (line != null) {
                // Split the line into tokens
                String[] tokens = line.split(",");

                // Display tokens in TextViews
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView realNameText = activity.findViewById(R.id.realName_text);
                        TextView aliasText = activity.findViewById(R.id.alias_text);
                        TextView heightText = activity.findViewById(R.id.height_text);
                        TextView weightText = activity.findViewById(R.id.weight_text);

                        realNameText.setText(tokens[0]);
                        aliasText.setText(tokens[1]);
                        // Continue updating other TextViews as needed
                        heightText.setText(tokens[7]);
                        weightText.setText(tokens[8]);
                    }
                });

                // Load and set the image for the photoImageView from the drawable folder
                String imageName;
                switch (dotIndex) {
                    case 0:
                        imageName = "yamcha";  //captain_america
                        break;
                    case 1:
                        imageName = "vegeta2";  //hulk
                        break;
                    case 2:
                        imageName = "gohan2";  //thor
                        break;
                    case 3:
                        imageName = "finalformfrieza";  //black_widow
                        break;
                    case 4:
                        imageName = "piccolo3";  //dr_strange
                        break;
                    case 5:
                        imageName = "frieza";  //iron_man
                        break;
                    case 6:
                        imageName = "bulma3";  //black_panther
                        break;
                    case 7:
                        imageName = "ssjgoku2";  //hawkeye
                        break;

                    default:
                        imageName = "capsule"; // Change this to your default image name
                        break;
                }

                int photoResId = activity.getResources().getIdentifier(imageName, "drawable", activity.getPackageName());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView photoImageView = activity.findViewById(R.id.photo);
                        photoImageView.setImageResource(photoResId);
                    }
                });

                // Update timestampText with the current timestamp
                displayTimestamp();
            }

            // Close resources
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the timestamp in the timestampText view.
     */
    private void displayTimestamp() {
        if (timestampText != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
            String timestamp = dateFormat.format(new Date());
            timestampText.setText("Last Updated: " + timestamp);
        }
    }

    // ... (rest of the class)



    // Getter and Setter for alias
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    // Getter and Setter for hasPowers
    public boolean hasPowers() {
        return hasPowers;
    }

    public void setHasPowers(boolean hasPowers) {
        this.hasPowers = hasPowers;
    }

    // Getter and Setter for colorCode
    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    // Getter and Setter for latitude
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    // Getter and Setter for longitude
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    // Method to update Avenger location
    public void updateLocation() {
        Random random = new Random();
        if (random.nextDouble() <= 0.100) { // 10% chance
            setLatitude(random.nextInt(MAX_LATITUDE * 2) - MAX_LATITUDE); // Adjust MAX_LATITUDE according to your requirements
            setLongitude(random.nextInt(MAX_LONGITUDE * 2) - MAX_LONGITUDE); // Adjust MAX_LONGITUDE according to your requirements
        }
    }

    @Override
    public String toString() {
        return "Avenger{" +
                "alias='" + alias + '\'' +
                ", hasPowers=" + hasPowers +
                ", colorCode=" + colorCode +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                "} " + super.toString();
    }
}