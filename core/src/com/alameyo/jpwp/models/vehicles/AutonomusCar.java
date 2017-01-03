package com.alameyo.jpwp.models.vehicles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;

public class AutonomusCar extends Car {

	int myWay;
	boolean canIgo;
	Random rand;
	boolean turning;
	boolean go;
	float pastAngle;
	float pastX;
	float pastY;

	public AutonomusCar(World world, float x, float y, float angle) {
		super(world, x, y, angle);
		rand = new Random();
		speed = 30;
		turning = false;
	}

	public void canIgoAhead(boolean roadLeft, boolean roadRight, boolean roadStraight) {
		if (roadRight == true) {
			canIgo = false;
		} else {
			canIgo = true;
		}
	}

	public void canIgoRight(boolean roadLeft, boolean roadRight, boolean roadStraight) {
		if (roadLeft == true) {
			canIgo = false;
		} else {
			canIgo = true;
		}
	}

	public void canIgoLeft(boolean roadLeft, boolean roadRight, boolean roadStraight, boolean leftSign,
			boolean rightSign, boolean straightSign, boolean way) {
		if (roadLeft == true || roadRight == true || roadStraight == true) {
			canIgo = false;
		} else {
			canIgo = true;
		}
	}

	public void carUpdate(ArrayList<Intersection> interSectionList) {
		checkForCollision(interSectionList);
	}

	protected void controlls() {
		if (turning == false) {
			if (Gdx.input.isKeyPressed(Keys.K) == true) {
				speed = 150;
			} else if (Gdx.input.isKeyPressed(Keys.L) == true) {
				pastAngle = angle;
				myWay = 2;
				turnRight();
			} else if (Gdx.input.isKeyPressed(Keys.J) == true) {
				pastAngle = angle;
				myWay = 1;
				turnLeft();
			} else if (Gdx.input.isKeyPressed(Keys.I) == true) {
				pastX = x;
				pastY = y;
				goAhead();
			} else {
				speed = 0;
			}
		} else if (myWay == 2) { // if myWay = 2
			turnRight();
		} else if (myWay == 1) {
			turnLeft();
		} else if (myWay == 0) {
			goAhead();
		}

	}

	private void turn(Intersection intersection) {
		myWay = rand.nextInt(3);
		if (myWay == 0 || angle == 0) {
			canIgoAhead(intersection.getRoadLeft().isTaken(), intersection.getRoadRight().isTaken(),
					intersection.getRoadDown().isTaken());
		}
	}

	private void goAhead() {
		turning = true;
		speed = 200;
		int dist = 500;
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			turning = false;
		}

	}

	private void turnRight() {
		turning = true;
		angle = angle - 2;
		speed = 250;
		if (pastAngle - 90 == angle) {
			turning = false;
		}
		System.out.println(angle + "   " + pastAngle);
	}

	private void turnLeft() {
		turning = true;
		angle = angle + 2;
		speed = 450;
		if (pastAngle + 90 == angle) {
			turning = false;
		}
		System.out.println(angle + "   " + pastAngle);
	}

	private void checkForCollision(ArrayList<Intersection> intersectionList) {
		for (Intersection intersection : intersectionList) {
			try {
				if (Intersector.overlapConvexPolygons(this, intersection.getRoadRight().getRectToPoly())) {
					System.out.println("Kolizja1");
					intersection.getRoadRight().setTaken(true);
				} else {
					intersection.getRoadRight().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (Intersector.overlapConvexPolygons(this, intersection.getRoadLeft().getRectToPoly())) {
					System.out.println("Kolizja2");
					intersection.getRoadLeft().setTaken(true);
				} else {
					intersection.getRoadLeft().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (Intersector.overlapConvexPolygons(this, intersection.getRoadUp().getRectToPoly())) {
					System.out.println("Kolizja3");
					intersection.getRoadUp().setTaken(true);
				} else {
					intersection.getRoadUp().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (Intersector.overlapConvexPolygons(this, intersection.getRoadDown().getRectToPoly())) {
					System.out.println("kolizja4");
					intersection.getRoadDown().setTaken(true);
				} else {
					intersection.getRoadDown().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
		}
	}
}