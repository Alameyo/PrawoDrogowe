package com.alameyo.jpwp.models.intersection;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Intersection {

	//road logic
	Road roadLeft;
	Road roadRight;
	Road roadUp;
	Road roadDown;
	boolean leftTaken;
	boolean rightTaken;
	boolean upTaken;
	boolean downTaken;
	
	
	protected Sprite sprite;
	protected Texture img;
	boolean taken;

	public float x;
	public float y;

	BodyDef bodyDef;
	Body body;

	public Intersection(World world,float x, float y) {
		img = new Texture("cross.png");

		sprite = new Sprite(img);
	//	sprite.flip(axisX, axisY);

		this.x = x;
		this.y = y;
		sprite.setPosition(x, y);

		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(sprite.getX(), sprite.getY());

		this.body = world.createBody(bodyDef);

		// Create a polygon shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);
		body.createFixture(shape, 0.0f);
		// Clean up after ourselves
		shape.dispose();

	}

	public Sprite getSprite() {
		return sprite;
	}

}
