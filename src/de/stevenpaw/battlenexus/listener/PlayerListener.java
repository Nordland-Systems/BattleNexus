package de.stevenpaw.battlenexus.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.stevenpaw.battlenexus.main.Main;
import de.stevenpaw.battlenexus.utils.PlayerTools;
import de.stevenpaw.battlenexus.utils.SignTools;
import de.stevenpaw.battlenexus.utils.Tools;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{

		PlayerTools.SaveInventory(event.getPlayer());
		Tools.broadcastAll("Balance: " + Main.getEconomy().getBalance(event.getPlayer()));


	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {

		Player p = Bukkit.getPlayer(e.getPlayer().getUniqueId());

		//Join-Sign
		if(e.getClickedBlock().getBlockData() instanceof Sign || e.getClickedBlock().getBlockData() instanceof WallSign){

			Tools.DebugMessage("Schild angeklickt");

			Sign si = ((Sign) e.getClickedBlock().getState());//from 0 to 3 (if im not wrong)
			String str;
			str = si.getLine(0);


			if(str.equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',Main.cfg.getString("Basic.prefix")))){

				Tools.DebugMessage("Schild hat BN Prefix");

				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {

					Tools.DebugMessage("Schild mit Links angeklickt");

					if (p.isOp() || p.hasPermission("*") || p.hasPermission("battlenexus.*") || 
							p.hasPermission("battlenexus.signs.*")|| p.hasPermission("battlenexus.signs.break")) {

							Tools.DebugMessage("Sign Breaked by Admin -> " + si.getLocation());
							SignTools.OnSignBreak(e);
							
					} else {
						str = si.getLine(0);
						if(str != null && !str.isEmpty()) {
							Tools.DebugMessage("UH! DONT TOUCH ME THERE! -> " + si.getLocation());
						}
						str = si.getLine(1);
						if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 2:"+str);
						str = si.getLine(2);
						if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 3:"+str);
						str = si.getLine(3);
						if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 4:"+str);

						e.setCancelled(true);
					}
				}else {

					Tools.DebugMessage("UH! DONT TOUCH ME THERE! -> " + si.getLocation());
				}
				str = si.getLine(1);
				if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 2:"+str);
				str = si.getLine(2);
				if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 3:"+str);
				str = si.getLine(3);
				if(str != null && !str.isEmpty()) e.getPlayer().sendMessage("line 4:"+str);

			}

		}
	}
}
