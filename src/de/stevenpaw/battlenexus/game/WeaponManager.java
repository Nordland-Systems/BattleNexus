package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.stevenpaw.battlenexus.database.SQL_Weapons;
import de.stevenpaw.battlenexus.utils.Tools;

public class WeaponManager {
	public static HashMap<String, Weapon> weapons = new HashMap<String, Weapon>();

	//   string-NAME   |   itemstack-ITEMSTACK   |   string-DISPLAYNAME   |   string-LORE1   |   string-LORE2
	//   list<enchantment,integer>-ENCHANTMENT   |   int-AMOUNT   |   list<feature>-FEATURES   |   int-CUSTOMMODELDATA   |   auto-CREATION_DATE
	
	//    public List<Arena> arenas = new ArrayList<>();
	public static Weapon getWeapon(String name){
		return weapons.get(name);
	}

	public static Boolean AddWeapon(String s, Weapon w) {

		Boolean works = false;
		if(!weapons.containsKey(s)) {
			weapons.put(s, w);
			works = true;
		}
		
		return works;
	}
	
	public static Boolean RemoveWeapon(String name) {

		Boolean works = false;

		if(weapons.containsKey(name)) {
			SQL_Weapons.removeWeaponSQL(name);
			weapons.remove(name);
			if(SaveWeapons()) {
				works = true;
			}
		}

		return works;
	}

	public static Boolean SaveWeapons() {

		SQL_Weapons.saveWeapons();
		
		return true;
	}

	public static List<String> getAllWeaponNames() {
		List<String> weaponnames = new ArrayList<String>();
		if (weapons.size() == 0) {
			Tools.DebugMessage("No Arenas yet!");
		} else {
			for(Weapon w : weapons.values()){
				weaponnames.add(w.getName());
			}
		}

		return weaponnames;
	}
}