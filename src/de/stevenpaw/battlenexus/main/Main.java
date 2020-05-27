package de.stevenpaw.battlenexus.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.stevenpaw.battlenexus.database.SQL_Tools;
import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.listener.PlayerListener;
import de.stevenpaw.battlenexus.utils.Tools;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin{

	//== VARIABLES =================
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
	public static Boolean debug;

    private static Economy economy = null;
    private ArenaManager arenaManager;
    //------------------------------
    

	//== ENABLE PLUGIN =============
	@Override
	public void onEnable() {

		//MySQL.connect();
		//MySQL.createTable();
		
		//lade Config
		loadConfig();
		
		//plugin festlegen und economy laden
		setInstance(this);
		plugin = this;
		this.setupEconomy();
		
		//Listener laden
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		
		//runnable
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
		
		//Check for successful Economy Hook
		if(setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage(prefix + Tools.cfgM("Eco.ConSuccess"));
		} else {
			Tools.ConsoleErrorMessage(Tools.cfgM("Eco.ConFailure"), null);
			Bukkit.getPluginManager().disablePlugin(this);
		}	
		
		//setup Arena Manager
		arenaManager = new ArenaManager();
	}
	//------------------------------
	
	
	//== DISABLE PLUGIN ============
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(prefix + "§c§lPlugin disabled!");
		SQL_Tools.disconnect();
	    arenaManager = null;
	}
	//------------------------------
	
	
	//== CONFIGS ===================
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
		
		//loadDebug
		if(cfg.getBoolean("Debug")) {
			debug = true;
			Tools.DebugMessage("Debug-Mode is active! Don't use this if you have no Problems!");
		} else debug = false;
		
		Tools.DebugMessage("Loaded all Configs!");
		
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
				Tools.ConsoleErrorMessage("FATAL ERROR: LANGUAGEFILE NOT FOUND", e);
				Bukkit.getPluginManager().disablePlugin(this);
			}
		}
		Main.lang = YamlConfiguration.loadConfiguration(Main.langfile);
		
		Tools.DebugMessage(Tools.cfgM("Load.LanguageLoaded"));
		Tools.ConsoleNoticeMessage(Tools.cfgM("Load.AllLoaded"));
	}
    //------------------------------
	
	
	//== PLUGIN-INSTANZ ============
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
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
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

	
	//== ARENA MANAGER ============
    public ArenaManager getArenaManager() {
        return arenaManager;
    }
    //-----------------------------
}
