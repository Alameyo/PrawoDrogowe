package com.alameyo.jpwp.models.intersection;

import java.util.LinkedList;

import com.alameyo.jpwp.models.vehicles.Car;

/**
 * Class with methods handling collisions.
 *
 */
public class CollisionListeners {

	void listenRoadCar(Car car, LinkedList<Intersection> interSectionList) {
		for (Intersection intersection : interSectionList) {
			//intersection.interUpdate(car);
		}
	}

}
