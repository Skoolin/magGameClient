package spells;

import org.lwjgl.util.vector.Vector3f;

import objects.assets.LightningStrikeObj;
import objects.characters.Mage;
import runes.Rune;

public abstract class LightningStrikeSpell {

	private static final int RUNECOSTONE = 10;
	private static final int RUNECOSTTWO = 10;

	public static void cast(Mage mage, Vector3f target, Rune first, Rune second) {
		if (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			new LightningStrikeObj(mage.get_faction(), target, mage.movable.getPosition());

		}
	}

}
