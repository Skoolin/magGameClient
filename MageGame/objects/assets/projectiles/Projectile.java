package objects.assets.projectiles;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Movable;
import gameObjects.GameObject;
import objects.characters.LivingObject;
import renderEngine.Engine;

public abstract class Projectile extends GameObject {
	protected float hitboxRadius;
	protected float speed;
	protected Vector3f target;
	protected Vector3f origin;
	protected int damage;
	protected float range;
	protected int team;

	public Projectile(Movable movable, Vector3f target, int team, Vector3f origin) {
		super(movable);
		this.target = target;
		this.damage = 50;
		this.hitboxRadius = 4;
		this.range = 100f;
		this.speed = 25f;
		this.team = team;
		this.origin = origin;
		Vector3f newVel = Vector3f.sub(target, movable.getPosition(), null).normalise(null);
		newVel.y = 0;
		movable.setVelocity((Vector3f) newVel.scale(speed));
	}

	@Override
	public void update() {
		if (range * range < movable.distanceSqr(origin)) {
			destroy();
			return;
		}

		List<GameObject> livingObjects = Engine.getObjectsOfType(LivingObject.class);
		for (GameObject object : livingObjects) {
			LivingObject livingObject = (LivingObject) object;
			if (livingObject.movable.flatDistanceSqr(movable) < hitboxRadius * hitboxRadius
					&& livingObject.get_faction() != team) {
				destroy();
				return;
			}
		}
	}

	public int get_faction() {
		return team;
	}
}
