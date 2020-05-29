package de.stevenpaw.battlenexus.main;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.stevenpaw.battlenexus.commands.Commands;
import de.stevenpaw.battlenexus.database.SQL_Arenas;
import de.stevenpaw.battlenexus.database.SQL_Tools;
import de.stevenpaw.battlenexus.game.ArenaManager;
import de.stevenpaw.battlenexus.game.KitManager;
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
	public static HashMap<String, File> langfile;
	public static HashMap<String, FileConfiguration> lang;


	public static String prefix;
	public static Boolean debug;

	private static Economy economy = null;
	private ArenaManager arenaManager;
	//------------------------------


	//== ENABLE PLUGIN =============
	@Override
	public void onEnable() {

		//lade Config
		loadConfig();

		//plugin festlegen und economy laden
		setInstance(this);
		plugin = this;
		this.setupEconomy();

		//Listener laden
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListener(), this);



		//Check for successful Economy Hook
		if(setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage(prefix + Tools.cfgM("Eco.ConSuccess", null));
		} else {
			Tools.ConsoleErrorMessage(Tools.cfgM("Eco.ConFailure", null), null);
			Bukkit.getPluginManager().disablePlugin(this);
		}	

		//setup Arena Manager
		arenaManager = new ArenaManager();

		//setup Commands
		this.getCommand("bn").setExecutor(new Commands());
		this.getCommand("bn").setTabCompleter(new Commands());
		
		KitManager.createKits();
		
		SQL_Arenas.createArenaTable();
		SQL_Arenas.loadArenas();
		Tools.DebugMessage("Created Database for Arenas");
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
		cfgfile = new File("plugins/BattleNexus", "config.yml");
		cfg = YamlConfiguration.loadConfiguration(Main.cfgfile);

		//loadplayerdata
		pfile = new File("plugins/BattleNexus", "players.yml");
		pcfg = YamlConfiguration.loadConfiguration(Main.cfgfile);

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

		langfile = new HashMap<String,File>();

		//addLanguages
		langfile.put("DE", new File("languages/BattleNexus/languages/", "DE.yml"));
		langfile.put("EN", new File("plugins/BattleNexus/languages/", "EN.yml"));

		
		for (File f : langfile.values()) {
			if(!f.exists()) {
				f.getParentFile().mkdir();
				try {
					saveResource("languages/" + f.getName(), false);
				} catch(Exception e) {
					Tools.ConsoleErrorMessage("FATAL ERROR: LANGUAGEFILE NOT FOUND", e);
					Bukkit.getPluginManager().disablePlugin(this);
				}
			}
		}

		langpath = new File("plugins/BattleNexus/languages/");
		if(!langpath.exists()) {
			langpath.mkdir();
		}

		lang = new HashMap<String,FileConfiguration>();
		File folder = langpath;
		File[] listOfFiles = folder.listFiles();

		for (File f : listOfFiles) {
			String name = f.getName();
			Tools.DebugMessage("Load Language: " + name);
			FileConfiguration langfileconfig = YamlConfiguration.loadConfiguration(f);
			lang.put(name, langfileconfig);
		}

		Tools.DebugMessage(Tools.cfgM("Load.LanguageLoaded", null));
		Tools.ConsoleNoticeMessage(Tools.cfgM("Load.AllLoaded", null));
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
