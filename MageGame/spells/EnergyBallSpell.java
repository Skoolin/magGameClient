package spells;

import org.lwjgl.util.vector.Vector3f;

import objects.assets.projectiles.Fireball;
import objects.characters.Mage;
import runes.Rune;

public abstract class EnergyBallSpell {

	//private static final int RUNECOSTONE = 5;  // TODO: don't think we need those any more?
	//private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Vector3f target) {
		new Fireball(mage.get_faction(), target, mage.movable.getPosition());
			// I want energyballs instead of fireballs!!

	}

}
