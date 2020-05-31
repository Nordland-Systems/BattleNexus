package de.stevenpaw.battlenexus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import de.stevenpaw.battlenexus.utils.PlayerTools;
import de.stevenpaw.battlenexus.utils.Tools;

public class CMD_Test {

	// /bn test <inventorysave/inventoryload/language> (<pointer>)
	//Args: 0                         1                     2
	//Count:1                         2                     3

	public static Boolean CMDgroup_Test (CommandSender sender, Command cmd, String label, String[] args) {
		Boolean works = false;

		Tools.DebugMessage("Performing Test command...");
		
		if (sender instanceof Player) {

			if (args.length == 2) {
				Tools.DebugMessage("Length of Test Command: 2");
				String b = args[1];
				switch(b) {
				case "inventorysave":
					Tools.DebugMessage("Saving Inventory...");
					works = CMD_InventorySave(Bukkit.getPlayer(sender.getName()));
					Tools.DebugMessage("§2Saved Inventory of §f" + sender.getName() + "§2!");
					break;
				case "inventoryload":
					Tools.DebugMessage("Loading Inventory...");
					works = CMD_InventoryLoad(Bukkit.getPlayer(sender.getName()));
					Tools.DebugMessage("§2Loaded Inventory of §f" + sender.getName() + "§2!");
					break;
				default:
					works = false;
				}
			}
			
			
			else if(args.length == 3) {
				Tools.DebugMessage("Length of Test Command: 3");
				String b = args[1];
				String c = args[2];
				switch(b) {
				case "inventorysave":
					works = CMD_InventorySave(Bukkit.getPlayer(sender.getName()));
					break;
				case "inventoryload":
					works = CMD_InventoryLoad(Bukkit.getPlayer(sender.getName()));
					break;
				case "inventorylanguage":
					works = CMD_Language(Bukkit.getPlayer(sender.getName()),c);
					break;
				default:
					works = false;
				}
			}
		}
		return works;
	}

	public static Boolean CMD_InventorySave(Player p) {
		PlayerTools.SaveInventory(p);
		return true;
	}

	public static Boolean CMD_InventoryLoad(Player p) {
		PlayerTools.LoadInventory(p);
		return true;
	}

	public static Boolean CMD_Language(Player p, String Pointer) {
		Boolean works = false;

		Tools.broadcastPlayer(Tools.cfgM(Pointer,p), p);

		return works;
	}
	
	
	
	//Tab Completer
	public static List<String> TabComplete (String[] args){
		List<String> options = new ArrayList<String>();
		
		switch(args.length) {
		case 2:
			options.add("inventorysave");
			options.add("inventoryload");
			options.add("language");
			break;
		default:
			break;
		}
		
		return options;
	}
}
