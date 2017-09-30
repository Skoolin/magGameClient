package runes;

import org.lwjgl.util.vector.Vector3f;

import objects.characters.Mage;
import spells.DamageReductionAreaSpell;
import spells.DmgShieldSpell;
import spells.EnergyBallSpell;
import spells.FlashSpell;
import spells.HealSpell;
import spells.HomingAttackSpell;
import spells.LightningStrikeSpell;
import spells.MovingDmgShieldSpell;
import spells.ProjectileShieldSpell;
import spells.ResistancesSpell;
import spells.ShockwaveSpell;
import spells.ShowRunesSpell;
import spells.StealthSpell;
import spells.UselessLightSpell;
import spells.WallSpell;
import spells.XerathSpell;

public class Spells {

	public static void invokeSpell(Mage mage, Rune rune1, Rune rune2, Vector3f target) {
		switch (rune1.getType()) {
		case 1: // Rune for life, not implemented yet

			break;
		case 2: // Rune for death, not implemented yet

			break;
		case 3: // Rune for persistence, not implemented yet

			break;
		case 4: // Rune for thrust
			castThrust(rune1, rune2, mage, target);
			break;
		case 5: // Rune for energy
			castEnergy(rune1, rune2, mage, target);
			break;
		case 6: // Rune for vision
			castVision(rune1, rune2, mage, target);
			break;
		case 7: // Rune for protection
			castProtection(rune1, rune2, mage, target);
			break;
		default:
			// no rune activated, shouldn't do anything
			break;
		}
	}

	private static void castThrust(Rune first, Rune second, Mage mage, Vector3f target) {
		switch (second.getType()) {
		case 4: // Rune for Thrust
			ShockwaveSpell.cast(mage, target, first, second);
			break;
		case 5: // Rune for energy
			EnergyBallSpell.cast(mage, target, first, second);
			break;
		case 6: // Rune for vision
			UselessLightSpell.cast(mage, target, first, second);
			break;
		case 7: // Rune for protection
			MovingDmgShieldSpell.cast(mage, target, first, second);
			break;
		default:
			// no rune activated, shouldn't do anything
			break;
		}
	}

	private static void castEnergy(Rune first, Rune second, Mage mage, Vector3f target) {
		switch (second.getType()) {
		case 4: // Rune for Thrust
			FlashSpell.cast(mage, target, first, second);
			break;
		case 5: // Rune for energy
			HomingAttackSpell.cast(mage, target, first, second);
			break;
		case 6: // Rune for vision
			ShowRunesSpell.cast(mage, target, first, second);
			break;
		case 7: // Rune for protection
			DamageReductionAreaSpell.cast(mage, first, second);
			break;
		default:
			// no rune activated, shouldn't do anything
			break;
		}
	}

	private static void castVision(Rune first, Rune second, Mage mage, Vector3f target) {
		switch (second.getType()) {
		case 4: // Rune for Thrust
			XerathSpell.cast(mage, target, first, second);
			break;
		case 5: // Rune for energy
			LightningStrikeSpell.cast(mage, target, first, second);
			break;
		case 6: // Rune for vision
			StealthSpell.cast(mage, first, second);
			break;
		case 7: // Rune for protection
			HealSpell.cast(mage, target, first, second);
			break;
		default:
			// no rune activated, shouldn't do anything
			break;
		}
	}

	private static void castProtection(Rune first, Rune second, Mage mage, Vector3f target) {
		switch (second.getType()) {
		case 4: // Rune for Thrust
			WallSpell.cast(mage, target, first, second);
			break;
		case 5: // Rune for energy
			DmgShieldSpell.cast(mage, target, first, second);
			break;
		case 6: // Rune for vision
			ProjectileShieldSpell.cast(mage, target, first, second);
			break;
		case 7: // Rune for protection
			ResistancesSpell.cast(mage, first, second);
			break;
		default:
			// no rune activated, shouldn't do anything
			break;
		}
	}

	public static void invokeSpell(Mage mage, Rune rune, Rune rune2, Rune rune3, Vector3f target) {
		// nothing to see here
	}
}
