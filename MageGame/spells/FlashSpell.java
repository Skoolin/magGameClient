package spells;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.characters.Mage;
import runes.Rune;

public abstract class FlashSpell {

	private static final float RANGE = 30f;

	private static final int RUNECOSTONE = 15;
	private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Vector3f target, Rune first, Rune second) {
		if (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			Vector3f position3d = mage.movable.getPosition();
			Vector2f position = new Vector2f(position3d.x, position3d.z);
			Vector2f targetpos = new Vector2f(target.x, target.z);
			Vector2f direction = Vector2f.sub(targetpos, position, null);
			float distance = direction.lengthSquared();
			if (distance <= RANGE * RANGE) {
				mage.movable.setPosition(target);
			} else {
				Vector2f.add(position, (Vector2f) direction.normalise().scale(RANGE), direction);
				mage.movable.setPosition(new Vector3f(direction.x, target.y, direction.y));
			}

			mage.setTarget(mage.movable.getPosition());
		}
	}
}
