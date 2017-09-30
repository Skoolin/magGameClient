package objects;

import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import particles.ParticleSystem;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class HealCircle extends GameObject {

	private float age;
	private static final float maxAge = 2f;
	
	public HealCircle(Vector3f position) {
		super(Engine.addParticleSystem("heal", 1, position.x, position.y, position.z, 1f, 0f, 0f, maxAge, 10f, 0f, 0f, 0f, false, 0f));
	}

	@Override
	public void update() {
		age += DisplayManager.getFrameTime();
		if(age > maxAge) {
			destroy();
		}
	}

	@Override
	public void exit() {
		Engine.removeParticleSystem((ParticleSystem) movable); 
	}
	
}
