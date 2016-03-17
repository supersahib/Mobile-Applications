package com.udacity.gamedev.avalanche;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import javax.swing.text.View;

/**
 * Created by Supersahib on 1/14/16.
 */
public class Icicle {
    //Vector(2D) for position and velocity
    Vector2 position;
    Vector2 velocity;

    //Constructor
    public Icicle(Vector2 position, Vector2 velocity){
        this.position = position;
        this.velocity = velocity;
    }

    //update icicle position and velocity vectors
    public void update(float delta){
        velocity.mulAdd(Constants.ICICLES_ACCELERATION, delta);
        position.mulAdd(velocity, delta);
    }

    //render icicle using Constants
    public void render(ShapeRenderer renderer){
        renderer.setColor(Constants.ICICLE_COLOR);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.triangle(position.x, position.y, position.x - (Constants.ICICLE_WIDTH/2), position.y + Constants.ICICLE_HEIGHT, position.x + (Constants.ICICLE_WIDTH/2), position.y + Constants.ICICLE_HEIGHT);
    }
}


