package de.stevenpaw.battlenexus.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import de.stevenpaw.battlenexus.game.Arena;
import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.BNSign.Type;

public class SignTools implements  Listener {
	
	public static List<BNSign> signs = new ArrayList<BNSign>();
	
	public static void loadSigns() {
		
	}
	
	public static void saveSigns() {
		for(BNSign bs : signs) {
			//TODO: Save Signs!!!
		}
	}
	
	
	@EventHandler
	public static void OnSignChange(SignChangeEvent e) {
		Boolean isBN = false;
		Location SignLoc = e.getBlock().getLocation();
        
        String str = e.getLine(0);
        Tools.DebugMessage("Line 0: " + str);
        if(str.equalsIgnoreCase("[battlenexus]")) {
        	isBN = true;
        	e.setLine(0, Main.cfg.getString("Basic.prefix"));
        	Tools.DebugMessage("Updated Sign");
        };
        str = e.getLine(1);
        if(str != null && isBN) {
        	switch(str) {
        	case "leaderboard":
        		e.setLine(1, "§4NOT AVAILABLE");
        		break;
        	case "teleport":
        		e.setLine(1, "§4NOT AVAILABLE");
        		break;
        	case "join":
        		Boolean k = false;
        		for(Arena a : ArenaManager.arenas.values()) {
        			if(a.getName().equalsIgnoreCase(str)) {
                		e.setLine(1, a.getName());
                		e.setLine(2, "--<>--");
                		e.setLine(3, Tools.cfgM("Arena.Status." + a.getState().toString(), null));
                		k = true;
                		
                		
                		signs.add(new BNSign(signs.size(), a.getName(), Type.JOIN, SignLoc));
                		saveSigns();
                		
//                		Main.signcfg.set("Sign.Arena." + a.getName() + SignLoc.toString(), SignLoc);
        			}
        		}
        		if(!k) {
            		e.setLine(0, "NOT FOUND");
        		}
        		break;
        	}
        }
	}
}
