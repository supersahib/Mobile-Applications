package com.udacity.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Supersahib on 1/15/16.
 * Constants Class
 */
public class Constants {

/* ---------------------- CONSTANTS USED FOR START SCREEN ----------------------------- */
    public static final float START_SCREEN_SIZE = 480.0f;
    // Difficulty Button Radius
    public static final float DIFFICULTY_BUBBLE_RADIUS = START_SCREEN_SIZE / 9;

    // DifficultyScreen Text Scale
    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;

    // Vector(2D) representing center of Difficulty Buttons
    public static final Vector2 EASY_CENTER = new Vector2(START_SCREEN_SIZE / 4, START_SCREEN_SIZE / 2);
    public static final Vector2 MEDIUM_CENTER = new Vector2(START_SCREEN_SIZE / 2, START_SCREEN_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(START_SCREEN_SIZE * 3 / 4, START_SCREEN_SIZE / 2);

    // Difficulty Button Colors on DifficultyScreen
    public static final Color EASY_COLOR = Color.SKY;
    public static final Color MEDIUM_COLOR = Color.CYAN;
    public static final Color HARD_COLOR = Color.NAVY;

    // Difficulty Labels Easy, Medium, Hard
    public static final String EASY_LABEL = "Easy";
    public static final String MEDIUM_LABEL = "Medium";
    public static final String HARD_LABEL = "Hard";


    // Difficulty enum holding: seconds till next spawn, brick health, label, and ball speed
    public enum Difficulty {
        EASY(EASY_MILLI_SECONDS_TILL_NEXT_SPAWN, EASY_BRICK_HEALTH, EASY_LABEL, EASY_BALL_SPEED),
        MEDIUM(MEDIUM_MILLI_SECONDS_TILL_NEXT_SPAWN, MEDIUM_BRICK_HEALTH,  MEDIUM_LABEL, MEDIUM_BALL_SPEED),
        HARD(HARD_MILLI_SECONDS_TILL_NEXT_SPAWN, HARD_BRICK_HEALTH, HARD_LABEL, HARD_BALL_SPEED);

        float spawnMilliSeconds;
        int brickHealth;
        String label;
        Vector2 ballSpeed;

        //initialize spawnRate and Label
        Difficulty(float milliSeconds, int brickHealth, String label, Vector2 ballSpeed) {
            this.spawnMilliSeconds = milliSeconds;
            this.brickHealth = brickHealth;
            this.label = label;
            this.ballSpeed = ballSpeed;
        }
    }


/* ---------------------- CONSTANTS USED FOR FIELD OF PLAY SCREEN (main screen) ----------------------------- */
    // World Size
    public static final float WORLD_SIZE = 480.0f;

    // Background Color
    public static final Color BACKGROUND_COLOR = Color.BLACK;


    //player panel dimensions and start position and color
    public static float PLAYER_PANEL_WIDTH = 150.0f; // i change this when a power-up is picked up
    public static final float ORIGINAL_PLAYER_PANEL_WIDTH = 150.0f; // change back to this width after power-up decays
    public static final float PLAYER_PANEL_HEIGHT = 20.0f;
    public static final float PLAYER_PANEL_LOWER_CORNER_X = WORLD_SIZE/2;
    public static final float PLAYER_PANEL_LOWER_CORNER_Y = 10;
    public static final Vector2 PLAYER_PANEL_LOWER_CORNER = new Vector2(PLAYER_PANEL_LOWER_CORNER_X,PLAYER_PANEL_LOWER_CORNER_Y);

    //ball radius, color, and start position
    public static final float BALL_RADIUS= 10.0f;
    public static final Color BALL_COLOR = Color.WHITE;
    public static final Vector2 EASY_BALL_SPEED = new Vector2(100, 100);
    public static final Vector2 MEDIUM_BALL_SPEED = new Vector2(150, 150);
    public static final Vector2 HARD_BALL_SPEED = new Vector2(200, 200);


    //Text Scales
    public static final float TEXT_SCALE = 3.0f;
    public static final float GAME_OVER_TEXT_SCALE = 3.0f;
    public static final float GAME_OVER_MARGIN = 190.0f;

    // HUD Font ScreenSize
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;

    // HUD Margin from Edges of screen
    public static final float HUD_MARGIN = 20.0f;

    //player Movement speed
    public static final float PLAYER_MOVEMENT_SPEED = 350.0f;

    // Accelerometer Sensitivity
    public static final float ACCELEROMETER_SENSITIVITY = 0.2f;

    // Gravitational Acceleration
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f;


    //Brick Dimensions & Color & SpawnRate & Health
    public static final float BRICK_WIDTH = 76.5f;
    public static final float BRICK_HEIGHT = 36.0f;

    public static final float BRICK_X_OFFSET = 8.0f;
    public static final float BRICK_Y_OFFSET = 8.0f;

    public static final int EASY_BRICK_HEALTH = 1;
    public static final int MEDIUM_BRICK_HEALTH = 2;
    public static final int HARD_BRICK_HEALTH = 3;

    public static final float EASY_MILLI_SECONDS_TILL_NEXT_SPAWN = 30000; // 30 seconds
    public static final float MEDIUM_MILLI_SECONDS_TILL_NEXT_SPAWN = 25000; // 25 seconds
    public static final float HARD_MILLI_SECONDS_TILL_NEXT_SPAWN = 17000; // 17 seconds



    //Power-up radius, speed, period, Movement Distance
    public static final float POWER_UP_RADIUS = 10.0f;
    public static final Vector2 POWER_UP_SPEED = new Vector2(0, -100);
    public static final float POWER_UP_PERIOD = 1;
    public static final float POWER_UP_MOVEMENT_DISTANCE_X = WORLD_SIZE / 20;
}
