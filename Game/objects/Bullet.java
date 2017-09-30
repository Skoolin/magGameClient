package objects;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import gameObjects.GameObject;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class Bullet extends GameObject {

	private static final float speed = 50f;
	private static final float lifeTime = 10f;
	private float age = 0f;

	public Bullet(Vector3f position, Vector3f target) {
		super(Engine.addStaticEntity(position.x, position.y, position.z, 0f, 0f, 0f, 0.1f, "barrel", "barrel", 1f, 0f));
		movable.setVelocity((Vector3f) Vector3f.sub(target, movable.getPosition(), null).normalise().scale(speed));
	}

	@Override
	public void update() {
		age += DisplayManager.getFrameTime();
		if (age > lifeTime) {
			destroy();
		}
		GameObject collision = Engine.detectCollision(this, Controlled.class, 3f);
		if (collision != null) {
			((Controlled) collision).life--;
			destroy();
			return;
		}
		
		collision = Engine.detectCollision(this, ShootingStall.class, 3f);
		if (collision != null) {
			((ShootingStall) collision).life--;
			destroy();
		}
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
	}

}
