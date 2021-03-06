package com.alameyo.jpwp.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
/**
 * 
 *  Budynki.
 *
 */
public class Building extends Rectangle {
	/**
	 * Sprite budynku
	 */
	protected Sprite sprite;
	/**
	 * Tekstura Budynku
	 */
	protected Texture img;
	/**
	 * Po�o�enie.
	 */
	private float x,y;
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param which
	 */
	public Building(float x, float y, int which){
		this.x = x;
		this.y = y;
		if(which ==0){
		img = new Texture("Store1.png");
		}else{
			img = new Texture("Store2.png");
		}
		sprite = new Sprite(img);
		sprite.setPosition(x, y);
	}
	/**
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}

}
