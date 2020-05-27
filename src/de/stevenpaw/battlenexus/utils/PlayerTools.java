package de.stevenpaw.battlenexus.utils;

import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.main.Main;

public class PlayerTools {
	
	public static void SaveInventory(Player p) {		
		Main.pcfg.set("Players." + p.getName() + ".inventory", p.getInventory().getContents());
	}
	
	public static void getGameState(Player p) {
		Main.pcfg.get("Players." + p.getName() + ".gamestate");
	}
	
	public static void setGameState(Player p, String state) {
		
	}
}
