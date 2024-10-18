package com.sulav_sagar_QFC;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MonsterSetupScreen extends JFrame {
	private static final long serialVersionUID = 1L; // Unique ID for serialization
	private GameController gameController;

	// Constructor to set up monster setup
	public MonsterSetupScreen(GameController gameController) {
		this.gameController = gameController;
		// Create new frame for monster setup
		this.setTitle("Monster Setup");
		this.setSize(600, 400);
		this.setLayout(new GridLayout(4, 2)); // Set grid layout for fields

		// Add monster setup fields
		JLabel monsterTypeLabel = new JLabel("Monster Type:");
		String[] monsterTypes = { "Lightning Beast", "Wood Golem", "Metal Titan", "Sulav the Eternal Shadow",
				"Sagar the Dark Shadow" }; // Monster
		// types
		JComboBox<String> monsterTypeCombo = new JComboBox<>(monsterTypes); // Dropdown for monster selection

		JButton addMonsterButton = new JButton("Add Monster"); // Button to add monster
		JButton finishMonsterSetupButton = new JButton("Finish Setup"); // Button to finish monster setup

		List<String> addedMonsters = new ArrayList<>(); // List to keep track of added monsters

		// Add components to the frame
		this.add(monsterTypeLabel);
		this.add(monsterTypeCombo);
		this.add(addMonsterButton);
		this.add(finishMonsterSetupButton);

		// Action listener to handle monster addition
		addMonsterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String monsterType = (String) monsterTypeCombo.getSelectedItem(); // Get selected monster type
				addedMonsters.add(monsterType); // Add to the list of added monsters
				gameController.addSelectedMonster(monsterType); // Add selected monster to game controller

				// Build the output message
				StringBuilder monsterListMessage = new StringBuilder("Monster Added\n");
				for (String monster : addedMonsters) {
					monsterListMessage.append(monster).append("\n");
				}
				// Show message dialog with the complete list of added monsters
				JOptionPane.showMessageDialog(MonsterSetupScreen.this, monsterListMessage.toString()); // Confirmation

			}
		});
		// Action listener for finishing setup
		finishMonsterSetupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (addedMonsters.isEmpty()) {
					JOptionPane.showMessageDialog(MonsterSetupScreen.this,
							"No monsters added. You must add at least one monster before finishing.", "Warning",
							JOptionPane.WARNING_MESSAGE);

				} else {
					Object[] options = { "Read Rules" }; // Define custom buttons
					JOptionPane.showOptionDialog(MonsterSetupScreen.this,
							"Monster setup finished Successfully!\nNow please press 'Read Rules' before starting the game.",
							"Setup Complete", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
							options, options[0]);

					MonsterSetupScreen.this.dispose(); // Close the monster setup frame
					// Show game rules after finishing the monster setup
					GameRules.displayRules(gameController); // Display the game rules

				}
			}

		});

		this.setVisible(true); // Make monster setup frame visible

	}

	public static void main(String[] args) {
		GameController gameController = new GameController(); // Initialize GameController
		new MonsterSetupScreen(gameController); // Pass the controller to MonsterSetupScreen
	}
}