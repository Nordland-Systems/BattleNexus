package de.stevenpaw.battlenexus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.stevenpaw.battlenexus.main.Main;
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
	
	//broadcast Serverwide
	public static void broadcastAll(String text) {
		Bukkit.broadcastMessage(Main.prefix + text);
	}
	
	//Get Message from Language
	public static String cfgM(String pointer) {
		String output = ChatColor.translateAlternateColorCodes('&', Main.lang.getString(pointer));
//		String output = Main.cfg.getString(pointer);
		return output;
	}
	
	//Send Error Message
	public static void ConsoleErrorMessage(String text, Exception e) {
		Integer Length = text.length();
		String s = "-";
		String Line = new String(new char[Length + 6]).replace("\0", s);
		
		Tools.sendToConsole("§4"+Line);
		if(e != null) {
			sendToConsole("§4|||" + text + "||| - §f" + e);
		} else {
			sendToConsole("§4|||" + text + "||| - §f" + cfgM("UnknownError"));
		}
		Tools.sendToConsole("§4" + Line);
	}
}
