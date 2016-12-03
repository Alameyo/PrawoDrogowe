package com.alameyo.jpwp;

import com.alameyo.jpwp.models.vehicles.Car;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MainClass extends ApplicationAdapter {
    SpriteBatch batch;
    Car car;
    World world;
    Body body;

	@Override
	public void create() {
		world = new World(new Vector2(0, 0), true);
		batch = new SpriteBatch();
		car = new Car(world);
		
		
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		car.getSprite().draw(batch);

		batch.end();
	}

	private void update() {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		car.getSprite().setPosition(car.body.getPosition().x, car.body.getPosition().y);
	}

	@Override
	public void dispose() {
		batch.dispose();
		car.dispose();
		world.dispose();
		//panSamochodzik.img.dispose();
		//shape.dispose();
	}
}
