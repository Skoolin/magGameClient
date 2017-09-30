package objects;

import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import renderEngine.DisplayManager;
import renderEngine.Engine;

public class ShootingStall extends GameObject {

	private GameObject target;
	private static final float cooldown = 4f;
	private float counter = 0f;

	public int life;
	private LifeIndicator lifeIndicator;

	public ShootingStall(GameObject target) {
		super(Engine.addStaticEntity(5, 5, 0, 0, 0, 0, 1, "stall", "stallTexture", 1, 0));
		this.target = target;

		life = 5;
		lifeIndicator = new LifeIndicator(movable.getPosition());
	}

	@Override
	public void update() {
		if (counter <= 0f) {
			new Bullet(new Vector3f(movable.getPosition().x, movable.getPosition().y + 6, movable.getPosition().z),
					target.movable.getPosition());
			counter = cooldown;
		} else {
			counter -= DisplayManager.getFrameTime();
		}

		lifeIndicator.update(life, 5, movable.getPosition());
	}

	@Override
	public void exit() {

	}

}
