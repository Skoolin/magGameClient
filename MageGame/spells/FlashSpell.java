package spells;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.characters.Mage;
import runes.Rune;

public abstract class FlashSpell {

	private static final float RANGE = 20f;

	private static final int RUNECOSTONE = 15;
	private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Vector3f target) {
		mage.movable.setPosition(target);
		mage.setTarget(target);
	}
}
