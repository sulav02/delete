package com.sulav_sagar_QFC;

import java.util.ArrayList; // To hold the list of players and monsters
import java.util.List; // To use List interface

// Class to represent a game zone
public class Zone {
	private String name; // Name of the zone
	private List<Player> players; // List of players in this zone
	private List<Monster> monsters; // List of monsters in this zone
	private List<GameObject> objects; // List of objects in this zone
	private List<Weapon> weapons; // List of weapons in this zone
	private Zone north; // Neighboring zone to the north
	private Zone south; // Neighboring zone to the south
	private Zone east; // Neighboring zone to the east
	private Zone west; // Neighboring zone to the west
	private int x; // X coordinate
	private int y; // Y coordinate

	// Constructor to initialize a zone with a name
	public Zone(String name, int x, int y) {
		this.name = name; // Set the zone's name
		this.x = x; // Set the x coordinate
		this.y = y; // Set the y coordinate
		players = new ArrayList<>(); // Initialize players list
		monsters = new ArrayList<>(); // Initialize monsters list
		objects = new ArrayList<>(); // Initialize objects list
		weapons = new ArrayList<>(); // Initialize weapons list
	}

	// Set neighboring zones
	public void setNeighbors(Zone north, Zone south, Zone east, Zone west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}

	// Get a neighboring zone based on direction
	public Zone getNeighbor(String direction) {
		switch (direction.toLowerCase()) {
		case "north":
			return north;
		case "south":
			return south;
		case "east":
			return east;
		case "west":
			return west;
		default:
			return null; // Invalid direction
		}
	}

	// Method to add a player to this zone
	public void addPlayer(Player player) {
		players.add(player); // Add the player to the list
	}

	// Method to remove a player from this zone
	public void removePlayer(Player player) {
		players.remove(player); // Remove the player from the list
	}

	// Method to add a monster to this zone
	public void addMonster(Monster monster) {
		monsters.add(monster); // Add the monster to the list
	}

	// Method to remove a monster from this zone
	public void removeMonster(Monster monster) {
		monsters.remove(monster); // Remove the monster from the list
	}

	// Method to add an object to this zone
	public void addObject(GameObject object) {
		objects.add(object); // Add the object to the zone's list
	}

	// Method to add a weapon to this zone
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon); // Add the weapon to the zone
	}

	// Method for players to collect weapons
	public Weapon collectWeapon(String weaponName) {
		for (Weapon weapon : weapons) {
			if (weapon.getName().equals(weaponName)) {
				weapons.remove(weapon);
				return weapon; // Return the collected weapon
			}
		}
		return null; // Return null if the weapon is not found
	}

	// Method for players to collect objects
	public GameObject collectObject(String objectName) {
		// Loop through the objects in the zone
		for (GameObject object : objects) {
			// Check if the object's name matches the requested name
			if (object.getName().equals(objectName)) {
				objects.remove(object); // Remove the object from the zone
				return object; // Return the collected object
			}
		}
		return null; // Return null if the object is not found
	}

	// Method to get the status of the zone (players and monsters present)
	public String getZoneStatus() {
		StringBuilder status = new StringBuilder(); // Create a StringBuilder for status
		status.append(name).append(":\n"); // Append the zone's name

		// Append players in the zone
		status.append("  Players: ");
		if (players.isEmpty()) {
			status.append("None\n"); // Indicate no players
		} else {
			for (Player player : players) {
				status.append(player.getName()).append(", "); // Append player names
			}
			status.setLength(status.length() - 2); // Remove trailing comma
			status.append("\n"); // New line
		}

		// Append monsters in the zone
		status.append("  Monsters: ");
		if (monsters.isEmpty()) {
			status.append("None\n"); // Indicate no monsters
		} else {
			for (Monster monster : monsters) {
				status.append(monster.getType()).append(", "); // Append monster types
			}
			status.setLength(status.length() - 2); // Remove trailing comma
			status.append("\n"); // New line
		}
		// Append objects in the zone
		status.append("  Objects: ");
		if (objects.isEmpty()) {
			status.append("None\n"); // Indicate no objects
		} else {
			for (GameObject object : objects) {
				status.append(object.getName()).append(", "); // Append object names
			}
			status.setLength(status.length() - 2); // Remove trailing comma
			status.append("\n"); // New line
		}
		// Append weapons in the zone
		status.append("  Weapons: ");
		if (weapons.isEmpty()) {
			status.append("None\n"); // Indicate no weapons
		} else {
			for (Weapon weapon : weapons) {
				status.append(weapon.getName()).append(", "); // Append weapon names
			}
			status.setLength(status.length() - 2); // Remove trailing comma
			status.append("\n"); // New line
		}

		return status.toString(); // Return the constructed status
	}

	// Getter for the zone's name
	public String getName() {
		return name; // Return the zone's name
	}

	// Getters for coordinates
	public int getX() {
		return x; // Return the x coordinate
	}

	public int getY() {
		return y; // Return the y coordinate
	}

	public List<Player> getPlayers() {
		return players; // Return the list of players in this zone
	}

	public List<Monster> getMonsters() {
		return monsters; // Return the list of monsters in this zone
	}

	public List<Weapon> getWeapon() {
		return weapons;
	}

	public List<GameObject> getObjects() {
		return objects; // Return the list of objects in this zone
	}

//	public List<GameObject> getInventory() {
//		return inventory;
//	}

	public void removeObject(GameObject object) {
		objects.remove(object); // Remove the object from the zone
	}

}
