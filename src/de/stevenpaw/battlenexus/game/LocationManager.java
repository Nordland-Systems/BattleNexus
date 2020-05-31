package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import de.stevenpaw.battlenexus.database.SQL_Arenas;
import de.stevenpaw.battlenexus.utils.Tools;


public class LocationManager {

	public static List<Location> locations = new ArrayList<Location>();
	public static List<String> arenanames = new ArrayList<String>();

	//    public List<Arena> arenas = new ArrayList<>();
	//	public static Location getArena(String name){
	//		return locations.get(name);
	//	}

	public static Boolean CreateLocation(String arenaname, Location loc) {

		Tools.DebugMessage("CreateArena 1/6");
		Boolean works = false;

		locations.add(loc);
		arenanames.add(arenaname);

		Tools.DebugMessage("CreateArena 6/6 - completed");
		return works;
	}

	public static Boolean RemoveLocationByLocation(Location loc) {

		Boolean works = false;
		Location closest = new Location(null,0.0,0.0,0.0);
		closest = locations.get(0);
		for(Location l : locations) {
			if(loc.distance(l) < loc.distance(closest)) {
				closest = l;
			}
		}
		
		for (int i = 0; i< locations.size();i++){
			if (locations.get(i) == closest){
				locations.remove(i);
				arenanames.remove(i);
				works = true;
			}
		}

		return works;
	}

	public static Boolean SaveLocations() {

		SQL_Arenas.saveArenas();

		return true;
	}

	public static List<Location> getAllLocationsOfArena(String arena) {
		List<Location> locs = new ArrayList<Location>();
		
		for(int i = 0; i < arenanames.size(); i++) {
			if (arenanames.get(i).equalsIgnoreCase(arena)) {
				locs.add(locations.get(i));
			}
		}

		return locs;
	}
}