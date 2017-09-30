package listenerClient;

import main.MainGame;
import main.Starter;
import renderEngine.Engine;


public class GameThread extends Thread {
	public MainGame game;
	private Starter starter;
	
	public GameThread() {
		starter = new Starter();
	}
	
	public void run() {
		Engine.run(starter);
	}
}
