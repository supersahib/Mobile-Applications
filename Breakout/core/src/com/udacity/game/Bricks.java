package com.udacity.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector2;
import com.udacity.game.Constants.Difficulty;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.udacity.game.PowerUps.*;

/**
 * Created by Supersahib on 1/15/16.
 * 2D array of Bricks class
 */
public class Bricks {
    public static final String TAG = Bricks.class.getName();

    /* ------------------------ Declarations ------------------- */
    Viewport viewport;

    //2D array of Bricks
    Brick[][] brickList;

    //ball and difficulty
    Ball ball;
    Difficulty difficulty;

    //Score
    int brickCounter;

    //AssetManager, TextureAtlas, and AtlasRegions of the textures
    AssetManager manager;
    AtlasRegion notDamaged;
    AtlasRegion slightlyDamaged;
    AtlasRegion reallyDamaged;
    TextureAtlas atlas;
    PowerUps powerUps;
    long timeSinceLastSpawn;


    //Constructor
    public Bricks(Viewport viewport, Ball ball, PowerUps powerUps, Difficulty difficulty) {
        this.viewport = viewport;
        this.ball = ball;
        this.powerUps = powerUps;
        this.difficulty = difficulty;
        init();
    }

    //init method
    public void init() {
        brickList = new Brick[4][10];
        brickCounter = 0;
        timeSinceLastSpawn = TimeUtils.nanoTime();

        //load AssetManager
        manager = new AssetManager();
        manager.load("data/breakout.pack.atlas", TextureAtlas.class);
        manager.finishLoading();
        atlas = manager.get("data/breakout.pack.atlas");

        //textures based on health of the brick
        notDamaged = atlas.findRegion("platform");
        slightlyDamaged = atlas.findRegion("slightlydamaged");
        reallyDamaged = atlas.findRegion("reallydamaged");

        //initialize 2D array of bricks with proper health
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[i].length; j++) {
                float xPosition = ((j + 1) * Constants.BRICK_X_OFFSET) + (j * Constants.BRICK_WIDTH);
                float yPosition = viewport.getWorldHeight() - ((i + 1) * (Constants.BRICK_Y_OFFSET + Constants.BRICK_HEIGHT));
                Vector2 position = new Vector2(xPosition, yPosition);
                brickList[i][j] = new Brick(position, difficulty.brickHealth);
            }
        }
    }


    //render method
    public void render(SpriteBatch brickBatch) {

        //iterate through 2D array
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[i].length; j++) {
                if (brickList[i][j] != null) {

                    //draw notDamaged texture on brick
                    if (brickList[i][j].health == 3) {
                        brickList[i][j].render(brickBatch, notDamaged);
                    }
                    //draw slightlyDamaged texture on brick
                    else if (brickList[i][j].health == 2) {
                        brickList[i][j].render(brickBatch, slightlyDamaged);
                    }
                    //draw reallyDamaged texture on brick
                    else if (brickList[i][j].health == 1) {
                        brickList[i][j].render(brickBatch, reallyDamaged);
                    }

                }
            }
        }
    }


    //check if ball collides with a brick.
    public void collideWithBricks(Ball ball) {
        //iterate through 2D array
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[i].length; j++) {
                if (brickList[i][j] != null) {                    //if brick is not null, then check collision
                    if (brickList[i][j].collideWithBall(ball)) {
                        brickList[i][j].health -= 1;            //if collided, then decrement health
                        if (brickList[i][j].health == 0) {      //remove brick if health = 0 and increment score
                            randomSpawnPowerUp(powerUps, brickList[i][j].position);
                            brickList[i][j] = null;
                            brickCounter++;
                        }
                    }
                }
            }
        }
    }

    public void randomSpawnPowerUp(PowerUps powerUps, Vector2 position) {
        int rand = (int) (MathUtils.random(0, 10));
        if (rand == 5) {
            powerUps.powerUpList.add(new PowerUp(position));
        }
    }


    public void update(float delta) {
        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime() - timeSinceLastSpawn) > difficulty.spawnMilliSeconds){
            randomSpawn();
            timeSinceLastSpawn = TimeUtils.nanoTime();
        }
    }

    //check if 2D array is full, return True if full
    public boolean isFull()     {
        boolean check = true;
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[i].length; j++) {
                if (brickList[i][j] == null) {
                    check = false;
                }
            }
        }
        return check;
    }

    //check if 2D array if empty, return false if there is a brick
    public boolean isEmpty() {
        boolean check = true;
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[i].length; j++) {
                if (brickList[i][j] != null) {
                    check = false;
                }
            }
        }
        return check;
    }


    public void randomSpawn() {
        for (int i = 0; i < brickList.length; i++) {
            for (int j = 0; j < brickList[0].length; j++) {
                if (brickList[i][j] == null) {
                    float xPosition = ((j + 1) * Constants.BRICK_X_OFFSET) + (j * Constants.BRICK_WIDTH);
                    float yPosition = viewport.getWorldHeight() - ((i + 1) * (Constants.BRICK_Y_OFFSET + Constants.BRICK_HEIGHT));
                    Vector2 position = new Vector2(xPosition, yPosition);
                    brickList[i][j] = new Brick(position, 1);

                    //reset
                    i = brickList.length;
                    j = brickList[0].length;
                }
            }
        }
    }
}
