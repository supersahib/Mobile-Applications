package com.udacity.gamedev.avalanche;


import com.udacity.gamedev.avalanche.Constants.Difficulty;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

/**
 * Created by Supersahib on 1/14/16.
 */
public class IciclesScreen extends InputAdapter implements Screen {

    //declarations
    //batches
    ShapeRenderer renderer;
    ExtendViewport extendViewport;
    ScreenViewport textViewport;
    SpriteBatch batch;
    BitmapFont font;


    Icicles icicle;
    Player player;

    int topScore; //topScore
    int deathCount; //number of deaths

    Difficulty difficulty;
    AvalancheGame game;

    //Constructor
    public IciclesScreen(AvalancheGame game, Difficulty difficulty){
        this.game = game;
        this.difficulty = difficulty;
    }

    //start of screen
    @Override
    public void show() {
        //Viewport set to World Size Constants
        extendViewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        textViewport = new ScreenViewport();

        batch = new SpriteBatch(); // text batch
        font = new BitmapFont(); //font batch
        font.getData().setScale(Constants.TEXT_SCALE); //set font scale
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear); //set font texture filter

        player = new Player(extendViewport);
        icicle = new Icicles(extendViewport, difficulty);
        topScore = 0;
    }


    //resize Viewports and reset Font batch scale
    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height, true);
        textViewport.update(width, height, true);

        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);

        player.init();
    }

    @Override
    public void render(float delta){
        //update icicle and player
        icicle.update(delta);
        player.update(delta, extendViewport);

        //check if player was hit by icicle
        if (player.hitByIcicle(icicle)){
            deathCount++; //increase deathcount
            icicle.init(); // reset player
        }

        extendViewport.apply(); //apply viewport



        //set ShapeRenderer Batch Projection Matrix to viewport (world's) projection matrix
        renderer.setProjectionMatrix(extendViewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);
        //render icicle and player
        icicle.render(renderer);
        player.render(renderer);
        renderer.end();

        //Set Projection matrix and begin Text Batch (SpriteBatch)
        topScore = Math.max(topScore, icicle.icicleCounter);
        textViewport.apply();                                       //Apply text viewport
        batch.setProjectionMatrix(textViewport.getCamera().combined);

        batch.begin();
        //deathcount
        font.draw(batch, "Deaths: " + deathCount + "\nDifficulty: " + difficulty.label,
                Constants.HUD_MARGIN, textViewport.getWorldHeight() - Constants.HUD_MARGIN);

        // Current Score and Top Score
        font.draw(batch, "Score: " + icicle.icicleCounter + "\nTop Score: " + topScore,
                textViewport.getWorldWidth() - Constants.HUD_MARGIN, textViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        //end batch
        batch.end();
    }

    //nothing
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
    }

    //touch event
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.showDifficultyScreen();
        return true;
    }


    //dispose of batches
    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();


    }
}
