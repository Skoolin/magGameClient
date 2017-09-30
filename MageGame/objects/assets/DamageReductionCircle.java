package objects.assets;

import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import objects.administration.LivingObjectParameter;
import objects.characters.LivingObject;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class DamageReductionCircle extends GameObject {

	private final static float LIFETIME = 8f;
	private final static float RANGE_RADIUS = 20f;
	private final static int REDUCTIVE_POWER = 20; // percent
	private Vector3f position;
	private float age;
	private int faction;

	public DamageReductionCircle(Vector3f position, int faction) {
		super();
		age = 0f;
		this.faction = faction;
		this.position = new Vector3f(position);
		// TODO: visuals
	}

	@Override
	public void update() {
		age += DisplayManager.getFrameTime();
		if (age >= LIFETIME) {
			destroy();
		}
		for (GameObject obj : Engine.detectCollisions(position, LivingObject.class, RANGE_RADIUS)) {
			LivingObject target = (LivingObject) obj;
			if (target.get_faction() == faction) {
				target.addBuff(new Buff(LivingObjectParameter.DAMAGE_MULTIPLIER, REDUCTIVE_POWER,
						"Damage Reduction Area Buff", 0.1f, true));
			}
		}
	}

	@Override
	public void exit() {

	}

}
