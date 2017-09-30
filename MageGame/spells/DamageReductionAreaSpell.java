package spells;

import objects.assets.DamageReductionCircle;
import objects.characters.Mage;
import runes.Rune;

public abstract class DamageReductionAreaSpell {

	private static final int RUNECOSTONE = 5;
	private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Rune first, Rune second) {
		if (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0)) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			new DamageReductionCircle(mage.movable.getPosition(), mage.get_faction());

		}
	}

}
