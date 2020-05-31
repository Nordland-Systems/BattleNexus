package de.stevenpaw.battlenexus.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.stevenpaw.battlenexus.game.Kit;
import de.stevenpaw.battlenexus.game.KitManager;
import de.stevenpaw.battlenexus.main.Main;

public class PlayerTools {


	//INVENTAR
	public static Inventory getInventory(Player p) {
		Inventory player_inv = p.getInventory();
		return player_inv;
	}

	public static void SaveInventory(Player p) {		
		Main.pcfg.set("Players." + p.getName() + ".inventory", p.getInventory().getContents());
		try {
			Main.pcfg.save(Main.pfile);
		} catch (IOException e) {
			Tools.DebugMessage("Couldn't save Player Inventory!!! | §f"+e);
		}
	}

	public static void LoadInventory(Player p) {
		try {
			Main.pcfg.load(Main.pfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//V1
//		Tools.DebugMessage("Starting Loading!");
//		Tools.DebugMessage("raw: " + (List<ItemStack>) Main.pcfg.getList("Players." + p.getName() + ".inventory"));
//		List<ItemStack> items = (List<ItemStack>) Main.pcfg.getList("Players." + p.getName() + ".inventory");
//		if(items.isEmpty()) {
//			Tools.DebugMessage("is Empty!!!");
//		}
//		Tools.DebugMessage("items: " + items.toString());
//		
//		p.getInventory().setContents(items.toArray(new ItemStack[items.size()]));
		
		
		//V2
		Tools.DebugMessage("Playertools Itemloader - §fStarting | Player: " + p.getName());
		ArrayList<ItemStack> content = new ArrayList<ItemStack>();
		Tools.DebugMessage("Playertools Itemloader - §fStarted");
		Tools.DebugMessage("Playertools Itemloader - Player: " + p.getName() + " | Length: " + Main.pcfg.getList("Players." + p.getName() + ".inventory").size());
		
		for(int i = 0; i < Main.pcfg.getList("Players." + p.getName() + ".inventory").size();i++) {
			Tools.DebugMessage("Playertools Itemloader - §fStart Load Item: #" + i);
			if(Main.pcfg.getList("Players." + p.getName() + ".inventory").get(i) != null) {
				content.add((ItemStack) Main.pcfg.getList("Players." + p.getName() + ".inventory").get(i));
				Tools.DebugMessage("Playertools Itemloader - §fLoading Item: #" + i + "...");
			} else {
				content.add(null);
				Tools.DebugMessage("Playertools Itemloader - §7Loading Null: #" + i + "...");
			}
		}
		Tools.DebugMessage("Playertools Itemloader - §fLoaded Inventory");
		Tools.DebugMessage("Playertools Itemloader - §fAdding Inventory...");
		ItemStack[] items = new ItemStack[content.size()];
		for (int i = 0; i < content.size(); i++) {
			ItemStack item = content.get(i);
			if (item != null) {
				items[i] = item;
				Tools.DebugMessage("Playertools Itemloader - §fAdding Item: #" + i);
			} else {
				items[i] = null;
				Tools.DebugMessage("Playertools Itemloader - §7Adding Null: #" + i);
			}
		}
		Tools.DebugMessage("Playertools Itemloader - §fAdded Inventory");
		Tools.DebugMessage("Playertools Itemloader - §fSetting Inventory...");
		p.getInventory().setContents(items);
		Tools.DebugMessage("Playertools Itemloader - §fInventory set!");
		Tools.DebugMessage("Playertools Itemloader - §fFinished");
	}



	//STATISTIKEN
	public static void setStats(Player p, String cat, String stat, String kit, Integer value) {
		switch (cat) {
		case "usedpads":
			Integer jumppads = (int)Main.pcfg.get("Players." + p.getName() + ".stat.pads.jumppads", p.getInventory().getContents());
			Integer speedpads = (int)Main.pcfg.get("Players." + p.getName() + ".stat.pads.speedpads", p.getInventory().getContents());
			Integer hidepads = (int)Main.pcfg.get("Players." + p.getName() + ".stat.pads.hidepads", p.getInventory().getContents());
			Integer healpads = (int)Main.pcfg.get("Players." + p.getName() + ".stat.pads.healpads", p.getInventory().getContents());
			switch(stat) {
			case "jumppads":
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.jumppads", value);
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.total", value+speedpads+hidepads+healpads);
				break;
			case "speedpads":
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.jumppads", value);
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.total", value+jumppads+hidepads+healpads);
				break;
			case "hidepads":
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.jumppads", value);
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.total", value+jumppads+speedpads+healpads);
				break;
			case "healpads":
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.jumppads", value);
				Main.pcfg.set("Players." + p.getName() + ".stat.pads.total", value+jumppads+speedpads+hidepads);
				break;
			default:
				Tools.DebugMessage(Tools.cfgM("Stats.Players.NotKnown", null));
				break;
			}
			break;
		case "perkit":
			//Statistics per Kit
			if(kit!=null) {
				for (Kit k : KitManager.kits.values()) {

					if (k.getName() == kit){
						switch(stat) {
						case "games":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".games", value);
							break;
						case "wins":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".wins", value);
							break;
						case "kills":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".kills", value);
							break;
						case "deaths":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".deaths", value);
							break;
						case "killspg":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".killspg", value);
							break;
						case "deathspg":
							Main.pcfg.set("Players." + p.getName() + ".stat.perkit" + kit + ".deathspg", value);
							break;
						default:
							Tools.DebugMessage(Tools.cfgM("Stats.Players.NotKnown", null));
						}
					} else {
						Tools.DebugMessage(Tools.cfgM("Stats.Players.NotKnown", null));
					}

				}
			}
			break;
		default:
			Tools.DebugMessage(Tools.cfgM("Stats.Players.NotKnown", null));
			break;
		}
	}

	public static Integer getStats(Player p, String cat, String stat) {
		Integer value = 0;
		value = (int)Main.pcfg.get("Players." + p.getName() + ".stat." + cat + stat , p.getInventory().getContents());
		return value;
	}

	public static void getGameState(Player p) {
		Main.pcfg.get("Players." + p.getName() + ".gamestate");
	}

	public static void setGameState(Player p, String state) {

	}
}
