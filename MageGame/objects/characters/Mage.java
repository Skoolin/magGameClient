package objects.characters;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Engine;
import runes.Rune;
import runes.RuneSet;
import runes.Spells;

public class Mage extends LivingObject {

	private RuneSet _myRunes;

	public Mage(int faction, RuneSet runes, Vector3f position) {
		super("mage", new Vector3f(position));
		_faction = faction;
		_myRunes = runes;
	}

	public int get_maxRuneRating() {
		return _myRunes.getMaxRuneRating();
	}

	/**
	 * gives the Rune at the specified index
	 * 
	 * @param index
	 *            (which position is adressed)
	 * 
	 * @return (the Rune at index)
	 */
	public Rune getRune(int index) {
		return _myRunes.getRune(index);
	}

	public RuneSet get_myRunes() {
		return _myRunes;
	}

	/** done with get - methods */

	/**
	 * adds a Rune, used by Player probably
	 * 
	 * @param index
	 * @param rune
	 */
	public void addRune(int index, Rune rune) {
		_myRunes.addRune(index, rune);
	}

	/**
	 * done leveling
	 */

	/**
	 * now casting, yay
	 */

	public void cast(int runeIndex1, int runeIndex2, Vector3f target) {
		Spells.invokeSpell(this, new Rune(runeIndex1, 1), new Rune(runeIndex2, 1), target);
	}

	public void cast(int runeIndex1, int runeIndex2, int runeIndex3) {
		Spells.invokeSpell(this, _myRunes.getRune(runeIndex1), _myRunes.getRune(runeIndex2),
				_myRunes.getRune(runeIndex3), Engine.getMouseAtHeight(0));
	}

}
