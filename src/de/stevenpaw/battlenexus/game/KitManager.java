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
		ItemStack Stick = new ItemBuilder(Material.STICK).build();
		Tools.DebugMessage("CreateKits 2/4");
		List<ItemStack> is = new ArrayList<ItemStack>();
		is.add(Stick);
		Tools.DebugMessage("CreateKits 3/4 | is: " + is.toString());
		Kit basic = new Kit("basic", 0, 0, is, Kit.AccessState.BLOCKED);
		kits.put(basic.getName(), basic);
		Tools.DebugMessage("CreateKits 4/4");
	}
}
