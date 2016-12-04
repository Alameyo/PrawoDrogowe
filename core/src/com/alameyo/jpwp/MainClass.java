package com.alameyo.jpwp;

import java.util.ArrayList;
import java.util.List;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class MainClass extends Game {
    SpriteBatch batch;
    Car car;
    Road road;
    Intersection intersection;
    World world;
    Body body;
    Texture img;
    OrthographicCamera cam;
    List<Road> roadList;
    
	@Override
	public void create() {
		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();
		car = new PlayerCar(world);
		roadList = new ArrayList();
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
		intersection = new Intersection(world,1300,0);
		
		
		//img = new Texture("badlogic.jpg");
		
	}

	@Override
	public void render() {
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

	private void update() {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		cam.update();

		batch.setProjectionMatrix(cam.combined);
		cam.position.set(car.x + car.getSprite().getWidth() / 2, car.y + car.getSprite().getHeight(), 0);
		car.carUpdate();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		car.dispose();
		world.dispose();
	}
}
