package characters;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import animatedModels.AnimatedModel;
import entities.Entity;
import gameObjects.GameObject;
import loaders.AnimationLoader;
import objects.HealCircle;
import objects.Pointer;
import objects.administration.AnimationNames;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import objects.assets.projectiles.Fireball;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class Player extends Character {

	private static final int STARTING_HEALTH = 15;
	private static final float HITBOX_RADIUS = 3f;
	private static final float ATTACK_RANGE = 20f;
	private static final float RUN_SPEED = 15f;
	private static final float ATTACK_ANIMATION_TIME = 1f;
	private static final float ATTACK_TIME = 0.5f;
	private static final int MAX_HEALTH = 30;
	private static final int ATTACK_DAMAGE = 30;

	private static final float FIREBALL_COOLDOWN = 2;
	private static final float HEAL_COOLDOWN = 10;

	private Vector3f target;
	private Character attackTarget;

	private float fireballCD;
	private float healCD;

	private boolean isWalking = false;

	public Player(int team, Vector2f position) {
		super(team, Engine.addAnimatedEntity(position.x, 0f, position.y, 0f, 0f, 0f, 1f, ModelNames.MAGE1.getFileName(),
				TextureNames.MAGE1.getFileName(), AnimationNames.MAGE1_IDLE.getFileName(), 1f, 0f));
		this.health = STARTING_HEALTH;
		this.maxHealth = STARTING_HEALTH;
		this.target = movable.getPosition();
		this.hitboxRadius = HITBOX_RADIUS;
		this.attackRangeSqr = ATTACK_RANGE * ATTACK_RANGE;
		this.runSpeed = RUN_SPEED;
		this.attackAnimationTime = ATTACK_ANIMATION_TIME;
		this.attackTime = ATTACK_TIME;
		this.attackDamage = ATTACK_DAMAGE;
		this.health = MAX_HEALTH;
		this.maxHealth = MAX_HEALTH;
		this.fireballCD = 0f;
		this.healCD = 0f;
		target = new Vector3f(position.x, 0, position.y);
	}

	@Override
	public void update() {
		super.update();

		checkInputs();

		fireballCD -= DisplayManager.getFrameTime();
		if (fireballCD < -1f) {
			fireballCD = -1f;
		}
		healCD -= DisplayManager.getFrameTime();
		if (healCD < -1f) {
			healCD = -1f;
		}

		if (attackTarget == null || !Engine.containsObject(attackTarget)) {
			moveTowards(target);
			attackTarget = null;
		} else {
			attack(attackTarget);
			target = movable.getPosition();
		}
	}

	private void checkInputs() {

		if (Keyboard.isKeyDown(Keyboard.KEY_Q) && fireballCD < 0f) {
			target = movable.getPosition();
			attackTarget = null;
			fireballCD = FIREBALL_COOLDOWN;
			new Fireball(team, getAimingPointOnGround(), movable.getPosition());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_W) && healCD < 0f) {
			healCD = HEAL_COOLDOWN;
			new HealCircle(movable.getPosition());
			health += 3;
		} else if (Mouse.isButtonDown(1)) {
			target = getAimingPointOnGround();
			new Pointer(target);
			List<GameObject> characters = Engine.getObjectsOfType(Character.class);
			attackTarget = null;
			for (GameObject object : characters) {
				Character character = (Character) object;
				if (character.movable.distance(target) < 3 && character.team != team) {
					attackTarget = character;
					break;
				}
			}
		}
	}

	@Override
	protected void updateMoveAnimation() {
		if (currentSpeed == 0 && isWalking) {
			((AnimatedModel) ((Entity) movable).getModel())
					.doAnimation(AnimationLoader.loadNewAnimation(AnimationNames.MAGE1_IDLE.getFileName()), 0.1f);
			isWalking = false;
		} else if (currentSpeed != 0 && !isWalking) {
			((AnimatedModel) ((Entity) movable).getModel())
					.doAnimation(AnimationLoader.loadNewAnimation(AnimationNames.MAGE1_WALKING.getFileName()), 0.1f);
			isWalking = true;
		}
	}

	@Override
	protected void updateIdleAnimation() {
		if (currentSpeed == 0 && isWalking) {
			((AnimatedModel) ((Entity) movable).getModel())
					.doAnimation(AnimationLoader.loadNewAnimation(AnimationNames.MAGE1_IDLE.getFileName()), 0.1f);
			isWalking = false;
		}
	}

	@Override
	protected void updateAttackAnimation() {
		if (attackAnimationTimer < 0.1 && attackAnimationTimer != 0f) {
			((AnimatedModel) ((Entity) movable).getModel())
					.doAnimation(AnimationLoader.loadNewAnimation(AnimationNames.MAGE1_ATTACK.getFileName()), 0.1f);
			isWalking = true;
		}
		if (attackTimer > attackAnimationTime) {
			updateIdleAnimation();
		}
	}

	private Vector3f getAimingPointOnGround() {
		return Engine.getMouseAtHeight(0f);
	}
}
