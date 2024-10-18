package com.sulav_sagar_QFC;

import java.util.ArrayList;
import java.util.List;

public class Player implements Entity {
	private String name; // Player name
	private int life; // Player life
	private int health; // Player health
	private int speed; // Player speed
	private int power; // Player power
	private int defense; // Player defense
	private Zone currentZone; // Current zone of the player
	private List<GameObject> inventory; // Declare inventory to hold GameObjects
	private List<Weapon> weapons; // Inventory for weapons
	private Weapon equippedWeapon; // Currently equipped weapon
	private int stunDuration; // Duration for the stun

	// Constructor to initialize player attributes
	public Player(String name, int life, int health, int speed, int power, int defense) {
		this.name = name; // Set player name
		this.life = life; // Set player life
		this.health = life; // Set health equal to life
		this.speed = speed; // Set player speed
		this.power = power; // Set player power
		this.defense = defense; // Set player defense
		this.inventory = new ArrayList<>(); // Initialize inventory
		this.weapons = new ArrayList<>(); // Initialize weapons list
		this.equippedWeapon = null; // No weapon equipped initially
		this.stunDuration = 0; // Initialize stun duration
	}

	// Method to collect an object
	public void collectObject(GameObject object) {
		inventory.add(object); // Add the object to the inventory
	}

	// Method to collect a weapon
	public void collectWeapon(Weapon weapon) {
		weapons.add(weapon); // Add the weapon to the weapons list
	}

	// Method to equip a weapon
	public void equipWeapon(Weapon weapon) {
		this.equippedWeapon = weapon; // Set the equipped weapon
	}

	// Method to drop an object
	public void dropObject(GameObject object, Zone zone) {
		inventory.remove(object); // Remove from inventory
		zone.addObject(object); // Add object back to the zone
	}

	// Method to drop a weapon
	public void dropWeapon(Weapon weapon, Zone zone) {
		if (weapons.remove(weapon)) { // Remove the weapon from inventory
			zone.addWeapon(weapon); // Add weapon back to the zone
		}
	}

	// Method to use an object
	public void useObject(GameObject object, Entity target, Tactician tactician) {
		object.use(this, target, tactician); // Use the object on the target
	}

	// Method for player to use a weapon on a monster
	public void useWeapon(Monster monster, Tactician tactician) {
		if (equippedWeapon == null) {
			tactician.getTextArea().append(name + " has no weapon equipped!\n");
			return;
		}

		int damage = calculateWeaponDamage(equippedWeapon, monster);

		// Apply damage to the monster
		monster.takeDamage(damage);

		// Log action in GUI
		tactician.getTextArea().append(name + " used " + equippedWeapon.getName() + " on " + monster.getType() + " for "
				+ damage + " damage.\n");

	}

	// Method to calculate damage based on weapon and monster type
	private int calculateWeaponDamage(Weapon weapon, Monster monster) {
		int damage = power; // Base damage is the player's power

		// Adjust damage based on weapon and monster types
		if (weapon.getType().equals("Lightning"))

		{
			if (monster.getType().equals("Metal")) {
				damage *= 2; // Double damage to Metal
			} else if (monster.getType().equals("Wood")) {
				damage /= 2; // Half damage to Wood
			}
		} else if (weapon.getType().equals("Wood")) {
			if (monster.getType().equals("Lightning")) {
				damage *= 2; // Double damage to Lightning
			} else if (monster.getType().equals("Metal")) {
				damage /= 2; // Half damage to Metal
			}
		} else if (weapon.getType().equals("Metal")) {
			if (monster.getType().equals("Wood")) {
				damage *= 2; // Double damage to Wood
			} else if (monster.getType().equals("Lightning")) {
				damage /= 2; // Half damage to Lightning
			}
		}

		// Calculate final damage
		damage -= monster.getDefense(); // Subtract monster's defense
		return Math.max(1, damage); // Ensure minimum damage is 1
		// monster.takeDamage(damage); // Apply damage to the monster

	}

	// Method to get the player's inventory
	public List<GameObject> getInventory() {
		return inventory; // Return the inventory list
	}

	public List<Weapon> getWeapons() {
		return weapons; // Return the weapons list
	}

	// Getters for player attributes
	@Override
	public String getName() {
		return name; // Return player name
	}

	public int getLife() {
		return life; // Return player life
	}

	public int getHealth() {
		return health; // Return player health
	}

	@Override
	public int getSpeed() {
		return speed; // Return player speed
	}

	public int getPower() {
		return power; // Return player power
	}

	public int getDefense() {
		return defense; // Return player defense
	}

	@Override
	public Zone getCurrentZone() {
		return currentZone; // Getter for current zone
	}

	@Override
	public void setCurrentZone(Zone zone) {
		this.currentZone = zone; // Setter for current zone
	}

	// Method to update health
	public void updateHealth(int amount) {
		health += amount; // Update health value
		if (health < 0) {
			health = 0; // Ensure health doesn't go below 0
		}
	}

	// Method for player to take damage
	public String takeDamage(int damage) {
		health -= damage; // Subtract damage from health
		if (health <= 0) {
			return name + " has been defeated!";
		}
		return name + " takes " + damage + " damage. Remaining health: " + health;
	}

	// Method for player to attack
	public void attack(Monster monster) {
		int damage = power - monster.getDefense(); // Calculate damage
		if (damage > 0) {
			monster.takeDamage(damage); // Inflict damage on monster
		}
	}

	// Method for player to heal
	public void heal(int amount) {
		this.health += amount; // Increase health by the specified amount
		if (this.health > life) {
			this.health = life; // Ensure health does not exceed maximum life
		}
	}

	// Stun-related methods
	@Override
	public void stun(int duration) {
		this.stunDuration = duration; // Set the stun duration
	}

	// Implementing Entity interface method to end the turn
	@Override
	public void endTurn() {
		if (stunDuration > 0) {
			stunDuration--; // Decrement stun duration
		}
	}

	@Override
	public boolean isStunned() {
		return stunDuration > 0; // Return true if the player is stunned
	}

	// Implementing Entity interface method
	@Override
	public boolean isAlive() {
		return health > 0; // Player is alive if health is greater than 0
	}

	@Override
	public void setHealth(int health) {
		this.health = health; // Set health for the player

	}

}
