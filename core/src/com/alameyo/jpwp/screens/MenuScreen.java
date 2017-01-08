package com.alameyo.jpwp.screens;

import com.alameyo.jpwp.MainClass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
/**
 * Menu g³ówne.
 * Game main menu
 */
public class MenuScreen implements Screen
	{
		/**
		 * Klasa main.
		 */
		private MainClass game;
		/**
		 * Skórka dla przycisków.
		 */
		private Skin skin;
		/*
		 * Scene wykorzystana do narysowania menu.
		 */
		private Stage stage;
		
		/**
		 * Font do wyniku.
		 */
		private BitmapFont font;
		/**
		 * Batch do wyniku.
		 */
		private SpriteBatch batch;
		/**
		 * Wynik.
		 */
		private String score;
		/**
		 * Konstruktor menu.
		 * @param game
		 */
			public	MenuScreen(MainClass game){
		this.game = game;
		create();
		}
		/**
		 * Metoda wywo³ywana przez konstruktor.
		 */
		private void create(){	
			try{
			FileHandle file = Gdx.files.external("Score.txt");
			score = file.readString();
			}catch(Exception e){
				score = "0";
			}
			batch = new SpriteBatch();
			font = new BitmapFont();
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);	
			skin = new Skin();
			Pixmap pixmap = new Pixmap(500, 500, Format.RGBA8888);
			pixmap.setColor(Color.BLUE);
			pixmap.fill();

			skin.add("white", new Texture(pixmap));

			BitmapFont bfont=new BitmapFont();
			skin.add("default",bfont);

			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = skin.newDrawable("white", Color.BLUE);
			textButtonStyle.down = skin.newDrawable("white", Color.YELLOW);
			textButtonStyle.checked = skin.newDrawable("white", Color.LIME);
			textButtonStyle.over = skin.newDrawable("white", Color.NAVY);

			textButtonStyle.font = skin.getFont("default");

			skin.add("default", textButtonStyle);

			final TextButton textButton1=new TextButton("ZAGRAJ",textButtonStyle);
			
			float x = Gdx.graphics.getWidth() / 4;
			float y = Gdx.graphics.getHeight() / 5;
			textButton1.setPosition(x, y);
		
			stage.addActor(textButton1);
					
			textButton1.addListener(new ChangeListener() {
				public void changed (ChangeEvent event, Actor actor) {
					textButton1.setText("Starting new game");
					game.setScreen( new GameScreen(game));
				}
			});	}
	
		/**
		 * 
		 */
	@Override
	public void show() {	
	}
	/**
	 * Renderowanie obrazu.
	 */
	@Override
	public void render(float delta) {
		//start.show();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		batch.begin();
		font.getData().setScale(3);
		font.draw(batch, "Wynik: " +score, 300, 600);
		batch.end();
	}
	/**
	 * 
	 */
	@Override
	public void resize(int width, int height) {		
	}
	/**
	 * 
	 */
	@Override
	public void pause() {
	}
	/**
	 * 
	 */
	@Override
	public void resume() {
	}
	/**
	 * 
	 */
	@Override
	public void hide() {
	}
	/**
	 * 
	 */
	@Override
	public void dispose() {
	}

}