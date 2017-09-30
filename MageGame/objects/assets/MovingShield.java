package objects.assets;

import org.lwjgl.util.vector.Vector3f;

import objects.characters.LivingObject;
import renderEngine.DisplayManager;

public class MovingShield extends LivingObject {

	private float age;

	public MovingShield(Vector3f position, int team, Vector3f target) {
		super("movingShield", position);
		this._faction = team;
		this.target = new Vector3f(target);
		this.age = 0f;

	}

	public void update() {
		super.update();
		age += DisplayManager.getFrameTime();
		if (age > attackTime) {
			destroy();
			return;
		}
	}
}
