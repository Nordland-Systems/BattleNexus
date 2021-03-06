package de.stevenpaw.battlenexus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.main.Main;
import me.smessie.MultiLanguage.bukkit.AdvancedMultiLanguageAPI;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Tools {

	static Economy economy = Main.getEconomy();


	//add Money to Player
	public static void addMoney(double Amount, Player p) {
		economy.depositPlayer(p, Amount);
	}


	//take Money off Player
	public static void takeMoney(double Amount, Player p) {
		economy.depositPlayer(p, -Amount);
	}


	//send Message to Console
	public static void sendToConsole(String text) {
		ConsoleCommandSender console = Bukkit.getConsoleSender();
		console.sendMessage(Main.prefix + text);
	}


	//send Message to Console
	public static void DebugMessage(String text) {
		if (Main.debug) {
			ConsoleCommandSender console = Bukkit.getConsoleSender();
			console.sendMessage(Main.prefix + "�2[-DEBUG-] " + text);
		}
	}


	//broadcast Serverwide
	public static void broadcastAll(String text) {
		Bukkit.broadcastMessage(Main.prefix + text);
	}

	//broadcast To one player
	public static void broadcastPlayer(String text, Player p) {
		p.sendMessage(Main.prefix + text);
	}

	public static String cfgM(String pointer, Player p) {
		return cfgM(pointer, p, null, null);
	}
	
	public static String cfgM(String pointer, Player p, String a) {
		return cfgM(pointer, p, a, null);
	}

	//Get Message from Language
	public static String cfgM(String pointer, Player p, String a, String b) {
		String output = "ERROR";
		if (p != null) {
			String language = Main.cfg.getString("Basic.language") + ".yml";
			if(Bukkit.getPluginManager().isPluginEnabled("AdvancedMultiLanguage")) {
				language = AdvancedMultiLanguageAPI.getLanguageOfUuid(p.getUniqueId().toString()) + ".yml";
				DebugMessage("MultiLangAPI is used");
			}

			try {
				output = ChatColor.translateAlternateColorCodes('&', Main.lang.get(language).getString(pointer));
				//		String output = Main.cfg.getString(pointer);
			} catch (Exception e){
				DebugMessage("Couldnt find Message: " + pointer + " | " + e);
				output = " �4-- ERROR -- ";
			}
		} else {
			output = ChatColor.translateAlternateColorCodes('&', Main.lang.get(Main.cfg.getString("Basic.language") + ".yml").getString(pointer));
		}
		
		if(a != null) {
			output = output.replaceAll("%a%", a);
		}
		
		if(b != null) {
			output = output.replaceAll("%a%", b);
		}
		
		Date now = new Date();
		SimpleDateFormat dateform = new SimpleDateFormat("HH:mm:ss-SS");
			output = output.replaceAll("%Time%", dateform.format(now));
		
		return output;
	}


	//Send Error Message
	public static void ConsoleErrorMessage(String text, Exception e) {
		Integer Length = text.length();
		String s = "-";
		String Line = new String(new char[Length + 6]).replace("\0", s);

		sendToConsole("�4"+Line);
		if(e != null) {
			sendToConsole("�4|||" + text + "�4||| - �f" + e);
		} else {
			sendToConsole("�4|||" + text + "�4||| - �f" + cfgM("UnknownError", null));
		}
		sendToConsole("�4" + Line);
	}


	//Send Notice Message
	public static void ConsoleNoticeMessage(String text) {
		Integer Length = text.length();
		String s = "-";
		String Line = new String(new char[Length + 6]).replace("\0", s);

		sendToConsole("�e"+Line);
		sendToConsole("�e|||" + text + "||| - �e");
		sendToConsole("�e" + Line);
	}


	//Send Title to Player
	public static void pTitle(Player p, String Title, String subTitle, ChatColor c, int duration, int fade) {
		p.sendTitle(Title, subTitle, fade, duration, fade);
	}
}
