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
	boolean haveNewWay;
	boolean normalWay;
	float pastAngle, pastX, pastY;
	Intersection currentIntersection;

	/**
	 * @param world
	 * @param x
	 * @param y
	 * @param angle
	 */
	public AutonomusCar(World world, float x, float y, float angle) {
		super(world, x, y, angle);
		rand = new Random();
		normalWay = true;
		haveNewWay = false;
		canIgo = false;

	}

	/**
	 * Check if car can go ahead through intersection.
	 */
	private boolean canIgoAhead() {
		if (angle == 0) {
			if (currentIntersection.getRoadRight() != null) {

				if (currentIntersection.getRoadDown() != null) {
					if (currentIntersection.getRoadDown().isTaken() == true) {

						canIgo = false;
					} else {
						canIgo = true;
					}
				} else {
					canIgo = true;
				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}

		} else if (angle == -90 || angle == 270) {
			if (currentIntersection.getRoadDown() != null) {

				if (currentIntersection.getRoadLeft() != null) {
					if (currentIntersection.getRoadLeft().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else {
					canIgo = true;
				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -180 || angle == 180) {
			if (currentIntersection.getRoadLeft() != null) {
				if (currentIntersection.getRoadUp() != null) {
					if (currentIntersection.getRoadUp().isTaken() == true) {
						canIgo = false;
					} else {
						canIgo = true;
					}
				} else {
					canIgo = true;
				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -270 || angle == 90) {
			if (currentIntersection.getRoadUp() != null) {
				if (currentIntersection.getRoadRight() != null) {
					if (currentIntersection.getRoadRight().isTaken() == true) {
						canIgo = false;

					} else {
						canIgo = true;
					}
				} else {
					canIgo = true;

				}
			} else {
				canIgo = false;
				haveNewWay = false;
			}
		}
		return canIgo;
	}

	/**
	 * @return
	 */
	private boolean canIgoRight() {
		if (angle == 0) {
			if (currentIntersection.getRoadDown() != null) {
				canIgo = true;

			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -90 || angle == 270) {
			if (currentIntersection.getRoadLeft() != null) {
				canIgo = true;

			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -180 || angle == 180) {
			if (currentIntersection.getRoadUp() != null) {
				canIgo = true;

			} else {
				canIgo = false;
				haveNewWay = false;
			}
		} else if (angle == -270 || angle == 90) {
			if (currentIntersection.getRoadRight() != null) {
				canIgo = true;

			} else {
				canIgo = false;
				haveNewWay = false;
			}
		}

		/*
		 * if (roadLeft == true) { canIgo = false; } else { canIgo = true; }
		 */
		return canIgo;

	}

	/**
	 * @return
	 */
	private boolean canIgoLeft() {
		if (angle == 0) {
			if (currentIntersection.getRoadUp() != null) {

				if (currentIntersection.getRoadRight() != null) {
					if (currentIntersection.getRoadRight().isTaken() == false) {

						if (currentIntersection.getRoadDown() != null) {
							if (currentIntersection.getRoadDown().isTaken() == false) {
								canIgo = true;
							} else {
								canIgo = false;
							}
						} else {
							canIgo = true;
						}
					} else {
						canIgo = false;
					}
				} else if (currentIntersection.getRoadDown() != null) {
					if (currentIntersection.getRoadDown().isTaken() == false) {
						canIgo = true;
					} else {
						canIgo = false;
					}
				}
			} else {
				haveNewWay = false;
				canIgo = false;
			}
		} else if (angle == -90 || angle == 270) {
			if (currentIntersection.getRoadRight() != null) {

				if (currentIntersection.getRoadDown() != null) {
					if (currentIntersection.getRoadDown().isTaken() == false) {

						if (currentIntersection.getRoadLeft() != null) {
							if (currentIntersection.getRoadLeft().isTaken() == false) {
								canIgo = true;
							} else {
								canIgo = false;
							}
						} else {
							canIgo = true;
						}
					} else {
						canIgo = false;
					}
				} else if (currentIntersection.getRoadLeft() != null) {
					if (currentIntersection.getRoadLeft().isTaken() == false) {
						canIgo = true;
					} else {
						canIgo = false;
					}
				}
			} else {
				haveNewWay = false;
				canIgo = false;
			}

		} else if (angle == -180 || angle == 180) {
			if (currentIntersection.getRoadDown() != null) {

				if (currentIntersection.getRoadUp() != null) {
					if (currentIntersection.getRoadUp().isTaken() == false) {

						if (currentIntersection.getRoadLeft() != null) {
							if (currentIntersection.getRoadLeft().isTaken() == false) {
								canIgo = true;
							} else {
								canIgo = false;
							}
						} else {
							canIgo = true;
						}
					} else {
						canIgo = false;
					}
				} else if (currentIntersection.getRoadLeft() != null) {
					if (currentIntersection.getRoadLeft().isTaken() == false) {
						canIgo = true;
					} else {
						canIgo = false;
					}
				}
			} else {
				haveNewWay = false;
				canIgo = false;
			}

		} else if (angle == -270 || angle == 90) {
			if (currentIntersection.getRoadLeft() != null) {

				if (currentIntersection.getRoadRight() != null) {
					if (currentIntersection.getRoadRight().isTaken() == false) {

						if (currentIntersection.getRoadUp() != null) {
							if (currentIntersection.getRoadUp().isTaken() == false) {
								canIgo = true;
							} else {
								canIgo = false;
							}
						} else {
							canIgo = true;
						}
					} else {
						canIgo = false;
					}
				} else if (currentIntersection.getRoadUp() != null) {
					if (currentIntersection.getRoadUp().isTaken() == false) {
						canIgo = true;
					} else {
						canIgo = false;
					}
				}
			} else {
				haveNewWay = false;
				canIgo = false;
			}
		}
		return canIgo;

	}

	@Override
	protected void controlls() {
		if (normalWay == true) {
			speed = 300;

		} else if (normalWay == false) {

			if (haveNewWay == false) {

				myWay = rand.nextInt(3);
				// myWay = 2;
				haveNewWay = true;

			}
			if (myWay == 0) {
				if (canIgoLeft() == true) {
					turnLeft();
				} else {
					speed = 0;
				}
			} else if (myWay == 2) {
				if (canIgoRight() == true) {
					turnRight();

				} else {
					speed = 0;
				}
			} else if (myWay == 1) {
				if (canIgoAhead() == true) {
					goAhead();
				} else {
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
		wayLightRightFront.setTexture(img2);
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle - 2;
			if (pastAngle - 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				wayLightRight.setTexture(img);
				wayLightRightFront.setTexture(img);
				if (angle == -360) {
					angle = 0;
				}
			}
		}

	}

	/**
	 * Manewr skrêtu w lewo.
	 */
	private void turnLeft() {

		turning = true;

		speed = 430;
		int dist = 200;
		wayLightLeft.setTexture(img2);
		wayLightLeftFront.setTexture(img2);
		if (x >= pastX + dist || x <= pastX - dist || y >= pastY + dist || y <= pastY - dist) {
			angle = angle + 2;
			if (pastAngle + 90 == angle) {
				turning = false;
				normalWay = true;
				haveNewWay = false;
				wayLightLeft.setTexture(img);
				wayLightLeftFront.setTexture(img);
				if (angle == 360) {
					angle = 0;

				}
			}
		}

	}

	/**
	 * Check for collision with intersection and update position before turning.
	 */
	protected void checkForCollision(LinkedList<Intersection> intersectionList) {
		for (Intersection intersection : intersectionList) {
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadRight().getRectToPoly())) {

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

					normalWay = false;
					rememberPositionBeforeTurning(intersection);
				}
			} catch (NullPointerException e) {
			}
			try {
				if (normalWay == true
						&& Intersector.overlapConvexPolygons(this, intersection.getRoadDown().getRectToPoly())) {
					normalWay = false;
					rememberPositionBeforeTurning(intersection);

				}
			} catch (NullPointerException e) {
			}
		}
	}

	/**
	 * @param intersection
	 */
	private void rememberPositionBeforeTurning(Intersection intersection) {
		pastX = x;
		pastY = y;
		pastAngle = angle;
		currentIntersection = intersection;

	}
}
