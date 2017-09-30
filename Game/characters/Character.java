package characters;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Movable;
import gameObjects.GameObject;
import objects.LifeIndicator;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public abstract class Character extends GameObject {
	public int team;
	public int health;
	protected int maxHealth;
	public float hitboxRadius;
	protected float runSpeed;
	protected float attackAnimationTime;
	protected float attackAnimationTimer;
	protected float attackTime;
	protected float attackTimer;
	protected float attackRangeSqr;
	protected float attackDamage;
	protected LifeIndicator lifeIndicator;
	protected float currentSpeed;

	public Character(int team, Movable movable) {
		this.team = team;
		this.movable = movable;
		this.attackAnimationTimer = 0f;
		this.attackTimer = 0f;
		lifeIndicator = new LifeIndicator(movable.getPosition());
		currentSpeed = 0f;
	}

	@Override
	public void update() {
		lifeIndicator.update(health, maxHealth, movable.getPosition());
		if (health < 1) {
			destroy();
			return;
		}
		this.attackTimer -= DisplayManager.getFrameTime();
		if (this.attackTimer < -1f) {
			this.attackTimer = -1f;
		}
		updateIdleAnimation();
	}

	protected void moveTowards(Vector3f target) {
		this.attackAnimationTimer = 0f;

		if (movable.distance(target) > 1f) {

			currentSpeed = runSpeed;
			float distance = currentSpeed * DisplayManager.getFrameTime();

			turnTowards(target);

			float dx = (float) (distance * Math.sin(Math.toRadians(((Entity) movable).getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(((Entity) movable).getRotY())));

			((Entity) movable).increasePosition(dx, 0, dz);
		} else {
			currentSpeed = 0f;
		}

		updateMoveAnimation();
	}
	
	protected void turnTowards(Vector3f target) {
		Vector2f target2f = new Vector2f(target.x, target.z);
		Vector2f me = new Vector2f(movable.getPosition().x, movable.getPosition().z);

		Vector2f diff = (Vector2f) Vector2f.sub(me, target2f, null).normalise();

		float angle = (float) Math.atan2(-diff.y, diff.x);

		((Entity) movable).setRotY((float) Math.toDegrees(angle) - 90);
	}

	protected void attack(Character target) {
		if (movable.flatDistanceSqr(target.movable) < attackRangeSqr) {
			doAttack(target);
		} else {
			moveTowards(target.movable.getPosition());
		}
	}

	private void doAttack(Character target) {
		if (movable.distance(target.movable) > 1f) {
			turnTowards(target.movable.getPosition());
		}
		
		if (attackTimer < 0f) {
			if (attackAnimationTimer > attackAnimationTime) {
				attackTimer = attackTime;
				attackAnimationTimer = 0f;
				target.health -= attackDamage;
			} else {
				attackAnimationTimer += DisplayManager.getFrameTime();
			}
		}
		updateAttackAnimation();
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
		lifeIndicator.destroy();
	}

	protected void updateIdleAnimation() {
		movable.getPosition().y = 0f;
	}

	protected void updateMoveAnimation() {
		movable.getPosition().y = 0f;
	}

	protected void updateAttackAnimation() {
		movable.getPosition().y = 4 * attackAnimationTimer;
	}
}
