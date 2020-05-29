package de.stevenpaw.battlenexus.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.stevenpaw.battlenexus.game.WeaponManager;
import de.stevenpaw.battlenexus.utils.ItemBuilder;
import de.stevenpaw.battlenexus.utils.Tools;
import de.stevenpaw.battlenexus.game.Weapon;
import de.stevenpaw.battlenexus.game.Weapon.Feature;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;

public class SQL_Weapons {
	static String WeaponsTable = "weapons";
	//ArenaTable:
	//   string-NAME   |   itemstack-ITEMSTACK   |   string-DISPLAYNAME   |   string-LORE1   |   string-LORE2
	//   list<enchantment,integer>-ENCHANTMENT   |   int-AMOUNT   |   list<feature>-FEATURES   |   int-CUSTOMMODELDATA   |   auto-CREATION_DATE

	public static void createWeaponTable() {
		if (MySQL.isConnected()) {
			if (!SQL.tableExists(WeaponsTable)){
				SQL.createTable(WeaponsTable, "name VARCHAR(255) PRIMARY KEY, itemstack VARCHAR(255), displayname VARCHAR(255), "
						+ "lore1 VARCHAR(255), lore2 VARCHAR(255), enchantments VARCHAR(255), amount TINYINT, features VARCHAR(255), "
						+ "custommodeldata SMALLINT, creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP");
			}
		}
	}

	public static void saveWeapons() {
		for(Weapon w : WeaponManager.weapons.values()) {
			MySQL.update(new String ("INSERT INTO " + WeaponsTable +" (name, itemstack, displayname, lore1, lore2, enchantments, amount, features, custommodeldata)"
					+ " VALUES ('" + w.getName() + "', '" + w.getItemStack() + "', '" + w.getDisplayName() + "', '" + w.getLore1() + "',"
					+ " '" + w.getLore2() + "', '" + w.getEnchantment().toString() + "', '" + w.getAmount() + "', '" + w.getFeatures().toString() +  "',"
					+ " '" + w.getCustomModelData() + "')"));
		}
	}

	public static void loadWeapons() {

		Tools.DebugMessage("Loading Weapons");

		WeaponManager.weapons.clear();

		ResultSet rs = MySQL.query("SELECT * FROM "+ WeaponsTable);



		String name = "error";
		ItemStack is = new ItemBuilder(Material.BARRIER).build();
		String displayname = "error";
		String lore1 = "";
		String lore2 = "";
		Map<Enchantment,Integer> enchantment = new HashMap<Enchantment,Integer>();
		Integer amount = 1;
		Integer custommodeldata = 0;
		List<Feature> features = new ArrayList<Feature>();
		Boolean enabled = false;

		try {
			while(rs.next()) {
				name = rs.getString("name");
				//			    is = rs.getString("itemstack");
				displayname = rs.getString("displayname");
				lore1 = rs.getString("lore1");
				lore2 = rs.getString("lore2");
				//			    enchantment = rs.getString("enchantments");
				amount = rs.getInt("amount");
				custommodeldata = rs.getInt("custommodeldata");
				//			    features = rs.getString("features");
				enabled = rs.getBoolean("enabled");
				
				WeaponManager.weapons.put(name, new Weapon(name, is, displayname, lore1, lore2, enchantment, amount, custommodeldata, features, enabled));
			}
		} catch (SQLException e) {
			Tools.DebugMessage("Couldnt set values for Weapons | &f" + e.toString());
		}
		Tools.DebugMessage("Weapons loaded: " + WeaponManager.weapons.toString());
	}



	public static void removeWeaponSQL(String name) {
		MySQL.update(new String("DELETE FROM " + WeaponsTable + " WHERE name = '" + name + "'"));
	}

	public static boolean weaponExists(final String weaponname) {
		try {
			final ResultSet res = MySQL.query("SELECT * FROM " + WeaponsTable + "  WHERE name= '" + weaponname + "'");
			return res.next() && res.getString("name") != null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}