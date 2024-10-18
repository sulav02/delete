package com.sulav_sagar_QFC;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; // For random number generation

// Class to represent the world consisting of zones
public class World {
	private Zone[][] zones; // 2D array of zones
	private int size; // Size of the world grid

	// Constructor to initialize the world with a specific size
	public World(int size) {
		if (size < 2 || size > 3) {
			throw new IllegalArgumentException("World size must be between 2 and 3.");
		}

		this.size = size; // Set the size of the world
		zones = new Zone[size][size]; // Initialize the zone grid

		// Create zones and assign names
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				zones[i][j] = new Zone("Zone (" + i + "," + j + ")", i, j); // Naming zones based on their grid position
																			// and also includes coordinates

			}
		}
		// Set neighbors for each zone
		for (int i = 0; i < size; i++) { // Loop through each row of the zones
			for (int j = 0; j < size; j++) { // Loop through each column of the zones
				Zone current = zones[i][j]; // Get the current zone at position (i, j)

				// Determine the neighboring zones, setting them to null if they are out of
				// bounds
				Zone north = (i > 0) ? zones[i - 1][j] : null; // North neighbor (above) if not in the first row
				Zone south = (i < size - 1) ? zones[i + 1][j] : null; // South neighbor (below) if not in the last row
				Zone east = (j < size - 1) ? zones[i][j + 1] : null; // East neighbor (right) if not in the last column
				Zone west = (j > 0) ? zones[i][j - 1] : null; // West neighbor (left) if not in the first column

				// Set the neighbors for the current zone
				current.setNeighbors(north, south, east, west); // Assign the determined neighbors to the current zone
			}
		}
		// Initialize objects and weapons
		initializeObjectsAndWeapons();
	}

	private void initializeObjectsAndWeapons() {
		Random rand = new Random();

		// Define some example weapons
		List<Weapon> weaponList = new ArrayList<>();
		weaponList.add(new Weapon("Lightning Sword", "Lightning"));
		weaponList.add(new Weapon("Wood Weapon", "Wood"));
		weaponList.add(new Weapon("Metal Axe", "Metal"));

		// Define some game objects
		List<GameObject> objectList = new ArrayList<>();
		objectList.add(new GameObject("Crown", "Crown"));
		objectList.add(new GameObject("Yellow Wand", "Yellow Wand", 5)); // 5 uses
		objectList.add(new GameObject("Green Wand", "Green Wand", 3)); // 3 uses
		objectList.add(new GameObject("Red Wand", "Red Wand", 2)); // 2 uses
		objectList.add(new GameObject("Blue Wand", "Blue Wand", 1)); // 1 use

		// Randomly place weapons in zones
		for (Weapon weapon : weaponList) {
			int x = rand.nextInt(size);
			int y = rand.nextInt(size);
			zones[x][y].addWeapon(weapon);
		}

		// Randomly place objects in zones
		for (GameObject object : objectList) {
			int x = rand.nextInt(size);
			int y = rand.nextInt(size);
			zones[x][y].addObject(object);
		}
	}

	// Method to get a random zone
	public Zone getRandomZone() {
		Random rand = new Random(); // Create a Random object
		int x = rand.nextInt(size); // Random x coordinate
		int y = rand.nextInt(size); // Random y coordinate
		return zones[x][y]; // Return the randomly selected zone
	}

	// Method to get the grid of zones
	public Zone[][] getZones() {
		return zones; // Return the grid of zones
	}

	// Method to get the status of all zones in the world
	public String getZonesStatus() {
		StringBuilder status = new StringBuilder(); // Create a StringBuilder for status
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				status.append(zones[i][j].getZoneStatus()); // Get the status of each zone
			}
		}
		return status.toString(); // Return the constructed status of all zones
	}
}
