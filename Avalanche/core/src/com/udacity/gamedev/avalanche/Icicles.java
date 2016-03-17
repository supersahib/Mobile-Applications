package com.udacity.gamedev.avalanche;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Array;
import com.udacity.gamedev.avalanche.Constants.Difficulty;

/**B
 * Created by Supersahib on 1/14/16.
 */
public class Icicles {
    public static final String TAG = Icicles.class.getName();

    //declarations
    Viewport viewport;
    int icicleCounter; //icicle that has passed the screen counts as a point

    Difficulty difficulty; //level difficulty
    DelayedRemovalArray<Icicle> icicleList; //array of icicles

    //initializatio
    public Icicles(Viewport viewport, Difficulty difficulty){
        this.viewport = viewport;
        this.difficulty = difficulty;
        init();
    }


    public void init() {
        icicleList = new DelayedRemovalArray<Icicle>(false, 100); //the screen will not show more than 100 icicles
        icicleCounter = 0; //score starts at 0
    }

    public void update(float delta){
        //spawn if necessary
        if(MathUtils.random() < delta * difficulty.spawnRate){
            Icicle newIcicle = new Icicle(new Vector2(MathUtils.random() * viewport.getWorldWidth(), viewport.getWorldHeight()), new Vector2(0,0));
            icicleList.add(newIcicle);
        }

        //iterate through array to update individual icicles
        for(Icicle ice: icicleList) {
            ice.update(delta);
        }

        //DelayedRemovalArray.begin()
        icicleList.begin();
        for(int i = 0; i < icicleList.size; i++){
            if(icicleList.get(i).position.y + Constants.ICICLE_HEIGHT < 0){ //if the icicle is not in the World anymore, remove it
                icicleCounter++;
                icicleList.removeIndex(i);
            }
        }
        icicleList.end(); //end
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(Constants.ICICLE_COLOR);

        //render each icicle
        for(Icicle ice: icicleList){
            ice.render(renderer);
        }
    }
}
