package com.sulav_sagar_QFC;

// Import AWT components
import java.awt.BorderLayout;
import java.awt.GridLayout;
// Import AWT event handling
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
// Import Swing components
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GameFrame extends JFrame {
	// The `serialVersionUID` is a unique identifier used during the deserialization
	// of a serialized object.
	// It ensures that the sender and receiver of the serialized object have loaded
	// classes that are compatible with respect to serialization.
	// If the `serialVersionUID` of the class doesn't match during deserialization,
	// it throws an `InvalidClassException`.
	// By explicitly defining `serialVersionUID`, we avoid potential issues when
	// modifying the class in the future, ensuring compatibility
	// between different versions of the class, especially when saved objects may
	// need to be loaded after future updates.
	private static final long serialVersionUID = 1L;
	private GameController gameController; // GameController to manage players and monsters

	// Constructor to set up the game frame
	public GameFrame() {
		gameController = new GameController(); // Initialize game controller
		setTitle("Quest for the Crown"); // Set title
		setSize(900, 600); // Set size of the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
		setLayout(new BorderLayout()); // Set layout manager

		// Add title panel
		JPanel titlePanel = new JPanel(); // Create a panel
		JLabel titleLabel = new JLabel("Welcome to my Game:\n Quest for the Crown", SwingConstants.CENTER); // Title

		titlePanel.add(titleLabel); // Add title to panel
		add(titlePanel, BorderLayout.NORTH); // Add title panel to the frame

		// Add player info panel
		JPanel playerInfoPanel = new JPanel(); // Create a panel for player info
		playerInfoPanel.setLayout(new GridLayout(5, 2)); // Set grid layout

		// Add form fields
		playerInfoPanel.add(new JLabel("  Enter your name or nickname:"));
		JTextField playerNameField = new JTextField(); // Text field for player name
		playerInfoPanel.add(playerNameField);

		playerInfoPanel.add(new JLabel("  Enter your age: "));
		JTextField playerAgeField = new JTextField(); // Text field for age
		playerInfoPanel.add(playerAgeField);

		playerInfoPanel.add(new JLabel(""));
		// Create Enter button
		JButton enterButton = new JButton("Enter"); // Enter button
		playerInfoPanel.add(enterButton); // Add button to panel
		playerInfoPanel.add(new JLabel(""));
		add(playerInfoPanel, BorderLayout.CENTER); // Add player info p anel to frame

		// Create a panel for the developer info
		JPanel developerInfoPanel = new JPanel(); // Create a separate panel
		developerInfoPanel.setLayout(new GridLayout(2, 1)); // Grid layout to stack labels

		// Create a panel for the developer info and copyright
		JPanel footerPanel = new JPanel(); // Create a new panel for footer
		footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS)); // Set layout to stack components
																				// vertically

		// Add developer labels to the developer info panel
		footerPanel.add(new JLabel(" Developed by Sulav & Sagar "));
		footerPanel.add(new JLabel("\n"));
		footerPanel.add(new JLabel("              Software Developer/Engineer - Sulav"));
		footerPanel.add(new JLabel("              Software Developer/Engineer - Sagar"));
		footerPanel.add(new JLabel("\n"));

		// Create a panel for copyright information
		int currentYear = Calendar.getInstance().get(Calendar.YEAR); // Get current year
		// Copyright message
		JLabel copyrightLabel = new JLabel(" Copyright © " + currentYear + " Sulav & Sagar. All rights reserved.");

		// Create a separate panel for copyright to avoid overlap
		footerPanel.add(copyrightLabel);
		// Add copyright panel to0 the bottom of the frame
		add(footerPanel, BorderLayout.SOUTH);
		footerPanel.add(new JLabel("\n"));

		// Action listener for Enter button
		enterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Proceed to next step on button click
				String playerName = playerNameField.getText(); // Get player name
				String ageText = playerAgeField.getText(); // Get player age

				if (playerName.isEmpty() || ageText.isEmpty()) {
					JOptionPane.showMessageDialog(GameFrame.this, "Please fill in all fields."); // Alert if fields are
																									// empty
				} else {
					try {

						int age = Integer.parseInt(ageText); // Attempt to Parse age
						if (age < 5 || age > 100) { // Check if age is within the valid range
							JOptionPane.showMessageDialog(GameFrame.this, "Age must be between 5 and 100."); // Alert
																												// for
																												// invalid
																												// age
						} else {
							// gameController.addPlayer(playerName, 100, 100, 10, 15, 5); // Add player with
							// default stats
							JOptionPane.showMessageDialog(GameFrame.this,
									"Welcome " + playerName + "!\n Please press OK to move on to Next Step"); // Welcome
																												// message

							// Call method to show player setup
							showPlayerSetup();
						}
					} catch (NumberFormatException ex) { // Catch exception for invalid input
						JOptionPane.showMessageDialog(GameFrame.this, "Please enter a valid number for age."); // Alert
																												// for
																												// invalid
																												// input
					}
				}
			}
		});

		setVisible(true); // Make frame visible
	}

	// Method to display player setup
	private void showPlayerSetup() {
		dispose(); // Close the current window
		PlayerSetupScreen playerSetup = new PlayerSetupScreen(gameController); // Open the player setup screen
		playerSetup.setVisible(true);
	}

	// Method to display monster setup
	private void showMonsterSetup() {
		dispose(); // Close the player setup screen
		MonsterSetupScreen monsterSetup = new MonsterSetupScreen(gameController); // Open the monster setup screen
		monsterSetup.setVisible(true);
	}

//// for permanent run
//	public static void main(String[] args) {
//		new GameFrame();
//	}
//}
// for temporary purpose
	public static void main(String[] args) {
		GameController gameController = new GameController(); // Create GameController instance
		new PlayerSetupScreen(gameController); // Open the Player Setup screen first
	}

}