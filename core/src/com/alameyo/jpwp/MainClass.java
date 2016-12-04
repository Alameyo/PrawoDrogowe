package com.alameyo.jpwp;

import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class MainClass extends ApplicationAdapter {
    SpriteBatch batch;
    Car car;
    Road road;
    World world;
    Body body;
    Texture img;
    OrthographicCamera cam;

	@Override
	public void create() {
		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();
		car = new PlayerCar(world);
		road = new Road(world);
		img = new Texture("badlogic.jpg");
		
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		car.getSprite().draw(batch);
		batch.draw(img, 50,50);

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
