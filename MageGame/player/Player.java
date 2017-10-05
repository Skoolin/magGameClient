package player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import gameObjects.GameObject;
import guis.GuiTexture;
import listenerClient.ClientTester;
import objects.administration.TextureNames;
import objects.characters.Mage;
import renderEngine.DisplayManager;
import renderEngine.Engine;
import runes.Rune;
import runes.RuneSet;
import userInterface.UI;

public class Player extends GameObject {

	private Mage mage;
	private int faction;

	private boolean isCasting;
	private boolean isRunePos1Pressed;
	private boolean isRunePos2Pressed;
	private boolean isRunePos3Pressed;
	private boolean isRunePos4Pressed;
	private int runePosition1;
	private int runePosition2;
	private int runePosition3;
	private float moveCommandCD;
	private static final float MOVE_COOLDOWN = 0.1f;

	private GuiTexture healthBG = Engine.addGui(TextureNames.RED.getFileName(), -0.8f, 0.75f, 0.1f, 0.01f);
	private GuiTexture healthBar = Engine.addGui(TextureNames.GREEN.getFileName(), -0.8f, 0.75f, 0.1f, 0.01f);

	/**
	 * creates a new Player with a Mage who gets a new RuneSet for test and
	 * gameplay purpose
	 * 
	 * @param newfaction
	 *            the faction of the Player
	 */
	public Player(int newfaction) {
		super();
		faction = newfaction;
		mage = new Mage(faction, new RuneSet(3, 1, faction), new Vector3f(0, 0, 0));
		mage.setTarget(new Vector3f(mage.movable.getPosition()));
		mage.addRune(1, new Rune(4, 1));
		mage.addRune(2, new Rune(5, 1));
		mage.addRune(3, new Rune(6, 1));
	}

	@Override
	public void update() {

		if (Engine.containsObject(mage)) {
			checkInputs();

			float currentHealth = ((float) mage.get_hP()) / mage.get_max_HP();
			healthBar.setScale(currentHealth / 10, 0.01f);
		}

	}

	@Override
	public void exit() {
		mage.destroy();
	}

	public Mage getMage() {
		return mage;
	}

	/**
	 * Player interaction: if the Player presses buttons, shit might happen.
	 * Cast is somewhere below. Move commands are in here
	 */
	private void checkInputs() {

		moveCommandCD -= DisplayManager.getFrameTime();
		if (Mouse.isButtonDown(1) && moveCommandCD < 0f) {
			Vector3f newTarget = getAimingPointOnGround();
			// mage.setTarget(newTarget);
			byte[] output = new byte[9];
			output[0] = 0x01;
			int bits1 = Float.floatToIntBits(newTarget.x);
			output[4] = (byte) (bits1 & 0xff);
			output[3] = (byte) ((bits1 >> 8) & 0xff);
			output[2] = (byte) ((bits1 >> 16) & 0xff);
			output[1] = (byte) ((bits1 >> 24) & 0xff);
			int bits2 = Float.floatToIntBits(newTarget.z);
			output[8] = (byte) (bits2 & 0xff);
			output[7] = (byte) ((bits2 >> 8) & 0xff);
			output[6] = (byte) ((bits2 >> 16) & 0xff);
			output[5] = (byte) ((bits2 >> 24) & 0xff);
			ClientTester.client.writeOut(output);
			moveCommandCD = MOVE_COOLDOWN;
		}

		doCasting();
	}

	/**
	 * activated in checkInputs. does shit if the left mouse button is pressed.
	 * then buttons can activate runes and so forth. in the end there should be
	 * a message to the server that the player wants to cast with some
	 * combination of runepositions
	 */
	private void doCasting() {
		if (isCasting) {
			if (Mouse.isButtonDown(0)) {

				displayRunes();

				if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
					runePosition1 = 0;
					runePosition2 = 0;
					runePosition3 = 0;
				}

				if (runePosition3 == 0) {
					if (!isRunePos1Pressed && Keyboard.isKeyDown(Keyboard.KEY_Q)) {
						isRunePos1Pressed = true;
						activateRune(1);
					}
					if (!isRunePos2Pressed && Keyboard.isKeyDown(Keyboard.KEY_W)) {
						isRunePos2Pressed = true;
						activateRune(2);
					}
					if (!isRunePos3Pressed && Keyboard.isKeyDown(Keyboard.KEY_E)) {
						isRunePos3Pressed = true;
						activateRune(3);
					}
					if (!isRunePos4Pressed && Keyboard.isKeyDown(Keyboard.KEY_R)) {
						isRunePos4Pressed = true;
						activateRune(4);
					}

				}

				if (isRunePos1Pressed && !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					isRunePos1Pressed = false;
				}

				if (isRunePos2Pressed && !Keyboard.isKeyDown(Keyboard.KEY_W)) {
					isRunePos2Pressed = false;
				}

				if (isRunePos3Pressed && !Keyboard.isKeyDown(Keyboard.KEY_E)) {
					isRunePos3Pressed = false;
				}

				if (isRunePos4Pressed && !Keyboard.isKeyDown(Keyboard.KEY_R)) {
					isRunePos4Pressed = false;
				}

			} else if ((runePosition1 != 0) && (runePosition2 != 0) && (runePosition3 == 0)) {
				Vector3f mouseTarget = Engine.getMouseAtHeight(0f);
				byte[] output = new byte[14];
				//TODO send rune id instead of fixed fireball id
				output[0] = 0x02;
				output[1] = (byte) 4;
				output[2] = (byte) 14;
				output[3] = 0x00;
				int bits1 = Float.floatToIntBits(mouseTarget.x);
				int bits2 = Float.floatToIntBits(mouseTarget.z);
				output[7] = (byte) (bits1 & 0xff);
				output[6] = (byte) ((bits1 >> 8) & 0xff);
				output[5] = (byte) ((bits1 >> 16) & 0xff);
				output[4] = (byte) ((bits1 >> 24) & 0xff);
				output[11] = (byte) (bits2 & 0xff);
				output[10] = (byte) ((bits2 >> 8) & 0xff);
				output[9] = (byte) ((bits2 >> 16) & 0xff);
				output[8] = (byte) ((bits2 >> 24) & 0xff);
				output[12] = 0x00;
				output[13] = 0x00;
				ClientTester.client.writeOut(output);
				// TODO
				finishCast();

			} else if ((runePosition1 != 0) && (runePosition2 != 0) && (runePosition3 != 0)) {
				//TODO, changed network protocol!!!
				Vector3f mouseTarget = Engine.getMouseAtHeight(0f);
				byte[] output = new byte[13];
				output[0] = 0x02;
				output[1] = (byte) ((runePosition1 << 4) + runePosition2);
				output[2] = (byte) (runePosition3 << 4);
				int bits1 = Float.floatToIntBits(mouseTarget.x);
				int bits2 = Float.floatToIntBits(mouseTarget.z);
				output[6] = (byte) (bits1 & 0xff);
				output[5] = (byte) ((bits1 >> 8) & 0xff);
				output[4] = (byte) ((bits1 >> 16) & 0xff);
				output[3] = (byte) ((bits1 >> 24) & 0xff);
				output[10] = (byte) (bits2 & 0xff);
				output[9] = (byte) ((bits2 >> 8) & 0xff);
				output[8] = (byte) ((bits2 >> 16) & 0xff);
				output[7] = (byte) ((bits2 >> 24) & 0xff);
				output[11] = 0x00;
				output[12] = 0x00;
				ClientTester.client.writeOut(output);
				// TODO
				finishCast();

			} else {
				finishCast();

			}

		} else if (Mouse.isButtonDown(0)) {
			isCasting = true;
		}
	}

	/**
	 * displayes 4 textures interactively on the screen while casting
	 */
	private void displayRunes() {
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			UI.firstSpell.setTexture(TextureNames.RED.getFileName());
		} else {
			UI.firstSpell.setTexture(TextureNames.GREEN.getFileName());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			UI.secondSpell.setTexture(TextureNames.RED.getFileName());
		} else {
			UI.secondSpell.setTexture(TextureNames.GREEN.getFileName());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			UI.thirdSpell.setTexture(TextureNames.RED.getFileName());
		} else {
			UI.thirdSpell.setTexture(TextureNames.GREEN.getFileName());
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			UI.fourthSpell.setTexture(TextureNames.RED.getFileName());
		} else {
			UI.fourthSpell.setTexture(TextureNames.GREEN.getFileName());
		}
	}

	/**
	 * resets the Rune UI textures
	 */
	private void finishCast() {

		isCasting = false;
		runePosition1 = 0;
		runePosition2 = 0;
		runePosition3 = 0;

		UI.firstSpell.setTexture(TextureNames.GREY.getFileName());
		UI.secondSpell.setTexture(TextureNames.GREY.getFileName());
		UI.thirdSpell.setTexture(TextureNames.GREY.getFileName());
		UI.fourthSpell.setTexture(TextureNames.GREY.getFileName());

	}

	/**
	 * activates certain runes, used in doCasting
	 * 
	 * @param position
	 *            the position that was activated by the Player
	 */
	private void activateRune(int position) {
		if (runePosition1 == 0) {
			runePosition1 = position;
		} else if (runePosition2 == 0) {
			if (runePosition1 != position) {
				runePosition2 = position;
			}
		} else if (runePosition3 == 0 && runePosition1 != position && runePosition2 != position) {
			runePosition3 = position;
		}
	}

	private Vector3f getAimingPointOnGround() {
		return Engine.getMouseAtHeight(0f);
	}

}
