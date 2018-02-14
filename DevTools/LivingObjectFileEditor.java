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

import objects.administration.LivingObjectParameter;

public class LivingObjectFileEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private String[] names;
	private JTextField[] fields;
	LivingObjectParameter[] allParams;

	// Application start point
	public static void main(String[] args) {

		// Use the event dispatch thread for Swing components
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// create GUI frame
				new LivingObjectFileEditor().setVisible(true);
			}
		});

	}

	public LivingObjectFileEditor() {
		// make sure the program exits when the frame closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Mob File Editor");
		setSize(412, 600);

		// This will center the JFrame in the middle of the screen
		setLocationRelativeTo(null);

		allParams = LivingObjectParameter.class.getEnumConstants();
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
		String input = JOptionPane.showInputDialog(this, "load from File: ");

		loadFile(input);
	}

	public void openSaveSequence() {
		String input = JOptionPane.showInputDialog(this, "object File Name: ");

		saveFile(input);
	}

	// Append the picked choice to the tracker JTextArea
	public void loadFile(String file) {
		File f = new File(System.getProperty("user.dir"));
		f = new File(f.getParentFile().getAbsolutePath() + "/GameEngine/res/lgo/" + file + ".lgo");

		// showInputDialog method returns null if the dialog is exited
		// without an option being chosen
		if (file == null || file.equals("")) {
			JOptionPane.showMessageDialog(this, "creating new File");
		} else if (f.exists() && !f.isDirectory()) {
			for (int i = 0; i < names.length; i++) {
				fields[i].setText(allParams[i].getProperty(file));
			}
		} else {
			JOptionPane.showMessageDialog(this, "File doesn't exist.");
		}
	}

	public void saveFile(String file) {
		// showInputDialog method returns null if the dialog is exited
		// without an option being chosen
		if (file == null || file.equals("")) {
			JOptionPane.showMessageDialog(this, "Please specify a Name.");
		} else {
			File f = new File(System.getProperty("user.dir"));
			f = new File(f.getParentFile().getAbsolutePath() + "/GameEngine/res/lgo/" + file + ".lgo");
			if (f.exists() && !f.isDirectory()) {
				Object[] options = { "yes", "cancel" };
				if (JOptionPane.showOptionDialog(this, "The specified Object File already exists. Want to override?",
						"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
						options[1]) == 0) {
					try {
						PrintWriter writer = new PrintWriter(f, "UTF-8");
						for(int i = 0; i < names.length; i++) {
							writer.println(allParams[i].name() + '=' + fields[i].getText());
						}
						writer.close();
					} catch (IOException e) {
						// do something
					}
					JOptionPane.showMessageDialog(this, "overrode old File.");
				}
			} else {
				try {
					PrintWriter writer = new PrintWriter(f, "UTF-8");
					for(int i = 0; i < names.length; i++) {
						writer.println(allParams[i].name() + '=' + fields[i].getText());
					}
					writer.close();
				} catch (IOException e) {
					// do something
				}
				JOptionPane.showMessageDialog(this, "created new File.");
			}
		}
	}
}