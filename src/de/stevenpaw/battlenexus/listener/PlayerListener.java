package de.stevenpaw.battlenexus.listener;

import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		//Join-Sign
		if(e.getClickedBlock().getBlockData() instanceof Sign || e.getClickedBlock().getBlockData() instanceof WallSign){

			Sign si = ((Sign) e.getClickedBlock().getState());//from 0 to 3 (if im not wrong)
			String str;
			str = si.getLine(0);
			if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 1:"+str);
			str = si.getLine(1);
			if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 2:"+str);
			str = si.getLine(2);
			if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 3:"+str);
			str = si.getLine(3);
			if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 4:"+str);

		}
	}


}
