package com.alameyo.jpwp.screens;

import java.util.ArrayList;

import com.alameyo.jpwp.MainClass;
import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {

	MainClass game;
	SpriteBatch batch;
	Car car;
    Road road;
    Intersection intersection;
    World world;
    Body body;
    Texture img;
    OrthographicCamera cam;
    ArrayList<Road> roadList;

	public GameScreen(MainClass game){
		this.game=game;
		this.batch= game.getBatch();
		
		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();
		car = new PlayerCar(world);
		roadList = new ArrayList<Road>();
		roadList.add(new Road(world, 100, 100, false, false));
		roadList.add(new Road(world, 300, 100, false, false));
		roadList.add(new Road(world, 500, 100, false, false));
		roadList.add(new Road(world, 700, 100, false, false));
		roadList.add(new Road(world, 900, 100, false, false));
		roadList.add(new Road(world, 1100, 100, false, false));
		roadList.add(new Road(world, 100, 0, false, true));
		roadList.add(new Road(world, 300, 0, false, true));
		roadList.add(new Road(world, 500, 0, false, true));
		roadList.add(new Road(world, 700, 0, false, true));
		roadList.add(new Road(world, 900, 0, false, true));
		roadList.add(new Road(world, 1100, 0, false, true));
		roadList.add(new Road(world, 1300, 100, true, false));
		roadList.add(new Road(world, 1300, 200, true, false));
		roadList.add(new Road(world, 1300, 300, true, false));
		roadList.add(new Road(world, 1300, 400, true, false));
		roadList.add(new Road(world, 1300, 500, true, false));
		roadList.add(new Road(world, 1300, 600, true, false));
		intersection = new Intersection(world,1300,0);
		

		
	}
	private void update() {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		cam.update();

		batch.setProjectionMatrix(cam.combined);
		cam.position.set(car.x + car.getSprite().getWidth() / 2, car.y + car.getSprite().getHeight(), 0);
		car.carUpdate();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		car.dispose();
		world.dispose();		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		for (Road road : roadList) {
			road.getSprite().draw(batch);
		}
		intersection.getSprite().draw(batch);
		//road.getSprite().draw(batch);
		car.getSprite().draw(batch);
		//batch.draw(img, 50,50);
		
		batch.end();
	}

}
