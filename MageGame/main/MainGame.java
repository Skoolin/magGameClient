package main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import guis.GuiTexture;
import objects.administration.TextureNames;
import objects.characters.Mage;
import player.Player;
import renderEngine.Engine;
import renderEngine.Game;
import runes.RuneSet;
import runes.Spells;
import statics.Const;
import userInterface.UI;

public class MainGame implements Game {

	private List<byte[]> serverCommands;
	private Player player;
	private Map<Integer, Mage> mageRefs;
	private GuiTexture cursor;
	private long delay;

	private Object sharedLock = new Object();

	public MainGame() {
		mageRefs = new HashMap<>();
		serverCommands = Collections.synchronizedList(new ArrayList<byte[]>());
		cursor = Engine.addGui("textures/ling", Const.SCREEN_WIDTH - Mouse.getX(), Const.SCREEN_HEIGHT - Mouse.getY(),
				0.015f, 0.015f);

		Engine.addTerrain(0, 0, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(0, -1, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(-1, 0, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(-1, -1, "blendMap", "grass", "mud", "grassFlowers", "path");

		UI.firstSpell = Engine.addGui(TextureNames.GREY.getFileName(), -0.4f, -0.8f, 0.05f, 0.05f);
		UI.secondSpell = Engine.addGui(TextureNames.GREY.getFileName(), -0.2f, -0.8f, 0.05f, 0.05f);
		UI.thirdSpell = Engine.addGui(TextureNames.GREY.getFileName(), 0.2f, -0.8f, 0.05f, 0.05f);
		UI.fourthSpell = Engine.addGui(TextureNames.GREY.getFileName(), 0.4f, -0.8f, 0.05f, 0.05f);

		player = new Player(1);

		Engine.addLight(10000, 10000, 10000, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 0f, 0f));
		Mouse.setGrabbed(false);
	}

	public void setTimeDelay(long newDelay) {
		synchronized (sharedLock) {
			delay = newDelay;
		}
	}

	@Override
	public void update() {
		synchronized (sharedLock) {
			for (byte[] byteArray : serverCommands) {
				switch (byteArray[0]) {
				case 0x03: // ENTER
					mageRefs.put(
							((byteArray[1] & 0xff) << 8)
									| (byteArray[2] & 0xff),
							new Mage(2,
									new RuneSet(3,
											1, 1),
									new Vector3f(ByteBuffer
											.wrap(new byte[] { byteArray[3], byteArray[4], byteArray[5], byteArray[6] })
											.getFloat(), 0f,
											ByteBuffer.wrap(new byte[] { byteArray[7], byteArray[8], byteArray[9],
													byteArray[10] }).getFloat())));
					break;
				case 0x06:
					Vector3f pos = new Vector3f(
							ByteBuffer.wrap(new byte[] { byteArray[5], byteArray[6], byteArray[7], byteArray[8] })
									.getFloat(),
							0f,
							ByteBuffer.wrap(new byte[] { byteArray[9], byteArray[10], byteArray[11], byteArray[12] })
									.getFloat());
					player.getMage().movable.setPosition(pos);
					player.getMage().setTarget(new Vector3f(pos));
					mageRefs.put(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff), player.getMage());
					break;

				case 0x01: // MOVE
					if (mageRefs.containsKey(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff))) {
						Mage toChange = mageRefs.get(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff));

						byte[] timePart = new byte[8];

						for (int i = 0; i < 8; i++) {
							timePart[i] = byteArray[i + 19];
						}

						long time = bytesToLong(timePart);
						// TODO use timePassed to move already passed space
						long timePassed = (System.nanoTime() / 1_000_000) - (time + delay);

						float xPos = ByteBuffer
								.wrap(new byte[] { byteArray[3], byteArray[4], byteArray[5], byteArray[6] }).getFloat();
						float zPos = ByteBuffer
								.wrap(new byte[] { byteArray[7], byteArray[8], byteArray[9], byteArray[10] })
								.getFloat();

						float xTarget = ByteBuffer
								.wrap(new byte[] { byteArray[11], byteArray[12], byteArray[13], byteArray[14] })
								.getFloat();
						float zTarget = ByteBuffer
								.wrap(new byte[] { byteArray[15], byteArray[16], byteArray[17], byteArray[18] })
								.getFloat();

						Vector2f toAdd = Vector2f.sub(new Vector2f(xTarget, zTarget), new Vector2f(xPos, zPos), null);
						if(toAdd.lengthSquared() > 0) {
							toAdd.normalise();

							xPos += toAdd.x * (((float) timePassed) / 1_000f) * toChange.getSpeed();
							zPos += toAdd.y * (((float) timePassed) / 1_000f) * toChange.getSpeed();

							toChange.setTarget(new Vector3f(xTarget, 0f, zTarget));
							toChange.movable.setPosition(new Vector3f(xPos, 0f, zPos));
						} else {
							toChange.setTarget(new Vector3f(xTarget, 0f, zTarget));
							toChange.movable.setPosition(new Vector3f(xTarget, 0f, zTarget));
						}
					}
					break;

				case 0x02: // CAST
					int mageId = ((byteArray[3] & 0xff) << 8) | (byteArray[4] & 0xff);
					if (mageRefs.containsKey(mageId)) {

						int spellId = ((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff);

						Spells.invokeSpell(mageRefs.get(mageId), spellId, byteArray);
					}
					break;

				case 0x04: // DIE
				case 0x05: // LEAVE
					if (mageRefs.containsKey(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff))) {
						Mage toChange = mageRefs.get(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff));
						toChange.destroy();
						mageRefs.remove(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff));
					}
					break;

				case 0x0B: // CHANGEHEALTH
					if (mageRefs.containsKey(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff))) {
						Mage toChange = mageRefs.get(((byteArray[1] & 0xff) << 8) | (byteArray[2] & 0xff));

						int health = ((byteArray[3] & 0xff) << 8) | (byteArray[4] & 0xff);
						toChange.setHP(health);
					}
					break;

				default:
					break;

				}
			}
			serverCommands.clear();
		}

		cursor.setPosition(((float) Mouse.getX() / (float) Const.SCREEN_WIDTH * 2) - 1,
				((float) Mouse.getY() / (float) Const.SCREEN_HEIGHT * 2) - 1);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Engine.end();
		}
	}

	@Override
	public void exit() {

	}

	public void addCommand(byte[] command) {
		synchronized (sharedLock) {
			serverCommands.add(command);
		}

	}

	public long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.put(bytes);
		buffer.flip();// need flip
		return buffer.getLong();
	}
}
