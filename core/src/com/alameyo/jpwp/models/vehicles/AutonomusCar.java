package com.alameyo.jpwp.models.vehicles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.physics.box2d.World;

public class AutonomusCar extends Car {

	int myWay;
	boolean canIgo;
	Random rand;
	boolean turning;
	boolean go;
	boolean haveNewWay;
	// int takenRoad;

	boolean normalWay;
	float pastAngle;
	float pastX;
	float pastY;
	Intersection currentIntersection;

	public AutonomusCar(World world, float x, float y, float angle) {
		super(world, x, y, angle);
		rand = new Random();
		normalWay = true;
		haveNewWay = false;
		canIgo = false;

	}

	/**
	 * Go ahead if there is nobody on right
	 */
	private boolean canIgoAhead() {
		if (angle == 0) {
			try {
				currentIntersection.getRoadRight().isTaken();
				System.out.println("znalaz³o prawo droge");
				try {
					if (currentIntersection.getRoadDown().isTaken() == true) {

						canIgo = false;
					} else {
						canIgo = true;
						System.out.println("drogadolna niezajeta");
					}
				} catch (NullPointerException e) {
					canIgo = true;
					System.out.println("droga dolnanieistnieje");
				}
			} catch (NullPointerException e2) {
				canIgo = false;
				haveNewWay = false;
			}

		} else if (angle == -90 || angle == 270) {
			try {
				currentIntersection.getRoadDown().isTaken();
				try {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = true;
				}
			} catch (NullPointerException e2) {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -180 || angle == 180) {
			try {
				currentIntersection.getRoadLeft().isTaken();
				try {
					if (currentIntersection.getRoadUp().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = true;
				}
			} catch (NullPointerException e2) {
				canIgo = false;
				System.out.println("Co jest nie tak");
				haveNewWay = false;
			}
		} else if (angle == -270 || angle == 90) {
			System.out.println("sprawdzam");
			try {
				currentIntersection.getRoadUp().isTaken();
				System.out.println("jest górna droga");

				try {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;

					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = true;

				}
			} catch (NullPointerException e2) {
				canIgo = false;
				haveNewWay = false;
				System.out.println("niema górnej drogi");
			}
		}
		/*
		 * if (roadRight == true) { canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;
	}

	private boolean canIgoRight() {
		if (angle == 0) {
			if (currentIntersection.getRoadDown() != null) {
				try {
					if (currentIntersection.getRoadUp().isTaken() == true) {

						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = false;
					haveNewWay = false;
				}
			}
		} else if (angle == -90 || angle == 270) {
			if (currentIntersection.getRoadLeft() != null) {
				try {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = true;
				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -180 || angle == 180) {
			if (currentIntersection.getRoadUp() != null) {
				try {
					if (currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = true;
				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -270 || angle == 90) {
			if (currentIntersection.getRoadRight() != null) {
				try {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} catch (NullPointerException e) {
					canIgo = false;
					haveNewWay = false;
				}
			} else {
				canIgo = false;

			}
		}

		/*
		 * if (roadLeft == true) { canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;
	}

	private boolean canIgoLeft() {

		if (angle == 0) {
			try {
				if (currentIntersection.getRoadUp().isTaken() == true
						|| currentIntersection.getRoadDown().isTaken() == true
						|| currentIntersection.getRoadRight().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				boolean up = false;
				boolean down = false;
				boolean right = false;
				// check which roads exists
				try {
					currentIntersection.getRoadUp().isTaken();
					up = true;
				} catch (NullPointerException e2) {
					up = false;
					System.out.println("Górna nie istnieje");
				}
				try {
					currentIntersection.getRoadDown().isTaken();
					down = true;
				} catch (NullPointerException e2) {
					down = false;
				}
				try {
					currentIntersection.getRoadRight().isTaken();
					right = true;
				} catch (NullPointerException e2) {
					right = false;
				}
				if (up == true && down == true && right == false) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && down == false && right == true) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == true && right == true) {
					if (currentIntersection.getRoadDown().isTaken() == true
							|| currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == false && right == true) {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == true && right == false) {
					if (currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && down == false && right == false) {
					if (currentIntersection.getRoadUp().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				}
			}
		} else if (angle == -90 || angle == 270) {
			try {
				if (currentIntersection.getRoadLeft().isTaken() == true
						|| currentIntersection.getRoadDown().isTaken() == true
						|| currentIntersection.getRoadRight().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				boolean left = false;
				boolean down = false;
				boolean right = false;
				// check which roads exists
				try {
					currentIntersection.getRoadLeft().isTaken();
					left = true;
				} catch (NullPointerException e2) {
					left = false;
					System.out.println("Lewo nie istnieje");
				}
				try {
					currentIntersection.getRoadDown().isTaken();
					down = true;
				} catch (NullPointerException e2) {
					down = false;
				}
				try {
					currentIntersection.getRoadRight().isTaken();
					right = true;
				} catch (NullPointerException e2) {
					right = false;
				}
				if (left == true && down == true && right == false) {
					if (currentIntersection.getRoadLeft().isTaken() == true
							|| currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (left == true && down == false && right == true) {
					if (currentIntersection.getRoadLeft().isTaken() == true
							|| currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (left == false && down == true && right == true) {
					if (currentIntersection.getRoadDown().isTaken() == true
							|| currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (left == false && down == false && right == true) {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (left == false && down == true && right == false) {
					if (currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (left == true && down == false && right == false) {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				}
			}
		} else if (angle == -180 || angle == 180) {
			try {
				if (currentIntersection.getRoadUp().isTaken() == true
						|| currentIntersection.getRoadDown().isTaken() == true
						|| currentIntersection.getRoadLeft().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {
				boolean up = false;
				boolean down = false;
				boolean left = false;
				// check which roads exists
				try {
					currentIntersection.getRoadUp().isTaken();
					up = true;
				} catch (NullPointerException e2) {
					up = false;
					System.out.println("Górna nie istnieje");
				}
				try {
					currentIntersection.getRoadDown().isTaken();
					down = true;
				} catch (NullPointerException e2) {
					down = false;
				}
				try {
					currentIntersection.getRoadLeft().isTaken();
					left = true;
				} catch (NullPointerException e2) {
					left = false;
				}
				if (up == true && down == true && left == false) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && down == false && left == true) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == true && left == true) {
					if (currentIntersection.getRoadDown().isTaken() == true
							|| currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == false && left == true) {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && down == true && left == false) {
					if (currentIntersection.getRoadDown().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && down == false && left == false) {
					if (currentIntersection.getRoadUp().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				}
			}
		} else if (angle == -270 || angle == 90) {
			try {
				if (currentIntersection.getRoadUp().isTaken() == true
						|| currentIntersection.getRoadLeft().isTaken() == true
						|| currentIntersection.getRoadRight().isTaken() == true) {
					canIgo = false;
				} else {
					canIgo = true;
				}
			} catch (NullPointerException e) {

				boolean up = false;
				boolean right = false;
				boolean left = false;
				// check which roads exists
				try {
					currentIntersection.getRoadUp().isTaken();
					up = true;
				} catch (NullPointerException e2) {
					up = false;
					System.out.println("Górna nie istnieje");
				}
				try {
					currentIntersection.getRoadRight().isTaken();
					right = true;
				} catch (NullPointerException e2) {
					right = false;
				}
				try {
					currentIntersection.getRoadLeft().isTaken();
					left = true;
				} catch (NullPointerException e2) {
					left = false;
				}
				if (up == true && right == true && left == false) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && right == false && left == true) {
					if (currentIntersection.getRoadUp().isTaken() == true
							|| currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && right == true && left == true) {
					if (currentIntersection.getRoadRight().isTaken() == true
							|| currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && right == false && left == true) {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == false && right == true && left == false) {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else if (up == true && right == false && left == false) {
					if (currentIntersection.getRoadUp().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				}
			}
		}
		return canIgo;

	}

	// public void carUpdate(ArrayList<Intersection> interSectionList) {
	// checkForCollision(interSectionList);
	// }

	@Override
	protected void controlls() {
		try {
			System.out.println("On da way" + currentIntersection.getRoadLeft().isTaken()
					+ currentIntersection.getRoadRight().isTaken() + currentIntersection.getRoadUp().isTaken()
					+ currentIntersection.getRoadDown().isTaken());
		} catch (NullPointerException e) {
		}
		if (normalWay == true) {
			speed = 300;

		} else if (normalWay == false) {

			if (haveNewWay == false) {

				myWay = rand.nextInt(2);
				// myWay = 2;
				haveNewWay = true;
				System.out.println("rand" + myWay);

			}
			if (myWay == 0) {
				if (canIgoLeft() == true) {
					turnLeft();
				} else {
					// haveNewWay = false;
					speed = 0;
				}
			} else if (myWay == 2) {
				if (canIgoRight() == true) {
					turnRight();

				} else {
					// haveNewWay = false;
					speed = 0;
				}
			} else if (myWay == 1) {
				if (canIgoAhead() == true) {
					goAhead();
				} else {
					// haveNewWay = false;
					speed = 0;
				}
			}
		}

	}

	private void goAhead() {
		turning = true;

		speed = 400;
		int dist = 800;
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
		wayLightRight.setTexture(img2);
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle - 2;
			if (pastAngle - 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				wayLightRight.setTexture(img);
				if (angle == -360) {
					angle = 0;
				}
			}
		}

		System.out.println(angle + "   " + pastAngle);
	}

	private void turnLeft() {

		turning = true;

		speed = 430;
		int dist = 200;
		wayLightLeft.setTexture(img2);
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle + 2;
			if (pastAngle + 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				wayLightLeft.setTexture(img);
				if (angle == 360) {
					angle = 0;

				}
			}
		}
		System.out.println(angle + "   " + pastAngle);
	}

	protected void checkForCollision(LinkedList<Intersection> intersectionList) {
		for (Intersection intersection : intersectionList) {
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadRight().getRectToPoly())) {
					System.out.println("Kolizja1");
					normalWay = false;
					rememberPositionBeforeTurning(intersection);
					// regenerator
					x = currentIntersection.getRoadLeft().x + 400;
					y = currentIntersection.getRoadLeft().y + 100;

				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadLeft().getRectToPoly())) {
					System.out.println("Kolizja2");
					normalWay = false;
					rememberPositionBeforeTurning(intersection);
					// regenerator
					x = currentIntersection.getRoadLeft().x - 200;
					y = currentIntersection.getRoadLeft().y;
				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadUp().getRectToPoly())) {
					System.out.println("Kolizja3");
					normalWay = false;
					rememberPositionBeforeTurning(intersection);

				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadDown().getRectToPoly())) {
					System.out.println("kolizja4");
					normalWay = false;
					rememberPositionBeforeTurning(intersection);

				}
			} catch (NullPointerException e) {
			}
		}
	}

	private void rememberPositionBeforeTurning(Intersection intersection) {
		pastX = x;
		pastY = y;
		pastAngle = angle;
		currentIntersection = intersection;

	}
}
