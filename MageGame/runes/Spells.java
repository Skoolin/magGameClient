package runes;

import org.lwjgl.util.vector.Vector2f;
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

import java.nio.ByteBuffer;

public class Spells {

	public static void invokeSpell(Mage mage, int spellId, byte[] spellInfoBuffer) {

		switch (spellId) {
			case 16:

				break;
			case 23:
				float target_x = ByteBuffer.wrap(
						new byte[] {spellInfoBuffer[5],
									spellInfoBuffer[6],
									spellInfoBuffer[7],
									spellInfoBuffer[8]}).getFloat();
				float target_z = ByteBuffer.wrap(
						new byte[] {spellInfoBuffer[9],
									spellInfoBuffer[10],
									spellInfoBuffer[11],
									spellInfoBuffer[12]}).getFloat();
				Vector3f target = new Vector3f(target_x, 0f, target_z);
				EnergyBallSpell.cast(mage, target);

				break;
			case 41:

				break;
			case 24:
				target_x = ByteBuffer.wrap(
						new byte[] {spellInfoBuffer[5],
								spellInfoBuffer[6],
								spellInfoBuffer[7],
								spellInfoBuffer[8]}).getFloat();
				target_z = ByteBuffer.wrap(
						new byte[] {spellInfoBuffer[9],
								spellInfoBuffer[10],
								spellInfoBuffer[11],
								spellInfoBuffer[12]}).getFloat();
				target = new Vector3f(target_x, 0f, target_z);
				FlashSpell.cast(mage, target);

				break;
			case 32:

				break;
			case 53:

				break;
			case 45:

				break;
			case 55:

				break;
			case 78:

				break;
			default:
				// no known spell, shouldnt be doing anything
				break;
		}
	}
}
