package characters;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import gameObjects.GameObject;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import renderEngine.Engine;

public class Mob extends Character {

	private float engageRangeSqr;

	private static final float ENGAGE_RANGE = 100f;
	private static final float HITBOX_RADIUS = 3f;
	private static final float ATTACK_RANGE = 8f;
	private static final float RUN_SPEED = 10f;
	private static final float ATTACK_ANIMATION_TIME = 1f;
	private static final float ATTACK_TIME = 2f;
	private static final int MAX_HEALTH = 15;

	public Mob(Vector2f position) {
		super(3, Engine.addStaticEntity(position.x, 0f, position.y, 0f, 0f, 0f, 0.3f, ModelNames.BARREL.getFileName(),
				TextureNames.BARREL.getFileName(), 1f, 0f));
		this.engageRangeSqr = ENGAGE_RANGE * ENGAGE_RANGE;
		this.hitboxRadius = HITBOX_RADIUS;
		this.attackRangeSqr = ATTACK_RANGE * ATTACK_RANGE;
		this.runSpeed = RUN_SPEED;
		this.attackAnimationTime = ATTACK_ANIMATION_TIME;
		this.attackTime = ATTACK_TIME;
		this.attackDamage = 3;
		this.health = MAX_HEALTH;
		this.maxHealth = MAX_HEALTH;
	}

	@Override
	public void update() {
		super.update();
		List<GameObject> enemies = Engine.getObjectsOfType(Player.class);
		float maxDistSqr = engageRangeSqr;
		Character target = null;
		for (GameObject object : enemies) {
			Character newEnem = (Character) object;
			float newDist = newEnem.movable.distanceSqr(movable);
			if (newDist < maxDistSqr) {
				target = newEnem;
				maxDistSqr = newDist;
			}
		}

		if (target != null) {
			attack(target);
		}
	}

}
