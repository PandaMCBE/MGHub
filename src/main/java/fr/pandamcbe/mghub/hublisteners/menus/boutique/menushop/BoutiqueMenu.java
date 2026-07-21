package fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop;

import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.cosmemenu.CosmetiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa.FFAItems;
import fr.pandamcbe.mghub.hublisteners.menus.mainmenu.MGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BoutiqueMenu implements Listener {


    public static final String invBoutique = "§8§l⨠ Boutique";

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, invBoutique);

        ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 4).setName("").toItemStack();
        for (int i = 0; i < 9; i++) inv.setItem(i, out);

        inv.setItem(9, out);
        for(int i = 17; i <= 26; i++) inv.setItem(i, out);

        inv.setItem(10, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§8■ §cKits CJFFA").setLore("", "§4(PRECISION)","§7Chaque 'kit' est", "§7une extension ", "§7du votre actuel.","§7sauf pour les armures", "" ,"§aClique pour accéder").toItemStack());
        inv.setItem(15, new ItemBuilder(Material.NAME_TAG).setName("§8■ §eGrades & Badges").setLore("", "§7Par ici pour payer", "§7des grades pour", "§7le soutient du projet", "§7ou bien pour récupérer", "§7un badge que vous", "§7avez gagné.", "", "§aClique pour accéder").toItemStack());
        inv.setItem(16, new ItemBuilder(Material.SLIME_BALL).setName("§8■ §3Cosmétiques").setLore("", "§7Vos cosmétiques ","§7s'appliqueront", "§7quasiment sur","§7tous les mini-jeux !", "", "§aClique pour accéder").toItemStack());
        inv.setItem(26, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Sortir du menu").toItemStack());
        inv.setItem(18, new ItemBuilder(Material.ENDER_PEARL).setName("§7§l⨠ §b§lMg'sMain").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
        p.openInventory(inv);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if(event.getCurrentItem() == null && event.getAction() != null) return;
        if(event.getView().getTitle().equals(invBoutique)) {
            event.setCancelled(true);
            p.updateInventory();

            switch (event.getCurrentItem().getType()) {
                //Kits FFA
                case DARK_OAK_DOOR_ITEM:
                    p.closeInventory();
                    break;
                case ENDER_PEARL:
                    new MGMenu().open(p);
                    break;
                case CHAINMAIL_CHESTPLATE:
                    new FFAItems().open(p);
//                    Main.getInstance().gameMechanics.changeMenu(p, new FFAItems().open(p));
                    break;
                case SLIME_BALL:
                    new CosmetiqueMenu().open(p);
                default:
                    break;
            }
        }
    }

}