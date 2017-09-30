package listenerClient;

//This program shows a series of input dialog boxes.
//The next dialog is launched on the closing of the current dialog. 
//It provides examples of how to create dialog boxes
//with a text field, combo box and list box.

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.MainGame;
import renderEngine.Engine;

public class ClientUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextArea text;
	private JTextField userName;
	private JTextField password;
	
	private static int synchronizedTimerCounter = 0;
	private static final int MAX_TIME_PINGS = 20;
	
	private static long[] pingIntervalls = new long[MAX_TIME_PINGS];
	private static long[] clockIntervalls = new long[MAX_TIME_PINGS];
	
	private static boolean pingHasReturned = true;

	public ClientUI() throws UnknownHostException, IOException {

		// make sure the program exits when the frame closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Client UI");
		setSize(424, 200);

		// This will center the JFrame in the middle of the screen
		setLocationRelativeTo(null);

		panel = new JPanel();
		text = new JTextArea();
		text.setEditable(false);
		text.setPreferredSize(new Dimension(400, 100));
		panel.add(text);

		userName = new JTextField("username");
		userName.setPreferredSize(new Dimension(120, 20));
		password = new JTextField("password");
		password.setPreferredSize(new Dimension(120, 20));

		panel.add(userName);
		panel.add(password);

		Button save = new Button("login");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				byte[] userNameByte = userName.getText().getBytes(StandardCharsets.UTF_8);
				byte[] passwordByte = password.getText().getBytes(StandardCharsets.UTF_8);
				byte[] output = new byte[] { 0x06 };

				byte[] finalArray = new byte[userNameByte.length + userNameByte.length + 2 + output.length];
				finalArray[0] = output[0];
				int i = 1;
				while (i < userNameByte.length + 1) {
					finalArray[i] = userNameByte[i - 1];
					i++;
				}
				finalArray[i] = 0;
				i++;
				int j = 0;
				while (j < passwordByte.length) {
					finalArray[i] = passwordByte[j];
					i++;
					j++;
				}
				finalArray[i] = 0;
				String out = "";
				for(byte dd : finalArray){
					out = out + dd;
				}
				System.out.println(out);
				ClientTester.client.writeOut(finalArray);
			}
		});

		Button register = new Button("register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				byte[] userNameByte = userName.getText().getBytes(StandardCharsets.UTF_8);
				byte[] passwordByte = password.getText().getBytes(StandardCharsets.UTF_8);
				byte output = 0x08;

				byte[] finalArray = new byte[userNameByte.length + userNameByte.length + 2 + 1];
				finalArray[0] = output;
				int i = 1;
				while (i < userNameByte.length + 1) {
					finalArray[i] = userNameByte[i - 1];
					i++;
				}
				finalArray[i] = 0;
				i++;
				int j = 0;
				while (j < passwordByte.length) {
					finalArray[i] = passwordByte[j];
					i++;
					j++;
				}
				finalArray[i] = 0;
				ClientTester.client.writeOut(finalArray);
			}
		});

		panel.add(save);
		panel.add(register);
		add(panel);

		// Using JTextArea to show clicks and responses
		setVisible(true);
		// Input dialog with a text field

	}

	public void parsePackage(byte[] input) {

		switch (input[0]) {

		case 0x01:
			parseMove(input);
			break;
		case 0x02:
			parseCast(input);
			break;
		case 0x03:
			parseEnter(input);
			break;
		case 0x04:
			parseDie(input);
			break;
		case 0x05:
			parseLeave(input);
			break;
		case 0x06:
			parseLoginAccepted(input);
			break;
		case 0x07:
			parseLoginDenied();
			break;
		case 0x08:
			parseRegisterAccepted();
			break;
		case 0x09:
			parseRegisterDenied();
			break;
		case 0x0A:
			parseSynchronize(input);
			break;
		case 0x0B:
			parseHealthChange(input);
			break;
		default:
			writeCorruptedError();
			break;
		}
	}

	private void parseHealthChange(byte[] input) {
		if (ClientTester.game != null) {
			ClientTester.game.addCommand(input);
		} else {
			System.out.println("no game???");
		}
	}

	private void parseSynchronize(byte[] input) {
		pingHasReturned = true;
		synchronizedTimerCounter++;
		
		byte[] oldTimeB = new byte[8];
		for(int i = 0; i < 8; i++) {
			oldTimeB[i] = input[i+1];
		}
		byte[] serverTimeB = new byte[8];
		for(int i = 0; i < 8; i++) {
			serverTimeB[i] = input[i+9];
		}
		
		long oldTime = bytesToLong(oldTimeB);
		long serverTime = bytesToLong(serverTimeB);
		long currentTimeMillis = System.nanoTime() / 1_000_000;
		
		pingIntervalls[synchronizedTimerCounter-1] = currentTimeMillis - oldTime;
		clockIntervalls[synchronizedTimerCounter-1] = currentTimeMillis - serverTime;
		if(pingHasReturned && synchronizedTimerCounter < MAX_TIME_PINGS) {
			sendPingForSynchronization();
		} else {
			long averageDiffFromServer = 0;
			int averagePing = 0;
			for(int i = 0; i < pingIntervalls.length; i++) {
				averagePing += pingIntervalls[i];
				averageDiffFromServer += clockIntervalls[i];
			}
			averageDiffFromServer /= pingIntervalls.length;
			averagePing /= pingIntervalls.length;
			
			long timeDifference = averageDiffFromServer - (averagePing/2);
			ClientTester.game.setTimeDelay(timeDifference);
			synchronizedTimerCounter = 0;
		}
	}

	private void parseLoginDenied() {
		text.append("Login denied" + System.lineSeparator());

	}

	private void parseLeave(byte[] input) {
		if (ClientTester.game != null) {
			ClientTester.game.addCommand(input);
		} else {
			System.out.println("no game???");
		}

	}

	private void parseDie(byte[] input) {
		if (ClientTester.game != null) {
			ClientTester.game.addCommand(input);
		} else {
			System.out.println("no game???");
		}
	}

	private void parseCast(byte[] input) {
		if (ClientTester.game != null) {
			ClientTester.game.addCommand(input);
		} else {
			System.out.println("no game???");
		}
	}

	private void parseMove(byte[] input) {
		if (input.length == 27) {
			if (ClientTester.game != null) {
				ClientTester.game.addCommand(input);
			} else {
				System.out.println("no game???");
			}
		} else {
			writeCorruptedError();
		}
	}

	private void parseEnter(byte[] input) {
		if (ClientTester.game != null) {
			ClientTester.game.addCommand(input);
		} else {
			System.out.println("no game???");
		}
	}
	
	

	private void parseRegisterAccepted() {
		text.append("thanks for registering" + System.lineSeparator());
	}

	private void parseRegisterDenied() {
		text.append(
				" Oh! Registering failed, try again or contact our support. " + System.lineSeparator() + "(We don't have one, though.. well, just write us we'll know)"
						+ System.lineSeparator());
	}

	private void parseLoginAccepted(byte[] input) {
		if (input.length == 13) {
			text.append("connection accepted" + System.lineSeparator());
			ClientTester.startGame();
			long time = System.currentTimeMillis();
			while (System.currentTimeMillis() - 10000 < time) {
			}
				sendPingForSynchronization();
			ClientTester.game = (MainGame) Engine.manager;
			if (ClientTester.game != null) {
				ClientTester.game.addCommand(input);
			} else {
				System.out.println("no game???");
			}

		} else {
			writeCorruptedError();
		}
	}

	private void sendPingForSynchronization() {
		pingHasReturned = false;
		byte[] output = new byte[9];
		output[0] = 0x0A;
		long currentTimeMillis = System.nanoTime() / 1_000_000;
		byte[] bytes = longToBytes(currentTimeMillis);
		for(int i = 1; i < 9; i++) {
			output[i] = bytes[i-1];
		}
		ClientTester.client.writeOut(output);
	}

	private void writeCorruptedError() {
		text.append("error, corrupted answer from server" + System.lineSeparator());
	}

	public void logText(String input) {
		text.append(input + System.lineSeparator());
	}
	
	public byte[] longToBytes(long x) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(x);
	    return buffer.array();
	}
	
	public long bytesToLong(byte[] bytes) {
	    ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();//need flip 
	    return buffer.getLong();
	}
}
