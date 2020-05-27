package de.stevenpaw.battlenexus.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.PlayerTools;
import de.stevenpaw.battlenexus.utils.Tools;

public class PlayerListener implements Listener{

	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {

		PlayerTools.SaveInventory(event.getPlayer());
		Tools.broadcastAll("Balance: " + Main.getEconomy().getBalance(event.getPlayer()));
    }
	
}
