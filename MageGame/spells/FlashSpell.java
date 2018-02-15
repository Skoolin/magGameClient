package spells;

import loaders.PTKLoader;
import objects.assets.effects.Sparkles;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.characters.Mage;
import runes.Rune;

public abstract class FlashSpell {

	private static final float RANGE = 20f;

	private static final int RUNECOSTONE = 15;
	private static final int RUNECOSTTWO = 15;

	public static void cast(Mage mage, Vector3f target) {
		Vector3f pos = mage.movable.getPosition();
		new Sparkles(pos);
		new Sparkles(target);
		mage.movable.setPosition(target);
		mage.setTarget(target);
	}
}
