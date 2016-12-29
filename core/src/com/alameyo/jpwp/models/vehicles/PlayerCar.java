package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerCar extends Car {


public PlayerCar(World world, float x, float y) {
		super(world, x, y);
		// TODO Auto-generated constructor stub
	}

/**
 * Player controller
 */
	@Override
	protected void controlls() {
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			if (speed < topSpeed) {
				speed += acceleration * Gdx.graphics.getDeltaTime();
				System.out.println("Faster" + speed);
			}else{
				
			}
		} else if (Gdx.input.isKeyPressed(Keys.S) ||Gdx.input.isKeyPressed(Keys.DOWN)) {
			if (speed > -topSpeed) {
				speed -= acceleration * Gdx.graphics.getDeltaTime();
				System.out.println("Slowing" + speed);
			}
		} else {
			if (speed > 0) {
				speed -= deacceleration * Gdx.graphics.getDeltaTime();
			} else if (speed < 0) {
				speed += deacceleration * Gdx.graphics.getDeltaTime();
			}
		}
		if (speed > 10) {

			if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				angle = angle + angleChange;
			} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				angle = angle - angleChange;
			}
		} else if (speed < -10) {
			if (Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)) {
				angle = angle - 1.2f;
			} else if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
				angle = angle + 1.2f;
			}
		}
	}

	private void turning() {

	}
}
