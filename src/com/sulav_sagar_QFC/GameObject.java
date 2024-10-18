package com.sulav_sagar_QFC; // Package declaration for organization

import java.util.Random; // Importing Random class for random number generation

// Class representing a game object
public class GameObject {
	private String name; // Name of the object (e.g., "Crown")
	private String type; // Type of the object (e.g., "Crown", "Wand", etc.)
	private int uses; // Number of uses left for wands

	// Constructor for objects that don't have uses (like the Crown)
	public GameObject(String name, String type) {
		this.name = name; // Set the name of the object
		this.type = type; // Set the type of the object
		this.uses = 0; // Initialize uses to 0 for non-wand objects
	}

	// Constructor for wands, which have a specific number of uses
	public GameObject(String name, String type, int uses) {
		this.name = name; // Set the name of the wand
		this.type = type; // Set the type of the wand
		this.uses = uses; // Set the number of uses for the wand
	}

	// Getter method for the object's name

	public String getName() {
		return name; // Return the name of the object
	}

	// Getter method for the object's type
	public String getType() {
		return type; // Return the type of the object
	}

	// Getter method for the number of uses left
	public int getUses() {
		return uses; // Return the number of uses left
	}

	// Method to use the object on a target entity
	public void use(Player player, Entity target, Tactician tactician) {
		switch (type) { // Determine action based on the object's type
		case "Crown": // Case for the Crown object
			// Check if target is a player and not the same as the user
			if (target instanceof Player && target != player) {
				tactician.endGame(player); // End the game for the player

			} else {
				// Inform the player that they can't use the Crown on themselves
				tactician.getTextArea().append("You cannot use the Crown on yourself.\n");
			}
			break; // End of Crown case

		case "Yellow Wand": // Case for the Yellow Wand
			if (uses > 0) { // Check if there are uses left
				tactician.stunEntity(target); // Apply stun effect to the target
				uses--; // Decrement the number of uses left
			}
			break; // End of Yellow Wand case

		case "Green Wand": // Case for the Green Wand
			if (uses > 0) { // Check if there are uses left
				if (new Random().nextBoolean()) { // 50% chance of success
					// Calculate health increase based on player's power
					int healthIncrease = Math.min(player.getPower(),
							((Player) target).getLife() - ((Player) target).getHealth());
					((Player) target).setHealth(((Player) target).getHealth() + healthIncrease); // Increase target's
																									// health
					tactician.getTextArea().append(
							player.getName() + " used the Green Wand on " + ((Player) target).getName() + ".\n"); // Inform
																													// action
				} else {
					tactician.getTextArea().append("The Green Wand failed!\n"); // Inform failure
				}
				uses--; // Decrement the number of uses left
			}
			break; // End of Green Wand case

		case "Red Wand": // Case for the Red Wand
			if (uses > 0 && target instanceof Monster) { // Check if there are uses left and target is a monster
				// Halve the monster's health
				((Monster) target).takeDamage(((Monster) target).getHealth() / 2);
				tactician.getTextArea()
						.append(player.getName() + " used the Red Wand on " + ((Monster) target).getType() + ".\n"); // Inform
																														// action
				uses--; // Decrement the number of uses left
			}
			break; // End of Red Wand case

		case "Blue Wand": // Case for the Blue Wand
			if (uses > 0 && target instanceof Player && !((Player) target).isAlive()) { // Check if uses are left and
																						// target is a dead player
				// Revive the player with 50% of their life
				((Player) target).setHealth(((Player) target).getLife() / 2);
				tactician.getTextArea().append(
						player.getName() + " revived " + ((Player) target).getName() + " with the Blue Wand.\n"); // Inform
																													// action
				uses--; // Decrement the number of uses left
			}
			break; // End of Blue Wand case

		}
	}

}
