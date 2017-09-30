package spells;

import objects.characters.Mage;
import runes.Rune;

public abstract class StealthSpell {

	private static final int RUNECOSTONE = 15;
	private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Rune first, Rune second) {
		if (((first == second) && ((first.getLoad() - (RUNECOSTONE + RUNECOSTTWO)) >= 0)) || ((!(first == second)
				&& (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0))))) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);

			// TODO

		}
	}
}
