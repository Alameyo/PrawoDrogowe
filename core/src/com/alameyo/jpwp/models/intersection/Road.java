package com.alameyo.jpwp.models.intersection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Road extends Rectangle{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Sprite sprite;
	protected Texture img;
	boolean taken;
	
	
	public float x;
	public float y;
	private float angle;
	Polygon rectToPoly;
		
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
		rectToPoly = new Polygon(new float[] { 0, 0, 0, 0+sprite.getHeight(), 0 + sprite.getWidth(),
	            0 + sprite.getHeight(), 0+sprite.getWidth(), 0 });
	    rectToPoly.setPosition(x, y);
	    rectToPoly.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
	    rectToPoly.rotate(angle);
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}


	public Polygon getRectToPoly() {
		return rectToPoly;
	}


	public boolean isTaken(){
		return taken;
	}


	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
}
