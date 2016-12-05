package com.alameyo.jpwp;

import com.alameyo.jpwp.screens.GameScreen;
import com.alameyo.jpwp.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainClass extends Game {
    SpriteBatch batch;
        
	@Override
	public void create() {
		batch = new SpriteBatch();
		System.out.println("rwrw");
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
