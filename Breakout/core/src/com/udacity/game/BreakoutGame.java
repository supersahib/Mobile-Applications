package com.udacity.game;

import com.badlogic.gdx.Game;
import com.udacity.game.Constants.Difficulty;


public class BreakoutGame extends Game {

	
	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}

	public void showFieldOfPlayScreen (Difficulty difficulty){
		setScreen(new FieldOfPlayScreen(this, difficulty));
	}

}
