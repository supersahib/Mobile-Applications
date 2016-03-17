package com.udacity.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.udacity.game.Constants.Difficulty;

/**
 * Created by Supersahib on 1/15/16.
 * StartScreen (difficulty Screen)
 */
public class StartScreen extends InputAdapter implements Screen {
    public static final String TAG = StartScreen.class.getName();

/* ------------------------ Declarations ------------------- */
    ShapeRenderer renderer;
    FitViewport viewport;
    SpriteBatch batch;
    BitmapFont font;
    BreakoutGame game;


    //Constructor
    public StartScreen(BreakoutGame game){
        this.game = game;
    }


    //intialize Renderer And Spritbatch (text)
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        //InputProcessor is used to receive input events from the keyboard and the touch screen
        Gdx.input.setInputProcessor(this);

        //initialize viewPort to our World Size
        viewport = new FitViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);

        //If true, when drawing a shape cannot be performed with the current shape type, the batch is flushed and the shape type is changed automatically.
        renderer.setAutoShapeType(true);

        //renders bitmapfont. Text is drawn using a batch
        font = new BitmapFont();
        font.getData().setScale(Constants.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }


    //render method, draw difficulty bubbles and their labels
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //set ShapeRenderer batch Projection Matrix to our World's (Viewport's) projection matrix
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        //ShapeType Filled
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //Constants used to draw Easy, Medium, Hard Buttons/Bubbles
        renderer.setColor(Constants.EASY_COLOR);
        renderer.circle(Constants.EASY_CENTER.x, Constants.EASY_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(Constants.MEDIUM_COLOR);
        renderer.circle(Constants.MEDIUM_CENTER.x, Constants.MEDIUM_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(Constants.HARD_COLOR);
        renderer.circle(Constants.HARD_CENTER.x, Constants.HARD_CENTER.y, Constants.DIFFICULTY_BUBBLE_RADIUS);
        renderer.end();


        //set SprieBatch's Projection Matrix to our World's (Viewport's) projection matrix
        batch.setProjectionMatrix(viewport.getCamera().combined);
        //begin spritebatch
        batch.begin();

        //using constants to draw Labels for the Easy, Medium, and Hard "Bubbles" or buttons
        final GlyphLayout easyLayout = new GlyphLayout(font, Constants.EASY_LABEL);
        font.draw(batch, Constants.EASY_LABEL, Constants.EASY_CENTER.x, Constants.EASY_CENTER.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, Constants.MEDIUM_LABEL);
        font.draw(batch, Constants.MEDIUM_LABEL, Constants.MEDIUM_CENTER.x, Constants.MEDIUM_CENTER.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.HARD_LABEL, Constants.HARD_CENTER.x, Constants.HARD_CENTER.y + hardLayout.height / 2, 0, Align.center, false);

        //end batch
        batch.end();
    }


    //resize viewport
    public void resize(int width, int height) {
        //update viewport width and height
        viewport.update(width, height, true);

    }


    //dispose of SpriteBatch, BitMapfont, and ShapeRenderer batches
    public void hide() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }


    //check if touch was within the difficulty bubbles to start game with correct difficulty
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Unproject the touch from the screen to the world
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        // Checks if the touch was inside a button, and launch the IciclesScreen with the appropriate difficulty

        //if touch was within Radius of Difficulty buttons
        if (worldTouch.dst(Constants.EASY_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showFieldOfPlayScreen(Difficulty.EASY);
        }

        if (worldTouch.dst(Constants.MEDIUM_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showFieldOfPlayScreen(Difficulty.MEDIUM);
        }

        if (worldTouch.dst(Constants.HARD_CENTER) < Constants.DIFFICULTY_BUBBLE_RADIUS) {
            game.showFieldOfPlayScreen(Difficulty.HARD);
        }
        //return True when action/event was handled
        return true;
    }


    //empty
    public void pause() {
    }
    public void resume() {
    }
    public void dispose() {
    }
}
