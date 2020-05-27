package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ArenaManager {
    public List<Arena> arenas = new ArrayList<>();
    public Arena getArena(String name){
        return arenas.stream().filter(arena -> arena.getName().equals(name)).findFirst().orElse(null);
    }
    
    public Boolean CreateArena(String name, Integer minP, Integer maxP, Player p) {
    	
    	arenas.add(new Arena(name, 1, 4, p.getLocation()));
    	
    	
    	
    	return true;
    }
}