package spells;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.assets.projectiles.HomingAttackMissile;
import objects.characters.LivingObject;
import objects.characters.Mage;
import renderEngine.Engine;
import runes.Rune;

public abstract class HomingAttackSpell {

	private static final int RUNECOSTONE = 10;
	private static final int RUNECOSTTWO = 15;

	private static final float RANGE = 30f;

	public static void cast(Mage mage, Vector3f target, Rune first, Rune second) {
		Vector3f position3d = mage.movable.getPosition();
		Vector2f position = new Vector2f(position3d.x, position3d.z);
		Vector2f targetpos = new Vector2f(target.x, target.z);
		Vector2f direction = Vector2f.sub(targetpos, position, null);
		float distance = direction.lengthSquared();
		LivingObject myTarget = (LivingObject) Engine.detectCollision(target, LivingObject.class, 3f);
		if ((myTarget != null) && ((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)
				&& distance <= RANGE * RANGE && myTarget.get_faction() != mage.get_faction()) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			new HomingAttackMissile(mage.get_faction(), position3d, myTarget);

		}
	}
}
