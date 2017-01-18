package com.alameyo.jpwp.models.vehicles;

import java.util.ArrayList;
import java.util.LinkedList;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerCar extends Car {

	Sound sound, sound2;

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param angle
	 */
	public PlayerCar(World world, float x, float y, float angle) {
		super(world, x, y, angle);
		sound = Gdx.audio.newSound(Gdx.files.internal("GPHORSEpower.mp3"));
		sound.play();
		sound.loop();
		sound.pause();
		sound2 = Gdx.audio.newSound(Gdx.files.internal("car+driveby2.mp3"));
		sound2.play();
		sound2.loop();
		sound2.pause();
	}

	@Override
	protected void controlls() {
		if (Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)) {
			if (speed < topSpeed) {
				speed += acceleration * Gdx.graphics.getDeltaTime();

				// System.out.println("Faster" + speed);
			}
			sound2.pause();
			sound.resume();
		} else if (Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)) {
			sound.pause();
			sound2.resume();
			if (speed > -topSpeed) {
				speed -= acceleration * Gdx.graphics.getDeltaTime();
				// System.out.println("Slowing" + speed);
			}
		} else {
			sound.pause();
			sound2.resume();
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

	@Override
	protected void checkForCollision(LinkedList<Intersection> intersectionList) {

	}

}
