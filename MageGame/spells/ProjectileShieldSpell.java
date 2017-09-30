package spells;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.assets.ProjectileShield;
import objects.characters.Mage;
import runes.Rune;

public abstract class ProjectileShieldSpell {

	private static final int RUNECOSTONE = 10;
	private static final int RUNECOSTTWO = 15;
	private static final float RANGE = 20;

	public static void cast(Mage mage, Vector3f target, Rune first, Rune second) {
		if (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			Vector2f pos = new Vector2f(mage.movable.getPosition().x, mage.movable.getPosition().z);
			Vector2f targetpos = new Vector2f(target.x, target.z);
			Vector2f direction = Vector2f.sub(targetpos, pos, null);
			float distance = direction.lengthSquared();
			if (distance <= RANGE * RANGE) {
				new ProjectileShield(mage.get_faction(), target,
						new Vector3f(target.x + direction.x, target.y, target.z + direction.y));
			} else {
				Vector2f.add(pos, (Vector2f) direction.normalise().scale(RANGE), direction);
				new ProjectileShield(mage.get_faction(), new Vector3f(direction.x, target.y, direction.y),
						new Vector3f(direction.x * 2, target.y, direction.y * 2));
			}

			new ProjectileShield(mage.get_faction(), target, mage.movable.getPosition());

		}
	}

}
