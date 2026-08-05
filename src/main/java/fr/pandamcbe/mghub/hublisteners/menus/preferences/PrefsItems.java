package fr.pandamcbe.mghub.hublisteners.menus.preferences;

import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PrefsItems {

    public static final String invPrefs = "§8§l⨠ Préférences";
    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, invPrefs);

        UUID uuid = p.getUniqueId();
        ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getProfilAccount(MGHub.getInstance().getMainMap(), uuid, AccountType.PROFIL.getKeyPrefix());

        ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 3).setName("").toItemStack();

        for (int i = 0; i < 9; i++) inv.setItem(i, out);

        inv.setItem(9, out);
        for (int i = 17; i <= 26; i++) inv.setItem(i, out);

        if (profilAccount.getMsg_allow() == 1) {
            inv.setItem(11, new ItemBuilder(Material.FEATHER).setName("§6Messages privÉs §7» §aON").toItemStack());
        } else inv.setItem(11, new ItemBuilder(Material.FEATHER).setName("§6Messages privÉs §7» §cOFF").toItemStack());

        if (profilAccount.getC_visibility() == 1) {
            inv.setItem(12, new ItemBuilder(Material.BOOK).setName("§6Chat Global §7» §aON").toItemStack());
        } else inv.setItem(12, new ItemBuilder(Material.BOOK).setName("§6Chat Global §7» §cOFF").toItemStack());

        if (profilAccount.getP_visibility() == 1) {
            inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 3).setName("§6VisibilitÉe des joueurs §7» §aON").toItemStack());
        } else
            inv.setItem(14, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 3).setName("§6VisibilitÉe des joueurs §7» §cOFF").toItemStack());

        if (profilAccount.getDuel_allow() == 1) {
            inv.setItem(15, new ItemBuilder(Material.YELLOW_FLOWER).setName("§6Demandes de duels §7» §aON").toItemStack());
        } else
            inv.setItem(15, new ItemBuilder(Material.YELLOW_FLOWER).setName("§6Demandes de duels §7» §cOFF").toItemStack());

        inv.setItem(26, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Sortir du menu").toItemStack());
        inv.setItem(18, new ItemBuilder(Material.ENDER_PEARL).setName("§7§l⨠ §b§lMg'sMain").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());

        //essayer d'ouvrir le menu pour le joueur dans cette méthode pour voir
        p.openInventory(inv);
    }
}
