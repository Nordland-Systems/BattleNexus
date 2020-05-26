package de.stevenpaw.battlenexus.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.stevenpaw.battlenexus.database.SQL_Tools;
import de.stevenpaw.battlenexus.utils.Tools;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{

	private static Main plugin;
	private static Main instance;
	
	public static File cfgfile;
	public static FileConfiguration cfg;
	
	public static File pfile;
	public static FileConfiguration pcfg;

	public static File langpath;
	public static File langfile;
	public static FileConfiguration lang;
	

	public static String prefix;

	public static Economy economy = null;


	// When plugin is first enabled
	@Override
	public void onEnable() {

		//MySQL.connect();
		//MySQL.createTable();
		
		loadConfig();
		
		setInstance(this);
		plugin = this;
		this.setupEconomy();

		if(setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage(prefix + Tools.cfgM("Eco.ConSuccess"));
		} else {
			Tools.ConsoleErrorMessage(Tools.cfgM("Eco.ConFailure"), null);
		}	
		
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable()
		{
			int time = 3; //or any other number you want to start countdown from

			@Override
			public void run()
			{
				if (this.time == 0)
				{
					return;
				}

				for (final org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
				{
					player.sendMessage(this.time + " second(s) remains!");
				}

				this.time--;
			}
		}, 0L, 20L);

	}
	
	
	public void loadConfig() {
		//loadConfig
		saveDefaultConfig();
		Main.cfgfile = new File("plugins/BattleNexus", "config.yml");
		Main.cfg = YamlConfiguration.loadConfiguration(Main.cfgfile);
		
		//loadplayerdata
		Main.pfile = new File("plugins/BattleNexus", "players.yml");
		Main.pcfg = YamlConfiguration.loadConfiguration(Main.cfgfile);

		//loadPrefix
		String prefixRaw = cfg.getString("Basic.prefix");
		prefix = ChatColor.translateAlternateColorCodes('&', prefixRaw);
		
		Tools.sendToConsole(Tools.cfgM("ConfigsLoaded"));
		
		//loadLanguages
		Main.langfile = new File("plugins/BattleNexus/languages/", cfg.getString("Basic.language") + ".yml");
		
		langpath = new File("plugins/BattleNexus/languages/");
		if(!langpath.exists()) {
			langpath.mkdir();
		}
		if(!langfile.exists()) {
			langfile.getParentFile().mkdir();
			try {
			saveResource("languages/" + cfg.getString("Basic.language") + ".yml", true);
			} catch(Exception e) {
				Tools.ConsoleErrorMessage("LANGUAGEFILE NOT FOUND", e);
			}
		}
		Main.lang = YamlConfiguration.loadConfiguration(Main.langfile);
		
		Tools.sendToConsole(Tools.cfgM("LanguageLoaded"));
	}


	// When plugin is disabled
	@Override
	public void onDisable() {
		Tools.sendToConsole(Tools.cfgM("Plugin.disabled"));
		Bukkit.getConsoleSender().sendMessage(prefix + "§c§ldisabled!");
		SQL_Tools.disconnect();
	}




	//== PLUGIN-INSTANZ=============
	public static Main getInstance() {
		return instance;
	}	

	public static void setInstance(Main instance) {
		Main.instance = instance;
	}

	public static Main getPlugin() {
		return plugin;
	}
	//-----------------------------



	//== ECONOMY ==================
	private boolean setupEconomy(){

		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}

	public static Economy getEconomy() {
		return economy;
	}
	//-----------------------------

}
