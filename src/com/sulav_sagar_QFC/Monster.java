package com.sulav_sagar_QFC;

public class Monster implements Entity {
	private String type; // Monster name
	private int life; // Monster life
	private int health; // Monster health
	private int power; // Monster power
	private int defense; // Monster defense
	private int speed; // Speed of the monster
	private Zone currentZone; // Current zone of the monster
	private int stunDuration; // Duration for the stun

	// Constructor to initialize monster attributes
	public Monster(String type, int life, int health, int power, int defense, int speed) {
		this.type = type; // Set monster name
		this.life = life; // Set monster life
		this.health = life; // Set health equal to life
		this.power = power; // Set monster power
		this.defense = defense; // Set monster defense
		this.speed = speed; // Set monster speed
		this.stunDuration = 0; // Initialize stun duration
	}

	// Getters for monster's type
	public String getType() {
		return type; // Return monster's type
	}

	// Getters for monster's life
	public int getLife() {
		return life; // Return monster's life
	}

	// Getters for monster's health
	public int getHealth() {
		return health; // Return monster's health
	}

	public int getPower() {
		return power; // Return monster's power
	}

	// Getter for monster's defense
	public int getDefense() {
		return defense; // Return monster's defense
	}

	// Getter for the current zone of the monster
	@Override
	public Zone getCurrentZone() {
		return currentZone; // Getter for current zone
	}

	// Setter for the current zone of the monster
	@Override
	public void setCurrentZone(Zone zone) {
		this.currentZone = zone; // Setter for current zone
	}

	// Method to get the name of the monster
	@Override
	public String getName() {
		return type; // Return the type as the name
	}

//	// Method to update health
	public void updateHealth(int amount) {
		health += amount; // Update health value
		if (health < 0) {
			health = 0; // Ensure health doesn't go below 0
		}
	}

	// Method to inflict damage on the monster
	public void takeDamage(int damage) {
		health -= damage; // Reduce health by damage
		if (health < 0) {
			health = 0; // Prevent negative health
		}
	}

	// Stun-related methods

	@Override
	public void stun(int duration) {
		this.stunDuration = duration; // Set the stun duration
	}

	@Override
	public void endTurn() {
		if (stunDuration > 0) {
			stunDuration--; // Decrement stun duration
		}
	}

	@Override
	public boolean isStunned() {
		return stunDuration > 0; // Return true if the monster is stunned
	}

	// Implementing Entity interface methods
	@Override
	public boolean isAlive() {
		return health > 0; // Monster is alive if health is greater than 0
	}

	@Override
	public void setHealth(int health) {
		this.health = health; // Set health for the monster
	}

	@Override
	public int getSpeed() {
		return speed; // Return the monster's speed (add a speed attribute if necessary)
	}
}
