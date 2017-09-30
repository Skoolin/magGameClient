package objects.assets;

import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import objects.assets.projectiles.Projectile;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class ProjectileShield extends GameObject {

	private static final float CAST_DELAY = 0.5f;
	private static final float RANGE_RADIUS = 8f;
	private static final float LIFETIME = 3f;
	private boolean isActivated;
	private Vector3f position;
	private float age;
	private int faction;

	public ProjectileShield(int faction, Vector3f target, Vector3f position) {
		super();
		this.faction = faction;
		this.age = 0;
		isActivated = false;
	}

	@Override
	public void update() { // TODO: visuals
		age += DisplayManager.getFrameTime();
		if (age > CAST_DELAY) {
			if (age > LIFETIME) {
				destroy();
				return;
			} else {
				if (!isActivated) {
					isActivated = true;
				}
				for (GameObject obj : Engine.detectCollisions(position, Projectile.class, RANGE_RADIUS)) {
					Projectile target = (Projectile) obj;
					if (target.get_faction() != faction) {
						target.destroy();
					}
				}

			}
		}

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
