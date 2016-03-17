package com.udacity.gamedev.avalanche;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Supersahib on 1/14/16.
 */
public class Player {
    //Declarations
    Vector2 position;
    Vector2 velocity;
    Viewport viewport;

    //Constructor
    public Player(Viewport viewport){
        this.viewport = viewport;
        init();
    }

    //Initial player position and velocity vectors
    public void init(){
        position = new Vector2(viewport.getWorldWidth() / 2, Constants.PLAYER_HEAD_HEIGHT);
        velocity = new Vector2(0,0);
    }

    //update player position based on input (left or Right key input OR Accelerometer input)
    //Checks if player is in bounds
    public void update(float delta, Viewport viewport){
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            position.x -= delta * Constants.MOVEMENT_SPEED;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            position.x += delta * Constants.MOVEMENT_SPEED;
        }

        float accelerometerInput = -Gdx.input.getAccelerometerY() / (Constants.GRAVITATIONAL_ACCELERATION * Constants.ACCELEROMETER_SENSITIVITY);

        //Use the accelerometer input to move the player
        position.x += -delta * accelerometerInput * Constants.MOVEMENT_SPEED;

        ensureInBounds();
    }

    //checks if player was hit by icicle
    public boolean hitByIcicle(Icicles icicles) {

        boolean isHit = false;

        //iterate through array of icicles on the screen
        for (Icicle ice : icicles.icicleList) {
            //if position of icicle is inside player's head, he dies
            if (ice.position.dst(position) < Constants.PLAYER_HEAD_RADIUS) {
                isHit = true;
            }
        }
        return isHit;
    }

    //check if player is in bounds
    public void ensureInBounds(){
        if(position.x - Constants.PLAYER_HEAD_RADIUS < 0){
            position.x = Constants.PLAYER_HEAD_RADIUS;
        }
        if(position.x + Constants.PLAYER_HEAD_RADIUS > viewport.getWorldWidth()){
            position.x = viewport.getWorldWidth() - Constants.PLAYER_HEAD_RADIUS;
        }
    }

    //Render player by drawing heads and limbs. Player was drawn using Constants in Constants.java

    public void render(ShapeRenderer renderer){
        //Player Color
        renderer.setColor(Constants.PLAYER_COLOR);
        renderer.set(ShapeRenderer.ShapeType.Filled);

        //headVpubK
        renderer.circle(position.x, position.y, Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_HEAD_SEGMENTS);

        //Torso Vectors and then rectangle drawing the torso
        Vector2 torsoTop = new Vector2(position.x, position.y - Constants.PLAYER_HEAD_RADIUS);
        Vector2 torsoBottom = new Vector2(torsoTop.x, torsoTop.y - 2 * Constants.PLAYER_HEAD_RADIUS);
        renderer.rectLine(torsoTop, torsoBottom, Constants.PLAYER_LIMB_WIDTH);


        //Limbs
        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x + Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
        renderer.rectLine(
                torsoTop.x, torsoTop.y,
                torsoTop.x - Constants.PLAYER_HEAD_RADIUS, torsoTop.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x + Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
        renderer.rectLine(
                torsoBottom.x, torsoBottom.y,
                torsoBottom.x - Constants.PLAYER_HEAD_RADIUS, torsoBottom.y - Constants.PLAYER_HEAD_RADIUS, Constants.PLAYER_LIMB_WIDTH);
    }

}
