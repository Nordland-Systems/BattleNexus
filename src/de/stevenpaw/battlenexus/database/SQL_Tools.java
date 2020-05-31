package de.stevenpaw.battlenexus.database;

//import java.sql.ResultSet;
//import java.sql.SQLException;
//import org.bukkit.Bukkit;
//import java.sql.DriverManager;
import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.Tools;

import java.sql.Connection;
import me.vagdedes.mysql.database.*;

public class SQL_Tools {
    public static Connection con;
    public static String Host;
    public static String Port;
    public static String Database;
    public static String User;
    public static String Password;
    
    
    public static void connect() {
    	MySQL.connect();
    }
    
    public static void disconnect() {
        if (MySQL.isConnected()) {
            MySQL.disconnect();
            Tools.sendToConsole(String.valueOf(Main.prefix) + Tools.cfgM("MySQL.disconnected", null));
//			Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.prefix) + "§cDisconnected with MySQL!");
        }
    }
    
    public static void createTable() {
		SQL_Arenas.createArenaTable();
		SQL_Arenas.loadArenas();
		Tools.DebugMessage("Created/Loaded Database for Arenas");
		SQL_Locations.createLocationTable();
		SQL_Locations.loadLocations();
		Tools.DebugMessage("Created/Loaded Database for Locations");
		SQL_Weapons.createWeaponTable();
		SQL_Weapons.loadWeapons();
		Tools.DebugMessage("Created/Loaded Database for Weapons");
    }
}
