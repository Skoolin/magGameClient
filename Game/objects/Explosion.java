package objects;

import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import particles.ParticleSystem;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class Explosion extends GameObject {
	
	private float timer;
	private static final float lifeLength = 1f;

	public Explosion(Vector3f position) {
		super(Engine.addParticleSystem("fire", 8, position.x, position.y + 3, position.z, 40f, 2f, 0f, 2f, 6f, 0.1f, 0.1f, 0.1f, true, 0f));
		timer = 0f;
	}

	@Override
	public void update() {
		if(timer > lifeLength) {
			destroy();
		} else {
			timer += DisplayManager.getFrameTime();
		}
	}

	@Override
	public void exit() {
		Engine.removeParticleSystem((ParticleSystem) movable);
	}

}
