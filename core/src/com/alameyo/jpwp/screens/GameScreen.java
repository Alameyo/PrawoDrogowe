package com.alameyo.jpwp.screens;

import java.util.LinkedList;

import com.alameyo.jpwp.MainClass;
import com.alameyo.jpwp.models.Building;
import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.AutonomusCar;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {
	MainClass game;
	SpriteBatch batch;
	World world;
	OrthographicCamera cam;

	LinkedList<Road> roadList;
	LinkedList<Intersection> interSectionList;
	LinkedList<Car> carList;
	LinkedList<Building> builidingList;

	private ShapeRenderer shapeRenderer;
	long startTime;
	long endTime;
	public GameScreen(MainClass game) {
		this.game = game;
		this.batch = game.getBatch();

		startTime = System.currentTimeMillis();

		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();

		carList = new LinkedList<Car>();
		carList.add(new PlayerCar(world, Gdx.graphics.getWidth() / 2 - 450, Gdx.graphics.getHeight() / 2, 90));
		carList.add(new AutonomusCar(world, 0, 0, 0));
		carList.add(new AutonomusCar(world, 500, 0, 0));
		carList.add(new AutonomusCar(world, 0, 1400, 0));
		carList.add(new AutonomusCar(world, 500, -1400, 0));
		carList.add(new AutonomusCar(world, 1000, 1400, 0));
		carList.add(new AutonomusCar(world, 700, 1400, 0));

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
		}
		for (Intersection intersection : interSectionList) {
			intersection.reset();
		}

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			cam.zoom = 10.02f;
		} else {
			cam.zoom = 2.0f;
		}
		for (int i = 1; i < carList.size(); i++)
			if (Intersector.overlapConvexPolygons(carList.getFirst(), carList.get(i))) {
				endTime = System.currentTimeMillis() - startTime;
				System.out.println("Kolizja" + endTime);
				FileHandle file = Gdx.files.external("Score.txt");
				file.writeString(""+endTime, false);
			game.setScreen(new MenuScreen(game));
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
		for (Building building : builidingList) {
			building.getSprite().draw(batch);

		}
		for (Car autoCar : carList) {
			autoCar.getSprite().draw(batch);
			autoCar.getWayLightLeft().draw(batch);
			autoCar.getWayLightRight().draw(batch);
			autoCar.getWayLightLeftFront().draw(batch);
			autoCar.getWayLightRightFront().draw(batch);
		}


		batch.end();

		// drawDebug uncomment to see polygons
/*
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
*/
	}

/*	void drawDebug(ShapeRenderer shapeRenderer, Polygon ca) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.polygon(ca.getTransformedVertices());
		shapeRenderer.end();
	}
*/
	/**
	 * Initialization of map. 
	 */
	private void mapInit() {
		roadList = new LinkedList<Road>();
		interSectionList = new LinkedList<Intersection>();
		builidingList = new LinkedList<Building>();
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

		// bottom right without ways
		roadList.add(new Road(world, 1500, -1300, false, false, false));
		roadList.add(new Road(world, 1700, -1300, false, false, false));
		roadList.add(new Road(world, 1900, -1300, false, false, false));
		roadList.add(new Road(world, 2100, -1300, false, false, false));
		roadList.add(new Road(world, 2300, -1300, false, false, false));
		roadList.add(new Road(world, 2500, -1300, false, false, false));

		roadList.add(new Road(world, 1500, -1400, false, true, false));
		roadList.add(new Road(world, 1700, -1400, false, true, false));
		roadList.add(new Road(world, 1900, -1400, false, true, false));
		roadList.add(new Road(world, 2100, -1400, false, true, false));
		roadList.add(new Road(world, 2300, -1400, false, true, false));
		roadList.add(new Road(world, 2500, -1400, false, true, false));
		// num 131
		roadList.add(new Road(world, 2900, -1300, false, false, false));
		roadList.add(new Road(world, 3100, -1300, false, false, false));
		roadList.add(new Road(world, 3300, -1300, false, false, false));
		roadList.add(new Road(world, 3500, -1300, false, false, false));
		roadList.add(new Road(world, 3700, -1300, false, false, false));
		roadList.add(new Road(world, 3900, -1300, false, false, false));

		roadList.add(new Road(world, 2900, -1400, false, true, false));
		roadList.add(new Road(world, 3100, -1400, false, true, false));
		roadList.add(new Road(world, 3300, -1400, false, true, false));
		roadList.add(new Road(world, 3500, -1400, false, true, false));
		roadList.add(new Road(world, 3700, -1400, false, true, false));
		roadList.add(new Road(world, 3900, -1400, false, true, false));
		// num 143

		roadList.add(new Road(world, 4050, -150, false, false, true));
		roadList.add(new Road(world, 4050, -350, false, false, true));
		roadList.add(new Road(world, 4050, -550, false, false, true));
		roadList.add(new Road(world, 4050, -750, false, false, true));
		roadList.add(new Road(world, 4050, -950, false, false, true));
		roadList.add(new Road(world, 4050, -1150, false, false, true));

		roadList.add(new Road(world, 4150, -150, false, true, true));
		roadList.add(new Road(world, 4150, -350, false, true, true));
		roadList.add(new Road(world, 4150, -550, false, true, true));
		roadList.add(new Road(world, 4150, -750, false, true, true));
		roadList.add(new Road(world, 4150, -950, false, true, true));
		roadList.add(new Road(world, 4150, -1150, false, true, true));
		// num 155
		// inter1 road1
		roadList.add(new Road(world, 2900, 100, false, false, false));
		roadList.add(new Road(world, 3100, 100, false, false, false));
		roadList.add(new Road(world, 3300, 100, false, false, false));
		roadList.add(new Road(world, 3500, 100, false, false, false));
		roadList.add(new Road(world, 3700, 100, false, false, false));
		roadList.add(new Road(world, 3900, 100, false, false, false));

		roadList.add(new Road(world, 2900, 0, false, true, false));
		roadList.add(new Road(world, 3100, 0, false, true, false));
		roadList.add(new Road(world, 3300, 0, false, true, false));
		roadList.add(new Road(world, 3500, 0, false, true, false));
		roadList.add(new Road(world, 3700, 0, false, true, false));
		roadList.add(new Road(world, 3900, 0, false, true, false));
		// num 167
		roadList.add(new Road(world, 4050, 250, false, false, true));
		roadList.add(new Road(world, 4050, 450, false, false, true));
		roadList.add(new Road(world, 4050, 650, false, false, true));
		roadList.add(new Road(world, 4050, 850, false, false, true));
		roadList.add(new Road(world, 4050, 1050, false, false, true));
		roadList.add(new Road(world, 4050, 1250, false, false, true));

		roadList.add(new Road(world, 4150, 250, false, true, true));
		roadList.add(new Road(world, 4150, 450, false, true, true));
		roadList.add(new Road(world, 4150, 650, false, true, true));
		roadList.add(new Road(world, 4150, 850, false, true, true));
		roadList.add(new Road(world, 4150, 1050, false, true, true));
		roadList.add(new Road(world, 4150, 1250, false, true, true));
		// num 179
		roadList.add(new Road(world, 2900, 1500, false, false, false));
		roadList.add(new Road(world, 3100, 1500, false, false, false));
		roadList.add(new Road(world, 3300, 1500, false, false, false));
		roadList.add(new Road(world, 3500, 1500, false, false, false));
		roadList.add(new Road(world, 3700, 1500, false, false, false));
		roadList.add(new Road(world, 3900, 1500, false, false, false));

		roadList.add(new Road(world, 2900, 1400, false, true, false));
		roadList.add(new Road(world, 3100, 1400, false, true, false));
		roadList.add(new Road(world, 3300, 1400, false, true, false));
		roadList.add(new Road(world, 3500, 1400, false, true, false));
		roadList.add(new Road(world, 3700, 1400, false, true, false));
		roadList.add(new Road(world, 3900, 1400, false, true, false));
		// num 191

		interSectionList.add(new Intersection.Builder().x(1300).y(0).roadLeft(roadList.get(11)).roadUp(roadList.get(12))
				.roadDown(roadList.get(30)).roadRight(roadList.get(60)).build());
		interSectionList.add(new Intersection.Builder().x(1300).y(1400).roadDown(roadList.get(23))
				.roadLeft(roadList.get(47)).roadRight(roadList.get(84)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(1400).roadRight(roadList.get(36))
				.roadDown(roadList.get(59)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(0).roadRight(roadList.get(0)).roadUp(roadList.get(48))
				.roadDown(roadList.get(114)).build());
		interSectionList.add(new Intersection.Builder().x(2700).y(0).roadLeft(roadList.get(71)).roadUp(roadList.get(72))
				.roadRight(roadList.get(156)).build());
		interSectionList.add(new Intersection.Builder().x(2700).y(1400).roadDown(roadList.get(83))
				.roadLeft(roadList.get(95)).roadRight(roadList.get(180)).build());
		interSectionList.add(new Intersection.Builder().x(1300).y(-1400).roadUp(roadList.get(29))
				.roadLeft(roadList.get(107)).roadRight(roadList.get(120)).build());
		interSectionList.add(new Intersection.Builder().x(-100).y(-1400).roadUp(roadList.get(113))
				.roadRight(roadList.get(96)).build());
		interSectionList.add(new Intersection.Builder().x(2700).y(-1400).build());
		interSectionList.add(new Intersection.Builder().x(4100).y(-1400).roadLeft(roadList.get(143))
				.roadUp(roadList.get(149)).build());
		interSectionList.add(new Intersection.Builder().x(4100).y(0).roadDown(roadList.get(150))
				.roadLeft(roadList.get(167)).roadUp(roadList.get(168)).build());
		interSectionList.add(new Intersection.Builder().x(4100).y(1400).roadDown(roadList.get(179))
				.roadLeft(roadList.get(191)).build());

		builidingList.add(new Building(2000, -1100, 0));
		builidingList.add(new Building(200, 200, 0));
		builidingList.add(new Building(3000, 200, 2));
	}
}
