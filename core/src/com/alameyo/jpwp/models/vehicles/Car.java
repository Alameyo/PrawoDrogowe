package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Car {
	private float topSpeed = 130f;
	private float acceleration = 10f;
	private float speed = 0;
	private float angle = 0;
	private float angleChange = 5f;
	private float maxAngle = (float) Math.PI / 3;
	private Texture img;
	private Sprite playerSprite = new Sprite();
	private Body body;
	private PolygonShape shape;
	private FixtureDef fixtureDef;
	BodyDef bodyDef;
	Fixture fixture;
	public Sprite getPlayerSprite() {
		return playerSprite;
	}
	public BodyDef getBodyDef() {
		return bodyDef;
	}
	public Car() {
		//this.body = body;
		img = new Texture("badlogic.jpg");
		playerSprite = new Sprite(img);
		// Center the sprite in the top/middle of the screen
		playerSprite.setPosition(Gdx.graphics.getWidth() / 2 - playerSprite.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		BodyDef bodyDef = new BodyDef();
		bodyDef.linearDamping = 1f;
		
		bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(playerSprite.getX(), playerSprite.getY());

        // Now define the dimensions of the physics shape
        shape = new PolygonShape();
        // We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions as our sprite
        shape.setAsBox(playerSprite.getWidth()/2, playerSprite.getHeight()/2);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        
        
        

	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	public void update(){
		fixture = body.createFixture(fixtureDef);
	}
}
