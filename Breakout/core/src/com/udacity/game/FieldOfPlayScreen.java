package com.udacity.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.udacity.game.Constants.Difficulty;

/**
 * Created by Supersahib on 1/15/16.
 * Main game screen (FieldOfPlayScreen
 */
public class FieldOfPlayScreen extends InputAdapter implements Screen{

/* ------------------------ Declarations ------------------- */
    //Viewport
    ExtendViewport viewport;
    ScreenViewport textViewport;

    //Game, state of game, difficulty
    BreakoutGame game;
    State state;
    Difficulty difficulty;

    //ShapeRenderer and SpriteBatch's
    ShapeRenderer renderer;
    SpriteBatch batch;
    SpriteBatch brickBatch;

    //BitMapFont for Score/Lives and GameOver message
    BitmapFont font;
    BitmapFont gameOver;

    //Objects
    Ball ball;
    Bricks brickList;
    PlayerPanel player;
    PowerUps powerUpList;

    //Lives
    int life;
    long gameOverTime;


    //Constructor
    public FieldOfPlayScreen(BreakoutGame game, Difficulty difficulty){
        this.game = game;
        this.difficulty = difficulty;
    }

    //intializations
    public void show() {
        //Viewport initialization
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        textViewport = new ScreenViewport();

        //Renderer and Spritebatch initialization
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        brickBatch = new SpriteBatch();

        //BitmapFont initialization, scaling, and filtering
        font = new BitmapFont(); //font batch
        font.getData().setScale(Constants.TEXT_SCALE); //set font scale
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear); //set font texture filter

        gameOver = new BitmapFont();
        gameOver.getData().setScale(Constants.GAME_OVER_TEXT_SCALE);
        gameOver.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //initialization of objects (ball, Bricks, player, PowerUps)
        ball = new Ball(viewport, difficulty);
        powerUpList = new PowerUps(viewport);
        brickList = new Bricks(viewport, ball, powerUpList, difficulty);
        player = new PlayerPanel(viewport);

        //Lives and Game State initialization
        life = 3;
        state = State.RUN;
    }

    //render
    public void render(float delta) {
        //apply viewport
        viewport.apply();

        //stuff you have to do lol
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Projection matrix of batches and shaprenderer = viewports projection matrix
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        batch.setProjectionMatrix(textViewport.getCamera().combined);
        brickBatch.setProjectionMatrix(viewport.getCamera().combined);

        //If Game State is RUN
        if(state == State.RUN){
            //update objects
            ball.update(delta);
            player.update(delta);
            brickList.update(delta);
            powerUpList.update(delta);

            //check if ball is out of bounds. Decrease lives and reset ball if it is
            if(ball.isOutOfBounds()){
                player.lives--;
                ball.init();
            }

            //Game state = GAME_OVER is lives is less than 0
            if(player.lives <= 0){
                state = State.GAME_OVER;
                ball.velocity = new Vector2(0, 0);
                gameOverTime = TimeUtils.nanoTime();
            }

            //check for collisions of player with ball/power-ups and Bricks with ball
            player.collideWithBall(ball);
            player.hitByPowerUp(powerUpList);
            brickList.collideWithBricks(ball);

            //render objects with ShapeRenderer(ball and power-ups) or Spritebatch (player and bricks)
            renderer.begin(ShapeType.Filled);
            ball.render(renderer);
            powerUpList.render(renderer);
            player.render(brickBatch);
            brickList.render(brickBatch);
            renderer.end();

            //if all bricks are destroyed, then change game state to GAME_WON
            if(brickList.isEmpty()){
                state = State.GAME_WON;
                gameOverTime = TimeUtils.nanoTime();
            }
        }

        // drawing out current Lives, Difficulty, Score, and End Message (if necessary)
        batch.begin();
        textViewport.apply();

        drawScoreAndLives(font, batch, textViewport);
        drawEndMessage(gameOver, batch, textViewport, state);

        batch.end();
    }


    //Game State Enumeration
    public enum State
    {
        RUN,
        GAME_OVER,
        GAME_WON
    }


    //draws HUD for score and lives
    public void drawScoreAndLives(BitmapFont font, SpriteBatch batch, Viewport textViewport){
        //lives and difficulty
        font.draw(batch, "Lives: " + player.lives + "\nDifficulty: "  + difficulty.label,
                Constants.HUD_MARGIN, textViewport.getWorldHeight() - Constants.HUD_MARGIN);

        // Current Score and Top Score
        font.draw(batch, "Score: " + brickList.brickCounter,
                textViewport.getWorldWidth() - Constants.HUD_MARGIN, textViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);
    }


    //shows either "GAME OVER" or "GAME WON" message, depending on the state of the game
    public void drawEndMessage(BitmapFont gameOver, SpriteBatch batch, Viewport textViewport, State state){
        //display GAME_OVER message for 5 seconds and go back to Difficulty Screen
        if(state == State.GAME_OVER){
            gameOver.draw(batch, "GAME OVER", Constants.GAME_OVER_MARGIN, textViewport.getWorldHeight() /2);
            if(TimeUtils.nanosToMillis(TimeUtils.nanoTime() - gameOverTime) > 5000){
                game.create();
            }
        }

        //display GAME_WON message for 5 seconds and go back to Difficulty Screen
        if(state == State.GAME_WON){
            gameOver.draw(batch, "GOOD JOB!\n YOU WON!", Constants.GAME_OVER_MARGIN, textViewport.getWorldHeight() /2);
            if(TimeUtils.nanosToMillis(TimeUtils.nanoTime() - gameOverTime) > 5000){
                game.create();
            }
        }
    }


    //resize viewports
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        textViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
        ball.init();
        brickList.init();
    }

    //dispose of ShapeRenderer and Spritebatches
    public void dispose() {
        renderer.dispose();
        batch.dispose();
        brickBatch.dispose();
    }

    //Empty
    public void pause() {
    }
    public void resume() {
    }
    public void hide() {
    }
}
