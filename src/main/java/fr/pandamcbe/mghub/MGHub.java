package fr.pandamcbe.mghub;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.pandamcbe.mgapi.serverutilapi.scoreboards.ScoreboardManager;
import fr.pandamcbe.mghub.boards.leaderboard.Leaderboards;
import fr.pandamcbe.mghub.commands.Commands;
import fr.pandamcbe.mghub.commands.StatCommand;
import fr.pandamcbe.mghub.duelcommand.DuelCommand;
import fr.pandamcbe.mghub.duelcommand.DuelDatasManager;
import fr.pandamcbe.mghub.duelcommand.DuelMenu;
import fr.pandamcbe.mghub.duelcommand.Event;
import fr.pandamcbe.mghub.fireworks.FireworksManager;
import fr.pandamcbe.mghub.hublisteners.jumppads.PlayerMoveListener;
import fr.pandamcbe.mghub.hublisteners.mainevents.ParameterListeners;
import fr.pandamcbe.mghub.hublisteners.mainevents.PlayerJoinListeners;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.cosmemenu.CosmetiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons.Sons;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa.FFA;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.BoutiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.ConfirmMenu;
import fr.pandamcbe.mghub.hublisteners.menus.mainmenu.MGMenu;
import fr.pandamcbe.mghub.hublisteners.menus.mainmenu.StatsMenu;
import fr.pandamcbe.mghub.hublisteners.menus.preferences.PrefsMenu;
import fr.pandamcbe.mghub.portals.MetricsLite;
import fr.pandamcbe.mghub.portals.PortalsConfigFile;
import fr.pandamcbe.mghub.rediscommunication.RPbSbUtils;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import runnables.AutoMessage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MGHub extends JavaPlugin {

    private static MGHub INSTANCE;

    public LuckPerms luckPerms;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private ScoreboardManager scoreboardManager;

    public DuelDatasManager duelDatasManager;

    public Map<UUID, Map<String, Object>> mainMap;

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    private FireworksManager fireworksManager;

    public final Logger logger = Bukkit.getLogger();

    public Map<String, String> portalData = new HashMap<>();

    public WorldEditPlugin worldEdit;

    public YamlConfiguration configFile;

    public YamlConfiguration portalsFile;

    public RPbSbUtils subToChannels;

    public PortalsConfigFile portalsConfigFile = new PortalsConfigFile();

    @Override
    public void onEnable() {

        INSTANCE = this;
        fireworksManager = new FireworksManager();
        fireworksManager.craftEffects();

        System.out.println("Main ON");
        getCommand("report").setExecutor(new Commands());
        getCommand("spawn").setExecutor(new Commands());
        getCommand("hub").setExecutor(new Commands());
        getCommand("lobby").setExecutor(new Commands());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("stats").setExecutor(new StatCommand());

        // Les listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListeners(), this);
        getServer().getPluginManager().registerEvents(new MGMenu(), this);
        getServer().getPluginManager().registerEvents(new StatsMenu(), this);
        getServer().getPluginManager().registerEvents(new ParameterListeners(), this);
        getServer().getPluginManager().registerEvents(new CosmetiqueMenu(), this);
        getServer().getPluginManager().registerEvents(new BoutiqueMenu(), this);
        getServer().getPluginManager().registerEvents(new ConfirmMenu(), this);
        getServer().getPluginManager().registerEvents(new PrefsMenu(), this);
        getServer().getPluginManager().registerEvents(new Sons(), this);
        getServer().getPluginManager().registerEvents(new FFA(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new DuelMenu(), this);
        getServer().getPluginManager().registerEvents(new Event(), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().setIdleTimeout(180);
        getServer().setDefaultGameMode(GameMode.ADVENTURE);

        subToChannels = new RPbSbUtils();
        duelDatasManager = new DuelDatasManager();
        mainMap = new HashMap<>();
//
        new Leaderboards(this);

//        getConfig().options().copyDefaults();
//        saveDefaultConfig();

        createFile("messages");

        new AutoMessage().runTaskTimer(this, 0L, 15 * 60 * 20L);

        this.scheduledExecutorService = Executors.newScheduledThreadPool(16);
        this.executorMonoThread = Executors.newScheduledThreadPool(1);
        this.scoreboardManager = new ScoreboardManager();

        portalsConfigFile.portalsOnLoad();
        startMetrics();
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();

        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.scoreboardManager.onDisable();
        subToChannels.sendAccountsToProxy(this);
        long time = System.currentTimeMillis();
        portalsConfigFile.savePortalsData();
        this.logger.log(Level.INFO, "[BungeePortals] Version " + getDescription().getVersion() + " has been disabled. (" + (System.currentTimeMillis() - time) + "ms)");
        super.onDisable();
    }

    public static MGHub getInstance() {
        return INSTANCE;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public FireworksManager getFireworksManager() {
        return fireworksManager;
    }

    public Map<UUID, Map<String, Object>> getMainMap() {
        return mainMap;
    }

    private void startMetrics() {
        try {
            MetricsLite metrics = new MetricsLite((Plugin)this);
            metrics.start();
            this.logger.log(Level.INFO, "[BungeePortals] Metrics initiated!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createFile(String fileName) {
        if (getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();

                if (fileName.equalsIgnoreCase("messages")) {
                    FileConfiguration config = getConfig("messages");
                    config.set("messages", Arrays.asList("§6text"
                    ));
                    save(file, config);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getFile(String filename) {
        return new File(getDataFolder(), filename + ".yml");
    }

    public FileConfiguration getConfig(String filename) {
        return YamlConfiguration.loadConfiguration(getFile(filename));
    }

    public void save(File file1, FileConfiguration config) {
        try {
            config.save(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}