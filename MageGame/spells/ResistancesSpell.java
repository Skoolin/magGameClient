package spells;

import objects.administration.LivingObjectParameter;
import objects.assets.Buff;
import objects.characters.Mage;
import runes.Rune;

public abstract class ResistancesSpell {

	private static final int RUNECOSTONE = 10;
	private static final int RUNECOSTTWO = 10;

	private final static int AMOUNT = -30;
	private final static float LIFELENGTH = 5f;

	public static void cast(Mage mage, Rune first, Rune second) {
		if (((first == second) && ((first.getLoad() - (RUNECOSTONE + RUNECOSTTWO)) >= 0)) || ((!(first == second)
				&& (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0))))) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			mage.addBuff(new Buff(LivingObjectParameter.DAMAGE_MULTIPLIER, AMOUNT, "Resistances", LIFELENGTH, true));
		}
	}
}
