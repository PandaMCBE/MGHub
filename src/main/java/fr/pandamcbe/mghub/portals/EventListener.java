package fr.pandamcbe.mghub.portals;

import fr.pandamcbe.mghub.MGHub;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EventListener implements Listener {
    private MGHub plugin;

    private Map<String, Boolean> statusData = new HashMap<>();

    public EventListener(MGHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) throws IOException {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (!this.statusData.containsKey(playerName))
            this.statusData.put(playerName, Boolean.valueOf(false));
        Block block = player.getWorld().getBlockAt(player.getLocation());
        String data = block.getWorld().getName() + "#" + String.valueOf(block.getX()) + "#" + String.valueOf(block.getY()) + "#" + String.valueOf(block.getZ());
        if (this.plugin.portalData.containsKey(data)) {
            if (!((Boolean)this.statusData.get(playerName)).booleanValue()) {
                this.statusData.put(playerName, Boolean.valueOf(true));
                String destination = (String)this.plugin.portalData.get(data);
                ProxyTPRequest(player, destination);

            }
        } else if (((Boolean)this.statusData.get(playerName)).booleanValue()) {
            this.statusData.put(playerName, Boolean.valueOf(false));
        }
    }

    private void ProxyTPRequest(Player player, String destination) throws IOException {

        if (player.hasPermission("MGPortals.portal." + destination) || player.hasPermission("MGPortals.portal.*")) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF("Connect");
            dos.writeUTF(destination);
            player.sendPluginMessage((Plugin)this.plugin, "BungeeCord", baos.toByteArray());
            baos.close();
            dos.close();
        } else {
            player.sendMessage(this.plugin.configFile.getString("NoPortalPermissionMessage").replace("{destination}", destination).replaceAll("(&([a-f0-9l-or]))", "§$2"));
        }
    }
}
