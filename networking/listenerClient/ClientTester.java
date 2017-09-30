package listenerClient;

import java.io.IOException;
import java.net.UnknownHostException;

import main.MainGame;

public class ClientTester {

	public static String username;
	public static ClientUI ui;
	public static MainListenerClient client;
	public static MainGame game;

	public static void main(String[] args) throws UnknownHostException, IOException {
		ui = new ClientUI();
		client = new MainListenerClient();
		client.start();
	}
	
	public static void startGame() {
		GameThread thread = new GameThread();
		thread.start();
	}
}
