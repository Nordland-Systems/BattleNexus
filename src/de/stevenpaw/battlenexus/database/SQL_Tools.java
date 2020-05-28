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
    
//    static {
//        MySQL.Host = Main.cfg.getString("MySQL.host");
//        MySQL.Port = Main.cfg.getString("MySQL.port");
//        MySQL.Database = Main.cfg.getString("MySQL.database");
//        MySQL.User = Main.cfg.getString("MySQL.user");
//        MySQL.Password = Main.cfg.getString("MySQL.password");
//    }
    
    public static void connect() {
    	MySQL.connect();
//        if (!isConnected()) {
//            try {
//                MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.Host + ":" + MySQL.Port + "/" + MySQL.Database, MySQL.User, MySQL.Password);
//                Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.prefix) + "§aConnected with MySQL!");
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
    }
    
    public static void disconnect() {
        if (MySQL.isConnected()) {
            MySQL.disconnect();
            Tools.sendToConsole(String.valueOf(Main.prefix) + Tools.cfgM("MySQL.disconnected", null));
//			Bukkit.getConsoleSender().sendMessage(String.valueOf(Main.prefix) + "§cDisconnected with MySQL!");
        }
    }
//    
//    public static boolean isConnected() {
//        return MySQL.con != null;
//    }
//    
//    public static void update(final String query) {
//        if (isConnected()) {
//            try {
//                MySQL.con.createStatement().executeUpdate(query);
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    
//    public static ResultSet getResult(final String query) {
//        try {
//            return MySQL.con.createStatement().executeQuery(query);
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    public static void createTable() {
        SQL_Player.createPlayerTable();
        SQL_Arenas.createArenaTable();
        //SQL_Weapons.createWeaponsTable();
    }
}
