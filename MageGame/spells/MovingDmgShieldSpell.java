package spells;

import objects.characters.LivingObject;
import org.lwjgl.util.vector.Vector3f;

import objects.assets.MovingShield;
import objects.characters.Mage;
import runes.Rune;

public abstract class MovingDmgShieldSpell {

	private static final int RUNECOSTONE = 5;
	private static final int RUNECOSTTWO = 15;

	public static LivingObject cast(Mage mage, Vector3f target, int objId) {
		return new MovingShield(mage.movable.getPosition(), mage.get_faction(), target, objId);
	}

}
