package de.stevenpaw.battlenexus.database;

import me.vagdedes.mysql.database.MySQL;

public class SQL_Weapons {
	public static void createWeaponsTable() {
		if (MySQL.isConnected()) {
			MySQL.update("CREATE TABLE IF NOT EXISTS Player (Playername VARCHAR(50), AFK TINYINT(1), Builder TINYINT(1), Vanish TINYINT(1), Resourcepack TINYINT(1), CurrentInventory VARCHAR(50))");
		}
	}
}
