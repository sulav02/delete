package com.sulav_sagar_QFC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

//Class to manage the game controller, players, and monsters
public class GameController {
	private ArrayList<Player> players; // List of players
	private ArrayList<Monster> monsters; // List of monsters
	private ArrayList<Monster> predefinedMonsters; // Predefined list of monsters
	private ArrayList<GameObject> gameObjects; // List of game objects
	private World world; // Reference to the world of zones
	private Tactician tactician; // Reference to the Tactician
	private ArrayList<Entity> turnOrder; // List to hold turn order of players and monsters
	private int currentRound; // Counter for rounds
	private JTextArea gameArea; // JTextArea to display game actions
	private boolean gameOver = false;
	// Constructor to initialize game controller

	public GameController() {
		gameArea = new JTextArea();
		this.players = new ArrayList<>(); // Initialize players list
		this.monsters = new ArrayList<>(); // Initialize monsters list
		this.predefinedMonsters = new ArrayList<>(); // Initialize predefined monsters list
		this.gameObjects = new ArrayList<>(); // Initialize game objects list
		this.world = Singleton.world; // Initialize a world with a grid size (3x3)
		this.tactician = Singleton.getSingleTactician(players, monsters);

		// Initialize Tactician with players and monsters
		initializePredefinedMonsters(); // Call method to set up predefined monsters
		initializeGameObjects(); // Call method to set up game objects
		this.turnOrder = new ArrayList<>(); // Initialize the turn order list

		this.currentRound = 1; // Initialize round counter
	}

	public void startGame() {
		while (true) {
			startNewRound();
//			executeTurns(); // Execute turns for the current round

			// Check if game is over after turns
			if (checkGameOver()) {
				if (players.stream().allMatch(player -> !player.isAlive())) {
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(null, "Game Over! Better luck next time.\n"); // Player loss
																									// message
					});
				} else if (monsters.stream().allMatch(monster -> !monster.isAlive())) {
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(null, "Congratulations! You've defeated all the monsters!\n"); // Player
																														// win
																														// message
					});
				}
				break; // Exit the loop if game is over
			}
		}
	}

	// Method to initialize game objects
	private void initializeGameObjects() {
		gameObjects.add(new GameObject("Crown", "Crown"));
		gameObjects.add(new GameObject("Yellow Wand", "Wand"));
		gameObjects.add(new GameObject("Green Wand", "Wand"));
		gameObjects.add(new GameObject("Red Wand", "Wand"));
		gameObjects.add(new GameObject("Blue Wand", "Wand"));
	}

	// Method to start a new round
	public void startNewRound() {
		gameArea.append("\n");
		// Display the current round first
		gameArea.append("Round " + currentRound + " Commences:\n");

		// Reset health for all monsters and prepare turn order
		for (Monster monster : monsters) {
			monster.setHealth(monster.getLife()); // Set health equal to life
		}
		turnOrder.clear(); // Clear previous turn order

		// Add players to turn order based on their speed
		for (Player player : players) {
			if (player.isAlive()) {
				for (int i = 0; i < player.getSpeed(); i++) {
					turnOrder.add(player); // Add player turns
				}
			}
		}

		// Add monsters to turn order based on their speed
		for (Monster monster : monsters) {
			for (int i = 0; i < monster.getSpeed(); i++) {
				turnOrder.add(monster); // Add monster turns
			}
		}

		Collections.shuffle(turnOrder); // Randomize the turn order

		// Now increment the round counter for the next round
		currentRound++;

	}

	public String showTurnOrder() {
		int round = currentRound - 1;
		StringBuilder turnOrderString = new StringBuilder(
				"\nThe system randomly chooses the following TURN ORDER for Round " + round + ": \n\n");

		// Iterate through the turnOrder list
		for (Entity entity : turnOrder) {
			// Append each entity's name to the StringBuilder
			turnOrderString.append(entity.getName()).append(" (")
					.append(entity instanceof Player ? "Player" : "Monster").append(")").append("\n");
		}

		String firstTurn = turnOrder.get(0).getName();
		turnOrderString.append(" \nSo, " + firstTurn + " has first turn.");

		return turnOrderString.toString(); // Return the final string representation of turn order
	}

	// Method to execute all turns for the current round
	public void executeTurns() {
		for (Entity entity : turnOrder) { // Iterate through each entity in turn order
			if (entity.isAlive()) { // Check if the entity is alive

				if (!entity.isAlive()) {
					continue; // Skip the turn if the entity is not alive
				}
				if (entity instanceof Player) {
					tactician.manageTurn(entity); // Tactician manages player turns
				} else if (entity instanceof Monster) {
					tactician.manageMonsterTurn((Monster) entity); // Tactician manages monster turns
				} else {
					tactician.manageTurn(entity); // Let Tactician manage the turn for the entity
				}
			}
			// Check game over after each turn (optional)
			if (checkGameOver()) {
				if (players.stream().allMatch(player -> !player.isAlive())) {
					JOptionPane.showMessageDialog(null, "Game Over! Better luck next time.\n");
				} else if (monsters.stream().allMatch(monster -> !monster.isAlive())) {
					JOptionPane.showMessageDialog(null, "Congratulations! You've defeated all the monsters!\n");
				}
				break; // Exit if the game is over
			}
		}
	}

	// Method to check if all monsters or players are dead
	public boolean checkGameOver() {
		boolean allMonstersDead = monsters.stream().allMatch(monster -> !monster.isAlive()); // Check if all monsters
																								// are dead
		boolean allPlayersDead = players.stream().allMatch(player -> !player.isAlive()); // Check if all players are
																							// dead
		gameOver = allMonstersDead || allPlayersDead; // Return true if either condition is met
		return gameOver;
	}

	// Method to add player
	public void addPlayer(String name, int life, int health, int speed, int power, int defense) {
		Player player = new Player(name, life, health, speed, power, defense); // Create player instance
		players.add(player); // Add new player to list
		placePlayerInRandomZone(player); // Place player in a random zone
//		tactician.announceTurn(players.size() - 1); // Announce the new player's turn
	}

	// Method to place players in random zones when added
	private void placePlayerInRandomZone(Player player) {
		Zone randomZone = world.getRandomZone(); // Get a random zone
		player.setCurrentZone(randomZone); // Set current zone for the player
		randomZone.addPlayer(player); // Add player to that zone
		// player assignment info
		gameArea.append(player.getName() + " assigned to zone: " + player.getCurrentZone().getName() + "\n");

	}

	// Method to initialize predefined monsters
	private void initializePredefinedMonsters() {
		predefinedMonsters.add(new Monster("Lightning Beast", 20, 20, 2, 2, 1)); // Example monster
		predefinedMonsters.add(new Monster("Wood Golem", 20, 20, 1, 2, 1)); // Example monster
		predefinedMonsters.add(new Monster("Metal Titan", 15, 15, 1, 3, 1)); // Example monster
		predefinedMonsters.add(new Monster("Sulav the Eternal Shadow", 30, 30, 2, 7, 2)); // Example monster
		predefinedMonsters.add(new Monster("Sagar the Dark Shadow", 25, 25, 2, 6, 1)); // Example monster
	}

	// Method to add a selected monster from the predefined list
	public void addSelectedMonster(String type) {
		for (Monster predefinedMonster : predefinedMonsters) {

			if (predefinedMonster.getType().equals(type)) {

				// Create a new monster instance based on the predefined one
				Monster newMonster = new Monster(predefinedMonster.getType(), predefinedMonster.getLife(),
						predefinedMonster.getHealth(), predefinedMonster.getPower(), predefinedMonster.getDefense(),
						predefinedMonster.getSpeed());
				monsters.add(newMonster); // Add the new monster to the local monsters list

				// Assign the monster to a random zone
				Zone randomZone = world.getRandomZone(); // Get a random zone
				newMonster.setCurrentZone(randomZone); // Set the current zone for the monster
				randomZone.addMonster(newMonster); // Add monster to that zone

				// Optionally log the monster assignment
				gameArea.append(
						newMonster.getName() + " assigned to zone: " + newMonster.getCurrentZone().getName() + "\n");

				break;
			}
		}

	}

	// Method to get the status of all zones
	public String getZonesStatus() {
		return world.getZonesStatus();

	}

// Getters for players and monsters

	public List<Player> getPlayers() {
		return players; // Return list of players
	}

	public List<Monster> getPredefinedMonsters() {
		return predefinedMonsters; // Return list of monsters
	}

	public List<Monster> getMonsters() {
		return monsters; // Return the list of active added monsters
	}

	public List<GameObject> getGameObjects() {
		return gameObjects; // Return the list of game objects
	}

	public int getCurrentRound() {
		return currentRound; // Return the current round number
	}

	public Tactician getTactician() {
		return tactician;
	}

}