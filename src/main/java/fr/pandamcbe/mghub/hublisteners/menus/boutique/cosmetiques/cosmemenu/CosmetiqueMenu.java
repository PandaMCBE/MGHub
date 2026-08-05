package fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.cosmemenu;

import fr.pandamcbe.commons.WornAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons.SonsItems;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.BoutiqueMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CosmetiqueMenu implements Listener {
    final public static String invCosMenu = "§8§l⨠ CosmeShop";

    public CosmetiqueMenu() {

    }

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, invCosMenu);

        ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 15).setName("").toItemStack();
        for (int i = 0; i < 9; i++) {inv.setItem(i, out);}inv.setItem(9, out);for(int i = 17; i <= 26; i++) {inv.setItem(i, out);}
        inv.setItem(10, new ItemBuilder(Material.BARRIER).setName("§cDéséquiper tout").setLore("", "§7<Clique pour déséquiper tous les cosmétiques>").toItemStack());
        inv.setItem(12, new ItemBuilder(Material.RECORD_12).setName("§8■ §6Sons").setLore("", "§a? §7Tu peux choisir un son", "§7que tu pourras ", "§7entendre à chaque", "§7fois que tu feras ", "§7un kill sur n'importe", "§7quel mini-jeu !", "", "§aCliques pour accéder").toItemStack());
        inv.setItem(13, new ItemBuilder(Material.BLAZE_POWDER).setName("§8■ §6Particules").setLore("", "§a? §7Tu peux choisir ", "§7une/des particules(s)", "§7qui apparaîtront ", "§7autour de toi", "§7sur n'importe", "§7quel mini-jeu !", "", "§aCliques pour accéder").toItemStack());
        inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 3).setName("§8■ §6Compagnons").setLore("", "§a? §7Tu peux choisir ", "§7un mini Mob/Joueur)", "§7qui t'accompagnera ","§7partout à côté", "§7de toi sur", "§7n'importe quel","§7mini-jeu !", "", "§aCliques pour accéder").toItemStack());
        inv.setItem(22, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Vers le menu ⨠ Shop").toItemStack());
        p.openInventory(inv);
    }
    @EventHandler
    void onClickInventory6(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invCosMenu)) {
            event.setCancelled(true);

            switch (event.getCurrentItem().getType()) {
                case DARK_OAK_DOOR_ITEM:
                    new BoutiqueMenu().open(p);
                    break;
                //Retirer tout
                case BARRIER:
                    //retirer les sons
                    RemoveAll(p.getUniqueId());
                    //retirer les effets
                    break;
                case RECORD_12:
                    new SonsItems().open(p);
                default:
                    break;
            }
        }
    }
    void RemoveAll(UUID uuid){

            WornAccount a = (WornAccount) Main.getInstance().manipulation.getProfilAccount(MGHub.getInstance().getMainMap(), uuid, AccountType.COSMEWORN.getKeyPrefix());
            a.setSound_id(0);
        //            a.setMini_id(0);
        //            a.setParticule_id(0);


    }

}
