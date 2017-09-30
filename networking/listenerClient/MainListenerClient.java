package listenerClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainListenerClient extends Thread {
	private Socket socket = null;
	private DataOutputStream out;
	private DataInputStream in;

	public MainListenerClient() throws UnknownHostException, IOException {
//		String ip = "magegame.intellikt.de";
		String ip = "localhost";
		int port = 8082;
		socketConnect(ip, port);
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}

	public void run() {
		while (true) {
			try {
				byte[] number = new byte[4];
					in.read(number);
				int length = byteArrayToInt(number);
				if (length > 0) {
					byte[] returnPackage = new byte[length];
					in.readFully(returnPackage, 0, length);
					ClientTester.ui.parsePackage(returnPackage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void socketConnect(String ip, int port) throws UnknownHostException, IOException {
		ClientTester.ui.logText("[Connecting to Server...]");
		this.socket = new Socket(ip, port);
		ClientTester.ui.logText("connected!");
	}

	public void writeOut(byte[] information) {
		try {
			byte[] length = intToByteArray(information.length);
			byte[] output = new byte[information.length + 4];
			output[0] = length[0];
			output[1] = length[1];
			output[2] = length[2];
			output[3] = length[3];
			for(int i = 0; i < information.length; i++) {
				output[i + 4] = information[i];
			}
			out.write(output);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	byte[] intToByteArray(int data) {

		byte[] result = new byte[4];

		result[0] = (byte) ((data & 0xFF000000) >> 24);
		result[1] = (byte) ((data & 0x00FF0000) >> 16);
		result[2] = (byte) ((data & 0x0000FF00) >> 8);
		result[3] = (byte) ((data & 0x000000FF) >> 0);

		return result;
	}
	
	int byteArrayToInt(byte[] array) {
		int result = 0;
		result += array[0] << 24;
		result += array[1] << 16;
		result += array[2] << 8;
		result += array[3];
		return result;
	}
}