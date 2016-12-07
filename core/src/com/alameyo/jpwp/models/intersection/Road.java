package com.alameyo.jpwp.models.intersection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Road {
	
	
	protected Sprite sprite;
	protected Texture img;
	boolean taken;
	
	
	public float x;
	public float y;
	private float angle;
	
	BodyDef bodyDef;
	Body body;
	
	public Road(World world, float x, float y, boolean axisX, boolean axisY, boolean rotate){
		img = new Texture("blokDrogi.png");
		
		sprite = new Sprite(img);
		sprite.flip(axisX, axisY);
		if(rotate == true){
			angle = 90;
		}else{
			angle =0;
		}
		
		sprite.rotate(angle);
		
		this.x=x;
		this.y=y;
		sprite.setPosition(x, y);
		
		bodyDef = new BodyDef();  
		bodyDef.type = BodyDef.BodyType.StaticBody; 
		bodyDef.position.set(sprite.getX(), sprite.getY());

		this.body = world.createBody(bodyDef);  

		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);  
		body.createFixture(shape, 0.0f); 
		// Clean up after ourselves
		shape.dispose();
		
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}


	public void isTaken(){
		
	}
	
}
