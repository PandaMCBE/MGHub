package fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa;

import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FFAItems {
    public static final String invFFA = "§8§l⨠ Kits CJFFA ";

    public void open(Player p) {

//        FFAKitsAccount a = (FFAKitsAccount) Main.getInstance().manipulation.getShopAccounts(MGHub.getInstance().mainMap, p.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix());
//        CompletableFuture.supplyAsync(()-> (FFAKitsAccount) Main.getInstance().manipulation.getShopAccounts(MGHub.getInstance().mainMap, p.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix())).thenAccept(
//        a-> Bukkit.getScheduler().runTask(MGHub.getInstance(), ()-> {

            FFAKitsAccount a = (FFAKitsAccount) Main.getInstance().manipulation.getShopAccounts(MGHub.getInstance().mainMap, p.getUniqueId(), AccountType.CJFFAKITS.getKeyPrefix());
            Inventory inv = Bukkit.createInventory(null, 36, invFFA);

            ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 15).toItemStack();
            for (int i = 0; i < 36; i++) inv.setItem(i, out);

            inv.setItem(20, new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName("§8■ §bGuerrier").setLore("§aPossÉdÉ").toItemStack());

            if (a.getArcher() == 0) {
                inv.setItem(21, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§8■ §bArcher").setLore("§7MGCoins : §21500").toItemStack());
            } else inv.setItem(21, new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName("§8■ §bArcher").setLore("§aPossÉdÉ").toItemStack());

            if (a.getAssassin() == 0) {
                inv.setItem(22, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§8■ §bAssassin").setLore("§7MGCoins : §22000").toItemStack());
            } else inv.setItem(22, new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName("§8■ §bAssassin").setLore("§aPossÉdÉ").toItemStack());

            if (a.getPuncher() == 0) {
                inv.setItem(23, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§8■ §bPuncher").setLore("§7MGCoins : §22500").toItemStack());
            } else inv.setItem(23, new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName("§8■ §bPuncher").setLore("§aPossÉdÉ").toItemStack());

            if (a.getCheated() == 0) {
                inv.setItem(24, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§8■ §bCheaté").setLore("§7MGElos : §2800").toItemStack());
            } else inv.setItem(24, new ItemBuilder(Material.INK_SACK).setDurability((byte) 10).setName("§8■ §bCheaté").setLore("§aPossÉdÉ").toItemStack());

            if (a.getGuerrier() == 2) {
                inv.setItem(11, new ItemBuilder(Material.IRON_SWORD).setName("§8■ §bGuerrier").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 4", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en mailles", "§8- §b1§7x §fPlastron en cuirve", "§8- §b1§7x §fJambières en diamant", "§8- §b1§7x §fBottes en diamant").toItemStack());
            } else inv.setItem(11, new ItemBuilder(Material.IRON_SWORD).setName("§8■ §bGuerrier").setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 4", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en mailles", "§8- §b1§7x §fPlastron en cuivre", "§8- §b1§7x §fJambières en diamant", "§8- §b1§7x §fBottes en diamant").toItemStack());

            if(a.getArcher() == 2) {
                inv.setItem(12, new ItemBuilder(Material.BOW).setName("§8■ §bArcher").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 4", "§8- §b1§7x §fArc", "§8- §b2§7x §fFlèches", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en or", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en fer").toItemStack());
            } else inv.setItem(12, new ItemBuilder(Material.BOW).setName("§8■ §bArcher").setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 4", "§8- §b1§7x §fArc", "§8- §b2§7x §fFlèches", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en or", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en fer").toItemStack());

            if(a.getAssassin() == 2) {
                inv.setItem(13, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§8■ §bAssassin").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 1","§8- §b2§7x §fPotion de poison 20s", "§8- §b2§7x §fPotion d'invisibilité 20s", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en mailles", "§8- §b1§7x §fPlastron en mailles", "§8- §b1§7x §fJambières en diamant", "§8- §b1§7x §fBottes en cuivre").toItemStack());
            } else inv.setItem(13, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§8■ §bAssassin").setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer Sharp 1","§8- §b2§7x §fPotion de poison 20s", "§8- §b2§7x §fPotion d'invisibilité 20s", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en mailles", "§8- §b1§7x §fPlastron en mailles", "§8- §b1§7x §fJambières en diamant", "§8- §b1§7x §fBottes en cuivre").toItemStack());

            if(a.getPuncher() == 2) {
                inv.setItem(14, new ItemBuilder(Material.RAW_FISH).setName("§8■ §bPuncher").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer","§8- §b1§7x §fBâton KnockBack IV", "§8- §b1§7x §fCanne à pêche", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en diamant", "§8- §b1§7x §fPlastron en fer", "§8- §b1§7x §fJambières en mailles", "§8- §b1§7x §fBottes en or").toItemStack());
            } else inv.setItem(14, new ItemBuilder(Material.RAW_FISH).setName("§8■ §bPuncher").setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer","§8- §b1§7x §fBâton KnockBack IV", "§8- §b1§7x §fCanne à pêche", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en diamant", "§8- §b1§7x §fPlastron en fer", "§8- §b1§7x §fJambières en mailles", "§8- §b1§7x §fBottes en or").toItemStack());

            if (a.getCheated() == 2) {
                inv.setItem(15, new ItemBuilder(Material.DIAMOND).setName("§8■ §bCheaté").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer", "§8- §b1§7x §fÉpée en fer", "§8- §b1§7x §fPomme cheatée", "§8- §b3§7x §fEnderpearls", "§8- §b1§7x §fCasque en or", "§8- §b1§7x §fPlastron en diamant", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en mailles").toItemStack());
            } else inv.setItem(15, new ItemBuilder(Material.DIAMOND).setName("§8■ §bCheaté").setLore("§7Équipements", "", "§8- §b1§7x §fÉpée en fer", "§8- §b1§7x §fPomme cheatée", "§8- §b3§7x §fEnderpearls", "§8- §b1§7x §fCasque en or", "§8- §b1§7x §fPlastron en diamant", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en mailles").toItemStack());

            inv.setItem(31, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Vers le menu ⨠ Shop").toItemStack());

            p.openInventory(inv);



//            }));
    }

}