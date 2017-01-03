package com.alameyo.jpwp.models.intersection;

import com.alameyo.jpwp.models.vehicles.Car;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Intersection extends Rectangle {

	private static final long serialVersionUID = 1L;
	// road logic
	protected Road roadLeft;
	protected Road roadRight;
	protected Road roadUp;
	protected Road roadDown;
	protected boolean leftTaken;
	protected boolean rightTaken;
	protected boolean upTaken;
	protected boolean downTaken;

	protected boolean taken;

	public float x;
	public float y;

	protected Sprite sprite;
	protected Texture img;

	public Intersection(Builder builder) {

		this.roadLeft = builder.roadLeft;
		this.roadRight = builder.roadRight;
		this.roadUp = builder.roadUp;
		this.roadDown = builder.roadDown;
		this.x = builder.x;
		this.y = builder.y;

		sprite = new Sprite(new Texture("cross.png"));

		sprite.setPosition(x, y);
	}

	public void interUpdate(Car car) {
		try {
			this.leftTaken = roadListener(roadLeft, car);
		} catch (NullPointerException e) {}
		try {
			this.rightTaken = roadListener(roadRight, car);
		} catch (NullPointerException e) {}
		try {
			this.upTaken = roadListener(roadUp, car);
		} catch (NullPointerException e) {}
		try {
			this.downTaken = roadListener(roadDown, car);
		} catch (NullPointerException e) {}
	}

	boolean roadListener(Road road, Car car) {
		boolean roadTaken;
		if (Intersector.overlapConvexPolygons(road.rectToPoly, car)) {
			roadTaken = true;
			System.out.println("Na drodze");
		} else {
			roadTaken = false;
		}
		return roadTaken;
	}

	public boolean isLeftTaken() {
		return leftTaken;
	}

	public void setLeftTaken(boolean leftTaken) {
		this.leftTaken = leftTaken;
	}

	public boolean isRightTaken() {
		return rightTaken;
	}

	public void setRightTaken(boolean rightTaken) {
		this.rightTaken = rightTaken;
	}

	public boolean isUpTaken() {
		return upTaken;
	}

	public void setUpTaken(boolean upTaken) {
		this.upTaken = upTaken;
	}

	public boolean isDownTaken() {
		return downTaken;
	}

	public void setDownTaken(boolean downTaken) {
		this.downTaken = downTaken;
	}

	public Road getRoadLeft() {
		return roadLeft;
	}

	public Road getRoadRight() {
		return roadRight;
	}

	public Road getRoadUp() {
		return roadUp;
	}

	public Road getRoadDown() {
		return roadDown;
	}

	public static class Builder {

		protected Road roadLeft;
		protected Road roadRight;
		protected Road roadUp;
		protected Road roadDown;

		public float x;
		public float y;

		protected Sprite sprite;
		protected Texture img;

		public Builder roadLeft(Road roadLeft) {
			this.roadLeft = roadLeft;
			return this;
		}

		public Builder roadRight(Road roadRight) {
			this.roadRight = roadRight;
			return this;
		}

		public Builder roadUp(Road roadUp) {
			this.roadUp = roadUp;
			return this;
		}

		public Builder roadDown(Road roadDown) {
			this.roadDown = roadDown;
			return this;
		}

		public Builder x(float x) {
			this.x = x;
			return this;
		}

		public Builder y(float y) {
			this.y = y;
			return this;
		}

		public Intersection build() {
			return new Intersection(this);
		}

	}

	public Sprite getSprite() {
		return sprite;
	}

}
