package com.alameyo.jpwp;

import com.alameyo.jpwp.models.vehicles.Car;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MainClass extends ApplicationAdapter {
	SpriteBatch batch;
	private World myWorld;
	Car panSamochodzik;
	private Body body;
	
	Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    float torque = 0.0f;
    boolean drawSprite = true;

	@Override
	public void create() {
		batch = new SpriteBatch();
		
		myWorld = new World(new Vector2(0f, -5f), true);
		// Now create a BodyDefinition.  This defines the physics objects type and position in the simulation
        
		panSamochodzik = new Car();
        // Create a body in the world using our definition
        panSamochodzik.setBody(myWorld.createBody(panSamochodzik.getBodyDef()));
       // Fixture fixture = body.createFixture(panSamochodzik.getFixtureDef());

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the body
        // you also define it's properties like density, restitution and others we will see shortly
        // If you are wondering, density and area are used to calculate over all mass
                
        // Shape is the only disposable of the lot, so get rid of it
     
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                 getHeight());
		
	}

	@Override
	public void render() {
		update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(panSamochodzik.getPlayerSprite(), panSamochodzik.getPlayerSprite().getX(),panSamochodzik.getPlayerSprite().getY());
		

		batch.end();
	}

	private void update() {
		camera.update();
		myWorld.step(1f/60f, 3, 6);
		body.applyTorque(torque,true);
		panSamochodzik.getPlayerSprite().setPosition(body.getPosition().x, body.getPosition().y);
		if(Gdx.input.isKeyPressed(Keys.UP)){
			body.getPosition().add(2, 1);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		//panSamochodzik.img.dispose();
		//shape.dispose();
	}
}
