package com.udacity.gamedev.avalanche;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.udacity.gamedev.avalanche.Constants.Difficulty;

public class AvalancheGame extends Game{


	@Override
	public void create () {

		showDifficultyScreen();
	}

	public void showDifficultyScreen() {
		setScreen(new DifficultyScreen(this));
	}

	public void showIciclesScreen(Difficulty difficulty) {
		//set initial Screen to difficulty Screen
		setScreen(new IciclesScreen(this, difficulty));
	}
}
