package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Car {
	public Body body;
	protected Sprite sprite;
	protected Texture img;
	public float x;
	public float y;
	protected float speed;
	protected float topSpeed;
	protected float acceleration;
	protected float deacceleration;
	protected float angle;
	protected float velX;
	protected float velY;
	protected float height;
	protected float width;
	

	private PolygonShape shape;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;
	private Fixture fixture;

	public Car(World world) {
		img = new Texture("car.jpg");
		sprite = new Sprite(img);
		
		
		x = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
		y = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;
		sprite.setPosition(x, y);

		speed = 0;
		acceleration = 30;
		deacceleration =18;
		velX = 0;
		velY = 0;
		topSpeed = 150f;
		// body
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(sprite.getX(), sprite.getY());
		this.body = world.createBody(bodyDef);

		angle = 90;//MathUtils.radiansToDegrees * body.getAngle();
		// fixture
		shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
		fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1f;

		fixture = body.createFixture(fixtureDef);

		shape.dispose();
	}

	public void carUpdate() {
		controlls();
		velX = MathUtils.cos(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // X-component.
		velY = MathUtils.sin(MathUtils.degreesToRadians * angle) * speed * Gdx.graphics.getDeltaTime(); // Y-component.
		//angle = body.getAngle();
		x = x+velX;
		y = y+velY;
		body.setTransform(x, y, angle);
		checkForCollision();
		//body.setLinearVelocity(velX, velY);
		System.out.println(body.getAngle() + "||||" + angle);
		//body.setAngularVelocity(angle);
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
		sprite.setRotation(angle);
	}
	protected void controlls(){
		
	}
	protected void checkForCollision(){
		
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
