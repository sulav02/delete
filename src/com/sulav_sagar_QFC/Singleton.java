package com.sulav_sagar_QFC;

import java.util.List;

public class Singleton {
	private static World sameWorld = new World(3);
	private static Tactician sameTactician = new Tactician(null, null);

	public static World world = getSingleWorld();
	public static Tactician tactician = sameTactician;

	public static World getSingleWorld() {
		return sameWorld;
	}

	public static Tactician getSingleTactician(List<Player> player, List<Monster> monsters) {
		return new Tactician(player, monsters);
	}

}
