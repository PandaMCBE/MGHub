package fr.pandamcbe.mghub.portals;

import fr.pandamcbe.mghub.MGHub;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {
    private MGHub plugin;

    public SaveTask(MGHub plugin) {
        this.plugin = plugin;
    }

    PortalsConfigFile portalsConfigFile = new PortalsConfigFile();

    public void run() {
        if (this.plugin.configFile.getBoolean("SaveTask.Enabled"))
            portalsConfigFile.savePortalsData();
    }
}
