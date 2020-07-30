package de.stevenpaw.battlenexus.utils;

import org.bukkit.Location;

public class BNSign {
    private int id;
    private String arena;
    private Type type;
    private Location loc;

    public BNSign(int id, String arena, Type type, Location loc) {
        this.arena = arena;
        this.id = id;
        this.type = type;
        this.loc = loc;
    }
    
    public BNSign() {
		//for empty use
	}

	public enum Type{
    	JOIN, LEADERBOARD, TELEPORT
    }
    
    
    public int getID() {
    	return id;
    }
    
    public void setID(int id) {
    	this.id = id;
    }
    
    
    public String getArena() {
    	return arena;
    }
    
    public void setArena(String arena) {
    	this.arena = arena;
    }
    
    
    public Type getType() {
    	return type;
    }
    
    public void setType(Type type) {
    	this.type = type;
    }
    
    
    public Location getLocation () {
    	return loc;
    }
    
    public void setLocation(Location loc) {
    	this.loc = loc;
    }
}
