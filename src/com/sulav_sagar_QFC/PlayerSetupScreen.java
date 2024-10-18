package com.sulav_sagar_QFC;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PlayerSetupScreen extends JFrame {
	private static final long serialVersionUID = 1L;
	private GameController gameController; // GameController to manage players
	private List<String> playerNames = new ArrayList<>();
	private JTextArea outputTextArea; // Declare JTextArea here

	public PlayerSetupScreen(GameController gameController) {
		this.gameController = gameController; // Initialize game controller
		outputTextArea = new JTextArea(); // Initialize the JTextArea

		setTitle("Player Setup"); // Set title
		setSize(600, 400); // Set size of the frame
		setLayout(new GridLayout(7, 2)); // Set grid layout for fields

		// Add player setup fields
		JLabel nameLabel = new JLabel("  Player Name:");
		JTextField playerNameField = new JTextField(); // Text field for player name
		JLabel lifeLabel = new JLabel("  Life:");
		JTextField lifeField = new JTextField(); // Text field for life
		JLabel healthLabel = new JLabel("  Health :");
		JTextField healthField = new JTextField();
		// JTextField healthField = new JTextField("10"); // Auto-filled health
		// healthField.setEditable(false); // Make health field read-only
		JLabel speedLabel = new JLabel("  Speed:");
		JTextField speedField = new JTextField(); // Text field for speed
		JLabel powerLabel = new JLabel("  Power:");
		JTextField powerField = new JTextField(); // Text field for power
		JLabel defenseLabel = new JLabel("  Defense:");
		JTextField defenseField = new JTextField(); // Text field for defense

		JButton addPlayerButton = new JButton("Add Player"); // Button to add player
		JButton startMonsterSetupButton = new JButton("Start Monster Setup"); // Button for monster setup

		// Add components to the frame
		add(nameLabel);
		add(playerNameField);
		add(lifeLabel);
		add(lifeField);
		add(healthLabel);
		add(healthField);
		add(speedLabel);
		add(speedField);
		add(powerLabel);
		add(powerField);
		add(defenseLabel);
		add(defenseField);
		add(addPlayerButton);
		add(startMonsterSetupButton);

		addPlayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String playerName = playerNameField.getText(); // Get player name
					int life = Integer.parseInt(lifeField.getText()); // Get life
					int health = Integer.parseInt(healthField.getText()); // Get health
					int speed = Integer.parseInt(speedField.getText()); // Get speed
					int power = Integer.parseInt(powerField.getText()); // Get power
					int defense = Integer.parseInt(defenseField.getText()); // Get defense

					// Input validation
					if (life <= 0 || speed <= 0 || power <= 0 || defense <= 0) {
						JOptionPane.showMessageDialog(PlayerSetupScreen.this, "All fields must be greater than 0.");
						return; // Exit the method if validation fails
					}
					// Ensure health is not greater than life
					if (health != life) {
						JOptionPane.showMessageDialog(PlayerSetupScreen.this,
								"Life and Health must be equal and greater than 0.");
						return; // Exit the method if validation fails
					}
					// Add player to game controller
					gameController.addPlayer(playerName, life, life, speed, power, defense); // Health is auto-filled
																								// from
																								// life
					playerNames.add(playerName); // Add player name to the list
					StringBuilder message = new StringBuilder("Player Added:\n");
					for (String name : playerNames) {
						message.append(name).append("\n"); // Append each player name
					}
					JOptionPane.showMessageDialog(PlayerSetupScreen.this, message.toString()); // Show all added players
					// Clear fields after adding player
					playerNameField.setText("");
					lifeField.setText("");
					healthField.setText("");
					speedField.setText("");
					powerField.setText("");
					defenseField.setText("");
					// message

				} catch (NumberFormatException ex) { // Catch exception for invalid input
					JOptionPane.showMessageDialog(PlayerSetupScreen.this,
							"Please enter valid input and do not leave anything"); // Alert
					// for
					// invalid
					// input
				}
			}

		});
		// Start monster setup action
		startMonsterSetupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerNames.isEmpty()) {
					JOptionPane.showMessageDialog(PlayerSetupScreen.this,
							"Add at least one player before proceeding to Monster Setup.");
				} else {
					// Proceed to Monster Setup
					new MonsterSetupScreen(gameController);
					dispose(); // Close Player Setup screen
				}
			}
		});

		setVisible(true); // Make player setup frame visible
	}
}
