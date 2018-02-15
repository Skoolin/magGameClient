package objects.characters;

import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import animatedModels.AnimatedModel;
import entities.Entity;
import entities.Movable;
import gameObjects.GameObject;
import guis.GuiTexture;
import loaders.AnimationLoader;
import objects.administration.AnimationNames;
import objects.administration.LivingObjectParameter;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import objects.assets.Buff;
import objects.assets.HealthBar;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public abstract class LivingObject extends GameObject {

	protected int attackDamage;
	protected float hitboxRadius;
	protected float attackRange;
	protected float runSpeed;
	protected float attackAnimationTime;
	protected float attackTime;
	protected int maxHealth;
	protected int health;
	protected int damageShield;
	protected Vector3f target;
	protected int damageMultiplier; // percent

	protected HealthBar healthBar;

	protected float currentSpeed;
	protected boolean alife;
	protected String name;

	protected boolean isAnimated;

	protected ArrayList<Buff> buffs;

	/** faction decides what side his thing is on and who can attack it */
	protected int _faction;

	protected String animationName;
	protected int objId;

	public LivingObject(String fileName, Vector3f position, int objId) {
		super();
		this.isAnimated = Boolean.parseBoolean(LivingObjectParameter.HAS_ANIMATION.getProperty(fileName));
		movable = register(fileName, position);
		this.target = new Vector3f(position);
		this.name = fileName;
		this.attackDamage = Integer.parseInt(LivingObjectParameter.ATTACK_DAMAGE.getProperty(fileName));
		this.hitboxRadius = Float.parseFloat(LivingObjectParameter.HITBOX_RADIUS.getProperty(fileName));
		this.attackRange = Float.parseFloat(LivingObjectParameter.ATTACK_RANGE.getProperty(fileName));
		this.runSpeed = Float.parseFloat(LivingObjectParameter.RUN_SPEED.getProperty(fileName));
		this.attackAnimationTime = Float.parseFloat(LivingObjectParameter.ATTACK_ANIMATION_TIME.getProperty(fileName));
		this.attackTime = Float.parseFloat(LivingObjectParameter.ATTACK_TIME.getProperty(fileName));
		this.maxHealth = Integer.parseInt(LivingObjectParameter.MAX_HEALTH.getProperty(fileName));
		this.health = maxHealth;
		this.damageMultiplier = Integer.parseInt(LivingObjectParameter.DAMAGE_MULTIPLIER.getProperty(fileName));
		this.buffs = new ArrayList<>();
		this.objId = objId;
		healthBar = new HealthBar(position);
	}

	private Movable register(String fileName, Vector3f position) {
		String objectFile = ModelNames.valueOf(LivingObjectParameter.NAME.getProperty(fileName)).getFileName();
		String textureFile = TextureNames.valueOf(LivingObjectParameter.NAME.getProperty(fileName)).getFileName();
		if (isAnimated) {
			AnimationNames animationName = AnimationNames
					.valueOf(LivingObjectParameter.NAME.getProperty(fileName) + "_IDLE");
			this.animationName = animationName.getFileName();
			String animationFile = animationName.getFileName();

			return Engine.addAnimatedEntity(position.x, position.y, position.z, 0f, 0f, 0f, 1f, objectFile, textureFile,
					animationFile, 1f, 0f);
		} else {
			return Engine.addStaticEntity(position.x, position.y, position.z, 0f, 0f, 0f, 1f, objectFile, textureFile,
					1f, 0f);
		}
	}

	/** All the get - methods */

	public int get_faction() {
		return _faction;
	}

	public int get_max_HP() {
		return maxHealth;
	}

	public int get_hP() {
		return health;
	}

	public float getSpeed() {
		return runSpeed;
	}

	public boolean isAlife() {
		return alife;
	}

	public int getObjId() {
		return objId;
	}

	/** end of get - methods */

	/**
	 * 
	 * Hurting a living object is dark, man...
	 * 
	 * @param damage
	 *            the amount of damage
	 */
	public void hurt(int damage) {
		int newDamage = (damage * damageMultiplier) / 100;
		Iterator<Buff> it = buffs.iterator();
		while (it.hasNext()) {
			Buff buff = it.next();
			if (buff.getAttrib() == LivingObjectParameter.DAMAGE_SHIELD) {
				if (newDamage <= buff.getValue()) {
					buff.setValue(buff.getValue() - newDamage);
					newDamage = 0;
					break;
				} else {
					newDamage = newDamage += -buff.getValue();
					buff.destroy();
					it.remove();
				}
			}
		}
		trueHurt(newDamage);
	}

	private void trueHurt(int damage) {
		if (0 < health - damage) {
			health -= damage;
		} else {
			destroy();
		}
	}

	public void setHP(int value) {
		if (0 < value && value <= maxHealth) {
			health = value;
		} else if (value > maxHealth) {
			health = maxHealth;
		}
	}

	/**
	 * moves and turns toward the given Vector,
	 * 
	 * @param target
	 */

	public void moveTowards(Vector2f target) {
		if (movable.flatDistanceSqr(target) > 1f) {

			currentSpeed = runSpeed;
			float distance = currentSpeed * DisplayManager.getFrameTime();

			turnTowards(target);

			float dx = (float) (distance * Math.sin(Math.toRadians(((Entity) movable).getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(((Entity) movable).getRotY())));

			((Entity) movable).increasePosition(dx, 0, dz);
			if (isAnimated) {
				switchToAnimation(AnimationNames.valueOf(LivingObjectParameter.NAME.getProperty(name) + "_WALKING")
						.getFileName());
			}
		} else {
			currentSpeed = 0f;
			if (isAnimated) {
				switchToAnimation(
						AnimationNames.valueOf(LivingObjectParameter.NAME.getProperty(name) + "_IDLE").getFileName());
			}
		}
	}

	protected void turnTowards(Vector2f target) {
		Vector2f me = new Vector2f(movable.getPosition().x, movable.getPosition().z);

		Vector2f diff = (Vector2f) Vector2f.sub(me, target, null).normalise();

		float angle = (float) Math.atan2(-diff.y, diff.x);

		((Entity) movable).setRotY((float) Math.toDegrees(angle) - 90);
	}

	/**
	 * there u go already, every living thing has special needs. is for any such
	 * cases further down in the void.
	 * 
	 * this makes sure objects are removed from the game after they die
	 * 
	 * might be overwritten in subclasses if needed
	 */
	public void update() {

		moveTowards(new Vector2f(target.x, target.z));
		healthBar.update(movable.getPosition(), ((float) health) / maxHealth);

		if (health <= 0) {
			destroy();
		}

		updateBufflist();
	}

	public void setTarget(Vector3f vector) {
		target = new Vector3f(vector);
	}

	public void setPosition(float xPos, float yPos) {
	}

	@Override
	public void exit() {
		Engine.removeEntity((Entity) movable);
		healthBar.destroy();
	}

	protected void switchToAnimation(String animationFile) {
		if (!animationName.equals(animationFile)) {
			animationName = animationFile;
			((AnimatedModel) ((Entity) movable).getModel()).doAnimation(AnimationLoader.loadNewAnimation(animationFile),
					0.1f);
		}
	}

	// buff shit down here

	private void updateBufflist() {
		Iterator<Buff> it = buffs.iterator();
		while (it.hasNext()) {
			Buff buff = it.next();
			if (buff.getAge() >= buff.getLifeLength()) {
				buffEffects(buff, true);
				buff.destroy();
				it.remove();
			}
		}
	}

	private void buffEffects(Buff buff, boolean negative) {
		int neg = negative ? -1 : 1;
		switch (buff.getAttrib()) {
		case ATTACK_ANIMATION_TIME:
			attackAnimationTime += neg * buff.getValue();
			break;
		case ATTACK_DAMAGE:
			attackDamage += neg * buff.getValue();
			break;
		case ATTACK_RANGE:
			attackRange += neg * buff.getValue();
			break;
		case ATTACK_TIME:
			attackTime += neg * buff.getValue();
			break;
		case DAMAGE_MULTIPLIER:
			damageMultiplier += neg * buff.getValue();
			break;
		case HITBOX_RADIUS:
			hitboxRadius += neg * buff.getValue();
			break;
		case MAX_HEALTH:
			maxHealth += neg * buff.getValue();
			break;
		case RUN_SPEED:
			runSpeed += neg * buff.getValue();
			break;
		case DAMAGE_SHIELD:
			damageShield += neg * buff.getValue();
			break;
		default:
			break;
		}
	}

	private boolean containsUniqueBuff(Buff buff) {
		for (Buff baff : buffs) {
			if (buff.getName().equals(baff.getName())) {
				return true;
			}
		}
		return false;
	}

	public void addBuff(Buff buff) {
		if (!buffs.contains(buff)) {
			boolean containsBiff = containsUniqueBuff(buff);
			if (!buff.getUnique() || !containsBiff) {
				buffEffects(buff, false);
				buffs.add(buff);

			}

			else if (containsBiff) {
				for (Buff biff : buffs) {
					if (buff.getName().equals(biff.getName())) {
						biff.resetAge();
					}
				}
			}
		}
	}

}
