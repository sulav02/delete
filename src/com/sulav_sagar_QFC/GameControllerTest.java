package com.sulav_sagar_QFC;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameControllerTest {
	private GameController gameController;

	@BeforeEach
	void setUp() {

		gameController = new GameController(); // Create a GameController instance, passing the JTextArea
												// for output handling
	}

	@Test
	void testAddPlayer() {
		gameController.addPlayer("Hero", 100, 100, 15, 10, 5); // Add player
		assertEquals(1, gameController.getPlayers().size()); // Check if player added
		assertEquals("Hero", gameController.getPlayers().get(0).getName()); // Verify player's name
	}

	@Test
	void testAddMonster() {
		gameController.addSelectedMonster("Lightning Beast"); // test monster
		assertEquals(1, gameController.getMonsters().size()); // Check if monster added
		assertEquals("Lightning Beast", gameController.getMonsters().get(0).getType()); // Verify monster's
																						// type
	}

	@Test
	void testPlayerAttackMonster() {
		Player player = new Player("Hero", 100, 100, 15, 10, 5); // Create player
		Monster monster = new Monster("Wood Golem", 50, 50, 8, 2, 3); // Create monster
		player.attack(monster); // Player attacks monster
		assertEquals(50 - (10 - 2), monster.getHealth()); // Check if monster health reduced correctly
	}
}
