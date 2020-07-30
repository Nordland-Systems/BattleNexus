package de.stevenpaw.battlenexus.utils;

import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.stevenpaw.battlenexus.game.Arena;
import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.BNSign.Type;
import net.md_5.bungee.api.ChatColor;

public class SignTools implements  Listener {

	public static HashMap<Integer,BNSign> signs = new HashMap<Integer,BNSign>();

	public static void loadSigns() {
		Boolean CFGisLoaded = false;
		Tools.DebugMessage("[SignLoader] §f0/4");
		try {
			Main.signcfg.load(Main.signfile);
			CFGisLoaded = true;
			Tools.DebugMessage("[SignLoader] §f1/4");
		} catch (Exception e) {
			e.printStackTrace();
			CFGisLoaded = false;
			Tools.DebugMessage("[SignLoader] §fABORTED");

			try {
				Main.signcfg.save(Main.signfile);
				Main.signcfg.load(Main.signfile);
				Tools.DebugMessage("[SignLoader] §fLoaded at 2nd try");
				CFGisLoaded = true;
			} catch(Exception eb) {
				eb.printStackTrace();
				CFGisLoaded = false;
				Tools.DebugMessage("[SignLoader] §fABORTED 2ND TIME");
			}
		}

		if(CFGisLoaded) {
			try {
				Main.signcfg.load(Main.signfile);
			} catch (Exception exc){

			}
			Tools.DebugMessage("Signlistsize: " + Main.signcfg.get("Signs.2"));
			if(Main.signcfg.getList("Signs").size() > 0) {
				signs.clear();

				Tools.DebugMessage("[SignLoader] §f2/4");
				for(int i = 0; i< Main.signcfg.getList("Signs").size(); i++) {
					BNSign bs = new BNSign();
					bs.setID(i);
					bs.setArena(Main.signcfg.getString("Signs."+i+".arena"));
					switch(Main.signcfg.getString("Signs."+i+".type")) {
					case "JOIN":
						bs.setType(BNSign.Type.JOIN);
						break;
					case "LEADERBOARD":
						bs.setType(BNSign.Type.LEADERBOARD);
						break;
					case "TELEPORT":
						bs.setType(BNSign.Type.TELEPORT);
						break;
					default:
						bs.setType(BNSign.Type.JOIN);
						break;
					}
					bs.setLocation(new Location(Bukkit.getWorld(Main.signcfg.getString("Signs."+i+".location.world")),
							Main.signcfg.getInt("Signs."+i+".location.X"),Main.signcfg.getInt("Signs."+i+".location.Y"),
							Main.signcfg.getInt("Signs."+i+".location.Z")));
					Tools.DebugMessage("[SignLoader] §f3/4 Part " + i + " of " + Main.signcfg.getList("Signs").size());
					signs.put(bs.getID(), bs);
					Tools.DebugMessage("[SignLoader] §f4/4 Part " + i + " of " + Main.signcfg.getList("Signs").size());
				}
			} else {
				Tools.DebugMessage("Signentrysize: " + signs.size());
				Tools.DebugMessage("[SignLoader] §fCfg has no entrys. aborting load");
			}
		}
	}

	public static void saveSigns() {
		for(BNSign bs : signs.values()) {
			Main.signcfg.set("Signs." + bs.getID() + ".arena", bs.getArena());
			Main.signcfg.set("Signs." + bs.getID() + ".type", bs.getType().toString());
			Main.signcfg.set("Signs." + bs.getID() + ".location.X", bs.getLocation().getBlockX());
			Main.signcfg.set("Signs." + bs.getID() + ".location.Y", bs.getLocation().getBlockY());
			Main.signcfg.set("Signs." + bs.getID() + ".location.Z", bs.getLocation().getBlockZ());
			Main.signcfg.set("Signs." + bs.getID() + ".location.world", bs.getLocation().getWorld().getName());
			Tools.DebugMessage("Sign created and Saved! - " + bs.getID());
		}
		try {
			Main.signcfg.save(Main.signfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@EventHandler
	public static void OnSignChange(SignChangeEvent e) {
		Boolean isBN = false;
		Location SignLoc = e.getBlock().getLocation();

		String str = e.getLine(0);
		Tools.DebugMessage("Line 0: " + str);
		if(str.equalsIgnoreCase("[bn]")) {
			isBN = true;
			e.setLine(0, ChatColor.translateAlternateColorCodes('&',Main.cfg.getString("Basic.prefix")));
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
					if(a.getName().equalsIgnoreCase(e.getLine(2))) {
						e.setLine(1, a.getName());
						e.setLine(2, "--<>--");
						e.setLine(3, Tools.cfgM("Arena.Status." + a.getState().toString(), null));
						k = true;

						Tools.DebugMessage("Schild bearbeitet" + signs.size());
						Integer id = findFreeID();
						signs.put(id,new BNSign(id, a.getName(), Type.JOIN, SignLoc));
						Tools.DebugMessage("Schilderanzahl: " + signs.size());
						Tools.DebugMessage("SchildID: " + signs.get(id).getID());
						saveSigns();

						//                		Main.signcfg.set("Sign.Arena." + a.getName() + SignLoc.toString(), SignLoc);
					}
				}
				if(!k) {
					e.setLine(0, "§4[bn]");
					e.setLine(1, "§4NOT FOUND");
					e.setLine(2, "--<>--");
					e.setLine(3, "-");
				}
				break;
			}
		}
	}

	public static void OnSignBreak(PlayerInteractEvent e) {
		//coming from PlayerListener
		//Join-Sign
		Tools.DebugMessage("Wurde weitergegeben");
		Boolean isInCfg = false;
		for(BNSign sign : signs.values()) {
			Tools.DebugMessage("Teste ID: " + sign.getID());
			if(sign.getLocation().distance(e.getClickedBlock().getLocation()) < 1) {
				isInCfg = true;
				Tools.DebugMessage("Schild wird entfernt! Location: " + sign.getLocation() + " | ID: " + sign.getID());

				Main.signcfg.set("Signs." + sign.getID(), null);
				signs.remove(sign.getID());
				Tools.DebugMessage("Schild wurde entfernt!");

				try {
					Main.signcfg.save(Main.signfile);
				} catch (Exception ex){

				}

			}
			if(isInCfg) break;
		}
		if(!isInCfg) {
			Tools.ConsoleErrorMessage("SCHILD NICHT IN LISTE!!!", null);
		} else {
			saveSigns();
		}

	}

	public static int findFreeID() {
		Integer r = 0;

		Boolean a = false;
		while (!a) {
			if(signs.get(r) == null) {
				a = true;
			} else {
				r += 1;
			}
		}

		return r;
	}
}
