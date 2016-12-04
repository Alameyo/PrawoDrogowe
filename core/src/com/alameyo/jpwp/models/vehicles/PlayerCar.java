package com.alameyo.jpwp.models.vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class PlayerCar extends Car  {
	
public PlayerCar(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
@Override
protected void controlls(){
	if (Gdx.input.isKeyPressed(Keys.W)) {
		if (speed < topSpeed) {
			speed += acceleration*Gdx.graphics.getDeltaTime();
			System.out.println("Faster" +speed);
		}
	} else if (Gdx.input.isKeyPressed(Keys.S)) {
		if (speed > -topSpeed) {
			speed -= acceleration*Gdx.graphics.getDeltaTime();
			System.out.println("Slowing" + speed);
		}
	}else{
		if(speed>0){
			speed-=deacceleration*Gdx.graphics.getDeltaTime();
		}else if(speed<0){
			speed+=deacceleration*Gdx.graphics.getDeltaTime();
		}
	}
	if (Gdx.input.isKeyPressed(Keys.A)) {
		 angle = angle + 1.2f;
		//body.setAngularVelocity(1.2f);

	} else if (Gdx.input.isKeyPressed(Keys.D)) {
		//body.setAngularVelocity(-1.2f);
		 angle = angle - 1.2f;
	} 
}
}
