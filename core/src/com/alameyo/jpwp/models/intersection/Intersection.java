package com.alameyo.jpwp.models.intersection;

import java.util.LinkedList;

import com.alameyo.jpwp.models.vehicles.Car;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
/**
 * 
 * Skrzy¿owanie.
 *
 */
public class Intersection extends Rectangle {

	private static final long serialVersionUID = 1L;

	/**
	 * Drogi nale¿¹ce do skrzy¿owania.
	 */
	protected Road roadLeft, roadRight, roadUp, roadDown;
	/**
	 * Zajêtoœæ drogi.
	 */
	protected boolean taken;
	/**
	 * Po³o¿enie.
	 */
	public float x,y;
	/**
	 * Sprite skrzy¿owania.
	 */
	protected Sprite sprite;
	/**
	 * Tekstura skrzy¿owania.
	 */
	protected Texture img;

	/**
	 * Constructor for Intersection.
	 * Konstruktor dla skrzy¿owania.
	 * @param builder
	 *     
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
	 * Update information about roads of intersection with car on
	 * them.
	 * Aktualizuje informacje o po³o¿eniu pojazdów na skrzy¿owaniu.
	 * @param car
	 */
	public void interUpdate(LinkedList<Car> carList) {
		for (Car car : carList) {
			if (roadLeft != null) {
				if (roadListener(roadLeft, car) == true) {
					roadLeft.setTaken(true);
				}
			}
			if (roadRight != null) {
				if (roadListener(roadRight, car) == true) {
					roadRight.setTaken(true);
				}
			}
			if (roadUp != null) {
				if (roadListener(roadUp, car) == true) {
					roadUp.setTaken(true);
				}
			}
			if (roadDown != null) {
				if (roadListener(roadDown, car) == true) {
					roadDown.setTaken(true);
				}
			}
		}
	}
	/**
	 * Resetuje informacje o skrzy¿owaniu.
	 */
	public void reset() {
		if (roadLeft != null) {
			roadLeft.setTaken(false);
		}
		if (roadRight != null) {
			roadRight.setTaken(false);
		}
		if (roadUp != null) {
			roadUp.setTaken(false);
		}
		if (roadDown != null) {
			roadDown.setTaken(false);
		}
	}

	/**
	 * Nas³uchuje kolizji dróg z samochodami.
	 * Listening for collision with car.
	 * @param road
	 * @param car
	 * @return 
	 */
	boolean roadListener(Road road, Car car) {
		boolean roadTaken;
		if (Intersector.overlapConvexPolygons(road.rectToPoly, car)) {
			roadTaken = true;
			// road.setTaken(roadTaken);

		} else {
			roadTaken = false;
		}
		return roadTaken;
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
	 * Klasa wewnêtrzna wykorzystuj¹ca wzorzec projektowy budowniczy.
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
