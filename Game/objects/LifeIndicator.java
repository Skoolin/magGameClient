package objects;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import gameObjects.GameObject;
import objects.administration.ModelNames;
import renderEngine.Engine;

public class LifeIndicator extends GameObject {

	int maxLife;
	int currentLife;

	public LifeIndicator(Vector3f position) {
		super(Engine.addStaticEntity(position.x, position.y + 15, position.z, 0f, 0f, 90f, 0.2f,
				ModelNames.BARREL.getFileName(), "red", 1f, 0f));
	}

	@Override
	public void update() {
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
	}

	public void update(int life, int maxLife, Vector3f position) {
		currentLife = life;
		movable.setPosition(Vector3f.add(position, new Vector3f(0, 15, 0), null));
		((Entity) movable).setScale(0.2f * ((float) currentLife / (float) maxLife));
	}

}
