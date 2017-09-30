package gameLogic;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import characters.Mob;
import characters.Player;
import guis.GuiTexture;
import objects.administration.ModelNames;
import objects.administration.TextureNames;
import renderEngine.Engine;
import renderEngine.Game;
import statics.Const;

public class GameCommands implements Game {
	private GuiTexture cursor;

	// at game start:
	public GameCommands() {
		// hier setup sachen machen einmal :)

		cursor = Engine.addGui("textures/ling", Const.SCREEN_WIDTH - Mouse.getX(), Const.SCREEN_HEIGHT - Mouse.getY(), 0.015f,
				0.015f);

		Engine.addTerrain(0, 0, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(0, -1, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(-1, 0, "blendMap", "grass", "mud", "grassFlowers", "path");
		Engine.addTerrain(-1, -1, "blendMap", "grass", "mud", "grassFlowers", "path");
		
		Engine.addStaticEntity(new Vector3f(3f, 3f, 3f),  new Vector3f(0f, 0f, 0f), 1f, ModelNames.ROCK.getFileName(), TextureNames.ROCK.getFileName());
		
		new Mob(new Vector2f(153f, 3f));
		new Mob(new Vector2f(153f, 6f));
		new Mob(new Vector2f(156f, 6f));
		new Player(0, new Vector2f(0f, 0f));

		Engine.addLight(10000, 10000, 10000, new Vector3f(1f, 1f, 1f), new Vector3f(1f, 0f, 0f));
	}

	// every frame:
	@Override
	public void update() {
		// hier sachen machen jeden Frame

		cursor.setPosition(((float) Mouse.getX() / (float) Const.SCREEN_WIDTH * 2) - 1,
				((float) Mouse.getY() / (float) Const.SCREEN_HEIGHT* 2) - 1);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Engine.end();
		}
	}

	public void exit() {
		// hier Sachen machen beim schliessen vom Spiel
	}

}
