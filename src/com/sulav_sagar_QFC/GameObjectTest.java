package com.sulav_sagar_QFC;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameObjectTest {
	private GameObject gameObject;

	@BeforeEach
	public void setUp() {
		gameObject = new GameObject("Treasure Chest", "Item"); // Example initialization
	}

	@Test
	public void testGetName() {
		assertEquals("Treasure Chest", gameObject.getName()); // Test object name
	}

	@Test
	public void testGetType() {
		assertEquals("Item", gameObject.getType()); // Test object type
	}
}
