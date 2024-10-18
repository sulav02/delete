package com.sulav_sagar_QFC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameScreen extends JFrame {
	private GameController gameController; // Reference to the GameController for game management
	private JTextArea gameArea; // JTextArea to display game actions
	private Monster targetMonster; // Declare targetMonster

	// ComboBox for monster selection
	private JComboBox<Monster> monsterComboBox;

	public GameScreen(GameController gameController) {
		this.gameController = gameController; // Initialize the GameController

		// Set up the game window
		setTitle("Quest for the Crown: Game Screen");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
		setLayout(new BorderLayout()); // Set layout manager

		// Create a main game area
		this.gameArea = new JTextArea();
		gameArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(gameArea);
		add(scrollPane, BorderLayout.CENTER);

		// ComboBox for selecting a monster to attack
		monsterComboBox = new JComboBox<>(gameController.getMonsters().toArray(new Monster[0]));
		monsterComboBox.addActionListener(e -> {
			targetMonster = (Monster) monsterComboBox.getSelectedItem(); // Set the target monster
		});

		// Initialize the game display
		initializeGame();

		// Make the window visible
		setVisible(true);
	}

	private void showCurrentZoneStatus(Player player) {
		Zone currentZone = player.getCurrentZone();
		if (currentZone != null) {
			StringBuilder status = new StringBuilder("Your current zone status:\n");
			status.append("Zone Address: ").append(currentZone.getName()).append("\n");

			List<Player> playersInZone = currentZone.getPlayers();
			if (playersInZone.isEmpty()) {
				status.append("Players Found: None\n");
			} else {
				status.append("Players Found: ");
				for (Player p : playersInZone) {
					status.append(p.getName()).append(", ");
				}
				status.setLength(status.length() - 2); // Remove last comma
				status.append("\n");
			}

			List<Monster> monstersInZone = currentZone.getMonsters();
			if (monstersInZone.isEmpty()) {
				status.append("Monsters Found: None\n");
			} else {
				status.append("Monsters Found: ");
				for (Monster m : monstersInZone) {
					status.append(m.getName()).append(", ");
				}
				status.setLength(status.length() - 2); // Remove last comma
				status.append("\n");
			}

			List<GameObject> objectsInZone = currentZone.getObjects();
			if (objectsInZone.isEmpty()) {
				status.append("Objects Found: None\n");
			} else {
				status.append("Objects Found: ");
				for (GameObject obj : objectsInZone) {
					status.append(obj.getName()).append(", ");
				}
				status.setLength(status.length() - 2); // Remove last comma
				status.append("\n");
			}

			// popup, the status
			JOptionPane.showMessageDialog(this, status.toString());
		} else {
			JOptionPane.showMessageDialog(this, "You have no current zone to act in.");
		}
	}

	private void playerAction(Player player) {
		// Get the current zone status
		StringBuilder status = new StringBuilder();
		Zone currentZone = player.getCurrentZone();

		if (currentZone != null) {
			status.append("Hi " + player.getName() + ",\nYour current zone status:\n");
			status.append("Zone Address: ").append(currentZone.getName()).append("\n");

			List<Player> playersInZone = currentZone.getPlayers();
			status.append("Players Found: ")
					.append(playersInZone.isEmpty() ? "None"
							: String.join(", ", playersInZone.stream().map(Player::getName).toArray(String[]::new)))
					.append("\n");

			List<Monster> monstersInZone = currentZone.getMonsters();
			status.append("Monsters Found: ")
					.append(monstersInZone.isEmpty() ? "None"
							: String.join(", ", monstersInZone.stream().map(Monster::getName).toArray(String[]::new)))
					.append("\n");

			List<GameObject> objectsInZone = currentZone.getObjects();
			status.append("Objects Found: ")
					.append(objectsInZone.isEmpty() ? "None"
							: String.join(", ", objectsInZone.stream().map(GameObject::getName).toArray(String[]::new)))
					.append("\n");

			List<Weapon> weaponInZone = currentZone.getWeapon();
			status.append("Weapons Found: ")
					.append(weaponInZone.isEmpty() ? "None"
							: String.join(", ", weaponInZone.stream().map(Weapon::getName).toArray(String[]::new)))
					.append("\n");

		} else {
			status.append("You have no current zone to act in.\n");
		}
		// Prepare the action prompt
		String actionPrompt = status.toString() + "\n" + "Player: " + player.getName() + "\n"
				+ "Choose an action between (1-4):\n" + "1. Move\n" + "2. Drop an object\n"
				+ "3. Collect object or weapon\n" + "4. Use an object";

		String action = JOptionPane.showInputDialog(this, actionPrompt);
		switch (action) {
		case "1":
			handleMove(player);
			break;
		case "2":
			handleDrop(player);
			break;
		case "3":
			handleCollect(player);
			break;
		case "4":
			handleUse(player);
			break;
		default:
			JOptionPane.showMessageDialog(this, "Invalid action. Please choose again.");
			playerAction(player); // Recursively call until valid action
			break;
		}
	}

	private void handleMove(Player player) {
		Zone currentZone = player.getCurrentZone();

		// Check if currentZone is null
		if (currentZone == null) {
			JOptionPane.showMessageDialog(this, player.getName() + " has no current zone to move from.\n");
			return; // Exit the method to avoid NullPointerException
		}
		// Display possible directions

		String direction = showDirectionDialog();
		// Convert the direction to lowercase
		if (direction != null) {
			direction = direction.toLowerCase();
		} else {
			JOptionPane.showMessageDialog(this, "Invalid move. Please try again.\n");
			handleMove(player); // Retry if input is null
			return;
		}

		Zone nextZone = currentZone.getNeighbor(direction); // Implement this method to get direction input

		if (nextZone != null) {
			currentZone.removePlayer(player);
			nextZone.addPlayer(player);
			player.setCurrentZone(nextZone);
			JOptionPane.showMessageDialog(this, player.getName() + " moves to " + nextZone.getName() + "\n");
		} else {
			JOptionPane.showMessageDialog(this, "No Zone Found. Please move to another direction.\n");
			handleMove(player); // Retry if invalid move
		}
		playerAction(player);
	}

	private String showDirectionDialog() {
		// Variable to hold the selected direction
		final String[] direction = { null };

		// Create a panel to hold buttons
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		// Create buttons for each direction
		JButton northButton = new JButton("North");
		JButton southButton = new JButton("South");
		JButton eastButton = new JButton("East");
		JButton westButton = new JButton("West");

		// Action listener to set direction and close the dialog
		ActionListener listener = e -> {
			JButton source = (JButton) e.getSource();
			direction[0] = source.getText(); // Get the text of the clicked button
			JOptionPane.getRootFrame().dispose(); // Close dialog
		};

		// Add action listeners to buttons
		northButton.addActionListener(listener);
		southButton.addActionListener(listener);
		eastButton.addActionListener(listener);
		westButton.addActionListener(listener);

		// Add buttons to the panel
		panel.add(northButton);
		panel.add(southButton);
		panel.add(eastButton);
		panel.add(westButton);

		// Show the dialog with the panel
		JOptionPane.showMessageDialog(null, panel, "Choose a direction to move", JOptionPane.PLAIN_MESSAGE);

		return direction[0].toLowerCase(); // Return the selected direction
	}

	private void handleAttack(Player player) {
		List<Monster> monsters = player.getCurrentZone().getMonsters();

		if (monsters.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No monsters to attack. Please choose another action.",
					"Invalid Action", JOptionPane.WARNING_MESSAGE);
			playerAction(player); // Prompt for the next action
			return;
		}
		// Prepare the attack options
		StringBuilder sb = new StringBuilder("Choose a monster to attack:\n");
		for (int i = 0; i < monsters.size(); i++) {
			sb.append((i + 1)).append(". ").append(monsters.get(i).getName()).append("\n");
		}

		int targetIndex = Integer.parseInt(JOptionPane.showInputDialog(sb.toString())) - 1;
		if (targetIndex >= 0 && targetIndex < monsters.size()) {
			Monster target = monsters.get(targetIndex);

			if (target.isAlive() && player.getWeapons().getFirst() != null) {
				player.attack(target); // Update this line as per your Player's attack method

				JOptionPane.showMessageDialog(this, (player.getName() + " attacks " + target.getName()
						+ "\nRemaining Health:" + target.getHealth() + "\n"));
				System.out.println("rem:" + target.getHealth());

				// Check if the monster is defeated
				if (!target.isAlive()) {
					JOptionPane.showMessageDialog(this,
							" Congratulations! You've defeated " + target.getName() + "!\n");
					// Remove the monster from the zone
					player.getCurrentZone().removeMonster(target);
				}

				playerAction(player);
			} else {
				JOptionPane.showMessageDialog(this, target.getName() + " is already defeated. Choose another action.",
						"Invalid Action", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			gameArea.append("Invalid target. Please choose again.\n");
			handleAttack(player);
		}
	}

	private void handleDrop(Player player) {
		List<GameObject> inventory = player.getInventory();
		if (inventory.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No objects in inventory to drop.\n");
			playerAction(player); // Return to action prompt
			return;
		}

		StringBuilder sb = new StringBuilder("Choose an object to drop:\n");
		for (int i = 0; i < inventory.size(); i++) {
			sb.append((i + 1)).append(". ").append(inventory.get(i).getName()).append("\n");
		}

		int objectIndex = Integer.parseInt(JOptionPane.showInputDialog(sb.toString())) - 1;
		if (objectIndex >= 0 && objectIndex < inventory.size()) {
			GameObject objectToDrop = inventory.get(objectIndex);
			Zone currentZone = player.getCurrentZone(); // Get the player's current zone using the getter
			player.dropObject(objectToDrop, currentZone); // Ensure this method is implemented in the Player class
			JOptionPane.showMessageDialog(this, player.getName() + " drops " + objectToDrop.getName() + "\n");
		} else {
			JOptionPane.showMessageDialog(this, "Invalid object. Please choose again.\n");
		}

		playerAction(player); // Show the action prompt again
	}

	private void handleCollect(Player player) {
		Zone currentZone = player.getCurrentZone();
		List<GameObject> objects = currentZone.getObjects();
		List<Weapon> weapons = currentZone.getWeapon(); // Get the list of weapons

		if (objects.isEmpty() && weapons.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No objects or weapons to collect in this zone.\n");
			// After handling empty objects, return to the action prompt
			playerAction(player); // Call to show the next action prompt
			return;
		}

		StringBuilder sb = new StringBuilder("Choose an object or weapon to collect:\n");

		// List objects
		for (int i = 0; i < objects.size(); i++) {
			sb.append((i + 1)).append(". ").append(objects.get(i).getName()).append("\n");
		}

		// List weapons
		for (int i = 0; i < weapons.size(); i++) {
			sb.append((i + 1 + objects.size())).append(". ").append(weapons.get(i).getName()).append(" (Weapon)\n");
		}

		String input = JOptionPane.showInputDialog(sb.toString());

		if (input != null && !input.isEmpty()) {

			int objectIndex = Integer.parseInt(input) - 1;

			if (objectIndex >= 0 && objectIndex < objects.size()) {
				GameObject objectToCollect = objects.get(objectIndex);
				player.collectObject(objectToCollect);
				currentZone.removeObject(objectToCollect);
				JOptionPane.showMessageDialog(this, player.getName() + " collects " + objectToCollect.getName());
			} else if (objectIndex >= objects.size() && objectIndex < objects.size() + weapons.size()) {
				int weaponIndex = objectIndex - objects.size();
				Weapon weaponToCollect = weapons.get(weaponIndex);
				player.collectWeapon(weaponToCollect); // Use the new method to collect the weapon
				currentZone.collectWeapon(weaponToCollect.getName()); // Remove the weapon from the zone
				JOptionPane.showMessageDialog(this, player.getName() + " collects " + weaponToCollect.getName());

			} else {
				JOptionPane.showMessageDialog(this, "Invalid object. Please choose again.\n");
			}
		} else

		{
			JOptionPane.showMessageDialog(this, "Invalid input. Please choose again.\n");
		}

		// After collecting, show the action prompt again
		playerAction(player); // Call to show the next action prompt
	}

	private void handleUse(Player player) {
		List<GameObject> inventory = player.getInventory();
		List<Weapon> weaponInventory = player.getWeapons();

		if (inventory.isEmpty() && weaponInventory.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No objects or weapons in inventory to use.\n");
			playerAction(player); // Prompt for the next action
			return;
		}

		StringBuilder sb = new StringBuilder("Choose an object to use:\n");

		// Display GameObjects
		for (int i = 0; i < inventory.size(); i++) {
			sb.append((i + 1)).append(". ").append(inventory.get(i).getName()).append("\n");
		}
		// Display Weapons
		for (int i = 0; i < weaponInventory.size(); i++) {
			sb.append((i + 1 + inventory.size())).append(". ").append(weaponInventory.get(i).getName())
					.append(" (Weapon)\n");
		}

		int objectIndex = Integer.parseInt(JOptionPane.showInputDialog(sb.toString())) - 1;
		if (objectIndex >= 0 && objectIndex < inventory.size()) {
			GameObject objectToUse = inventory.get(objectIndex);

			String targetName = JOptionPane
					.showInputDialog("Who do you want to use it on? (Type player name or monster name)");

			Entity target = findTargetEntity(targetName); // Ensure this method is implemented
			if (target != null) {
				player.useObject(objectToUse, target, gameController.getTactician()); // Ensure Player class has this
																						// method
				JOptionPane.showMessageDialog(this,
						player.getName() + " uses " + objectToUse.getName() + " on " + target.getName() + "\n");
			} else {
				JOptionPane.showMessageDialog(this, "Target not found. Please choose again.\n");
				handleUse(player);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Invalid object. Please choose again.\n");
			handleUse(player);
		}
		playerAction(player);
	}

	private Entity findTargetEntity(String targetName) {
		// Implement logic to find a target by name from players and monsters
		for (Player player : gameController.getPlayers()) {
			if (player.getName().equalsIgnoreCase(targetName)) {
				return player;
			}
		}
		for (Monster monster : gameController.getMonsters()) {
			if (monster.getName().equalsIgnoreCase(targetName)) {
				return monster;
			}
		}
		return null; // Return null if no target is found
	}

	private void monsterAction(Monster monster) {
		List<Player> players = monster.getCurrentZone().getPlayers();
		if (!players.isEmpty()) {
			Player target = players.get(0); // For simplicity, just attack the first player
			int damage = monster.getPower() - target.getDefense();
			damage = Math.max(damage, 1); // Ensure minimum damage is 1
			target.takeDamage(damage);
			JOptionPane.showMessageDialog(this,
					monster.getName() + " attacks " + target.getName() + " for " + damage + " damage.\n");
		} else {
			JOptionPane.showMessageDialog(this, monster.getName() + " has no players to attack.\n");
		}
	}

	private void initializeGame() {
		// Display initial game state or instructions in the game area
		gameArea.setText(" Welcome to Quest for the Crown!\n");
		gameArea.append(" Game is starting..." + "\n");

		Timer timer = new Timer();
		TimerTask countdownTask = new TimerTask() {
			int countdown = 3; // Starting from 3

			@Override
			public void run() {
				// Create a message string to display in the popup
				String message = "Welcome to Quest for the Crown!\nGame is starting...\n";

				if (countdown > 0) {
					message += countdown; // Append the current countdown number
//					gameArea.append(countdown + "  \n");
//					countdown--;
					// Show countdown in a popup
					// JOptionPane.showMessageDialog(GameScreen.this, countdown);
					JOptionPane.showMessageDialog(GameScreen.this, message);

					countdown--;

				} else {
					timer.cancel(); // Stop the timer when countdown reaches 0

					// Show the final countdown message
					JOptionPane.showMessageDialog(GameScreen.this, message);

					// Show initial game state
					gameArea.setText("");
					String randomStatus = Singleton.world.getZonesStatus();
					gameArea.append(randomStatus + "\n");

					// Start the new round and display the round message before the turn order
					gameController.startNewRound();
					// gameArea.append("Round " + gameController.getCurrentRound() + "
					// Commences:\n");

//					gameArea.append(gameController.showTurnOrder()); // Display the turn order

					// Show turn order after the round starts
					String turnOrderMessage = gameController.showTurnOrder();
					JOptionPane.showMessageDialog(GameScreen.this, turnOrderMessage); // Show turn order in a popup
					gameController.executeTurns(); // Start executing turns

					// Start prompting for player actions
					for (Player player : gameController.getPlayers()) {
						playerAction(player); // Prompt each player for action
					}
				}
			}
		};

		// Schedule the task to run every 1 second (1000 milliseconds)
		timer.scheduleAtFixedRate(countdownTask, 0, 1000); // No initial delay, 1-second intervals

	}

	public void updateGameOutput(String message) {
		// Method to append messages to the game area
		gameArea.append(message + "\n");
		gameArea.setCaretPosition(gameArea.getDocument().getLength()); // Scroll to the bottom
	}
}