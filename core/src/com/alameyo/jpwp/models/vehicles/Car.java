package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Car {
	public Body body;
	Sprite sprite;
	Texture img;
	float x;
	float y;
	
	
	PolygonShape shape;
	BodyDef bodyDef;
	FixtureDef fixtureDef;
	Fixture fixture;
	
	public Car(World world) {
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		x = Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2;
		y = Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2;
		sprite.setPosition(x, y);
		bodyDef = new BodyDef();
	    bodyDef.type = BodyDef.BodyType.DynamicBody;
	    bodyDef.position.set(sprite.getX(), sprite.getY());
	    this.body = world.createBody(bodyDef);
	    shape = new PolygonShape();
	    shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
	    fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
	}
	public void carUpdate(){
		if(Gdx.input.isButtonPressed(Keys.UP)){
			 body.setLinearVelocity(0f, 100f);
		}
	}
	
	public void dispose(){
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
