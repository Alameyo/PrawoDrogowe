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
//	Car car, car2;
	Road road;
	Intersection intersection;
	World world;
	Body body;
	Texture img;
	OrthographicCamera cam;
	ArrayList<Road> roadList;
	ArrayList<Intersection> interSectionList;
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
		cam.position.set(carList.getFirst().x + carList.getFirst().getSprite().getWidth() / 2, carList.getFirst().y + carList.getFirst().getSprite().getHeight(), 0);
		
		

		//car.carUpdate(interSectionList);
		
		for (Intersection intersection : interSectionList) {
					intersection.interUpdate(carList);
				}
		
		for (Car car : carList) {
			car.carUpdate(interSectionList);
		
			
		//	autoCar.carUpdate();
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
	//	car.getSprite().draw(batch);

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
		drawDebug(shapeRenderer, roadList.get(11).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(12).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(30).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(23).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(47).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(36).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(59).getRectToPoly());
		drawDebug(shapeRenderer, roadList.get(0).getRectToPoly());
	}

	void drawDebug(ShapeRenderer shapeRenderer, Polygon ca) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.polygon(ca.getTransformedVertices());
		shapeRenderer.end();
	}
	
private	void mapInit(){
	roadList = new ArrayList<Road>();
	interSectionList = new ArrayList<Intersection>();
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

				// inter2 road4  // num35

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

				interSectionList.add(new Intersection.Builder().x(1300).y(0).roadLeft(roadList.get(11)).roadUp(roadList.get(12))
						.roadDown(roadList.get(30)).build());
				interSectionList.add(new Intersection.Builder().x(1300).y(1400).roadDown(roadList.get(23)).roadLeft(roadList.get(47)).build());
				interSectionList.add(new Intersection.Builder().x(-100).y(1400).roadRight(roadList.get(36)).roadDown(roadList.get(59)).build());
				interSectionList.add(new Intersection.Builder().x(-100).y(0).roadRight(roadList.get(0)).roadUp(roadList.get(48)).build());
	
	}
}
