package runnables;

import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AutoMessage extends BukkitRunnable {

    private List<String> messages = MGHub.getInstance().getConfig("messages").getStringList("messages");
    private int i = 0;

    @Override
    public void run() {
        if(messages.isEmpty()){
            cancel();
            return;
        }
        if(i >= messages.size()){
            i = 0;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.get(i)));
//            BukkitTask bukkitTask = MGHub.getInstance().menuTaskStoring.get(player);
//            bukkitTask.cancel();
//            MGHub.getInstance().menuTaskStoring.remove(player);

        });

        i++;
    }
}
