package spells;

import org.lwjgl.util.vector.Vector3f;

import objects.characters.Mage;
import runes.Rune;

public abstract class ShockwaveSpell {

	private static final int RUNECOSTONE = 5;
	private static final int RUNECOSTTWO = 5;

	public static void cast(Mage mage, Vector3f location, Rune first, Rune second) {
		if (((first == second) && ((first.getLoad() - (RUNECOSTONE + RUNECOSTTWO)) >= 0)) || ((!(first == second)
				&& (((first.getLoad() - RUNECOSTONE) >= 0) && ((second.getLoad() - RUNECOSTTWO) >= 0))))) {
			first.use(RUNECOSTONE);
			second.use(RUNECOSTTWO);
			// TODO
		}
	}
}
