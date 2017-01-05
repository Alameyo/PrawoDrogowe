package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Pawe³ Œcibiorski Abstract class for cars
 *
 */
public abstract class Car extends Polygon {

	// public Body body;
	protected Sprite sprite;
	protected Texture img;
	public float x;
	public float y;
	protected float speed;
	protected float topSpeed;
	protected float acceleration;
	protected float deacceleration;
	protected float angle;
	protected float angleChange;
	protected float velX;
	protected float velY;
	protected float height;
	protected float width;

	private float polygonAdjust;
	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param angle
	 * Constructor for car.
	 */
	public Car(World world, float x, float y, float angle) {
		img = new Texture("autko1.png");
		sprite = new Sprite(img);
		this.x = x;
		this.y = y;
		// x = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
		// y = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;
		sprite.setPosition(x, y);
		this.setPosition(x, y);
		this.setOrigin(x, y);

		speed = 0;
		acceleration = 80;
		deacceleration = acceleration + 20;
		angleChange = 2.2f;
		velX = 0;
		velY = 0;
		topSpeed = 450f;
		//angle = 90;
		polygonAdjust = 5;

		this.setVertices(new float[] { 0, 0, 0, 0 + sprite.getHeight(), 0 + sprite.getWidth(), 0 + sprite.getHeight(),
				0 + sprite.getWidth(), 0 });
		this.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		this.setRotation(angle);

	}
	/**
	 * Update movement of car and it's sprite. Check for collisions with other objects.
	 */
	public void carUpdate() {
		controlls();
		velX = MathUtils.cos(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // X-component.
		velY = MathUtils.sin(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // Y-component.
		// angle = body.getAngle();
		x = x + velX;
		y = y + velY;
		this.setPosition(x, y);
		this.setRotation(angle);
		checkForCollision();

		sprite.setPosition(x, y);
		sprite.setRotation(angle);
	}

	abstract protected void controlls();

	protected void checkForCollision() {

	}

	public void dispose() {
		img.dispose();
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Texture getImg() {
		return img;
	}

	public void setImg(Texture img) {
		this.img = img;
	}

}
