package particleDesigner;

//This program shows a series of input dialog boxes.
//The next dialog is launched on the closing of the current dialog. 
//It provides examples of how to create dialog boxes
//with a text field, combo box and list box.

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import particles.ParticleParameters;
import statics.Const;;

public class ParticleEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private String[] names;
	private JTextField[] fields;
	ParticleParameters[] allParams;
	private String fileName;

	// Application start point
	public static void start(ParticleDesigner designer) {

		// Use the event dispatch thread for Swing components
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// create GUI frame
				new ParticleEditor(designer).setVisible(true);
			}
		});

	}

	public ParticleEditor(ParticleDesigner designer) {
		this.fileName = designer.fileName;

		// make sure the program exits when the frame closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Particle File Editor");
		setSize(424, 1000);

		// This will center the JFrame in the middle of the screen
		setLocationRelativeTo(null);

		allParams = ParticleParameters.class.getEnumConstants();
		panel = new JPanel();
		names = new String[allParams.length];
		fields = new JTextField[allParams.length];
		for (int i = 0; i < allParams.length; i++) {
			names[i] = allParams[i].name();
			Label label = new Label(names[i]);
			label.setPreferredSize(new Dimension(200, 24));
			panel.add(label);
			fields[i] = new JTextField();
			fields[i].setPreferredSize(new Dimension(200, 24));

			panel.add(fields[i]);
		}

		Button save = new Button("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				openSaveSequence();
			}
		});

		panel.add(save);
		add(panel);

		// Using JTextArea to show clicks and responses
		setVisible(true);
		// Input dialog with a text field

		loadFile(fileName);
	}

	public void openSaveSequence() {
		saveFile(fileName);
	}

	// Append the picked choice to the tracker JTextArea
	public void loadFile(String file) {
		File f = new File(System.getProperty("user.dir"));
		f = new File(Const.RESSOURCES_FOLDER + "ptk/" + file + ".ptk");

		// showInputDialog method returns null if the dialog is exited
		// without an option being chosen
		if (file == null || file.equals("")) {
			JOptionPane.showMessageDialog(this, "input file name please");
		} else if (f.exists() && !f.isDirectory()) {
			for (int i = 0; i < names.length; i++) {
				fields[i].setText(allParams[i].getProperty(file));
			}
			try {
				PrintWriter writer = new PrintWriter(f, "UTF-8");
				for (int i = 0; i < names.length; i++) {
					writer.println(allParams[i].name() + '=' + fields[i].getText());
				}
				writer.close();
			} catch (IOException e) {
				// do something
			}
		} else {
			JOptionPane.showMessageDialog(this, "File doesn't exist. creating new File");
			for (int i = 0; i < names.length; i++) {
				fields[i].setText(allParams[i].getDefaultProperty());
			}
			try {
				PrintWriter writer = new PrintWriter(f, "UTF-8");
				for (int i = 0; i < names.length; i++) {
					writer.println(allParams[i].name() + '=' + fields[i].getText());
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveFile(String file) {
		// showInputDialog method returns null if the dialog is exited
		// without an option being chosen
		if (file == null || file.equals("")) {
			JOptionPane.showMessageDialog(this, "Please specify a Name.");
		} else {
			try {
				File f = new File(System.getProperty("user.dir"));
				f = new File(Const.RESSOURCES_FOLDER + "ptk/" + file + ".ptk");
				PrintWriter writer = new PrintWriter(f, "UTF-8");
				for (int i = 0; i < names.length; i++) {
					writer.println(allParams[i].name() + '=' + fields[i].getText());
				}
				writer.close();
			} catch (IOException e) {
				// do something
			}
		}
	}
}