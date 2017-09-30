package runes;

public class RuneSet {

	private final static int RUNESET_MAX_NUMBER_POSSIBLE_RUNES = 4;
	// TODO: I want this to replace the Array used to hold the Runes of a mage
	// and maybe make the management of runes and spells more efficient.

	private int _maxNumberRunes;
	private Rune[] _myRunes;
	private int _maxRuneRating;

	public RuneSet(int maxNumberRunes, int maxRuneRating, int faction) {
		_maxNumberRunes = maxNumberRunes;
		_maxRuneRating = maxRuneRating;
		_myRunes = new Rune[5]; // TODO : maybe add elemental Runes here, maybe
								// somewhere else

		_myRunes[0] = new Rune(faction, 5); // TODO : add actual faction runes
											// to the Rune constructor (right
											// now they are just normal Runes,
											// we want them to have infinite
											// energy or at least a regeneration
											// rate..
		for (int i = 1; i < 5; i++) {
			_myRunes[i] = new Rune(0, 0);
		}

	}

	/** get - methods */

	public int getMaxNumberRunes() {
		return _maxNumberRunes;
	}

	public int getMaxRuneRating() {
		return _maxRuneRating;
	}

	public Rune getRune(int index) {
		return _myRunes[index];
	}

	/** end of get - methods */

	public void addRune(int index, Rune rune) {
		if (0 < index && index <= _maxNumberRunes && rune.getRating() <= _maxRuneRating) {
			_myRunes[index] = rune;
		}
	}

	public void increaseMaxNumberRunes() {
		if (RUNESET_MAX_NUMBER_POSSIBLE_RUNES > _maxNumberRunes) {
			_maxNumberRunes++;
		}
	}

	public void increaseMaxRuneRating() {
		_maxRuneRating++;
	}
}
