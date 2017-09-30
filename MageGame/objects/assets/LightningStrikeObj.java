package objects.assets;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import objects.characters.LivingObject;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class LightningStrikeObj extends GameObject {

	private static final float CAST_DELAY = 0.5f;
	private static final float RANGE = 40f;
	private static final float RANGE_RADIUS = 8f;
	private static final int DAMAGE = 25;
	private Vector3f position;
	private float age;
	private int faction;

	public LightningStrikeObj(int faction, Vector3f target, Vector3f position) {
		super();
		this.faction = faction;
		this.age = 0;
		Vector2f pos = new Vector2f(position.x, position.z);
		Vector2f targetpos = new Vector2f(target.x, target.z);
		Vector2f direction = Vector2f.sub(targetpos, pos, null);
		float distance = direction.lengthSquared();
		if (distance <= RANGE * RANGE) {
			this.position = new Vector3f(target);
		} else {
			Vector2f.add(pos, (Vector2f) direction.normalise().scale(RANGE), direction);
			this.position = new Vector3f(direction.x, target.y, direction.y);
		}
	}

	@Override
	public void update() {
		age += DisplayManager.getFrameTime();
		if (CAST_DELAY <= age) {
			for (GameObject obj : Engine.detectCollisions(position, LivingObject.class, RANGE_RADIUS)) {
				LivingObject target = (LivingObject) obj;
				if (target.get_faction() != faction) {
					target.hurt(DAMAGE);
				}
			}
			// TODO: Visuals
			destroy();
		}

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
