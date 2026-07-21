package fr.pandamcbe.mghub.hublisteners.menus.mainmenu;

import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class StatsMenu implements Listener {

    public static final String invMain = "§8§l⨠ Menu Stats";

    public void open(Player p) {
        UUID uuid = p.getUniqueId();
        Inventory inv = Bukkit.createInventory(p, 36, invMain);

        StatsAccount statsCJFFA = (StatsAccount) Main.getInstance().manipulation.getAccount(MGHub.getInstance().mainMap, uuid, AllNames.statsPojo(AllNames.CJFFA.getName()));
        StatsAccount statsPerfectShot = (StatsAccount) Main.getInstance().manipulation.getAccount(MGHub.getInstance().mainMap, uuid, AllNames.statsPojo(AllNames.PFTSHOT.getName()));
        StatsAccount statsDuel = (StatsAccount) Main.getInstance().manipulation.getAccount(MGHub.getInstance().mainMap, uuid, AllNames.statsPojo(AllNames.DUELS.getName()));
        //                String base64Texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYxNTlhMWNiZTE3M2Q5MjczOTJmYTY1ZmNmZTc4NTUzZDgxYWE1ZGMyOTQ5NWI5ZmJiYWRlMzYyZjhiZjkifX19";
//                ItemStack customHead = CustomHeadUtil.createCustomHead(base64Texture);

//                CustomHeadUtil.createCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYxNTlhMWNiZTE3M2Q5MjczOTJmYTY1ZmNmZTc4NTUzZDgxYWE1ZGMyOTQ5NWI5ZmJiYWRlMzYyZjhiZjkifX19");
        ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 5).setName("").toItemStack();

        inv.setItem(0, out);

        inv.setItem(9, out);

        inv.setItem(18, out);

        inv.setItem(27, out);

        ItemStack out1 = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 1).setName("").toItemStack();
        inv.setItem(8, out1);
        inv.setItem(17, out1);
        inv.setItem(26, out1);
        inv.setItem(35, out1);

        ItemStack out2 = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 7).setName("").toItemStack();
        inv.setItem(3, out2);
        inv.setItem(4, out2);
        inv.setItem(5, out2);
        inv.setItem(30, out2);
        inv.setItem(31, out2);
        inv.setItem(32, out2);

//        inv.setItem(12, new ItemBuilder(customHead).setName("§8■ §a§lO.N.A.P").setLore("§8Type: Mini-jeu", "", "§dMGElos §8: §7" + stats.getElos(), "§aKills §8: §7" + stats.getKills(), "§bWins §8: §7" + stats.getWins(), "§cKillstreaks §8: §7" + stats.getKillstreaks()).toItemStack());
//        inv.setItem(13, new ItemBuilder(Material.EGG).setName("§8■ §a§lSPLEGG").setLore("§8Type: Mini-jeu", "", "§dMGElos §8: §7" + stats.getElos(), "§aKills §8: §7" + stats.getKills(), "§bWins §8: §7" + stats.getWins(), "§cKillstreaks §8: §7" + stats.getKillstreaks()).toItemStack());
        inv.setItem(14, new ItemBuilder(Material.BOW).setName("§8■ §a§lPERFECTSHOT").setLore("§8Type: Mini-jeu", "", "§dMGElos §8: §7" + statsPerfectShot.getElos(), "§aKills §8: §7" + statsPerfectShot.getKills(), "§bWins §8: §7" + statsPerfectShot.getWins(), "§cKillstreaks §8: §7" + statsPerfectShot.getKillstreaks()).toItemStack());
        inv.setItem(21, new ItemBuilder(Material.CACTUS).setName("§8■ §c§lCJFFA").setLore("§8Type: PvP", "", "§dMGElos §8: §7" + statsCJFFA.getElos(), "§aKills §8: §7" + statsCJFFA.getKills(), "§bWins §8: §7" + statsCJFFA.getWins(), "§cKillstreaks §8: §7" + statsCJFFA.getKillstreaks()).toItemStack());
        inv.setItem(22, new ItemBuilder(Material.IRON_SWORD).setName("§8■ §8§lDUELS").setLore("§8Type: Duel PvP", "", "§dMGElos §8: §7" + statsDuel.getElos(), "§aKills §8: §7" + statsDuel.getKills(), "§bWins §8: §7" + statsDuel.getWins(), "§cKillstreaks §8: §7" + statsDuel.getKillstreaks()).toItemStack());
//        inv.setItem(23, new ItemBuilder(Material.SANDSTONE).setName("§8■ §9§lR§6§lT§c§lF").setLore("§8Type: Mini-jeu x PvP", "", "§dMGElos §8: §7" + stats.getElos(), "§aKills §8: §7" + stats.getKills(), "§bWins §8: §7" + stats.getWins(), "§cKillstreaks §8: §7" + stats.getKillstreaks()).toItemStack());
        inv.setItem(31, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Retour dans le MGMenu").toItemStack());
//        inv.setItem(32, new ItemBuilder(Material.ENDER_PEARL).setName("§a ↩ Mg'sMain").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null && event.getAction() != null) return;
        if(event.getView().getTitle().equals(invMain)) {
            event.setCancelled(true);
                if(event.getCurrentItem().getType().equals(Material.DARK_OAK_DOOR_ITEM)) {
                    new MGMenu().open(p);
                }

        }

    }
}
