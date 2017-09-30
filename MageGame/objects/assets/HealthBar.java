package objects.assets;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import gameObjects.GameObject;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import renderEngine.Engine;

public class HealthBar extends GameObject {

	private Entity bar;
	private Entity healthPart;

	public HealthBar(Vector3f position) {
		Vector3f pos = new Vector3f(position);
		pos.y = 9;
		Vector3f pos2 = new Vector3f(pos);
		pos2.y = 9.3f;
		bar = Engine.addStaticEntity(pos, new Vector3f(), 0.98f, ModelNames.PLANE.getFileName(),
				TextureNames.RED.getFileName());
		healthPart = Engine.addStaticEntity(pos2, new Vector3f(), 1f, ModelNames.PLANE.getFileName(),
				TextureNames.GREEN.getFileName());
	}

	public void update(Vector3f position, float health) {
		Vector3f pos = new Vector3f(position);
		pos.y = 9;
		Vector3f pos2 = new Vector3f(pos);
		pos2.y = 9.3f;
		bar.setPosition(new Vector3f(pos));
		healthPart.setPosition(new Vector3f(pos2));
		this.healthPart.setScale(health);
	}

	@Override
	public void update() {
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) bar);
		Engine.removeEntity((Entity) healthPart);
	}

}
