package com.udacity.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/**
 * Created by Supersahib on 1/15/16.
 * Brick Objet class
 */
public class Brick {

    //declarations
    Vector2 position;
    int health;
    Texture brickTexture;

    //Constructor
    public Brick(Vector2 position, int brickHealth){
        this.position = position;
        this.health = brickHealth;
    }

    public void update(float delta){
    }

    //render method
    public void render(SpriteBatch brickBatch, AtlasRegion region){
        brickBatch.begin();

        //get region of texture and draw brick
        brickBatch.draw(region.getTexture(), position.x, position.y, position.x, position.y,
                Constants.BRICK_WIDTH, Constants.BRICK_HEIGHT, 1, 1, 0,
                region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
                false, false);
        brickBatch.end();
    }

    //collision with ball
    public boolean collideWithBall(Ball ball){

        //BOTTOM
        Vector2 point1 = new Vector2(position.x, position.y);
        Vector2 point2 = new Vector2(position.x + Constants.BRICK_WIDTH, position.y);
        //distance between bottom segment and center of ball
        float distanceBottomToCenter = (Intersector.distanceSegmentPoint(point1, point2, ball.position));
        boolean didBallHitBottom = (distanceBottomToCenter < Constants.BALL_RADIUS);
        //reflect in y component if collision occured and return true
        if(didBallHitBottom){
            ball.velocity.y = - ball.velocity.y;
            return true;
        }

        //TOP
        point1 = new Vector2(position.x, position.y + Constants.BRICK_HEIGHT);
        point2 = new Vector2(position.x + Constants.BRICK_WIDTH, position.y + Constants.BRICK_HEIGHT);
        //distance between top segment and center of ball
        float distanceTopToCenter = (Intersector.distanceSegmentPoint(point1, point2, ball.position));
        boolean didBallHitTop = (distanceTopToCenter < Constants.BALL_RADIUS);

        //reflect in y component if collision occured and return true
        if(didBallHitTop){
            ball.velocity.y = - ball.velocity.y;
            return true;
        }


        //LEFT
        point1 = new Vector2(position.x, position.y);
        point2 = new Vector2(position.x, position.y + Constants.BRICK_HEIGHT);
        //distance between LEFT segment and center of ball
        float distanceLeftToCenter = (Intersector.distanceSegmentPoint(point1, point2, ball.position));
        boolean didBallHitLeft = (distanceLeftToCenter < Constants.BALL_RADIUS);

        //reflect in x component if collision occured and return true
        if(didBallHitLeft){
            ball.velocity.x = - ball.velocity.x;
            return true;
        }

        //RIGHT
        point1 = new Vector2(position.x + Constants.BRICK_WIDTH, position.y);
        point2 = new Vector2(position.x + Constants.BRICK_WIDTH, position.y + Constants.BRICK_HEIGHT);
        ////distance between RIGHT segment and center of ball
        float distanceRightToCenter = (Intersector.distanceSegmentPoint(point1, point2, ball.position));
        boolean didBallHitRight = (distanceRightToCenter < Constants.BALL_RADIUS);

        //reflect in x component if collision occured and return true
        if(didBallHitRight){
            ball.velocity.x = - ball.velocity.x;
            return true;
        }

        return false;
    }
}