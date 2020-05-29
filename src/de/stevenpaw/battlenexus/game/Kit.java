package de.stevenpaw.battlenexus.game;

import java.util.List;

public class Kit {

	
    private String name;
    private double speed;
    private double jumpheight;
    private List<Weapon> weapons;
    private AccessState access;
    private List<CanUse> canuse;

    
    enum AccessState{
    	PUBLIC, VIP, ADMIN, BLOCKED;
    }
    enum CanUse{
    	JUMPPAD, SPEEDPAD, HIDEPAD;
    }
    
    public Kit(String name, double speed, double jumpheight, List<Weapon> weapons, AccessState access) {
        this.name = name;
        this.speed = speed;
        this.jumpheight = jumpheight;
        this.weapons = weapons;
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
    
    public void setWeapons(List<Weapon> newitems) {
    	weapons = newitems;
    }
    
    public void addWeapon(Weapon newweapon) {
    	weapons.add(newweapon);
    }
    
    public List<Weapon> getWeapons() {
    	return weapons;
    }

	public List<CanUse> getCanUse() {
		return canuse;
	}

	public void setCanUse(List<CanUse> canuse) {
		this.canuse = canuse;
	}
}
