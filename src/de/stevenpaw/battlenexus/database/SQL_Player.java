package de.stevenpaw.battlenexus.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import me.vagdedes.mysql.database.MySQL;

public class SQL_Player
{
	public static void createPlayerTable() {
		if (MySQL.isConnected()) {
			MySQL.update("CREATE TABLE IF NOT EXISTS Player (Playername VARCHAR(50), AFK TINYINT(1), Builder TINYINT(1), Vanish TINYINT(1), Resourcepack TINYINT(1), CurrentInventory VARCHAR(50))");
		}
	}
	
	public static boolean playerExists(final String Playername) {
		try {
			final ResultSet res = MySQL.query("SELECT * FROM Player WHERE Playername= '" + Playername + "'");
			return res.next() && res.getString("Playername") != null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void createPlayer(final String Playername) {
		if (!playerExists(Playername)) {
			MySQL.update("INSERT INTO Player (Playername, AFK, Builder, Vanish, Resourcepack, CurrentInventory) VALUES ('" + Playername + "','0','0','0','0','null')");
		}
	}

	public static boolean getBoolean(final String Playername, final String var) {
		boolean bool = false;
		if (playerExists(Playername)) {
			try {
				final ResultSet res = MySQL.query("SELECT * FROM Player WHERE Playername='" + Playername + "'");
				if (res.next()) {
					bool = res.getBoolean(var);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return bool;
		}
		createPlayer(Playername);
		return getBoolean(Playername, var);
	}

	public static void setBoolean(final String Playername, final String var, final boolean value) {
		if (playerExists(Playername)) {
			int i = 0;
			if (value) {
				i = 1;
			}
			MySQL.update("UPDATE Player SET " + var + "= '" + i + "' WHERE Playername= '" + Playername + "';");
		}
		else {
			createPlayer(Playername);
			setBoolean(Playername, var, value);
		}
	}

	public static String getString(final String Playername, final String var) {
		if (playerExists(Playername)) {
			final ResultSet rs = MySQL.query("SELECT * FROM Player WHERE Playername='" + Playername + "'");
			try {
				if (rs.next()) {
					return rs.getString(var);
				}
				return "";
			}
			catch (SQLException e) {
				e.printStackTrace();
				return "";
			}
		}
		createPlayer(Playername);
		return getString(Playername, var);
	}

	public static String setString(final String Playername, final String var, final String string) {
		if (playerExists(Playername)) {
			MySQL.update("UPDATE Player SET " + var + "= '" + string + "' WHERE Playername= '" + Playername + "';");
		}
		else {
			createPlayer(Playername);
			setString(Playername, var, string);
		}
		return "";
	}
}