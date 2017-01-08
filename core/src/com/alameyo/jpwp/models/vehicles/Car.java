package com.alameyo.jpwp.models.vehicles;

import java.util.LinkedList;
import java.util.Random;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Abstract class for cars
 *
 */
public abstract class Car extends Polygon {

	/**
	 * Sprite with car.
	 */
	protected Sprite sprite;
	/**
	 * Kierunkowskaz lewy tylni.
	 */
	protected Sprite wayLightLeft;
	/**
	 * Kierunkowskaz prawy tylni.
	 */
	protected Sprite wayLightRight;
	/**
	 * Kierunkowskaz lewy przedni.
	 */
	protected Sprite wayLightLeftFront;
	/**
	 * Kierunkowskaz prawy przedni.
	 */
	protected Sprite wayLightRightFront;
	/**
	 * Tekstury wykorzystane w konstruktorze a pózniej manipulacji œwiat³ami.
	 */
	protected Texture img, img2;
	/**
	 * Pozycje tekstur.
	 */
	public float x,y;
	/**
	 * Prêdkoœæ pojazdu.
	 */
	protected float speed;
	/**
	 * Maksymalna prêdkoœæ pojazdu.
	 */
	protected float topSpeed;
	/**
	 * Przyspieszenie pojazdu.
	 */
	protected float acceleration;
	/**
	 * Wychamowywanie pojazdu.
	 */
	protected float deacceleration;
	/**
	 * K¹t pod jakim znajduje siê pojazd.
	 */
	protected float angle;
	/**
	 * Prêdkoœæ zmiany k¹tu przy skrêcaniu.
	 */
	protected float angleChange;
	/**
	 * Prêdkoœci k¹towe.
	 */
	protected float velX,velY;
	/**
	 * Wysokoœæ i szerokoœæ obiektu.
	 */
	protected float heigh,width;
	/**
	 * Do losowania koloru pojazdu.
	 */
	protected Random carColor;
	/**
	 * Numer koloru pojazdu.
	 */
	protected int carColorInt;
	/**
	 * Poprawka na wymiar wieloboku do kolizji.
	 */
	private float polygonAdjust;

	/**
	 * Constructor for car.
	 * Konstruktor samochodu.
	 * @param world
	 * @param x
	 * @param y
	 * @param angle
	 *            
	 */
	public Car(World world, float x, float y, float angle) {
		carColor = new Random();
		carColorInt = carColor.nextInt(4);
		if(carColorInt == 0){
			img = new Texture("autko1.png");
		}else if (carColorInt == 1){
			img = new Texture("autko2.png");
		}else if(carColorInt == 2){
			img = new Texture("autko3.png");
		}else{
			img = new Texture("autko4.png");
		}
		sprite = new Sprite(img);
		img = new Texture("noWayLight.png");
		img2 = new Texture("WayLight.png");
		wayLightRight = new Sprite(img);
		wayLightLeft = new Sprite(img);
		wayLightRightFront = new Sprite(img);
		wayLightLeftFront = new Sprite(img);
		this.x = x;
		this.y = y;
		this.angle = angle;
		// x = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
		// y = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;
		sprite.setPosition(x, y);

		wayLightLeft.setPosition(x - 10, y + 60);
		wayLightLeft.setRotation(angle);

		wayLightRight.setPosition(x - 10, y - 5);
		wayLightRight.setRotation(angle);

		wayLightLeftFront.setPosition(x + 180, y + 60);
		wayLightLeftFront.setRotation(angle);

		wayLightRightFront.setPosition(x + 180, y - 5);
		wayLightRightFront.setRotation(angle);

		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		wayLightLeft.setOrigin(sprite.getWidth() / 2 + 10, sprite.getHeight() / 2 - 60);
		wayLightLeftFront.setOrigin(sprite.getWidth() / 2 - 180, sprite.getHeight() / 2 - 60);
		wayLightRight.setOrigin(sprite.getWidth() / 2 + 10, sprite.getHeight() / 2 + 5);
		wayLightRightFront.setOrigin(sprite.getWidth() / 2 -180, sprite.getHeight() / 2 + 5);

		this.setPosition(x, y);
		this.setOrigin(x, y);

		speed = 0;
		acceleration = 140;
		deacceleration = acceleration + 20;
		angleChange = 2.2f;
		velX = 0;
		velY = 0;
		topSpeed = 450f;
		// angle = 90;
		polygonAdjust = 3;
		this.setVertices(new float[] { 0 + polygonAdjust, 0 + polygonAdjust, 0 + polygonAdjust,
				0 + sprite.getHeight() - polygonAdjust, 0 + sprite.getWidth() - polygonAdjust,
				0 + sprite.getHeight() - polygonAdjust, 0 + sprite.getWidth() - polygonAdjust, 0 + polygonAdjust });
		this.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		this.setRotation(angle);

	}
	/**
	 * 
	 * @return
	 */
	public Sprite getWayLightLeft() {
		return wayLightLeft;
	}
	/**
	 * 
	 * @return
	 */
	public Sprite getWayLightRight() {
		return wayLightRight;
	}

	/**
	 * Update movement of car and it's sprite. Check for collisions with other
	 * objects.
	 * Aktualizuje po³o¿enie pojadu i sprawdza kolizjê z innymi obiektami.
	 */
	public void carUpdate(LinkedList<Intersection> intersectionList) {
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

		wayLightLeft.setPosition(x - 10, y + 60);
		wayLightLeft.setRotation(angle);

		wayLightRight.setPosition(x - 10, y - 5);
		wayLightRight.setRotation(angle);
		
		wayLightLeftFront.setPosition(x + 180, y + 60);
		wayLightLeftFront.setRotation(angle);

		wayLightRightFront.setPosition(x + 180, y - 5);
		wayLightRightFront.setRotation(angle);
		
	}
	/**
	 * Sterowanie pojazdu.
	 */
	abstract protected void controlls();
	
	/**
	 * Sprawdzanie czy nast¹pi³a kolizja ze skrzy¿owaniem.
	 * @param intersectionList
	 */
	abstract protected void checkForCollision(LinkedList<Intersection> intersectionList);

	public void dispose() {
		img.dispose();
	}
	/**
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}
	/**
	 * 
	 * @return
	 */
	public Sprite getWayLightLeftFront() {
		return wayLightLeftFront;
	}
	/**
	 * 
	 * @return
	 */
	public Sprite getWayLightRightFront() {
		return wayLightRightFront;
	}
	/**
	 * 
	 * @param sprite
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	/**
	 * 
	 * @return
	 */
	public Texture getImg() {
		return img;
	}
	/**
	 * 
	 * @param img
	 */
	public void setImg(Texture img) {
		this.img = img;
	}

}
