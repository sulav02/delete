package com.sulav_sagar_QFC;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
	private Player player;

	@BeforeEach
	public void setUp() {
		player = new Player("Hero", 100, 100, 5, 20, 10); // Initialize player for testing
	}

	@Test
	public void testGetName() {
		assertEquals("Hero", player.getName()); // Test player name
	}

	@Test
	public void testGetLife() {
		assertEquals(100, player.getLife()); // Test player life
	}

	@Test
	public void testUpdateHealth() {
		player.updateHealth(-30); // Reduce health
		assertEquals(70, player.getHealth()); // Verify updated health
		player.updateHealth(-100); // Reduce health below zero
		assertEquals(0, player.getHealth()); // Verify health does not go below zero
	}
}
