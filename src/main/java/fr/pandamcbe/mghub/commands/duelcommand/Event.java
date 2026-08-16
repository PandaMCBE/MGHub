package fr.pandamcbe.mghub.commands.duelcommand;

import fr.pandamcbe.commons.DuelAccount;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Event implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event){
        Inventory inv = event.getInventory();
        Player p = (Player) event.getPlayer();
        UUID uuid = p.getUniqueId();
        DuelDatasManager duelDatasManager = MGHub.getInstance().duelDatasManager;
        if(inv.getTitle().equalsIgnoreCase("Choisir un duel")){
            Player target = duelDatasManager.getPlayerKey().get(p);
            Bukkit.getScheduler().runTaskLater(MGHub.getInstance(), ()-> {
                if (!MGHub.getInstance().mainMap.get(uuid).containsKey(DuelAccount.class.getName())) {
                    if (duelDatasManager.getPlayerKey().containsKey(p) && duelDatasManager.getTargetKey().containsKey(target)) {
                        duelDatasManager.getPlayerKey().remove(p);
                        duelDatasManager.getTargetKey().remove(target);
                        p.sendMessage("§cVous n'avez pas confirmé le mode, alors la demande est annulée !");
                    }
                }
            }, 20L);
        }
    }
}