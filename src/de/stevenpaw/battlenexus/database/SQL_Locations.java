package de.stevenpaw.battlenexus.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import de.stevenpaw.battlenexus.utils.Tools;
import de.stevenpaw.battlenexus.game.LocationManager;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;

public class SQL_Locations {
	static String LocationsTable = "bn_locations";
	//ArenaTable:
	//   int-AUTO_INCREMENT   |   string-ARENANAME   |   int-X   |   int-Y   |   int-Z   |   world   |   float-YAW   |   float-PITCH

	public static void createLocationTable() {
		if (MySQL.isConnected()) {
			if (!SQL.tableExists(LocationsTable)){
				SQL.createTable(LocationsTable, "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, arenaname VARCHAR(50), "
						+ "x INT, y INT, z INT, pitch DOUBLE, yaw DOUBLE, world VARCHAR(50)");
			}
		}
	}

	public static void saveLocations() {
		for(int l = 0; l< LocationManager.locations.size(); l++) {
			MySQL.update(new String ("INSERT INTO " + LocationsTable +" (name, renaname, x, y, z, world, yaw, pitch)"
					+ " VALUES ('" + LocationManager.arenanames.get(l) + "' , '" + LocationManager.locations.get(l).getX() + "' , '"
							+ "" + LocationManager.locations.get(l).getY() + "' , '" + LocationManager.locations.get(l).getZ() + "' "
									+ ", '" + LocationManager.locations.get(l).getWorld() + "' , '" + LocationManager.locations.get(l).getYaw() + "' ,"
											+ " '" + LocationManager.locations.get(l).getPitch() + "')"));
		}
	}

	public static void loadLocations() {

		Tools.DebugMessage("Loading Locations");

		LocationManager.locations.clear();

		ResultSet rs = MySQL.query("SELECT * FROM "+ LocationsTable);

		try {
			while(rs.next()) {
				LocationManager.arenanames.add(rs.getString("name"));
				LocationManager.locations.add(new Location(Bukkit.getWorld(rs.getString("name")),rs.getDouble("x"),rs.getDouble("y"),rs.getDouble("z"
						+ ""), rs.getFloat("yaw"),rs.getFloat("pitch")));
			}
		} catch (SQLException e) {
			Tools.DebugMessage("Couldnt set values for Locations | &f" + e.toString());
		}
		Tools.DebugMessage("Locations loaded: " + LocationManager.locations.toString());
	}



	public static void removeLocationSQL(String name) {
		MySQL.update(new String("DELETE FROM " + LocationsTable + " WHERE name = '" + name + "'"));
	}

	public static boolean locationExists(final String weaponname) {
		try {
			final ResultSet res = MySQL.query("SELECT * FROM " + LocationsTable + "  WHERE name= '" + weaponname + "'");
			return res.next() && res.getString("name") != null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}