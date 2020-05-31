package de.stevenpaw.battlenexus.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import de.stevenpaw.battlenexus.game.Arena;
import de.stevenpaw.battlenexus.game.Arena.GameState;
import de.stevenpaw.battlenexus.utils.Tools;
import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.game.Kit;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;

public class SQL_Arenas {

	static String ArenaTable = "bn_arenas";
	//ArenaTable:
	//   NAME   |   MINPLAYERS   |   MAXPLAYERS   |   STATE   |   KITS   |   CREATION_DATE

	public static void createArenaTable() {
		if (MySQL.isConnected()) {
			if (!SQL.tableExists(ArenaTable)){
				SQL.createTable(ArenaTable, "name VARCHAR(255) PRIMARY KEY, minplayers INT (11), maxplayers INT (11), state VARCHAR(255), kit VARCHAR(255), creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
			}
		}
	}

	public static void saveArenas() {


		for(Arena a : ArenaManager.arenas.values()) {
			String Kits;
			StringBuilder SB = new StringBuilder();
			if (!a.getKits().equals(null)) {
				for(int i = 0; i < a.getKitsName().size(); i++) {
					SB.append(a.getKitsName().get(i)).append("|");					
				}
			}else {
				SB = null;
			}
			Kits = SB.toString();
			MySQL.update(new String ("INSERT INTO " + ArenaTable +" (name, minplayers, maxplayers, state, kit)"
					+ " VALUES ('" + a.getName() + "', '" + a.getMinPlayers() + "', '" + a.getMaxPlayers() + "', '" + a.getState() + "', '" + Kits + "')"));
		}
	}

	public static void loadArenas() {
		
		Tools.DebugMessage("Loading Arenas");

		ArenaManager.arenas.clear();

		ResultSet rs = MySQL.query("SELECT * FROM "+ArenaTable);

		try {
			while(rs.next()) {
				String name = "";
				Integer minPlayers = 0;
				Integer maxPlayers = 0;
				Arena.GameState gamestate = GameState.FINISHED;
				List<Kit> kits = new ArrayList<Kit>();

				name = rs.getString("name");
				minPlayers = rs.getInt("minplayers");
				maxPlayers = rs.getInt("maxplayers");
				String state = rs.getString("state");
				switch(state) {
				case "INGAME":
					gamestate = Arena.GameState.INGAME;
				case "STARTING":
					gamestate = Arena.GameState.STARTING;
				case "FINISHED":
					gamestate = Arena.GameState.FINISHED;
				case "DISABLED":
					gamestate = Arena.GameState.DISABLED;
				case "LOBBY":
					gamestate = Arena.GameState.LOBBY;


					ArenaManager.arenas.put(name, new Arena(name, minPlayers, maxPlayers, null, kits, gamestate));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Tools.DebugMessage("Arenas loaded: " + ArenaManager.arenas.toString());




		//		for(Object o : arenalist) {
		//			String os = o.toString();
		//			Tools.DebugMessage("Name: " + os);
		//			String name = "";
		//			Integer minPlayers = 0;
		//			Integer maxPlayers = 0;
		//			Arena.GameState gamestate = GameState.FINISHED;
		//			List<Kit> kits = new ArrayList<Kit>();
		//			ResultSet rs = MySQL.query("SELECT * FROM "+ArenaTable+" WHERE name='"+os+"'");
		//			try {
		//				name = rs.getString("name");
		//				minPlayers = rs.getInt("minplayers");
		//				maxPlayers = rs.getInt("maxplayers");
		//				String state = rs.getString("state");
		//				switch(state) {
		//				case "INGAME":
		//					gamestate = Arena.GameState.INGAME;
		//				case "STARTING":
		//					gamestate = Arena.GameState.STARTING;
		//				case "FINISHED":
		//					gamestate = Arena.GameState.FINISHED;
		//				case "DISABLED":
		//					gamestate = Arena.GameState.DISABLED;
		//				case "LOBBY":
		//					gamestate = Arena.GameState.LOBBY;
		//				}
		//			} catch (SQLException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			
		//			try {
		//				List<String> kitlist = new ArrayList<>(Arrays.asList(rs.getString("kit").split("|")));
		//				for (String s : kitlist) {
		//					if (KitManager.kits.containsKey(s)) {
		//						kits.add(KitManager.kits.get(s));
		//					}
		//				}
		//			} catch (SQLException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			ArenaManager.arenas.put(name, new Arena(name, minPlayers, maxPlayers, null, kits, gamestate));
		//		}

	}

	public static void removeArenaSQL(String name) {
		MySQL.update(new String("DELETE FROM " + ArenaTable + " WHERE name = '" + name + "'"));
	}

	public static boolean arenaExists(final String arenaname) {
		try {
			final ResultSet res = MySQL.query("SELECT * FROM " + ArenaTable + "  WHERE Playername= '" + arenaname + "'");
			return res.next() && res.getString("Playername") != null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
