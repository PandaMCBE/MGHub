package fr.pandamcbe.mghub.commands.duelcommand;

import fr.pandamcbe.commons.DuelAccount;
import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.UUID;


public class DuelMenu implements Listener {

    private static final String invName = "Choisir un duel";
    private static final Inventory inventory = Bukkit.createInventory(null, 9, invName);

    public static Inventory open(Player player) {
        //inventory.setItem(2, new ItemBuilder(Material.LAVA_BUCKET).setName("§6§l BuildUHC").toItemStack());
        inventory.setItem(3, new ItemBuilder(Material.FISHING_ROD).setName("§6§l SG").toItemStack());
        //inventory.setItem(5, new ItemBuilder(Material.SLIME_BALL).setName("§6§l Sumo").toItemStack());
        //inventory.setItem(6, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player).setDurability((byte) 3).setName("§6§l PvBot").toItemStack());
        inventory.setItem(4, new ItemBuilder(Material.SANDSTONE).setName("§6§l MLGRush").toItemStack());

        player.updateInventory();
        return inventory;
    }

    @EventHandler
    void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        MGHub main = MGHub.getInstance();
        Player target = main.duelDatasManager.getPlayerKey().get(p);
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invName) && target != null) {
            event.setCancelled(true);
            final User user1 = main.luckPerms.getPlayerAdapter(Player.class).getUser(p);
            final User user2 = main.luckPerms.getPlayerAdapter(Player.class).getUser(target);
            UUID uuid = target.getUniqueId();
            final ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getAccount(main.mainMap, uuid, AllNames.PROFILPOJO.getName());
            MemAccountsManipulation memoryProvider = Main.getInstance().manipulation;
            DuelAccount duelAccount = new DuelAccount(p.getUniqueId(), target.getUniqueId(), "");
            if (profilAccount.getDuel_allow() == 1) {
                switch (event.getCurrentItem().getType()) {
                    case LAVA_BUCKET:
                        duelAccount.setGamemode(AllNames.BUILDUHC.getName());
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§lVous venez d'envoyer une demande de duel en BuildUHC à §a§l" + target.getName() + " §r§l" + user2.getCachedData().getMetaData().getPrefix());
                        target.sendMessage(main.duelDatasManager.getPrefix() + "§b§l" + p.getName() + " §r§l" + user1.getCachedData().getMetaData().getPrefix() + " §e§lVous a demandé en DUEL DE BUILDUHC !");
                        memoryProvider.sendAccountToMemory(main.mainMap, p.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                        break;
                    case FISHING_ROD:
                        duelAccount.setGamemode(AllNames.SG.getName());
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§lVous venez d'envoyer une demande de duel en SG à §a§l" + target.getName() + " §r§l" + user2.getCachedData().getMetaData().getPrefix());
                        target.sendMessage(main.duelDatasManager.getPrefix() + "§b§l" + p.getName() + " §r§l" + user1.getCachedData().getMetaData().getPrefix() + " §e§lVous a demandé en DUEL DE SG !");
                        memoryProvider.sendAccountToMemory(main.mainMap, p.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                        break;
                    case SLIME_BALL:
                        duelAccount.setGamemode(AllNames.SUMO.getName());
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§lVous venez d'envoyer une demande de duel en SUMO à §a§l" + target.getName() + " §r§l" + user2.getCachedData().getMetaData().getPrefix());
                        target.sendMessage(main.duelDatasManager.getPrefix() + "§b§l" + p.getName() + " §r§l" + user1.getCachedData().getMetaData().getPrefix() + " §e§lVous a demandé en DUEL DE SUMO !");
                        memoryProvider.sendAccountToMemory(main.mainMap, p.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                        break;
                    case SKULL_ITEM:
                        duelAccount.setGamemode(AllNames.PVBOT.getName());
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§lVous venez d'envoyer une demande de duel en PvBot à §a§l" + target.getName() + " §r§l" + user2.getCachedData().getMetaData().getPrefix());
                        target.sendMessage(main.duelDatasManager.getPrefix() + "§b§l" + p.getName() + " §r§l" + user1.getCachedData().getMetaData().getPrefix() + " §e§lVous a demandé en DUEL DE PVBOT !");
                        memoryProvider.sendAccountToMemory(main.mainMap, p.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                        break;
                    case SANDSTONE:
                        duelAccount.setGamemode(AllNames.MLGRUSH.getName());
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§lVous venez d'envoyer une demande de duel en MLGRush à §a§l" + target.getName() + " §r§l" + user2.getCachedData().getMetaData().getPrefix());
                        target.sendMessage(main.duelDatasManager.getPrefix() + "§b§l" + p.getName() + " §r§l" + user1.getCachedData().getMetaData().getPrefix() + " §e§lVous a demandé en DUEL DE MLGRush !");
                        break;
                    default:
                        break;

                }
                memoryProvider.sendAccountToMemory(main.getMainMap(), p.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                memoryProvider.sendAccountToMemory(main.getMainMap(), target.getUniqueId(), duelAccount, AllNames.DUELPOJO.getName());
                p.closeInventory();

            } else {
                Bukkit.getScheduler().runTask(MGHub.getInstance(), () ->
                        p.sendMessage(main.duelDatasManager.getPrefix() + "§cCe gougnafier n'accepte pas les demandes de duel !"));
            }
        }
    }

}