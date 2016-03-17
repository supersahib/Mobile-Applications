package com.udacity.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Supersahib on 1/23/16.
 */
public class PowerUps {

    Viewport viewport;
    DelayedRemovalArray<PowerUp> powerUpList; //array of powerups

    //constructor
    public PowerUps(Viewport viewport){
        this.viewport = viewport;
        init();
    }

    public void init() {
        powerUpList = new DelayedRemovalArray<PowerUp>(false, 3); //the screen will not show more than 3 power-ups
    }

    public void update(float delta){

        //iterate through array to update individual PowerUps
        for(PowerUp power: powerUpList) {
            power.update(delta);
        }

        //DelayedRemovalArray.begin()
        powerUpList.begin();
        for(int i = 0; i < powerUpList.size; i++){
            if(powerUpList.get(i).position.y + Constants.POWER_UP_RADIUS < 0){ //if the icicle is not in the World anymore, remove it
                powerUpList.removeIndex(i);
            }
        }
        powerUpList.end(); //end
    }

    public void render(ShapeRenderer renderer){
        //render each icicle
        for(PowerUp power: powerUpList){
            power.render(renderer);
        }
    }

    //single power-up class
    static class PowerUp {
        /* ------------------------ Declarations ------------------- */
        Vector2 position;
        Vector2 velocity;
        float startx;
        long startTime;
        POWER_UP_TYPE type;

        //Constructor
        public PowerUp(Vector2 position) {
            this.position = position;
            init();
        }

        //init method
        public void init() {
            velocity = Constants.POWER_UP_SPEED;
            startx = position.x;
            startTime = TimeUtils.nanoTime();
            randomType();
        }

        //render POWER-UP
        public void render(ShapeRenderer renderer)  {
            renderer.setColor(type.color);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.circle(position.x, position.y, Constants.POWER_UP_RADIUS);
        }

        //update position (x and y). power up moves down(y) and back-and-forth(x)
        public void update(float delta) {

            long elapsedNanos = TimeUtils.nanoTime() - startTime;
            float elapsedSeconds = MathUtils.nanoToSec * elapsedNanos;
            float cycles = elapsedSeconds / Constants.POWER_UP_PERIOD;
            float cyclePosition = cycles % 1;
            float newX = startx + Constants.POWER_UP_MOVEMENT_DISTANCE_X * MathUtils.sin(MathUtils.PI2 * cyclePosition);

            position.x = newX;
            position.y += velocity.y * delta;
        }

        //selects type of power-up randomly
        public void randomType(){
            float random = MathUtils.random() * 100;
            if(random < 20){
                type = POWER_UP_TYPE.DECREASE_LENGTH;
            }
            else if (20 < random && random < 80){
                type = POWER_UP_TYPE.INCREASE_LENGTH;
            }
            else{
                type = POWER_UP_TYPE.LIFE;
            }
        }

        //check if power-up is Out-Of-Bounds
        public boolean isOutOfBounds() {
            return (position.y + Constants.POWER_UP_RADIUS < 0);
        }

    }

    // enumeration for Type of power-up
    public enum POWER_UP_TYPE
    {
        INCREASE_LENGTH(Color.BLUE),
        DECREASE_LENGTH(Color.RED),
        LIFE(Color.PINK);

        Color color;

        POWER_UP_TYPE(Color color){
            this.color = color;
        }
    }
}
