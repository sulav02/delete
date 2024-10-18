package com.sulav_sagar_QFC;

// Class to represent a weapon in the game
public class Weapon {
	private String name; // Name of the weapon
	private String type; // Type of the weapon (e.g., "Lightning", "Wood", "Metal")

	// Constructor to initialize weapon attributes
	public Weapon(String name, String type) {
		this.name = name; // Set the weapon's name
		this.type = type; // Set the weapon's type
	}

	// Getter for weapon name
	public String getName() {
		return name; // Return the weapon's name
	}

	// Getter for weapon type
	public String getType() {
		return type; // Return the weapon's type
	}

	// Method to use the object on a target entity
	public void use(Player player, Entity target, Tactician tactician) {
		switch (type) { // Determine action based on the object's type
		case "Lightning": // Case for the Lightning
			if (target instanceof Monster) { // Check if there are uses left and target is a monster
				// Halve the monster's health
				((Monster) target).takeDamage(((Monster) target).getHealth() / 2);
				tactician.getTextArea()
						.append(player.getName() + " used the Lightning on " + ((Monster) target).getType() + ".\n");
			}
			break;

		case "Metal": // Case for the Metal
			if (target instanceof Monster) { // Check if there are uses left and target is a monster
				// Halve the monster's health
				((Monster) target).takeDamage(((Monster) target).getHealth() / 2);
				tactician.getTextArea()
						.append(player.getName() + " used the Metal on " + ((Monster) target).getType() + ".\n");
			}
			break;
		case "Wood": // Case for the Wood
			if (target instanceof Monster) { // Check if there are uses left and target is a monster
				// Halve the monster's health
				((Monster) target).takeDamage(((Monster) target).getHealth() / 2);
				tactician.getTextArea()
						.append(player.getName() + " used the Wood on " + ((Monster) target).getType() + ".\n");
			}
			break;
		}
	}
}
