package de.stevenpaw.battlenexus.game;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.stevenpaw.battlenexus.utils.ItemBuilder;

public class Weapon {

	//   string-NAME   |   itemstack-ITEMSTACK   |   string-DISPLAYNAME   |   string-LORE1   |   string-LORE2
	//   list<enchantment,integer>-ENCHANTMENT   |   int-AMOUNT   |   list<feature>-FEATURES   |   int-CUSTOMMODELDATA   |   auto-CREATION_DATE
	
    private String name;
    private ItemStack is;
    private String displayname;
    private String lore1;
    private String lore2;
    private Map<Enchantment,Integer> enchantment;
    private Integer amount;
    private Integer custommodeldata;
    private List<Feature> features;
    private Boolean enabled;
    
    public enum Feature {
    	FIRECANNON, JUMPBOOST, HIGHLIGHT;
    }

    public Weapon(String name, ItemStack is, String displayname, String lore1, String lore2, Map<Enchantment, Integer> enchantment, Integer amount, Integer custommodeldata, List<Feature> features, Boolean enabled) {
        this.name = name;
        this.is = is;
        this.displayname = displayname;
        this.lore1 = lore1;
        this.lore2 = lore2;
        this.enchantment = enchantment;
        this.amount = amount;
        this.features = features;
        this.enabled = enabled;
        this.custommodeldata = custommodeldata;
    }
 
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    //ITEMSTACK
    public ItemStack getItemStack() {
    	Material m = is.getType();
    	ItemStack isreturn = new ItemBuilder(m).setDisplayName(displayname).setCustomModelData(custommodeldata).build();
        return isreturn;
    }
    
    public ItemMeta getItemMeta() {
        return is.getItemMeta();
    }
    
    public Material getMaterial() {
        return is.getType();
    }
    
    public Integer getCustomModelData() {
    	return is.getItemMeta().getCustomModelData();
    }
    
    public void setIS(ItemStack is) {
    	this.is = is;
    }
    
    //DISPLAYNAME
    public String getDisplayName() {
        return displayname;
    }
    
    public void setDisplayName(String displayname) {
    	this.displayname = displayname;
    }
    
    //LORE 1
    public String getLore1() {
        return lore1;
    }
    
    public void setLore1(String lore1) {
    	this.lore1 = lore1;
    }
    
    //LORE 2
    public String getLore2() {
        return lore2;
    }
    
    public void setLore2(String lore2) {
    	this.lore2 = lore2;
    }
    
    //ENABLED
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
    	this.enabled = enabled;
    }
    
    //ENCHANTMENTS
    public Map<Enchantment,Integer> getEnchantment() {
        return enchantment;
    }
    
    public void setEnchantment(Map<Enchantment,Integer> enchantment) {
    	this.enchantment = enchantment;
    }
    
    //AMOUNT
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
    	this.amount = amount;
    }
    
    //FEATURES
    public List<Feature> getFeatures() {
        return features;
    }
    
    public void setFeatures(List<Feature> features) {
    	this.features = features;
    }
}