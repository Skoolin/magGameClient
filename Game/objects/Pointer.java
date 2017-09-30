package objects;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import gameObjects.GameObject;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class Pointer extends GameObject {

	private float age;
	private static final float lifeTime = 0.4f;

	public Pointer(Vector3f target) {
		super(Engine.addStaticEntity(target.x, target.y, target.z, 0f, 0f, 0f, 0.01f, ModelNames.BARREL.getFileName(),
				TextureNames.BARREL.getFileName(), 1f, 0f));
		age = 0f;
	}

	@Override
	public void update() {
		((Entity) movable).setScale(((Entity) movable).getScale() + DisplayManager.getFrameTime() * 0.3f);
		age += DisplayManager.getFrameTime();
		if (age > lifeTime) {
			destroy();
		}
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
	}

}
