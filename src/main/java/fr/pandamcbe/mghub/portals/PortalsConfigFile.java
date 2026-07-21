package fr.pandamcbe.mghub.portals;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Map;
import java.util.logging.Level;

public class PortalsConfigFile {

    private void createConfigFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
                out.write(buf, 0, len);
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfigFiles() {
        MGHub mgHub = MGHub.getInstance();
        File cFile = new File(mgHub.getDataFolder(), "config.yml");
        if (!cFile.exists()) {
            cFile.getParentFile().mkdirs();
            createConfigFile(mgHub.getResource("config.yml"), cFile);
            mgHub.logger.log(Level.INFO, "[BungeePortals] Configuration file config.yml created!");
        }
        mgHub.configFile = YamlConfiguration.loadConfiguration(new File(mgHub.getDataFolder(), "config.yml"));
        mgHub.logger.log(Level.INFO, "[BungeePortals] Configuration file config.yml loaded!");
        File pFile = new File(mgHub.getDataFolder(), "portals.yml");
        if (!pFile.exists()) {
            pFile.getParentFile().mkdirs();
            createConfigFile(mgHub.getResource("portals.yml"), pFile);
            mgHub.logger.log(Level.INFO, "[BungeePortals] Configuration file portals.yml created!");
        }
        mgHub.portalsFile = YamlConfiguration.loadConfiguration(new File(mgHub.getDataFolder(), "portals.yml"));
        mgHub.logger.log(Level.INFO, "[BungeePortals] Configuration file portals.yml loaded!");
    }

    public void loadPortalsData() {
        MGHub mgHub = MGHub.getInstance();
        try {
            long time = System.currentTimeMillis();
            for (String key : mgHub.portalsFile.getKeys(false)) {
                String value = mgHub.portalsFile.getString(key);
                mgHub.portalData.put(key, value);
            }
            mgHub.logger.log(Level.INFO, "[BungeePortals] Portal data loaded! (" + (System.currentTimeMillis() - time) + "ms)");
        } catch (NullPointerException nullPointerException) {}
    }

    public void savePortalsData() {
        MGHub mgHub = MGHub.getInstance();
        long time = System.currentTimeMillis();
        for (Map.Entry<String, String> entry : mgHub.portalData.entrySet())
            mgHub.portalsFile.set(entry.getKey(), entry.getValue());
        try {
            mgHub.portalsFile.save(new File(mgHub.getDataFolder(), "portals.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mgHub.logger.log(Level.INFO, "[MGPortals] Portal data saved! (" + (System.currentTimeMillis() - time) + "ms)");
    }

    public void portalsOnLoad(){
        MGHub mgHub = MGHub.getInstance();
        if (mgHub.getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            mgHub.getPluginLoader().disablePlugin(mgHub);
            throw new NullPointerException("[MGPortals] WorldEdit not found, disabling...");
        }
        mgHub.worldEdit = (WorldEditPlugin) mgHub.getServer().getPluginManager().getPlugin("WorldEdit");
        mgHub.getCommand("MGPortals").setExecutor(new CommandPortals(mgHub));
        mgHub.logger.log(Level.INFO, "[MGPortals] Commands registered!");
        mgHub.getServer().getPluginManager().registerEvents(new EventListener(mgHub), mgHub);
        long time = System.currentTimeMillis();
        mgHub.logger.log(Level.INFO, "[MGPortals] Events registered!");
        mgHub.logger.log(Level.INFO, "[MGPortals] Plugin channel registered!");
        loadConfigFiles();
        loadPortalsData();
        int interval = mgHub.configFile.getInt("SaveTask.Interval") * 20;
        (new SaveTask(mgHub)).runTaskTimer(mgHub, interval, interval);
        mgHub.logger.log(Level.INFO, "[MGPortals] Save task started!");
        mgHub.logger.log(Level.INFO, "[MGPortals] Version " + mgHub.getDescription().getVersion() + " has been enabled. (" + (System.currentTimeMillis() - time) + "ms)");
    }
}
