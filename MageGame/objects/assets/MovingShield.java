package objects.assets;

import entities.Movable;
import org.lwjgl.util.vector.Vector3f;
import objects.characters.LivingObject;

public class MovingShield extends LivingObject {

	private Vector3f startingPos;
	private Vector3f directionVector;

	public MovingShield(Vector3f position, int team, Vector3f target, int objId) {
		super("movingShield", position, objId);
		this._faction = team;
		this.target = new Vector3f(target);
		this.startingPos = new Vector3f(position);
	}

	public void update() {
		super.update();

		float distanceSq = movable.distanceSqr(startingPos);
		if (distanceSq > attackRange * attackRange) {
			destroy();
			return;
		}
	}
}
