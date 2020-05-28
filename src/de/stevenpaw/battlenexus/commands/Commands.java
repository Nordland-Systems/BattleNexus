package de.stevenpaw.battlenexus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.utils.Tools;

public class Commands implements CommandExecutor, TabCompleter{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			Tools.DebugMessage("Args length: " + args.length);
			//			Location pLoc = p.getLocation();
			//			int x = p.getLocation().getBlockX();
			//			int y = p.getLocation().getBlockY();
			//			int z = p.getLocation().getBlockZ();

			if (p.hasPermission("bn.*") || p.hasPermission("bn.admin.createArena")) {
				if(args.length == 0) {
					Tools.broadcastPlayer("This is the BattleNexus!", p);
					return true;
				} else if(args.length == 1) {
					Boolean works = false;
					String a = args[0];
					switch (a) {
					case "admin":
						Tools.broadcastPlayer("/bn admin <reload/save>", p);
						//STuff to do
						works = true;
						break;
					case "arena":
						Tools.broadcastPlayer("/bn arena <create/remove> <name>", p);
						//STuff to do
						works = true;
						break;
					case "modify":
						Tools.broadcastPlayer("/bn modify <arena> <maxPlayers/minPlayers/type/weapons>", p);
						//STuff to do
						works = true;
						break;
					case "testlang":
						Tools.broadcastPlayer(Tools.cfgM("Test",p), p);
						break;
					default:
						Tools.broadcastPlayer(Tools.cfgM("Commands.UnknownCommand", p), p);
						//STuff to do
						works = false;
					}
					return works;

				} else if(args.length == 2) {
					Boolean works = false;
					String a = args[0];
					String b = args[1];
					switch (a) {
					case "admin":
						switch(b) {
						case "reload":
							Tools.broadcastPlayer("Reloaded", p);
							works = true;
						case "save":
							Tools.broadcastPlayer("Saved", p);
							works = true;
						default: 
							Tools.broadcastPlayer("/bn admin <reload/save>", p);
							works = false;
						}
						break;
					case "arena":
						Tools.broadcastPlayer("/bn arena <create/remove> <name>", p);
						//STuff to do
						works = true;
						break;
					case "modify":
						Tools.broadcastPlayer("/bn modify <arena> <maxPlayers/minPlayers/type/weapons>", p);
						//STuff to do
						works = true;
						break;
					case "testlang":
						Tools.broadcastPlayer(Tools.cfgM(b,p), p);
						break;
					default:
						Tools.broadcastPlayer(Tools.cfgM("Commands.UnknownCommand", p), p);
						//STuff to do
						works = false;
					}
					Tools.broadcastPlayer("Args length 2", p);
					return works;
				} else if(args.length == 3) {
					Tools.broadcastPlayer("Args length 3", p);
				}
			}
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if(args.length == 1) {
			List<String> options = new ArrayList<>();
			options.add("admin");
			options.add("arena");
			options.add("modify");
			options.add("testlang");

			return options;
		} 
		else if (args.length == 2) 
		{
			List<String> options = new ArrayList<>();
			String arg0 = args[0];
			switch(arg0) {
			case "admin":
				options.add("reload");
				options.add("save");
				options.add("stopall");
				break;
			case "arena":
				options.add("create");
				options.add("remove");
				options.add("stop");
				options.add("start");
				break;
			case "modify":
				List<String> arenas = ArenaManager.getAllArenaNames();
				for (String s: arenas) {
					options.add(s);
				}
				break;
			}
			return options;
		}
		else if (args.length == 3) 
		{
			List<String> options = new ArrayList<>();
			String arg0 = args[0];
			String arg1 = args[1];
			switch(arg0) {
			case "arena":
				switch(arg1) {
				case "stop":
					List<String> arenas = ArenaManager.getAllArenaNames();
					for (String s: arenas) {
						options.add(s);
					}
					break;
				case "start":
					List<String> arenasb = ArenaManager.getAllArenaNames();
					for (String s: arenasb) {
						options.add(s);
					}
					break;
				case "remove":
					List<String> arenasc = ArenaManager.getAllArenaNames();
					for (String s: arenasc) {
						options.add(s);
					}
					break;
				}
				break;
			case "modify":
				options.add("maxPlayers");
				options.add("minPlayers");
				options.add("lobby");
				options.add("type");
				options.add("status");
				break;
			}
			return null;
		}
		return null;
	}
}
