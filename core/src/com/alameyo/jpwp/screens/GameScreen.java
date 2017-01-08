package com.alameyo.jpwp.screens;

import java.util.ArrayList;
import java.util.LinkedList;

import com.alameyo.jpwp.MainClass;
import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.AutonomusCar;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Screen with game
 */
public class GameScreen implements Screen {

	MainClass game;
	SpriteBatch batch;
	// Car car, car2;
	Road road;
	World world;
	Body body;
	Texture img;
	OrthographicCamera cam;
	LinkedList<Road> roadList;
	LinkedList<Intersection> interSectionList;
	LinkedList<Car> carList;
	// Intersector intersector;
	private ShapeRenderer shapeRenderer;

	public GameScreen(MainClass game) {
		this.game = game;
		this.batch = game.getBatch();

		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();

		carList = new LinkedList<Car>();
		carList.add(new PlayerCar(world, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));
		carList.add(new AutonomusCar(world, 0, 0, 0));
		carList.add(new AutonomusCar(world, 500, 0, 0));

		// intersector = new Intersector();
		shapeRenderer = new ShapeRenderer();
		mapInit();
	}

	/**
	 * Update of gamestate, invocated in render function.
	 */
	private void update() {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		cam.update();

		batch.setProjectionMatrix(cam.combined);
		cam.position.set(carList.getFirst().x + carList.getFirst().getSprite().getWidth() / 2,
				carList.getFirst().y + carList.getFirst().getSprite().getHeight(), 0);

		// car.carUpdate(interSectionList);

		for (Intersection intersection : interSectionList) {
			intersection.interUpdate(carList);
		}

		for (Car car : carList) {
			car.carUpdate(interSectionList);

			// autoCar.carUpdate();
		}
		System.out.println("" + interSectionList.getFirst().getRoadLeft().isTaken()
				+ interSectionList.getFirst().getRoadUp().isTaken()
				+ interSectionList.getFirst().getRoadRight().isTaken()
				+ interSectionList.getFirst().getRoadDown().isTaken());
		for (Intersection intersection : interSectionList) {
			intersection.reset();
		}

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			cam.zoom = 10.02f;
		} else {
			cam.zoom = 2.0f;
		}
		// if(intersector.intersectPolygons(car.getFixture(), car2)){

		// }

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		world.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (Road road : roadList) {
			road.getSprite().draw(batch);
		}
		for (Intersection intersection : interSectionList) {
			intersection.getSprite().draw(batch);
		}
		// car.getSprite().draw(batch);

		for (Car autoCar : carList) {
			autoCar.getSprite().draw(batch);
			autoCar.getWayLightLeft().draw(batch);
			autoCar.getWayLightRight().draw(batch);
		}

		// if (Intersector.overlapConvexPolygons(car, car2)) {
		// System.out.println("Kolizja");
		// }

		// drawDebug(shapeRenderer, car2);

		batch.end();
		drawDebug(shapeRenderer, carList.getFirst());

		for (Intersection intersection : interSectionList) {
			try {
				drawDebug(shapeRenderer, intersection.getRoadUp().getRectToPoly());
			} catch (NullPointerException e) {
			}
			try {
				drawDebug(shapeRenderer, intersection.getRoadDown().getRectToPoly());
			} catch (NullPointerException e) {
			}
			try {
				drawDebug(shapeRenderer, intersection.getRoadLeft().getRectToPoly());
			} catch (NullPointerException e) {
			}
			try {
				drawDebug(shapeRenderer, intersection.getRoadRight().getRectToPoly());
			} catch (NullPointerException e) {
			}
		}
	}

	void drawDebug(ShapeRenderer shapeRenderer, Polygon ca) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.polygon(ca.getTransformedVertices());
		shapeRenderer.end();
	}

	private void mapInit() {
		roadList = new LinkedList<Road>();
		interSectionList = new LinkedList<Intersection>();
		// inter1 road1
		roadList.add(new Road(world, 100, 100, false, false, false));
		roadList.add(new Road(world, 300, 100, false, false, false));
		roadList.add(new Road(world, 500, 100, false, false, false));
		roadList.add(new Road(world, 700, 100, false, false, false));
		roadList.add(new Road(world, 900, 100, false, false, false));
		roadList.add(new Road(world, 1100, 100, false, false, false));

		roadList.add(new Road(world, 100, 0, false, true, false));
		roadList.add(new Road(world, 300, 0, false, true, false));
		roadList.add(new Road(world, 500, 0, false, true, false));
		roadList.add(new Road(world, 700, 0, false, true, false));
		roadList.add(new Road(world, 900, 0, false, true, false));
		roadList.add(new Road(world, 1100, 0, false, true, false));

		// inter1/2 road 2 //num11

		roadList.add(new Road(world, 1250, 250, false, false, true));
		roadList.add(new Road(world, 1250, 450, false, false, true));
		roadList.add(new Road(world, 1250, 650, false, false, true));
		roadList.add(new Road(world, 1250, 850, false, false, true));
		roadList.add(new Road(world, 1250, 1050, false, false, true));
		roadList.add(new Road(world, 1250, 1250, false, false, true));

		roadList.add(new Road(world, 1350, 250, false, true, true));
		roadList.add(new Road(world, 1350, 450, false, true, true));
		roadList.add(new Road(world, 1350, 650, false, true, true));
		roadList.add(new Road(world, 1350, 850, false, true, true));
		roadList.add(new Road(world, 1350, 1050, false, true, true));
		roadList.add(new Road(world, 1350, 1250, false, true, true));

		// inter1 road 3 // num23

		roadList.add(new Road(world, 1250, -150, false, false, true));
		roadList.add(new Road(world, 1250, -350, false, false, true));
		roadList.add(new Road(world, 1250, -550, false, false, true));
		roadList.add(new Road(world, 1250, -750, false, false, true));
		roadList.add(new Road(world, 1250, -950, false, false, true));
		roadList.add(new Road(world, 1250, -1150, false, false, true));

		roadList.add(new Road(world, 1350, -150, false, true, true));
		roadList.add(new Road(world, 1350, -350, false, true, true));
		roadList.add(new Road(world, 1350, -550, false, true, true));
		roadList.add(new Road(world, 1350, -750, false, true, true));
		roadList.add(new Road(world, 1350, -950, false, true, true));
		roadList.add(new Road(world, 1350, -1150, false, true, true));

		// inter2 road4 // num35

		roadList.add(new Road(world, 100, 1500, false, false, false));
		roadList.add(new Road(world, 300, 1500, false, false, false));
		roadList.add(new Road(world, 500, 1500, false, false, false));
		roadList.add(new Road(world, 700, 1500, false, false, false));
		roadList.add(new Road(world, 900, 1500, false, false, false));
		roadList.add(new Road(world, 1100, 1500, false, false, false));

		roadList.add(new Road(world, 100, 1400, false, true, false));
		roadList.add(new Road(world, 300, 1400, false, true, false));
		roadList.add(new Road(world, 500, 1400, false, true, false));
		roadList.add(new Road(world, 700, 1400, false, true, false));
		roadList.add(new Road(world, 900, 1400, false, true, false));
		roadList.add(new Road(world, 1100, 1400, false, true, false));

		// inter 3/0 road5 //num47

		roadList.add(new Road(world, -150, 250, false, false, true));
		roadList.add(new Road(world, -150, 450, false, false, true));
		roadList.add(new Road(world, -150, 650, false, false, true));
		roadList.add(new Road(world, -150, 850, false, false, true));
		roadList.add(new Road(world, -150, 1050, false, false, true));
		roadList.add(new Road(world, -150, 1250, false, false, true));

		roadList.add(new Road(world, -50, 250, false, true, true));
		roadList.add(new Road(world, -50, 450, false, true, true));
		roadList.add(new Road(world, -50, 650, false, true, true));
		roadList.add(new Road(world, -50, 850, false, true, true));
		roadList.add(new Road(world, -50, 1050, false, true, true));
		roadList.add(new Road(world, -50, 1250, false, true, true));

		// num59

		// inter5 road6
		roadList.add(new Road(world, 1500, 100, false, false, false));
		roadList.add(new Road(world, 1700, 100, false, false, false));
		roadList.add(new Road(world, 1900, 100, false, false, false));
		roadList.add(new Road(world, 2100, 100, false, false, false));
		roadList.add(new Road(world, 2300, 100, false, false, false));
		roadList.add(new Road(world, 2500, 100, false, false, false));

		roadList.add(new Road(world, 1500, 0, false, true, false));
		roadList.add(new Road(world, 1700, 0, false, true, false));
		roadList.add(new Road(world, 1900, 0, false, true, false));
		roadList.add(new Road(world, 2100, 0, false, true, false));
		roadList.add(new Road(world, 2300, 0, false, true, false));
		roadList.add(new Road(world, 2500, 0, false, true, false));
		// num 71

		// inter5 road 6

		roadList.add(new Road(world, 2650, 250, false, false, true));
		roadList.add(new Road(world, 2650, 450, false, false, true));
		roadList.add(new Road(world, 2650, 650, false, false, true));
		roadList.add(new Road(world, 2650, 850, false, false, true));
		roadList.add(new Road(world, 2650, 1050, false, false, true));
		roadList.add(new Road(world, 2650, 1250, false, false, true));

		roadList.add(new Road(world, 2750, 250, false, true, true));
		roadList.add(new Road(world, 2750, 450, false, true, true));
		roadList.add(new Road(world, 2750, 650, false, true, true));
		roadList.add(new Road(world, 2750, 850, false, true, true));
		roadList.add(new Road(world, 2750, 1050, false, true, true));
		roadList.add(new Road(world, 2750, 1250, false, true, true));
		// num 83

		// inter2/5/6 road 7
		roadList.add(new Road(world, 1500, 1500, false, false, false));
		roadList.add(new Road(world, 1700, 1500, false, false, false));
		roadList.add(new Road(world, 1900, 1500, false, false, false));
		roadList.add(new Road(world, 2100, 1500, false, false, false));
		roadList.add(new Road(world, 2300, 1500, false, false, false));
		roadList.add(new Road(world, 2500, 1500, false, false, false));

		roadList.add(new Road(world, 1500, 1400, false, true, false));
		roadList.add(new Road(world, 1700, 1400, false, true, false));
		roadList.add(new Road(world, 1900, 1400, false, true, false));
		roadList.add(new Road(world, 2100, 1400, false, true, false));
		roadList.add(new Road(world, 2300, 1400, false, true, false));
		roadList.add(new Road(world, 2500, 1400, false, true, false));
		// num 95
		roadList.add(new Road(world, 100, -1300, false, false, false));
		roadList.add(new Road(world, 300, -1300, false, false, false));
		roadList.add(new Road(world, 500, -1300, false, false, false));
		roadList.add(new Road(world, 700, -1300, false, false, false));
		roadList.add(new Road(world, 900, -1300, false, false, false));
		roadList.add(new Road(world, 1100, -1300, false, false, false));

		roadList.add(new Road(world, 100, -1400, false, true, false));
		roadList.add(new Road(world, 300, -1400, false, true, false));
		roadList.add(new Road(world, 500, -1400, false, true, false));
		roadList.add(new Road(world, 700, -1400, false, true, false));
		roadList.add(new Road(world, 900, -1400, false, true, false));
		roadList.add(new Road(world, 1100, -1400, false, true, false));
		// num107

		roadList.add(new Road(world, -150, -150, false, false, true));
		roadList.add(new Road(world, -150, -350, false, false, true));
		roadList.add(new Road(world, -150, -550, false, false, true));
		roadList.add(new Road(world, -150, -750, false, false, true));
		roadList.add(new Road(world, -150, -950, false, false, true));
		roadList.add(new Road(world, -150, -1150, false, false, true)); // 113

		roadList.add(new Road(world, -50, -150, false, true, true)); // 114
		roadList.add(new Road(world, -50, -350, false, true, true));
		roadList.add(new Road(world, -50, -550, false, true, true));
		roadList.add(new Road(world, -50, -750, false, true, true));
		roadList.add(new Road(world, -50, -950, false, true, true));
		roadList.add(new Road(world, -50, -1150, false, true, true));
		// num 119

		interSectionList.add(new Intersection.Builder().x(1300).y(0).roadLeft(roadList.get(11)).roadUp(roadList.get(12))
				.roadDown(roadList.get(30)).roadRight(roadList.get(60)).build());
		interSectionList.add(new Intersection.Builder().x(1300).y(1400).roadDown(roadList.get(23))
				.roadLeft(roadList.get(47)).roadRight(roadList.get(84)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(1400).roadRight(roadList.get(36))
				.roadDown(roadList.get(59)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(0).roadRight(roadList.get(0)).roadUp(roadList.get(48))
				.roadDown(roadList.get(114)).build());
		interSectionList.add(
				new Intersection.Builder().x(2700).y(0).roadLeft(roadList.get(71)).roadUp(roadList.get(72)).build());
		interSectionList.add(new Intersection.Builder().x(2700).y(1400).roadDown(roadList.get(83))
				.roadLeft(roadList.get(95)).build());
		interSectionList.add(new Intersection.Builder().x(1300).y(-1400).roadUp(roadList.get(29))
				.roadLeft(roadList.get(107)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(-1400).roadUp(roadList.get(113))
				.roadRight(roadList.get(96)).build());

	}
}
