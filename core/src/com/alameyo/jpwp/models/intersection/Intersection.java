package com.alameyo.jpwp.models.intersection;

import java.util.ArrayList;
import java.util.LinkedList;

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

	/**
	 * 
	 * @param builder
	 *            Constructor for Intersection.
	 */
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

	/**
	 * 
	 * @param car
	 *            Update information about roads of intersection with car on
	 *            them.
	 */
	public void interUpdate(LinkedList<Car> carList) {
		for (Car car : carList) {
			try {
				roadLeft.setTaken(roadListener(roadLeft, car)) ;
			} catch (NullPointerException e) {
			}
			try {
				roadRight.setTaken( roadListener(roadRight, car));
			} catch (NullPointerException e) {
			}
			try {
				roadUp.setTaken(roadListener(roadUp, car));
			} catch (NullPointerException e) {
			}
			try {
				roadDown.setTaken( roadListener(roadDown, car));
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * 
	 * @param road
	 * @param car
	 * @return Listening for collision with car.
	 */
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

	/**
	 * 
	 * @return
	 */
	public boolean isLeftTaken() {
		return leftTaken;
	}

	/**
	 * 
	 * @param leftTaken
	 */
	public void setLeftTaken(boolean leftTaken) {
		this.leftTaken = leftTaken;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRightTaken() {
		return rightTaken;
	}

	/**
	 * 
	 * @param rightTaken
	 */
	public void setRightTaken(boolean rightTaken) {
		this.rightTaken = rightTaken;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isUpTaken() {
		return upTaken;
	}

	/**
	 * 
	 * @param upTaken
	 */
	public void setUpTaken(boolean upTaken) {
		this.upTaken = upTaken;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDownTaken() {
		return downTaken;
	}

	/**
	 * 
	 * @param downTaken
	 */
	public void setDownTaken(boolean downTaken) {
		this.downTaken = downTaken;
	}

	/**
	 * 
	 * @return
	 */
	public Road getRoadLeft() {
		return roadLeft;
	}

	/**
	 * 
	 * @return
	 */
	public Road getRoadRight() {
		return roadRight;
	}

	/**
	 * 
	 * @return
	 */
	public Road getRoadUp() {
		return roadUp;
	}

	/**
	 * 
	 * @return
	 */
	public Road getRoadDown() {
		return roadDown;
	}

	/**
	 * 
	 * Inner class with builder design pattern for construction of the
	 * intersection.
	 *
	 */
	public static class Builder {

		protected Road roadLeft;
		protected Road roadRight;
		protected Road roadUp;
		protected Road roadDown;

		public float x;
		public float y;

		protected Sprite sprite;
		protected Texture img;

		/**
		 * 
		 * @param roadLeft
		 * @return
		 */
		public Builder roadLeft(Road roadLeft) {
			this.roadLeft = roadLeft;
			return this;
		}

		/**
		 * 
		 * @param roadRight
		 * @return
		 */
		public Builder roadRight(Road roadRight) {
			this.roadRight = roadRight;
			return this;
		}

		/**
		 * 
		 * @param roadUp
		 * @return
		 */
		public Builder roadUp(Road roadUp) {
			this.roadUp = roadUp;
			return this;
		}

		/**
		 * 
		 * @param roadDown
		 * @return
		 */
		public Builder roadDown(Road roadDown) {
			this.roadDown = roadDown;
			return this;
		}

		/**
		 * 
		 * @param x
		 * @return
		 */
		public Builder x(float x) {
			this.x = x;
			return this;
		}

		/**
		 * 
		 * @param y
		 * @return
		 */
		public Builder y(float y) {
			this.y = y;
			return this;
		}

		public Intersection build() {
			return new Intersection(this);
		}

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
	public int getNumberOfRoads() {
		int numberOfRoads = 0;
		if (roadUp != null) {
			numberOfRoads++;
		}
		if (roadLeft != null) {
			numberOfRoads++;
		}
		if (roadRight != null) {
			numberOfRoads++;
		}
		if (roadDown != null) {
			numberOfRoads++;
		}

		return numberOfRoads;
	}
}
