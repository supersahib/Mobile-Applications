package com.udacity.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.game.PowerUps.POWER_UP_TYPE;

import java.sql.Time;

/**
 * Created by Supersahib on 1/15/16.
 * Player Panel object class
 */
public class PlayerPanel {

/* ------------------------ Declarations ------------------- */
    Vector2 position;
    Vector2 velocity;
    Viewport viewport;
    Texture playerTexture;
    long powerUpLength;
    float powerUpEffect;
    int lives;
    long timeOfLastCollision;


    //Constructor
    public PlayerPanel(Viewport viewport){
        this.viewport = viewport;
        this.playerTexture =  new Texture("data/platform.png");
        init();
    }

    //init method
    public void init(){
        position = Constants.PLAYER_PANEL_LOWER_CORNER;
        velocity = new Vector2(0, 0);
        powerUpEffect = 0;
        lives = 3;
        timeOfLastCollision = TimeUtils.nanoTime();

    }

    //update playerPanel position based on player input
    public void update(float delta){

        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            position.x -= delta * Constants.PLAYER_MOVEMENT_SPEED;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            position.x += delta * Constants.PLAYER_MOVEMENT_SPEED;
        }
        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime() - powerUpLength) > 10000) {
            Constants.PLAYER_PANEL_WIDTH = Constants.ORIGINAL_PLAYER_PANEL_WIDTH;
        }

        float accelerometerInput = -Gdx.input.getAccelerometerY() / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);

        position.x += -delta * accelerometerInput * Constants.PLAYER_MOVEMENT_SPEED;

        //ensure the player is in-bounds
        ensureInBounds();


    }

    //ensure if player is in-bounds
    public void ensureInBounds(){
        if(position.x < 0){
            position.x = 0;
        }
        if(position.x + Constants.PLAYER_PANEL_WIDTH > viewport.getWorldWidth()){
            position.x = viewport.getWorldWidth() - Constants.PLAYER_PANEL_WIDTH;
        }
    }

    //render method
    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(playerTexture, position.x, position.y, Constants.PLAYER_PANEL_WIDTH, Constants.PLAYER_PANEL_HEIGHT);
        batch.end();
    }

    //check for collision with ball and reflect ball appropriately
    public void collideWithBall(Ball ball){
        boolean wasBallHitRecently = TimeUtils.nanosToMillis(TimeUtils.nanoTime() - timeOfLastCollision) < 300;
        //top
        Vector2 point1 = new Vector2(position.x, position.y + Constants.PLAYER_PANEL_HEIGHT);
        Vector2 point2 = new Vector2(position.x + Constants.PLAYER_PANEL_WIDTH, position.y + Constants.PLAYER_PANEL_HEIGHT);

        //distance between top segment and center of ball
        float distanceTopToCenter = (Intersector.distanceSegmentPoint(point1, point2, ball.position));
        boolean didBallHitTop = (distanceTopToCenter < Constants.BALL_RADIUS);

        //if ball hit top of player, reflect appropriately
        if (didBallHitTop && !wasBallHitRecently) {
            //Need to work on the launch velocity from the platform
            // ball should reflect proportional to distance from center
            ball.velocity.x = (ball.position.x - (position.x + (Constants.PLAYER_PANEL_WIDTH/2))) * Constants.PLAYER_MOVEMENT_SPEED/Constants.PLAYER_PANEL_WIDTH;
            ball.velocity.y = - ball.velocity.y;

            timeOfLastCollision = TimeUtils.nanoTime();
        }
    }


    //checks if player was hit by Power-Up
    public void hitByPowerUp(PowerUps powerUps) {

        boolean didPowerUpHit = false;

        //iterate through array of Power-Ups on the screen
        for (int i = 0; i < powerUps.powerUpList.size; i++) {
            //top
            Vector2 point1 = new Vector2(position.x, position.y + Constants.PLAYER_PANEL_HEIGHT);
            Vector2 point2 = new Vector2(position.x + Constants.PLAYER_PANEL_WIDTH, position.y + Constants.PLAYER_PANEL_HEIGHT);

            //distance between top segment and center of ball
            float distanceTopToCenter = (Intersector.distanceSegmentPoint(point1, point2, powerUps.powerUpList.get(i).position));
            didPowerUpHit = (distanceTopToCenter < Constants.POWER_UP_RADIUS);

            if(didPowerUpHit){
                powerUpLength = TimeUtils.nanoTime();
                executePowerUp(powerUps.powerUpList.get(i).type);
                powerUps.powerUpList.removeIndex(i);
            }
        }
    }

    public void executePowerUp(POWER_UP_TYPE type) {

        switch (type){
            case INCREASE_LENGTH:
                Constants.PLAYER_PANEL_WIDTH += 50;
                break;
            case DECREASE_LENGTH:
                Constants.PLAYER_PANEL_WIDTH -= 50;
                break;
            case LIFE:
                lives++;
                break;
        }
    }
}
