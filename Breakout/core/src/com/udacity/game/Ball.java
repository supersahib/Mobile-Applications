package com.udacity.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.game.Constants.Difficulty;

/**
 * Created by Supersahib on 1/15/16.
 * Ball Object class
 */
public class Ball {

/* ------------------------ Declarations ------------------- */
    Viewport viewport;
    Vector2 position;
    Vector2 velocity;
    Difficulty difficulty;


    //Constructor
    public Ball(Viewport viewport, Difficulty difficulty){
        this.viewport = viewport;
        this.difficulty = difficulty;
        init();
    }

    //init method
    public void init(){
        position = new Vector2(viewport.getWorldWidth()/2, 45);
        velocity = new Vector2(difficulty.ballSpeed.x, difficulty.ballSpeed.y);
    }

    //render ball
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Constants.BALL_COLOR);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, Constants.BALL_RADIUS);
    }

    //update position (x and y components) based on velocity and check for collisions with walls
    public void update(float delta){
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        collideWithWalls(Constants.BALL_RADIUS, viewport.getWorldWidth(), viewport.getWorldHeight());
    }


    //check if ball is Out-Of-Bounds
    public boolean isOutOfBounds(){
        return(position.y + Constants.BALL_RADIUS < 0);
    }

    //check if ball collides with walls and reflect appropriately
    public void collideWithWalls(float radius, float viewportWidth, float viewportHeight) {
        //left wall
        if (position.x - radius < 0) {
            position.x = radius;
            velocity.x = -velocity.x;
        }
        //right wall
        if (position.x + radius > viewportWidth) {
            position.x = viewportWidth - radius;
            velocity.x = -velocity.x;
        }
        //top wall
        if (position.y + radius > viewportHeight) {
            position.y = viewportHeight - radius;
            velocity.y = -velocity.y;
        }
    }
}
