package com.sulav_sagar_QFC;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class GameRules {
	public static String getRules() {

		// Return the game rules as a string
		return "Quest for the Crown: Game Rules\n" + "\nGame Overview\n"
				+ "Quest for the Crown is a role-playing game designed for groups of friends to play together in a mythical world.\n"
				+ "Players explore various zones to find the Crown and overcome challenges posed by monsters.\n"
				+ "\nObjective\n"
				+ "The goal of the game is to find and place the Crown on one of the player’s heads, signifying victory. Players can achieve this by eliminating monsters and collecting useful items along their journey.\n"
				+ "\nGame Setup\n" + "Players and Tactician:\n"
				+ "- Players take on roles in the game while one person is designated as the Tactician.\n"
				+ "- The Tactician manages the game, describing monster behaviors, interpreting player actions, and announcing turn orders.\n"
				+ "Zones:\n" + "- The game world consists of a grid of zones (4-9 zones recommended).\n"
				+ "- Each zone can contain multiple players, monsters, and collectible objects.\n"
				+ "Player and Monster Properties:\n" + "- Each player and monster has the following attributes:\n"
				+ "   - Life: Maximum health points.\n" + "   - Health: Current health points; cannot exceed Life.\n"
				+ "   - Speed: Determines the number of turns each entity gets per round.\n"
				+ "   - Power: Damage dealt when attacking.\n" + "   - Defense: Mitigates damage taken from attacks.\n"
				+ "\nGame Flow\n" + "Start of the Game:\n" + "- Each player and monster starts in a random zone.\n"
				+ "- All monsters begin with Health equal to their Life.\n" + "Turn Order:\n"
				+ "- At the beginning of each round, each player and monster receives turns based on their Speed.\n"
				+ "- Turn order is randomized.\n" + "Current Zone Information:\n"
				+ "- The Tactician provides details about the current zone whenever a player or monster takes their turn, including entities present and any objects available.\n"
				+ "End of Game:\n" + "- The game concludes when either all players or all monsters are dead.\n"
				+ "- If a player successfully places the Crown on another player's head, the players win.\n"
				+ "\nPlayer Actions\n" + "During their turn, players can choose from the following actions:\n"
				+ "- Move: Players can move to an adjacent zone (west, east, north, or south) if there is a connecting zone.\n"
				+ "- Collect an Object: Players can pick up objects found in their current zone to carry with them.\n"
				+ "- Drop an Object: Players can drop an object they carry in their current zone for others to collect.\n"
				+ "- Use an Object: Players can use an object from their inventory, targeting another player or a monster.\n"
				+ "\nMonster Actions\n" + "Monsters can either:\n"
				+ "- Move: Move to an adjacent zone (as with players).\n"
				+ "- Attack a Player: Damage a player based on the monster's Power and the player's Defense.\n"
				+ "\nCombat Mechanics\n" + "Damage Calculation:\n"
				+ "- When a player is attacked, the damage dealt is calculated as follows:\n"
				+ "  Damage = Monster Power - Player Defense (minimum 1 damage).\n"
				+ "- Players always take at least 1 point of damage if attacked.\n" + "Types of Weapons:\n"
				+ "- Players can collect weapons, which are categorized as Lightning, Wood, or Metal, with specific damage effects:\n"
				+ "   - Lightning Weapons: Double damage to Metal monsters, half damage to Wood monsters.\n"
				+ "   - Wood Weapons: Double damage to Lightning monsters, half damage to Metal monsters.\n"
				+ "   - Metal Weapons: Double damage to Wood monsters, half damage to Lightning monsters.\n"
				+ "Wands:\n" + "- Players can use magical wands with specific effects:\n"
				+ "   - Yellow Wand: Stuns the target for the next round.\n"
				+ "   - Green Wand: 50% chance to add the Player’s Power to another Player’s Health (maximum to Life).\n"
				+ "   - Red Wand: Halves the target's health.\n"
				+ "   - Blue Wand: Revives a dead player with 50% of their Life; they cannot participate in the current round.\n"
				+ "\nHealth Management\n" + "Life and Health:\n"
				+ "- Players and monsters start with full Health equal to their Life.\n"
				+ "- If a player's Health reaches 0, they are dead and cannot take further actions (unless revived).\n"
				+ "Healing:\n"
				+ "- Players cannot exceed their Life in Health, but can be healed by the Green Wand or through other gameplay mechanisms (if applicable).\n"
				+ "Player Death:\n" + "- Once dead, a player is removed from the game until revived by a Blue Wand.\n"
				+ "- Monsters are similarly removed when their Health drops to 0.\n" + "\nWinning and Losing\n"
				+ "Winning the Game:\n" + "- Players win by placing the Crown on another player's head.\n"
				+ "Losing the Game:\n"
				+ "- Players lose if all player characters are eliminated before achieving the objective.\n"
				+ "\nAdditional Rules\n"
				+ "- Objects can be collected from any zone, but players must be present in that zone to do so.\n"
				+ "- Players can only use one weapon at a time but can switch weapons by collecting new ones.\n"
				+ "- Players and monsters cannot interact with entities in a previously occupied zone once they move.";
	}

	// Method to display the rules in a dialog
	public static void displayRules(GameController gameController) {
		SwingUtilities.invokeLater(() -> {
			// Creating a text area with rules
			JTextArea rulesArea = new JTextArea(getRules());
			rulesArea.setEditable(false); // Making it non-editable
			rulesArea.setLineWrap(true); // Wrap text lines
			rulesArea.setWrapStyleWord(true); // Wrap at word boundaries

			// Creating a scroll pane for the text area
			JScrollPane scrollPane = new JScrollPane(rulesArea);
			scrollPane.setPreferredSize(new java.awt.Dimension(500, 400)); // Set preferred size for better
																			// visibility

			// Define custom button
			Object[] options = { "Play Game" };

			// Displaying rules in a dialog
			int result = JOptionPane.showOptionDialog(null, scrollPane, "Game Rules :: Quest for the Crown",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (result == 0) { // If "Play Game" is selected

				// Create and display the game screen
				GameScreen gameScreen = new GameScreen(gameController);
				gameScreen.setVisible(true);

			}
		});
	}
}
