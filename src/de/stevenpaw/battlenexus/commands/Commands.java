package de.stevenpaw.battlenexus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.Tools;

public class Commands implements CommandExecutor, TabCompleter{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Boolean works = false;

		if (sender instanceof Player) {
			Player p = (Player) sender;
			Tools.DebugMessage("Args length: " + args.length);
			//			Location pLoc = p.getLocation();
			//			int x = p.getLocation().getBlockX();
			//			int y = p.getLocation().getBlockY();
			//			int z = p.getLocation().getBlockZ();


			//bn
			if(args.length == 0) {
				Tools.broadcastPlayer("This is the BattleNexus!", p);
				return true;
			} 


			//bn <a>
			else if(args.length == 1) {
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
				case "testcountdown":
					//runnable
					Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), new Runnable()
					{
						int time = 20; //or any other number you want to start countdown from

						@Override
						public void run()
						{
							if (this.time == 0)
							{
								return;
							}

							for (final Player player : Bukkit.getOnlinePlayers())
							{
								player.sendMessage(this.time + " second(s) remains!");
							}

							this.time--;
						}
					}, 0L, 20L);
					break;
				default:
					Tools.broadcastPlayer(Tools.cfgM("Commands.UnknownCommand", p), p);
					//STuff to do
					works = false;
				}
			} 


			//bn <a> <b>
			else if(args.length == 2) {
				String a = args[0];
				String b = args[1];
				switch (a) {
				case "admin":
					switch(b) {
					case "reload":
						Tools.broadcastPlayer(Tools.cfgM("Commands.Admin.Reloaded", p), p);
						works = true;
						break;
					case "save":
						Tools.broadcastPlayer(Tools.cfgM("Commands.Admin.Saved", p), p);
						works = true;
						break;
					default: 
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn admin <reload/save>", p);
						works = false;
					}
					break;
				case "arena":
					switch (b) {
					case "create":
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn create <name> (<minPlayers> <maxPlayers>)", p);
						break;
					case "remove":
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn remove <name>", p);
						break;
					case "start":
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn remove <name>", p);
						break;
					default:
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn arena <create/remove/start> <name> (...)", p);
					}
					works = true;
					break;
				case "modify":
					Tools.broadcastPlayer("/bn modify <arena> <maxPlayers/minPlayers/type/weapons>", p);
					//STuff to do						
					works = true;
					break;
				case "testlang":
					Tools.broadcastPlayer(Tools.cfgM(b,p), p);
					works = true;
					break;
				default:
					Tools.broadcastPlayer(Tools.cfgM("Commands.UnknownCommand", p), p);
					//STuff to do
					works = false;
				}
				return works;
			} 


			//bn <0> <1> <2>
			else if(args.length == 3) {
				String a = args[0];
				String b = args[1];
				switch (a) {		
				case "arena":
					switch (b) {
					case "create":
						Tools.DebugMessage("Try to create Arena" + args[2]);
						if (ArenaManager.CreateArena(args[2], 2, 4, p)) {
							Tools.broadcastPlayer(Tools.cfgM("Commands.Arena.ArenaCreated", p, args[2]), p);
							works = true;
						} else {
							Tools.broadcastPlayer(Tools.cfgM("Commands.Arena.ArenaCreationFailed", p), p);
						}
						break;
					case "remove":
						if(ArenaManager.RemoveArena(args[2])) {
							Tools.broadcastPlayer(Tools.cfgM("Commands.Arena.ArenaRemoved", p), p);
							works = true;
						} else {
							Tools.broadcastPlayer(Tools.cfgM("Commands.Arena.ArenaRemoveFailed", p), p);
						}
						break;
					case "start":
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn remove <name>", p);
						break;
					default:
						Tools.broadcastPlayer(Tools.cfgM("Usage", p) + "/bn arena <create/remove/start> <name> (...)", p);
					}
					break;

				}
			}
		}

		return works;
	}






	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if(args.length == 1) {
			List<String> options = new ArrayList<>();
			options.add("admin");
			options.add("arena");
			options.add("modify");
			options.add("testlang");
			options.add("testcountdown");

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
