package com.alameyo.jpwp.models.vehicles;

import java.util.ArrayList;
import java.util.Random;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.physics.box2d.World;

public class AutonomusCar extends Car {

	int myWay;
	boolean canIgo;
	Random rand;
	boolean turning;
	boolean go;
	boolean haveNewWay;
	boolean normalWay;
	float pastAngle;
	float pastX;
	float pastY;
	int takenRoad;
	Intersection currentIntersection;

	public AutonomusCar(World world, float x, float y, float angle) {
		super(world, x, y, angle);
		rand = new Random();
		normalWay = true;
		haveNewWay = false;

	}

	/**
	 * Go ahead if there is nobody on right
	 */
	private boolean canIgoAhead() {
		if (angle == 0) {
			try {
				if (currentIntersection.getRoadDown().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				canIgo = true;
			}
		} else if (angle == -90 || angle == 270) {
			try {
				if (currentIntersection.getRoadLeft().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				canIgo = true;
			}
		} else if (angle == -180 || angle == 180) {
			try {
				if (currentIntersection.getRoadUp().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				canIgo = true;
			}
		} else if (angle == -270 || angle == 90) {
			try {
				if (currentIntersection.getRoadRight().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				canIgo = true;
			}
		}
		/*
		 * if (roadRight == true) { canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;
	}

	private boolean canIgoRight() {
		if (angle == 0) {
			try{
				if (currentIntersection.getRoadUp().isTaken() == true) {
			
				canIgo = false;
			} else {
				canIgo = true;
			}}catch(NullPointerException e){
				canIgo = true;
			}
		} else if (angle == -90 || angle == 270) {
			try{if (currentIntersection.getRoadRight().isTaken() == true) {
				canIgo = false;
			} else {
				canIgo = true;
			}}catch(NullPointerException e){
				canIgo= true;
			}
		} else if (angle == -180 || angle == 180) {
			try{if (currentIntersection.getRoadDown().isTaken() == true) {
				canIgo = false;
			} else {
				canIgo = true;
			}}catch(NullPointerException e){
				canIgo = true;
			}
		} else if (angle == -270 || angle == 90) {
			try{if (currentIntersection.getRoadLeft().isTaken() == true) {
				canIgo = false;
			} else {
				canIgo = true;
			}}catch(NullPointerException e){
				canIgo = true;
			}
		}

		/*
		 * if (roadLeft == true) { canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;
	}

	private boolean canIgoLeft() {

		if (angle == 0) {
		//	try {
				if (currentIntersection.getRoadUp().isTaken() == true || currentIntersection.getRoadDown().isTaken()
						|| currentIntersection.getRoadRight().isTaken()) {
					canIgo = false;
				} else {
					canIgo = true;
				}
		//	} catch (NullPointerException e) {
			//	int wrong = 0;
			//	try {
			//		if (currentIntersection.getRoadUp().isTaken()) {

				//	}
				//} catch (NullPointerException e) {

				//}
		//	}
		} else if (angle == -90 || angle == 270) {
			if (currentIntersection.getRoadLeft().isTaken() == true || currentIntersection.getRoadDown().isTaken()
					|| currentIntersection.getRoadRight().isTaken()) {
				canIgo = false;
			} else {
				canIgo = true;
			}
		} else if (angle == -180 || angle == 180) {
			if (currentIntersection.getRoadUp().isTaken() == true || currentIntersection.getRoadDown().isTaken()
					|| currentIntersection.getRoadLeft().isTaken()) {
				canIgo = false;
			} else {
				canIgo = true;
			}
		} else if (angle == -270 || angle == 90) {
			if (currentIntersection.getRoadUp().isTaken() == true || currentIntersection.getRoadLeft().isTaken()
					|| currentIntersection.getRoadRight().isTaken()) {
				canIgo = false;
			} else {
				canIgo = true;
			}

		}

		/*
		 * if (roadLeft == true || roadRight == true || roadAhead == true) {
		 * canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;
	}

	public void carUpdate(ArrayList<Intersection> interSectionList) {
		checkForCollision(interSectionList);
	}

	@Override
	protected void controlls() {
		if (normalWay == true) {
			speed = 200;
			if (haveNewWay == false) {

				// myWay = rand.nextInt(3);
				myWay = 0;
				haveNewWay = true;
			}
		} else if (normalWay == false) {

			if (myWay == 0) {
				if (canIgoLeft() == true) {
					turnLeft();
				}
			} else if (myWay == 1) {
				if (canIgoRight() == true) {
					turnRight();
				}
			} else if (myWay == 2) {
				if (canIgoAhead() == true)
					goAhead();
			}
		}

	}

	private void goAhead() {
		turning = true;
		speed = 200;
		int dist = 500;
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			turning = false;
			normalWay = true;
			haveNewWay = false;
		}

	}

	private void turnRight() {
		turning = true;

		speed = 250;
		int dist = 200;
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle - 2;
			if (pastAngle - 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				if (angle == -360) {
					angle = 0;
				}
			}
		}

		System.out.println(angle + "   " + pastAngle);
	}

	private void turnLeft() {
		turning = true;

		speed = 450;
		int dist = 200;
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle + 2;
			if (pastAngle + 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				if (angle == 360) {
					angle = 0;
				}
			}
		}
		System.out.println(angle + "   " + pastAngle);
	}

	private void checkForCollision(ArrayList<Intersection> intersectionList) {
		for (Intersection intersection : intersectionList) {
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadRight().getRectToPoly())) {
					System.out.println("Kolizja1");
					intersection.getRoadRight().setTaken(true);
					normalWay = false;
					pastX = x;
					pastY = y;
					pastAngle = angle;
					currentIntersection = intersection;
				} else {
					intersection.getRoadRight().setTaken(false);
				}

			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadLeft().getRectToPoly())) {
					System.out.println("Kolizja2");
					intersection.getRoadLeft().setTaken(true);
					normalWay = false;
					pastX = x;
					pastY = y;
					pastAngle = angle;
					currentIntersection = intersection;
				} else {
					intersection.getRoadLeft().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadUp().getRectToPoly())) {
					System.out.println("Kolizja3");
					intersection.getRoadUp().setTaken(true);
					normalWay = false;
					pastX = x;
					pastY = y;
					pastAngle = angle;
					currentIntersection = intersection;
				} else {
					intersection.getRoadUp().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadDown().getRectToPoly())) {
					System.out.println("kolizja4");
					intersection.getRoadDown().setTaken(true);
					normalWay = false;
					pastX = x;
					pastY = y;
					pastAngle = angle;
					currentIntersection = intersection;
				} else {
					intersection.getRoadDown().setTaken(false);
				}
			} catch (NullPointerException e) {
			}
		}
	}
}
