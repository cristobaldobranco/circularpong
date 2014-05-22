package org.whitesoft.circularpong;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class SegmentedArcedPaddle 
{
	ArrayList<Body> bodies = new ArrayList<Body>();
	
	public SegmentedArcedPaddle(World world, float centerx,float centery, float ccwendx,float ccwendy, float degrees, int segments, float width)
	{
		float angleStep = degrees / (float) segments;
		double dx = Math.abs(ccwendx - centerx);
		double dy = Math.abs(ccwendy - centery);
		double radius = Math.sqrt(dx*dx + dy*dy);
		dx = (ccwendx - centerx) / radius;
		dy = (ccwendy - centery) / radius;
		
		double anglebase = Math.atan2(dy, dx);
		
		BoxBodyBuilder factory = new BoxBodyBuilder();
		
		for ( int i = 1, j=0; i <= segments; i++, j++)
		{
			bodies.add(factory.createBoxBodyFromLine(world, BodyType.DynamicBody, 
					(float) (centerx + radius * Math.sin(Math.toRadians(Math.toDegrees(anglebase) + j * angleStep))), 
					(float) (centery + radius * Math.cos(Math.toRadians(Math.toDegrees(anglebase) + j * angleStep))), 
					(float) (centerx + radius * Math.sin(Math.toRadians(Math.toDegrees(anglebase) + i * angleStep))), 
					(float) (centery + radius * Math.cos(Math.toRadians(Math.toDegrees(anglebase) + i * angleStep))), 
							width));
		}
/*		
		for ( int i = 1, j=0; i < bodies.size(); i++, j++)
		{
			Body bodyA = bodies.get(i);
			Body bodyB = bodies.get(j);
			
	        WeldJointDef weldJointDef = new WeldJointDef();
	        weldJointDef.bodyA = bodyA;
	        weldJointDef.bodyB = bodyB;
	        weldJointDef.collideConnected = false;
	        weldJointDef.localAnchorA.set(bodyA.getLocalCenter());
	        weldJointDef.localAnchorB.set(bodyB.getLocalCenter());

	        world.createJoint(weldJointDef);			
			
		}
 */
	}
}
