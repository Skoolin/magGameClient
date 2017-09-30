package objects;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import animatedModels.AnimatedModel;
import entities.Entity;
import gameObjects.GameObject;
import loaders.AnimationLoader;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class Controlled extends GameObject {

	private static final float RUN_SPEED = 10f;
	private float currentSpeed = 0;
	private boolean isWalking = false;
	private Vector3f target;
	
	public int life;
	private LifeIndicator lifeIndicator;

	private static final float cooldown = 4f;
	private float counter = 0f;

	public Controlled() {
		super(Engine.addAnimatedEntity(5f, 0f, 10f, 0, 0, 0, 1, "model", "diffuse", "modelStanding", 1, 0));
		target = movable.getPosition();
		life = 5;
		lifeIndicator = new LifeIndicator(target);
	}
	
	@Override
	public void update() {
		if(life < 1) {
			Engine.end();
		}

		if (counter <= 0f && Mouse.isButtonDown(0)) {
			new Bullet(new Vector3f(movable.getPosition().x, movable.getPosition().y + 15, movable.getPosition().z),
					Engine.getMouseAtHeight(5));
			counter = cooldown;
		} else {
			counter -= DisplayManager.getFrameTime();
		}
		
		checkInputs();
		
		if (movable.distance(target) > 1f) {
			currentSpeed = RUN_SPEED;
			float distance = currentSpeed * DisplayManager.getFrameTime();
			
			Vector2f target2f = new Vector2f(target.x, target.z);
			Vector2f me = new Vector2f(movable.getPosition().x, movable.getPosition().z);
			
			Vector2f diff = (Vector2f) Vector2f.sub(me, target2f, null).normalise();
			
			float angle = (float) Math.atan2(-diff.y, diff.x);
			
			((Entity) movable).setRotY((float) Math.toDegrees(angle) - 90);
			
			float dx = (float) (distance * Math.sin(Math.toRadians(((Entity) movable).getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(((Entity) movable).getRotY())));
			
			((Entity) movable).increasePosition(dx, 0, dz);
		} else {
			currentSpeed = 0;
		}
		
		if(currentSpeed == 0 && isWalking) {
			((AnimatedModel) ((Entity) movable).getModel()).doAnimation(AnimationLoader.loadNewAnimation("modelStanding"), 0.1f);
			isWalking = false;
		} else if (currentSpeed != 0 && !isWalking) {
			((AnimatedModel) ((Entity) movable).getModel()).doAnimation(AnimationLoader.loadNewAnimation("model"), 0.1f);
			isWalking = true;
		}
		
		lifeIndicator.update(life, 5, movable.getPosition());
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
	}
	

	private void checkInputs() {
		if(Mouse.isButtonDown(1)) {
			target = getAimingPointOnGround();
			
			new Pointer(target);
		}
	}

	private Vector3f getAimingPointOnGround() {
		return Engine.getMouseAtHeight(0f);
	}
}
