package com.alameyo.jpwp.screens;

import java.nio.file.attribute.AclEntry.Builder;
import java.util.ArrayList;

import com.alameyo.jpwp.MainClass;
import com.alameyo.jpwp.models.intersection.Intersection;
import com.alameyo.jpwp.models.intersection.Road;
import com.alameyo.jpwp.models.vehicles.AutonomusCar;
import com.alameyo.jpwp.models.vehicles.Car;
import com.alameyo.jpwp.models.vehicles.PlayerCar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
/**
 * Screen with game
 */
public class GameScreen implements Screen {

	MainClass game;
	SpriteBatch batch;
	Car car, car2;
    Road road;
    Intersection intersection;
    World world;
    Body body;
    Texture img;
    OrthographicCamera cam;
    ArrayList<Road> roadList;
    ArrayList<Intersection> interSectionList;
   // Intersector intersector;
	private ShapeRenderer shapeRenderer;

	public GameScreen(MainClass game){
		this.game=game;
		this.batch= game.getBatch();
		
		
		world = new World(new Vector2(0f, 0f), true);
		cam = new OrthographicCamera(1024, 800);
		batch = new SpriteBatch();
		car = new PlayerCar(world,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		car2 = new AutonomusCar(world, 100, 100);
		//intersector = new Intersector();
		shapeRenderer = new ShapeRenderer();
		
		roadList = new ArrayList<Road>();
		interSectionList = new ArrayList<Intersection>();
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
		
		interSectionList.add(new Intersection.Builder().x(1300).y(0).roadLeft(roadList.get(11)).build());
		
		roadList.add(new Road(world, 1250, 200, false, false, true));
		roadList.add(new Road(world, 1250, 400, false, false, true));
		roadList.add(new Road(world, 1250, 600, false, false, true));
		roadList.add(new Road(world, 1250, 800, false, false, true));
		roadList.add(new Road(world, 1250, 1000, false, false, true));
		roadList.add(new Road(world, 1250, 1200, false, false, true));
		
		roadList.add(new Road(world, 1350, 200, false, true, true));
		roadList.add(new Road(world, 1350, 400, false, true, true));
		roadList.add(new Road(world, 1350, 600, false, true, true));
		roadList.add(new Road(world, 1350, 800, false, true, true));
		roadList.add(new Road(world, 1350, 1000, false, true, true));
		roadList.add(new Road(world, 1350, 1200, false, true, true));
		
		interSectionList.add(new Intersection.Builder().x(1300).y(1300).build());
		
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
		
		
	}
	/**
	 * Update of gamestate, invocated in render function.
	 */
	private void update() {
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		cam.update();

		batch.setProjectionMatrix(cam.combined);
		cam.position.set(car.x + car.getSprite().getWidth() / 2, car.y + car.getSprite().getHeight(), 0);
		car.carUpdate();
		car2.carUpdate();
	//	if(intersector.intersectPolygons(car.getFixture(), car2)){
			
		//}
		
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
		car.dispose();
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
		car.getSprite().draw(batch);
		
		car2.getSprite().draw(batch);
		if(Intersector.overlapConvexPolygons(car, car2)){
			System.out.println("Kolizja");
		}
		drawDebug(shapeRenderer, car);
		drawDebug(shapeRenderer, car2);
		
		
		batch.end();
	}
	void drawDebug(ShapeRenderer shapeRenderer, Car ca) {
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.polygon(ca.getTransformedVertices());
	    shapeRenderer.end();
	}
}
