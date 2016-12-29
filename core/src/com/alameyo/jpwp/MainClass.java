package com.alameyo.jpwp;

import com.alameyo.jpwp.screens.GameScreen;
import com.alameyo.jpwp.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
 * Main class of game, which is invocated
 * by main method in one of game's launchers.
 */
public class MainClass extends Game {
    SpriteBatch batch;
        
	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}



	@Override
	public void dispose() {
		batch.dispose();
		super.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
}
