package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.database.SQL_Arenas;
import de.stevenpaw.battlenexus.utils.Tools;


public class ArenaManager {

	public static HashMap<String, Arena> arenas = new HashMap<String, Arena>();

	//    public List<Arena> arenas = new ArrayList<>();
	public static Arena getArena(String name){
		return arenas.get(name);
	}

	public static Boolean CreateArena(String name, Integer minP, Integer maxP, Player p) {

		Tools.DebugMessage("CreateArena 1/6");
		Boolean works = false;

		if(!arenas.containsKey(name)) {
			Tools.DebugMessage("CreateArena 2/6");
			List<Kit> standardkit = new ArrayList<Kit>();
			
			standardkit.add(KitManager.kits.get("basic"));
			Tools.DebugMessage("CreateArena 3/6");
			arenas.put(name, new Arena(name, 1, 2, p.getLocation(), standardkit, Arena.GameState.LOBBY));
			Tools.DebugMessage("Arena "+name+" created");
			Tools.DebugMessage("CreateArena 4/6");
			if(SaveArenas()) {
				works = true;
				Tools.DebugMessage("CreateArena 5/6");
			}
		}

		Tools.DebugMessage("CreateArena 6/6 - completed");
		return works;
	}
	
	public static Boolean RemoveArena(String name) {

		Boolean works = false;

		if(arenas.get(name) != null) {
			SQL_Arenas.removeArenaSQL(name);
			arenas.remove(name);
			if(SaveArenas()) {
				works = true;
			}
		}

		return works;
	}

	public static Boolean SaveArenas() {

		SQL_Arenas.saveArenas();
		
		return true;
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