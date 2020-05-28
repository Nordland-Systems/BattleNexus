package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.utils.Tools;


public class ArenaManager {

	static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

	//    public List<Arena> arenas = new ArrayList<>();
	public static Arena getArena(String name){
		return arenas.get(name);
	}

	public static Boolean CreateArena(String name, Integer minP, Integer maxP, Player p) {

		Boolean works = false;

		if(arenas.get(name) != null) {
			arenas.put(name, new Arena(name, 1, 2, p.getLocation()));
			if(!SaveArenas()) {
				works = true;
			}
		}

		return works;
	}

	public static Boolean SaveArenas() {

		return false;
	}

	public static List<String> getAllArenaNames() {
		List<String> arenanames = new ArrayList<String>();
		if (arenas.size() == 0) {
			Tools.DebugMessage("No Arenas yet!");
		} else {
			for(Arena a : arenas.values()){
				arenanames.add(a.getName());
			}
		}

		return arenanames;
	}
}