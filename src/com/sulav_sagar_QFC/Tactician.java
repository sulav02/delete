package com.sulav_sagar_QFC;

import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

// Class to represent the Tactician in the game
public class Tactician {
	private List<Player> players; // List of players in the game
	private List<Monster> monsters; // List of monsters in the game
	private JTextArea textArea;

	// Constructor to initialize the Tactician
	public Tactician(List<Player> players, List<Monster> monsters) {
		this.players = players; // Set the list of players
		this.monsters = monsters; // Set the list of monsters
		this.textArea = textArea; // Set the JTextArea
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	// Method to announce whose turn it is
	public String makeAnnouncement(String announcement) {
		return announcement;
	}

	// Method to describe monster behavior
	public String describeMonsterBehavior(Monster monster) {
		return monster.getType() + " attacks!\n"; // Return monster's action description
	}

	// Method to interpret a player's action
	public String interpretPlayerAction(Player player, String action) {
		return player.getName() + " performs action: " + action + "\n"; // Return player action description
	}

	// Method to decide if a monster attacks
	public String decideMonsterActions() {
		StringBuilder actions = new StringBuilder();
		for (Monster monster : monsters) {
			actions.append(monster.getType()).append(" decides to attack a player.\n");
		}
		return actions.toString(); // Return the concatenated monster actions
	}

	// Method to manage the monster's turn
	public void manageMonsterTurn(Monster monster) {
		Zone currentZone = monster.getCurrentZone(); // Get the monster's current zone
		List<Player> playersInZone = currentZone.getPlayers(); // Get players in that zone

		String actionMessage; // Variable to hold action message

		if (!playersInZone.isEmpty()) {
			// If there are players in the zone, the monster can attack
			Player target = findNearestPlayer(monster); // Logic to choose a player to attack
			if (target != null) {
				String attackResult = target.takeDamage(monster.getPower()); // Assuming Player has a method to take //
																				// dama
				actionMessage = (monster.getType() + " attacks " + target.getName() + ":\n" + attackResult);
			} else {
				actionMessage = monster.getType() + " couldn't find a target to attack.";
			}
		} else {
			// If no players are in the zone, the monster could move or take another action
			// For simplicity, we can make the monster move to a random neighboring zone
			String direction = getRandomDirection(); // Implement logic to get a random direction
			moveEntity(monster, direction); // Move the monster
			actionMessage = monster.getType() + " moves to another zone.";
		}
		// Show the action result in a dialog box
		JOptionPane.showMessageDialog(null, actionMessage, "Monster Action", JOptionPane.INFORMATION_MESSAGE);

		// log the action to the game area
		makeAnnouncement(actionMessage);
	}

	// Helper method to get a random direction
	private String getRandomDirection() {
		String[] directions = { "north", "south", "east", "west" };
		Random rand = new Random();
		return directions[rand.nextInt(directions.length)];
	}

	// Method to find the nearest player (implement based on your game logic)
	private Player findNearestPlayer(Monster monster) {
		Player nearestPlayer = null;
		double shortestDistance = Double.MAX_VALUE; // Start with a large number

		for (Player player : players) {
			// Get the zones of the monster and the player
			Zone monsterZone = monster.getCurrentZone();
			Zone playerZone = player.getCurrentZone();

			// Calculate the distance (for example, using Manhattan distance)
			double distance = Math.abs(monsterZone.getX() - playerZone.getX())
					+ Math.abs(monsterZone.getY() - playerZone.getY());

			// Check if this player is closer than the previously found nearest player
			if (distance < shortestDistance) {
				shortestDistance = distance;
				nearestPlayer = player;
			}
		}

		return nearestPlayer; // Return the nearest player, or null if no players are present
	}

	// Method to manage the turn of an entity
	public void manageTurn(Entity entity) {
		if (entity instanceof Monster) {
			Monster monster = (Monster) entity; // Cast to Monster
			String action = describeMonsterBehavior(monster); // Get monster action description
			makeAnnouncement(action); // Show action in the text area
			showMonsterActionDialog(monster.getName(), action); // Show action in a pop-up
		} else if (entity instanceof Player) {
			Player player = (Player) entity; // Cast to Player

			// Check if the player is alive
			if (!player.isAlive()) {
				makeAnnouncement(player.getName() + " is defeated and cannot take a turn.\n");
				return; // Skip the turn if the player is not alive
			}

			makeAnnouncement(player.getName() + "'s turn.\n");

			// playerAction(player); // Call your existing playerAction method // Method to
			// handle player's actions
		} else {
			makeAnnouncement("Unknown entity turn.\n");
		}
	}

	// Method to show monster action in a pop-up with a "Next" button
	private void showMonsterActionDialog(String monsterName, String action) {
		// Create a message with the monster name and action
		String message = monsterName + " " + action;

		// Show a dialog with "Next" button
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = optionPane.createDialog("Monster Action");

		// Add a button to close the dialog
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(e -> dialog.dispose()); // Close dialog on button click

		optionPane.setOptions(new Object[] { nextButton }); // Set the button in the dialog
		dialog.setVisible(true); // Show the dialog
	}

	// Method to move an entity to a neighboring zone
	public String moveEntity(Entity entity, String direction) {
		Zone currentZone = entity.getCurrentZone(); // Get the current zone of the entity
		Zone nextZone = currentZone.getNeighbor(direction); // Get the neighboring zone based on the direction

		if (nextZone != null) {
			// Move the entity to the new zone
			entity.setCurrentZone(nextZone);
			// Update the zone's player/monster lists
			if (entity instanceof Player) {
				nextZone.addPlayer((Player) entity); // Add to the new zone
				currentZone.removePlayer((Player) entity); // Remove from the current zone
			} else if (entity instanceof Monster) {
				nextZone.addMonster((Monster) entity); // Add to the new zone
				currentZone.removeMonster((Monster) entity); // Remove from the current zone
			}
			return entity.getName() + " moved to " + nextZone.getName() + "\n";
		} else {
			return "Invalid move! There is no zone to the " + direction + "\n";
		}
	}

	// Method to handle the collection of an object
	public String handleCollectObject(Player player, String objectName, Zone currentZone) {
		GameObject collectedObject = currentZone.collectObject(objectName); // Attempt to collect the object
		if (collectedObject != null) {
			player.collectObject(collectedObject); // Add the object to the player's inventory
			return player.getName() + " collected " + objectName + ".\n";
		} else {
			return "No object named " + objectName + " in this zone.\n";
		}
	}

	// Method to display game end message
	public String endGame(Player winner) {
		return winner.getName() + " has won the game!\n"; // Return the game end announcement
	}

	// Method to stun an entity
	// Logic to stun the entity for one round
	public void stunEntity(Entity target) {
		if (target != null) {
			target.stun(1); // Stun for 1 round
			String message = target.getName() + " has been stunned for 1 round!";
			JOptionPane.showMessageDialog(null, message, "Stun Effect", JOptionPane.INFORMATION_MESSAGE);
			makeAnnouncement(message); // Log the stun in the game area
		}
	}
}