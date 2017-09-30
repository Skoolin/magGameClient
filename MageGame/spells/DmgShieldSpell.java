package spells;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.administration.LivingObjectParameter;
import objects.assets.Buff;
import objects.characters.LivingObject;
import objects.characters.Mage;
import renderEngine.Engine;
import runes.Rune;

public abstract class DmgShieldSpell {

	private static final int RUNECOSTONE = 5;
	private static final int RUNECOSTTWO = 10;

	private static final float RANGE = 60;
	private static final int AMOUNT = 15;
	private static final float TIME = 5f;

	public static void cast(Mage mage, Vector3f target, Rune first, Rune second) {
		if (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			Vector3f position3d = mage.movable.getPosition();
			Vector2f position = new Vector2f(position3d.x, position3d.z);
			Vector2f targetpos = new Vector2f(target.x, target.z);
			Vector2f direction = Vector2f.sub(targetpos, position, null);
			float distance = direction.lengthSquared();
			LivingObject myTarget = (LivingObject) Engine.detectCollision(target, LivingObject.class, 1f);
			if ((myTarget != null) && ((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)
					&& distance <= RANGE * RANGE && myTarget.get_faction() == mage.get_faction()) {
				first.use(RUNECOSTONE);
				second.use(RUNECOSTTWO);
				myTarget.addBuff(new Buff(LivingObjectParameter.DAMAGE_SHIELD, AMOUNT, "Dmg Shield", TIME, false));
				;

				// TODO: visuals
			}
		}
	}
}
