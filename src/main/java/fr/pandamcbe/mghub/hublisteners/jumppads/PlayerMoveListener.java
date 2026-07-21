package fr.pandamcbe.mghub.hublisteners.jumppads;

import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    MGHub plugin;

    public  PlayerMoveListener(MGHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent e) {
        if (plugin.getConfig().getBoolean("Jp-enable")) {
            Player p = e.getPlayer();
            Location blockUnder = p.getLocation();
            blockUnder.setY(blockUnder.getY() - 1);
            if (p.getLocation().getBlock().getType().equals(Material.IRON_PLATE) && blockUnder.getBlock().getType().equals(Material.SLIME_BLOCK)) {
                p.setVelocity(p.getLocation().getDirection().multiply(plugin.getConfig().getDouble("Jp-Velocity-Multiplier")).setY(plugin.getConfig().getDouble("Jp-Y-Velocity")));
            }
        }


    }
}
