package org.whitesoft.circularpong;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BoxBodyBuilder {

	public static float WORLD_TO_BOX=0.01f;
	public static float BOX_TO_WORLD=100f;

	static float ConvertToBox(float x){
		return x*WORLD_TO_BOX;
	}

	static float ConvertToWorld(float x){
		return x*BOX_TO_WORLD;
	}

	public Body CreateCircleBody(World world,BodyType bodyType,float posx,float posy,
			float radius){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.set(ConvertToBox(posx),ConvertToBox(posy));
		bodyDef.angle=0;

		Body body = world.createBody(bodyDef);
		makeCircleBody(body,radius,bodyType,1,1,0,0);
		return body;
	}

	void makeCircleBody(Body body,float radius,BodyDef.BodyType bodyType,
			float density,float restitution,float angle,float friction){

		FixtureDef fixtureDef=new FixtureDef();
		fixtureDef.density=density;
		fixtureDef.restitution=restitution;
		fixtureDef.friction=friction;
		fixtureDef.shape=new CircleShape();
		fixtureDef.shape.setRadius(ConvertToBox(radius));

		body.createFixture(fixtureDef);
		fixtureDef.shape.dispose();
	}
	
	public Body CreateBoxBody(World world,BodyType bodyType,float posx,float posy,float width,float height)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type=bodyType;
		bodyDef.linearDamping = 3.0f;
		bodyDef.angularDamping = 2.0f;
		// Then we set our bodies starting position in the world
		bodyDef.position.set(ConvertToBox(posx + width/2), ConvertToBox(posy + height /2));
		Body body = world.createBody(bodyDef);
		
		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = new PolygonShape();
		((PolygonShape) fixtureDef.shape).setAsBox(ConvertToBox(width), ConvertToBox(height));
		fixtureDef.density = 1;
		fixtureDef.restitution = 1; 
		fixtureDef.friction = 0;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);
		fixtureDef.shape.dispose();
		return body;	
	}	
	
}	