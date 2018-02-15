package particleDesigner;

import gameObjects.GameStarter;
import renderEngine.Engine;
import renderEngine.Game;

public class ParticleDesigner implements GameStarter {

	String fileName = "sparkles";
	static ParticleDesigner designer;

	public static void main(String[] args) {
		designer = new ParticleDesigner();
		ParticleEditor viewer = new ParticleEditor(designer);
		Engine.run(designer);
	}

	@Override
	public Game createGame() {
		return new ParticleViewer(fileName);
	}
}
