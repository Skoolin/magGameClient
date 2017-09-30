package runes;

// TODO : do faction and elemental rune types
public class Rune {

	private int _rating;
	private int _maxLoad;
	private int _load;
	private int _type;
	private String _typeName;
	private int _power;

	/**
	 * builds a Rune with the informations type as an int and rating as an int.
	 * uses switches to do so.
	 * 
	 * @param type
	 *            (what type the new rune is, 0 or anything not defined means an
	 *            empty rune)
	 * @param rating
	 */
	public Rune(int type, int rating) {

		_rating = rating;
		_type = type;

		/**
		 * this writes the name of the rune type into the field so it can be
		 * used more efficiently.
		 */
		switch (type) {
		case 1:
			_typeName = "life";
			break;
		case 2:
			_typeName = "death";
			break;
		case 3:
			_typeName = "persistence";
			break;
		case 4:
			_typeName = "thrust";
			break;
		case 5:
			_typeName = "energy";
			break;
		case 6:
			_typeName = "vision";
			break;
		case 7:
			_typeName = "protection";
			break;

		/*
		 * all additional runes have to be added here in the same way as the
		 * others
		 */

		default: // noRune is the name for runes in empty slots and placeholder
					// for runeslots. kind of a more eloquent null.
			_typeName = "noRune";
			_type = 0;
			break;
		}

		if (type != 0) {
			switch (_rating) {
			case 0:
				_maxLoad = 50;
				_power = 10;
				break;
			case 1:
				_maxLoad = 75;
				_power = 20;
				break;
			case 2:
				_maxLoad = 90;
				_power = 25;
				break;
			case 3:
				_maxLoad = 100;
				_power = 30;
				break;
			default:
				_maxLoad = 0;
				_power = 0;
				_type = 0;
				_typeName = "noRune";
				break;
			}
		} else {
			_maxLoad = 0;
			_power = 0;
			_rating = 0;
		}
		_load = _maxLoad;
	}

	/** all get-methods */

	public String getName() {
		return _typeName;
	}

	public int getType() {
		return _type;
	}

	public int getRating() {
		return _rating;
	}

	public int getLoad() {
		return _load;
	}

	public int getPower() {
		return _power;
	}

	/** end of get - methods */

	/**
	 * when u cast a spell u use energy from the runes.
	 * 
	 * @param cost
	 *            the energy cost for this rune
	 */
	public void use(int cost) {
		if (_load - cost >= 0) {
			_load -= cost;
		}
	}

	/**
	 * in the following methods i try to make the runes be the casting elements,
	 * and therefore making the casting much more efficient.
	 * 
	 * the Rune will use a abstract cast method in spell similar to the one in
	 * ourgame0_01
	 * 
	 * the rune will have to remember if it is active or not, and return a list
	 * with available runes for the completion of the spell as well as the spell
	 * that will be returned
	 */

	public void activate() {

	}

}
