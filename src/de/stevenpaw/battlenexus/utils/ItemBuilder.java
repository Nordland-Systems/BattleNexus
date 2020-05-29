package de.stevenpaw.battlenexus.utils;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.inventory.meta.Damageable;

public class ItemBuilder {

	//== BESCHREIBUNG =============
	// Erstellt Items Schritt für Schritt,
	// um sie irgendwo im Plugin verwenden
	// zu können.
	//-----------------------------
	
	
	//== VARIABLEN ================
	private ItemStack item;
	private ItemMeta itemMeta;
	//-----------------------------
	

	//== BUILD A ITEM =============
	public ItemBuilder(Material material) {
		item = new ItemStack(material);
		itemMeta = item.getItemMeta();
	}

	public ItemBuilder setDamage(int damage) {
		((Damageable) itemMeta).setDamage(damage);
		return this;
	}

	public ItemBuilder setDisplayName(String name) {
		itemMeta.setDisplayName(name);
		return this;
	}
	
	public ItemBuilder setLore(String... lore) {
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}
	
	public ItemBuilder addEnchant(Enchantment ench, Integer level) {
		itemMeta.addEnchant(ench, level, true);
		return this;
	}
	
	public ItemBuilder setUnbreakable(Boolean unbreak) {
		itemMeta.setUnbreakable(unbreak);
		return this;
	}
	
	public ItemBuilder setCustomModelData(Integer data) {
		itemMeta.setCustomModelData(data);
		return this;
	}
	
	public ItemStack build() {
		item.setItemMeta(itemMeta);
		return item;
	}
	//-----------------------------

}
