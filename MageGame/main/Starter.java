package main;

import gameObjects.GameStarter;
import renderEngine.Engine;
import renderEngine.Game;

public class Starter implements GameStarter {

	public MainGame main;

	public static void main(String[] args) {
		Engine.run(new Starter());
	}

	@Override
	public Game createGame() {
		main = new MainGame();
		return main;
	}

}
