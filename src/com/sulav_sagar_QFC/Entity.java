package com.sulav_sagar_QFC;

// Entity interface to be implemented by both Player and Monster
public interface Entity {
	boolean isAlive(); // Check if the entity is alive

	void setHealth(int health); // Set health for the entity

	int getSpeed(); // Get speed of the entity

	Zone getCurrentZone(); // Get the current zone of the entity

	void setCurrentZone(Zone zone); // Set the current zone of the entity

	String getName(); // Get the name of the entity

	void stun(int duration); // Stun the entity for a specified duration

	void endTurn(); // Handle end-of-turn logic for the entity

	boolean isStunned(); // Check if the entity is stunned
}
