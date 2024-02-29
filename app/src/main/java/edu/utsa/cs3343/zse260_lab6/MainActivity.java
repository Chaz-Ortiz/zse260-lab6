package edu.utsa.cs3343.zse260_lab6;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.utsa.cs3343.zse260_lab6.R;
import edu.utsa.cs3343.zse260_lab6.model.AvengerTask;
import edu.utsa.cs3343.zse260_lab6.model.Team;


//**********************************************************************************************
//
//  This version has all the Avengers.
//  Attempt to get the methods in the correct
//  classes for MVC architecture.
//
//**********************************************************************************************
/**
 * The main activity class representing the Avengers application.
 * This class is responsible for handling the user interface and coordinating
 * the display of Avengers information.
 * @author Chaz Ortiz (zse260)
 * UTSA CS 3443 - Lab 5
 * Fall 2023
 */
public class MainActivity extends AppCompatActivity {

    private ImageView map, photoImageView;
    private View[] dots;
    private TextView realNameText, aliasText, heightText, weightText, timestampText;
    private Handler handler;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        map = findViewById(R.id.map);
        photoImageView = findViewById(R.id.photo);

        // Array of dot colors
        int[] dotColors = new int[]{
                R.color.dot_color_1,
                R.color.dot_color_2,
                R.color.dot_color_3,
                R.color.dot_color_4,
                R.color.dot_color_5,
                R.color.dot_color_6,
                R.color.dot_color_7,
                R.color.dot_color_8
        };

        // Initialize dots array
        dots = new View[]{
                findViewById(R.id.dot1),
                findViewById(R.id.dot2),
                findViewById(R.id.dot3),
                findViewById(R.id.dot4),
                findViewById(R.id.dot5),
                findViewById(R.id.dot6),
                findViewById(R.id.dot7),
                findViewById(R.id.dot8)
        };

        // Set up click listeners for each dot
        for (int i = 0; i < dots.length; i++) {
            final int dotIndex = i;

            // Set background color for each dot
            dots[i].setBackgroundColor(ContextCompat.getColor(this, dotColors[i]));

            // Set click listener for each dot
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle dot click
                    updateViewsFromCSVData(dotIndex);
                    updateImageView(dotIndex);
                }
            });
        }

        // Initialize TextViews
        realNameText = findViewById(R.id.realName_text);
        aliasText = findViewById(R.id.alias_text);
        heightText = findViewById(R.id.height_text);
        weightText = findViewById(R.id.weight_text);
        timestampText = findViewById(R.id.timestamp_text);

        // Set OnClickListener for each dot
        for (View dot : dots) {
            dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Find the index of the clicked dot
                    int dotIndex = -1;
                    for (int i = 0; i < dots.length; i++) {
                        if (view.getId() == dots[i].getId()) {
                            dotIndex = i;
                            break;
                        }
                    }

                    // Display data for the clicked dot
                    if (dotIndex != -1) {
                        updateViewsFromCSVData(dotIndex);
                        // Update the image based on the dot clicked
                        updateImageView(dotIndex);
                    }
                }
            });
        }

        // Initialize handler
        handler = new Handler();

        // Initialize executor service for background tasks
        executorService = Executors.newSingleThreadExecutor();


        // Create an instance of Team and load Avengers data
        Team team = new Team();
        team.loadAvengers("data.csv");

        // Create an instance of AvengerTask and run it every 10 seconds
        AvengerTask avengerTask = new AvengerTask(this, team);
        handler.postDelayed(avengerTask, 900);   // 10000 for 10 seconds, edited for testing
    }

    // Getter methods for AvengerTask to access required members
    public Handler getHandler() {
        return handler;
    }

    public View[] getDots() {
        return dots;
    }

    public int getMapWidth() {
        return map.getWidth();
    }

    public int getMapHeight() {
        return map.getHeight();
    }

    // Runnable to update dot positions
    private final Runnable dotUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            // Update positions of all dots
            updateDotPositions();

            // Display timestamp in the timestampText
            displayTimestamp();

            // Repeat the updateDotPositions method every 10 seconds
            handler.postDelayed(this, 900);  // 10000 for 10 seconds, edited for testing
        }
    };

    // Method to update dot positions randomly with a minimum distance from the edges
    private void updateDotPositions() {
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();
        Random random = new Random();
        int minDistance = 50; // Minimum distance from the edges

        // Update each dot's position with a 10% chance
        for (View dot : dots) {
            if (random.nextDouble() <= 0.75) { // (edited) 33% chance
                int x = random.nextInt(mapWidth - 2 * minDistance) + minDistance;
                int y = random.nextInt(mapHeight - 2 * minDistance) + minDistance;
                updateDotPosition(dot, x, y);
            }
        }
    }

    // Method to update the position of a single dot
    public void updateDotPosition(View dot, int x, int y) {
        // Calculate the position relative to the map's top-left corner
        dot.setX(x);
        dot.setY(y);
    }

    // Method to update the image of photoImageView based on the dot clicked
    private void updateImageView(int dotIndex) {
        String imageName;
        switch (dotIndex) {
            case 0:
                imageName = "captain_america";
                break;
            case 1:
                imageName = "hulk";
                break;
            case 2:
                imageName = "thor";
                break;
            case 3:
                imageName = "black_widow";
                break;
            case 4:
                imageName = "dr_strange";
                break;
            case 5:
                imageName = "iron_man";
                break;
            case 6:
                imageName = "black_panther";
                break;
            case 7:
                imageName = "hawkeye";
                break;
            default:
                imageName = "shield"; // default image name
                break;
        }

        // Load and set the image for the photoImageView from the drawable folder
        int photoResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoImageView.setImageResource(photoResId);
            }
        });
    }

    // Method to read data from CSV file and update TextViews and ImageView for the specified dot
    private void updateViewsFromCSVData(int dotIndex) {
        // Assuming the CSV file is in the assets folder with the name "data.csv"
        String csvFileName = "data2.csv";

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getAssets().open(csvFileName);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = null;
                    // Read the desired line from CSV based on dotIndex
                    for (int i = 0; i <= dotIndex; i++) {
                        line = bufferedReader.readLine(); // Read lines until the desired line
                    }

                    // Read the line corresponding to the dotIndex
                    //String line = bufferedReader.readLine();

                    if (line != null) {
                        // Split the line into tokens
                        String[] tokens = line.split(",");

                        // Display tokens in TextViews
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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

                        int photoResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
        });
    }



    // Method to display timestamp in the timestampText
    public void displayTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
        String timestamp = dateFormat.format(new Date());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timestampText.setText("Last Updated: " + timestamp);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown executor service when the activity is destroyed
        executorService.shutdown();
    }
}