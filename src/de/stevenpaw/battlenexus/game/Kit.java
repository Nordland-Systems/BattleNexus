package de.stevenpaw.battlenexus.game;

import java.util.List;
import org.bukkit.inventory.ItemStack;

public class Kit {

	
    private String name;
    private double speed;
    private double jumpheight;
    private List<ItemStack> items;
    private AccessState access;

    
    enum AccessState{
    	PUBLIC, VIP, ADMIN, BLOCKED;
    }
    public Kit(String name, double speed, double jumpheight, List<ItemStack> items, AccessState access) {
        this.name = name;
        this.speed = speed;
        this.jumpheight = jumpheight;
        this.items = items;
        this.access = access;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String newname) {
    	this.name = newname;
    }
    
    public double getSpeed() {
    	return speed;
    }
    public void setSpeed(double newspeed) {
    	this.speed = newspeed;
    }
    
    public double getJumpheight() {
    	return jumpheight;
    }
    
    public void setAccess(AccessState newaccess) {
    	this.access = newaccess;
    }
    
    public AccessState getAccess() {
    	return access;
    }
    
    public void setItems(List<ItemStack> newitems) {
    	items = newitems;
    }
    
    public void addItem(ItemStack newitem) {
    	items.add(newitem);
    }
    
    public List<ItemStack> getItems() {
    	return items;
    }
}
