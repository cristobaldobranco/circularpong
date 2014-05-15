package org.whitesoft.circularpong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

public class CircularPongMain extends ApplicationAdapter {
	World world = new World(new Vector2(0, -20), true);  
	Box2DDebugRenderer debugRenderer;  
	OrthographicCamera camera;  
	static final float BOX_STEP=1/60f;  
	static final int BOX_VELOCITY_ITERATIONS=6;  
	static final int BOX_POSITION_ITERATIONS=2;  

	Body pivot;
	Body spike;
	BoxBodyBuilder bodyFactory;
	Joint joint;
	
	Matrix4 debugMatrix;
	
	//Creating joint
	void makeJoint()
	{
		//Using our custom class to create the revolute joint def
		RevoluteJoint j = new RevoluteJoint(pivot,spike,false);
		//Set Anchor A location
		j.SetAnchorA(BoxBodyBuilder.ConvertToBox(0), BoxBodyBuilder.ConvertToBox(0));
		//Set Anchor B location
		j.SetAnchorB(BoxBodyBuilder.ConvertToBox(100), BoxBodyBuilder.ConvertToBox(0));
		//Set revolution speed and torque
		j.SetMotor(20, 360);
		//This creates the joint
		joint=j.CreateJoint(world);
	}	

	@Override  
	public void create() {            
		float virtualWidth  = 480;
		float virtualHeight = 320;
		float viewportWidth = virtualWidth;
		float viewportHeight = virtualHeight;
		float physicalWidth = Gdx.graphics.getWidth();
		float physicalHeight = Gdx.graphics.getHeight();
		float aspect = virtualWidth / virtualHeight;
		// This is to maintain the aspect ratio.
		// If the virtual aspect ration does not match with the aspect ratio
		// of the hardware screen then the viewport would scaled to
		// meet the size of one dimension and other would not cover full
		// dimension
		// If we stretch it to meet the screen aspect ratio then textures will
		// get distorted either become fatter or elongated
		if (physicalWidth / physicalHeight >= aspect) {
			// Letterbox left and right.
			viewportHeight = virtualHeight;
			viewportWidth = viewportHeight * physicalWidth / physicalHeight;
		} else {
			// Letterbox above and below.
			viewportWidth = virtualWidth;
			viewportHeight = viewportWidth * physicalHeight / physicalWidth;
		}
		camera = new OrthographicCamera(viewportWidth, viewportHeight);
		camera.position.set(virtualWidth / 2, virtualHeight / 2, 0);
		camera.update();
		
		debugMatrix=camera.combined.cpy();
		debugMatrix.scale(BoxBodyBuilder.BOX_TO_WORLD, BoxBodyBuilder.BOX_TO_WORLD, 1f);

		debugRenderer = new Box2DDebugRenderer();  
		
		//Creating the pivot and the rotating body
		BoxBodyBuilder bodyFactory=new BoxBodyBuilder();
		pivot=bodyFactory.CreateCircleBody(world, BodyType.StaticBody, 200, 200, 30);
		spike=bodyFactory.CreateCircleBody(world, BodyType.DynamicBody, 200, 200, 20);
		makeJoint(); //the function below creates the joint
		
	}  
	@Override  
	public void dispose() {  
	}  
	@Override  
	public void render() {            
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
		debugRenderer.render(world, debugMatrix);  
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);  
	}  }
