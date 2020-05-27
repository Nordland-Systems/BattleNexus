package de.stevenpaw.battlenexus.game;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private String name;
    private int minPlayers;
    private int maxPlayers;
    private List<Player> players;
    private Location spawn;
    private GameState state;

    public Arena(String name, int minPlayers, int maxPlayers, Location spawn) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<Player>();
        this.spawn = spawn;
        this.state = GameState.STARTING;
    }

    enum GameState{
        INGAME, STARTING, FINISHED;
    }
    
    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }
}