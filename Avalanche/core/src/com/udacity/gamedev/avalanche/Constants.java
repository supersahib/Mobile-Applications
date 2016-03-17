package com.udacity.gamedev.avalanche;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Supersahib on 1/14/16.
 * Constants were used in all stub-files, making it easy to replace any values.
 */
public class Constants {

    // World Size
    public static final float WORLD_SIZE = 480.0f;

    // Background Color
    public static final Color BACKGROUND_COLOR = Color.BLUE;

    // Icicle Height
    public static  final float ICICLE_HEIGHT = 50.0f;

    // Icicle Width
    public static final float ICICLE_WIDTH = 25.0f;

    // Icicle Color
    public static final Color ICICLE_COLOR = Color.WHITE;

    // Player Head Radius
    public static final float PLAYER_HEAD_RADIUS = 25.0f;

    // Player Height
    public static final float PLAYER_HEAD_HEIGHT = 4.0f * PLAYER_HEAD_RADIUS;

    // Player Limb Width
    public static final float PLAYER_LIMB_WIDTH = 7.0f;

    // Segments used to create Player Head (circle is made of triangle segments)
    public static final int PLAYER_HEAD_SEGMENTS = 20;

    // Player Color
    public static final Color PLAYER_COLOR = Color.BLACK;

    // Player Movement Speed
    public static final float MOVEMENT_SPEED = 200.0f;

    // Accelerometer Sensitivity
    public static final float ACCELEROMETER_SENSITIVITY = 0.2f;

    // Gravitational Acceleration
    public static final float GRAVITATIONAL_ACCELERATION = 9.8f;

    // Icicles Acceleration Vector (2D)
    public static final Vector2 ICICLES_ACCELERATION = new Vector2(0, -110.0f);

    // HUD Font ScreenSize
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;

    // text Scale
    public static final float TEXT_SCALE = 3.0f;

    // HUD Margin from Edges of screen
    public static final float HUD_MARGIN = 20.0f;


    // Difficulty Labels Easy, Medium, Hard
    public static final String EASY_LABEL = "Cold";
    public static final String MEDIUM_LABEL = "Colder";
    public static final String HARD_LABEL = "Coldest";

    // Difficulty Spawn Rates
    public static final float EASY_SPAWNS_PER_SECOND = 5;
    public static final float MEDIUM_SPAWNS_PER_SECOND = 15;
    public static final float HARD_SPAWNS_PER_SECOND = 25;

    // Difficulty Button Colors on DifficultyScreen
    public static Color EASY_COLOR = Color.SKY;
    public static Color MEDIUM_COLOR = Color.CYAN;
    public static Color HARD_COLOR = Color.NAVY;


    // Difficulty Screen World Size
    public static final float DIFFICULTY_WORLD_SIZE = 480.0f;

    // Difficulty Button Radius
    public static final float DIFFICULTY_BUBBLE_RADIUS = DIFFICULTY_WORLD_SIZE / 9;

    // DifficultyScreen Text Scale
    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;

    // Vector(2D) representing center of Difficulty Buttons
    public static final Vector2 EASY_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 4, DIFFICULTY_WORLD_SIZE / 2);
    public static final Vector2 MEDIUM_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 2, DIFFICULTY_WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE * 3 / 4, DIFFICULTY_WORLD_SIZE / 2);


    // Difficulty enum holding the spawn rate and label for each difficulty
    public enum Difficulty {
        EASY(EASY_SPAWNS_PER_SECOND, EASY_LABEL),
        MEDIUM(MEDIUM_SPAWNS_PER_SECOND, MEDIUM_LABEL),
        HARD(HARD_SPAWNS_PER_SECOND, HARD_LABEL);

        float spawnRate;
        String label;

        //initialize spawnRate and Label
        Difficulty(float spawnRate, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
        }
    }

}
