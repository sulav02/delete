package com.sulav_sagar_QFC;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonsterTest {
	private Monster monster;

	@BeforeEach
	public void setUp() {
		monster = new Monster("Dragon", 80, 80, 25, 15, 3); // Initialize monster for testing
	}

	@Test
	public void testGetName() {
		assertEquals("Dragon", monster.getType()); // Test monster type/name
	}

	@Test
	public void testGetLife() {
		assertEquals(80, monster.getLife()); // Test monster life
	}

	@Test
	public void testUpdateHealth() {
		monster.takeDamage(20); // Reduce health
		assertEquals(60, monster.getHealth()); // Verify updated health
		monster.takeDamage(100); // Reduce health below zero
		assertEquals(0, monster.getHealth()); // Verify health does not go below zero
	}
}
