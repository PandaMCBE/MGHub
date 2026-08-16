package fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons;

import fr.pandamcbe.commons.WornAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.rediscache.RedisAccountsManager;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class SonsItems {

    public static final String invSound = "§8§l⨠ Sons";
    public void open(Player p) {
        UUID uuid = p.getUniqueId();
        Inventory inv = Bukkit.createInventory(p, 45, invSound);
        Bukkit.getScheduler().runTaskAsynchronously(MGHub.getInstance(), () -> {
            RedisAccountsManager connections = new RedisAccountsManager();

                Set<Integer> o = (Set<Integer>) connections.getShopAccount(uuid, AccountType.cosmeKey(AllNames.SOUND_ID.getName()));
                Bukkit.getScheduler().runTask(MGHub.getInstance(), () -> {
                    WornAccount wearedAccount = (WornAccount) Main.getInstance().manipulation.getShopAccounts(MGHub.getInstance().mainMap, uuid, AccountType.COSMEWORN.getKeyPrefix());
                    ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 15).toItemStack();

                    for (int i = 0; i < 10; i++) inv.setItem(i, out);

                    inv.setItem(17, out);
                    inv.setItem(18, out);
                    inv.setItem(26, out);
                    inv.setItem(27, out);

                    for (int i = 35; i <= 44; i++) inv.setItem(i, out);
                    if (!o.contains(40)) {
                        inv.setItem(10, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fPling §8[NOTEBLOCK]").setLore("§aACCESSIBLE", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 40) {
                        inv.setItem(10, new ItemBuilder(Material.NOTE_BLOCK).setName("§fPling §8[NOTEBLOCK]").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(10, new ItemBuilder(Material.NOTE_BLOCK).setName("§fPling §8[NOTEBLOCK]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(50)) {
                        inv.setItem(11, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fHarp §8[NOTEBLOCK]").setLore("§7MGCoins §7: §21000", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 50) {
                        inv.setItem(11, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHarp §8[NOTEBLOCK]").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(11, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHarp §8[NOTEBLOCK]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(10)) {
                        inv.setItem(12, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fHat §8[NOTEBLOCK]").setLore("§7MGCoins §7: §21500", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 10) {
                        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHat §8[NOTEBLOCK]").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(12, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHat §8[NOTEBLOCK]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(30)) {
                        inv.setItem(13, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fBass §8[NOTEBLOCK]").setLore("§7MGCoins §7: §22000", "", "§fRareté : §9Rare").toItemStack());
                    } else if (wearedAccount.getSound_id() == 30) {
                        inv.setItem(13, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBass §8[NOTEBLOCK]").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(13, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBass §8[NOTEBLOCK]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §9Rare").toItemStack());

                    if (!o.contains(60)) {
                        inv.setItem(14, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fSnare §8[NOTEBLOCK]").setLore("§2MGDonator", "", "§fRareté : §5Épique").toItemStack());
                    } else if (wearedAccount.getSound_id() == 60) {
                        inv.setItem(14, new ItemBuilder(Material.NOTE_BLOCK).setName("§fSnare §8[NOTEBLOCK]").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(14, new ItemBuilder(Material.NOTE_BLOCK).setName("§fSnare §8[NOTEBLOCK]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §5Épique").toItemStack());

                    if (!o.contains(20)) {
                        inv.setItem(15, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fBd §8[NOTEBLOCK").setLore("§2MGDonator+", "", "§fRareté : §6LÉgendaire").toItemStack());
                    } else if (wearedAccount.getSound_id() == 20) {
                        inv.setItem(15, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBd §8[NOTEBLOCK").addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(15, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBd §8[NOTEBLOCK").setLore("§eCliquez pour séléctionner", "", "§fRareté : LÉgendaire").toItemStack());

                    if (!o.contains(70)) {
                        inv.setItem(16, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fWolf's Death §8[MOB]").setLore("§aACCESSIBLE", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 70) {
                        inv.setItem(16, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 95).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fWolf's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(16, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 95).setName("§fWolf's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(80)) {
                        inv.setItem(19, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fSkeleton's Death §8[MOB]").setLore("§7MGCoins §7: §22500", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 80) {
                        inv.setItem(19, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 51).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fSkeleton's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(19, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 51).setName("§fSkeleton's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(90)) {
                        inv.setItem(20, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fVillager's Death §8[MOB]").setLore("§7MGCoins §7: §23000", "", "§fRareté : §9Rare").toItemStack());
                    } else if (wearedAccount.getSound_id() == 90) {
                        inv.setItem(20, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 120).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fVillager's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(20, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 120).setName("§fVillager's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §9Rare").toItemStack());

                    if (!o.contains(100)) {
                        inv.setItem(21, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fBat's Death §8[MOB]").setLore("§7MGCoins §7: §23500", "", "§fRareté : §9Rare").toItemStack());
                    } else if (wearedAccount.getSound_id() == 100) {
                        inv.setItem(21, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 65).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fBat's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(21, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 65).setName("§fBat's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §9Rare").toItemStack());

                    if (!o.contains(110)) {
                        inv.setItem(22, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fBlaze's Death §8[MOB]").setLore("§7MGCoins §7: §24000", "", "§fRareté : §5Épique").toItemStack());
                    } else if (wearedAccount.getSound_id() == 110) {
                        inv.setItem(22, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 61).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fBlaze's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(22, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 61).setName("§fBlaze's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §5Épique").toItemStack());

                    if (!o.contains(120)) {
                        inv.setItem(23, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fWither's Death §8[MOB]").setLore("§2MGDonator+", "§fRareté : §6LÉgendaire").toItemStack());
                    } else if (wearedAccount.getSound_id() == 120) {
                        inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fWither's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 1).setName("§fWither's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "§fRareté : §6LÉgendaire").toItemStack());

                    if (!o.contains(130)) {
                        inv.setItem(24, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fEnderman's Death §8[MOB]").setLore("§2MGDonator", "", "§fRareté : §6LÉgendaire").toItemStack());
                    } else if (wearedAccount.getSound_id() == 130) {
                        inv.setItem(24, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 58).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fEnderman's Death §8[MOB]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(24, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 58).setName("§fEnderman's Death §8[MOB]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §6LÉgendaire").toItemStack());

                    if (!o.contains(160)) {
                        inv.setItem(25, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fLevel Up §8[OTHERS]").setLore("§aACCESSIBLE", "", "§fRareté : §aCommun").toItemStack());
                    } else if (wearedAccount.getSound_id() == 160) {
                        inv.setItem(25, new ItemBuilder(Material.MAGMA_CREAM).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fLevel Up §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(25, new ItemBuilder(Material.MAGMA_CREAM).setName("§fLevel Up §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §aCommun").toItemStack());

                    if (!o.contains(140)) {
                        inv.setItem(29, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fClick §8[OTHERS]").setLore("§7MGCoins §7: §24500", "", "§fRareté : §9Rare").toItemStack());
                    } else if (wearedAccount.getSound_id() == 140) {
                        inv.setItem(29, new ItemBuilder(Material.LEVER).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fClick §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(29, new ItemBuilder(Material.LEVER).setName("§fClick §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §9Rare").toItemStack());

                    if (!o.contains(150)) {
                        inv.setItem(28, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fGlass §8[OTHERS]").setLore("§7MGCoins §7: §25000", "", "§fRareté : §9Rare").toItemStack());
                    } else if (wearedAccount.getSound_id() == 150) {
                        inv.setItem(29, new ItemBuilder(Material.LEVER).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fClick §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(29, new ItemBuilder(Material.GLASS).setName("§fGlass §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §9Rare").toItemStack());

                    if (!o.contains(170)) {
                        inv.setItem(30, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fExplode §8[OTHERS]").setLore("§7MGCoins §7: §25500", "", "§fRareté : §5Épique").toItemStack());
                    } else if (wearedAccount.getSound_id() == 170) {
                        inv.setItem(30, new ItemBuilder(Material.TNT).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fExplode §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(30, new ItemBuilder(Material.TNT).setName("§fExplode §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §5Épique").toItemStack());

                    if (!o.contains(180)) {
                        inv.setItem(31, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fHit §8[OTHERS]").setLore("§7MGCoins §7: §26000", "", "§fRareté : §5Épique").toItemStack());
                    } else if (wearedAccount.getSound_id() == 180) {
                        inv.setItem(31, new ItemBuilder(Material.ARROW).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fHit §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(31, new ItemBuilder(Material.ARROW).setName("§fHit §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §5Épique").toItemStack());

                    if (!o.contains(190)) {
                        inv.setItem(32, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fFirework §8[OTHERS]").setLore("§2MGDonator", "", "§fRareté : §6LÉgendaire").toItemStack());
                    } else if (wearedAccount.getSound_id() == 190) {
                        inv.setItem(32, new ItemBuilder(Material.FIREWORK).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fFirework §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(32, new ItemBuilder(Material.FIREWORK).setName("§fFirework §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §6LÉgendaire").toItemStack());

                    if (!o.contains(200)) {
                        inv.setItem(33, new ItemBuilder(Material.INK_SACK).setDurability((byte) 8).setName("§fBurp §8[OTHERS]").setLore("§2MGDonator+", "", "§fRareté : §6LÉgendaire").toItemStack());
                    } else if (wearedAccount.getSound_id() == 200) {
                        inv.setItem(33, new ItemBuilder(Material.COOKED_BEEF).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).setName("§fBurp §8[OTHERS]").setLore("§aSelectionnÉ").toItemStack());
                    } else
                        inv.setItem(33, new ItemBuilder(Material.COOKED_BEEF).setName("§fBurp §8[OTHERS]").setLore("§eCliquez pour séléctionner", "", "§fRareté : §6LÉgendaire").toItemStack());

                    inv.setItem(34, new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 7).toItemStack());
                    inv.setItem(38, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Vers le menu ⨠ CosmeShop").toItemStack());
                    inv.setItem(42, new ItemBuilder(Material.BARRIER).setName("§cDéséquiper").setLore("§eCliquez pour aucun son.").toItemStack());

                    p.openInventory(inv);

                });

        });
    }
}
