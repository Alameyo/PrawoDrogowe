package com.alameyo.jpwp.models.vehicles;

import java.util.ArrayList;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;

/**
 *  Abstract class for cars
 *
 */
public abstract class Car extends Polygon {

	// public Body body;
	protected Sprite sprite;
	protected Sprite wayLightLeft;
	protected Sprite wayLightRight;
	protected Texture img, img2;
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
		img = new Texture("noWayLight.png");
		img2 = new Texture("WayLight.png");
		wayLightRight = new Sprite(img);
		wayLightLeft = new Sprite(img);
		this.x = x;
		this.y = y;
		this.angle = angle;
		// x = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
		// y = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;
		sprite.setPosition(x, y);
		
		wayLightLeft.setPosition(x+10, y+20);
		wayLightLeft.setRotation(angle);
		
		wayLightRight.setPosition(x-10, y-10);
		wayLightRight.setRotation(angle);
		
		
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		wayLightLeft.setOrigin(sprite.getWidth()/2+10, sprite.getHeight()/2-60);
		wayLightRight.setOrigin(sprite.getWidth()/2+10, sprite.getHeight()/2+5);
		
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
	public Sprite getWayLightLeft() {
		return wayLightLeft;
	}
	public Sprite getWayLightRight() {
		return wayLightRight;
	}
	/**
	 * Update movement of car and it's sprite. Check for collisions with other objects.
	 */
	public void carUpdate(ArrayList<Intersection> intersectionList) {
		controlls();
		velX = MathUtils.cos(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // X-component.
		velY = MathUtils.sin(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // Y-component.
		// angle = body.getAngle();
		x = x + velX;
		y = y + velY;
		this.setPosition(x, y);
		this.setRotation(angle);
		checkForCollision(intersectionList);

		sprite.setPosition(x, y);
		sprite.setRotation(angle);
		
		wayLightLeft.setPosition(x-10, y+60);
		wayLightLeft.setRotation(angle);
		
		wayLightRight.setPosition(x-10, y-5);
		wayLightRight.setRotation(angle);
	}

	abstract protected void controlls();

	protected void checkForCollision(ArrayList<Intersection> intersectionList) {
		
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
