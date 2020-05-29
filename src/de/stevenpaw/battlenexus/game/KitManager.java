package de.stevenpaw.battlenexus.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.stevenpaw.battlenexus.utils.ItemBuilder;
import de.stevenpaw.battlenexus.utils.Tools;

public class KitManager {

	public static HashMap<String,Kit> kits = new HashMap<String,Kit>();
	
	public static void createKits() {
		Tools.DebugMessage("CreateKits 1/4");
		ItemStack is = new ItemBuilder(Material.STICK).setDisplayName("Teststab").build();
		Tools.DebugMessage("CreateKits 2/4");
		List<Weapon> wp = new ArrayList<Weapon>();
		wp.add(new Weapon("Stick", is, "", "", "", null, 3, null, null, null));
		Tools.DebugMessage("CreateKits 3/4 | is: " + wp.toString());
		Kit basic = new Kit("basic", 0, 0, wp, Kit.AccessState.BLOCKED);
		kits.put(basic.getName(), basic);
		Tools.DebugMessage("CreateKits 4/4");
	}
}
