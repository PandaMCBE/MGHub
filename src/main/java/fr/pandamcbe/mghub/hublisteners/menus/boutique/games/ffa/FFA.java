package fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa;

import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.BoutiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.ConfirmMenu;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

import static fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa.FFAItems.invFFA;

public class FFA implements Listener {

    private final MGHub main = MGHub.getInstance();
    MemAccountsManipulation memoryProvider = Main.getInstance().manipulation;
    //Partie sons
    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        UUID uuid = p.getUniqueId();
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invFFA)) {
            event.setCancelled(true);

            HashMap<String, ItemBuilder> subMap = new HashMap<>();

            String NotEnoughCoins = "§b§lMG-Land> §cVous n'avez pas assez de MGCoins pour acheter ceci !";
            String NotEnoughElos = "§b§lMG-Land> §cVous n'avez pas assez de MGElos pour acheter ceci !";

                StatsAccount statsAccount = (StatsAccount) memoryProvider.getAccount(main.mainMap, uuid, AllNames.statsPojo(AllNames.CJFFA.getName()));
                FFAKitsAccount ffaKitsAccount = (FFAKitsAccount) memoryProvider.getAccount(main.mainMap, uuid, AllNames.FFAKITSPOJO.getName());
                ItemStack itemStack = event.getCurrentItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    String displayName = itemMeta.getDisplayName();
                    if (displayName != null) {
                        switch (displayName) {
                            case "§a↩ Retour":
                                new BoutiqueMenu().open(p);
                                break;
                            case "§8■ §bGuerrier":
                                if (ffaKitsAccount.getGuerrier() == 2) {
                                    ffaKitsAccount.setGuerrier(1);
                                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                                } else if (ffaKitsAccount.getGuerrier() == 1) {
                                    ffaKitsAccount.setGuerrier(2);
                                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                }
                                itemStack.setItemMeta(itemMeta);
                                break;
                            case "§8■ §bArcher":
                                if (statsAccount.getCoins() > 1500 && ffaKitsAccount.getArcher() == 0) {
                                    // crée la map si elle n'existe pas.put(itemName, item);
                                    subMap.put(invFFA, new ItemBuilder(Material.BOW).setName("§8■ §bArcher").setLore("§7Équipements", "", "§8- §b1§7x §fArc", "§8- §b2§7x §fFlèches", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en fer", "§8- §b1§7x §fJambières en mailles", "§8- §b1§7x §fBottes en or"));
                                    ConfirmMenu.confirm.put(p.getUniqueId(), subMap);
                                    new ConfirmMenu().open(p);
                                } else if (statsAccount.getCoins() < 1500 && ffaKitsAccount.getArcher() == 0) {
                                    p.sendMessage(NotEnoughCoins);
                                }
                                if (ffaKitsAccount.getArcher() == 2) {
                                    ffaKitsAccount.setArcher(1);
                                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                                } else if (ffaKitsAccount.getArcher() == 1) {
                                    ffaKitsAccount.setArcher(2);
                                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                }
                                itemStack.setItemMeta(itemMeta);
                                break;
                            case "§8■ §bAssassin":
                                if (statsAccount.getCoins() > 2000 && ffaKitsAccount.getAssassin() == 0) {
                                    subMap.put(invFFA, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§8■ §bAssassin").setLore("§7Équipements", "", "§8- §b2§7x §fPotion de poison 20s", "§8- §b2§7x §fPotion d'invisibilité 20s", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en diamant", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en or"));
                                    ConfirmMenu.confirm.put(p.getUniqueId(), subMap);
                                    new ConfirmMenu().open(p);
                                } else if (statsAccount.getCoins() < 2000 && ffaKitsAccount.getAssassin() == 0) {
                                    p.sendMessage(NotEnoughCoins);
                                }

                                if (ffaKitsAccount.getAssassin() == 2) {
                                    ffaKitsAccount.setAssassin(1);
                                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                                } else if (ffaKitsAccount.getAssassin() == 1) {
                                    ffaKitsAccount.setAssassin(2);
                                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                }
                                itemStack.setItemMeta(itemMeta);
                                break;
                            case "§8■ §bPuncher":
                                if (statsAccount.getCoins() > 2500 && ffaKitsAccount.getPuncher() == 0) {
                                    subMap.put(invFFA, new ItemBuilder(Material.RAW_FISH).setName("§8■ §bPuncher").setLore("§7Équipements", "", "§8- §b1§7x §fBâton KnockBack IV", "§8- §b1§7x §fCanne à pêche", "§8- §b2§7x §fPommes dorées", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en diamant", "§8- §b1§7x §fJambières en fer", "§8- §b1§7x §fBottes en or"));
                                    ConfirmMenu.confirm.put(p.getUniqueId(), subMap);
                                    new ConfirmMenu().open(p);
                                } else if (statsAccount.getCoins() < 2500 && ffaKitsAccount.getPuncher() == 0) {
                                    p.sendMessage(NotEnoughCoins);
                                }

                                if (ffaKitsAccount.getPuncher() == 2) {
                                    ffaKitsAccount.setPuncher(1);
                                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                                } else if (ffaKitsAccount.getPuncher() == 1) {
                                    ffaKitsAccount.setPuncher(2);
                                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                }
                                itemStack.setItemMeta(itemMeta);
                                break;
                            case "§8■ §bCheaté":

                                if (statsAccount.getElos() > 800 && ffaKitsAccount.getCheated() == 0) {
                                    subMap.put(invFFA, new ItemBuilder(Material.DIAMOND).setName("§8■ §bCheaté").setLore("§7Équipements", "", "§8- §b1§7x §fPomme cheatée", "§8- §b3§7x §fEnderpearls", "§8- §b1§7x §fCasque en fer", "§8- §b1§7x §fPlastron en diamant", "§8- §b1§7x §fJambières en diamant", "§8- §b1§7x §fBottes en or"));
                                    ConfirmMenu.confirm.put(p.getUniqueId(), subMap);
                                    new ConfirmMenu().open(p);
                                } else if (statsAccount.getElos() < 800 && ffaKitsAccount.getCheated() == 0) {
                                    p.sendMessage(NotEnoughElos);
                                }
                                if (ffaKitsAccount.getCheated() == 2) {
                                    ffaKitsAccount.setCheated(1);
                                    itemMeta.removeEnchant(Enchantment.PROTECTION_ENVIRONMENTAL);
                                } else if (ffaKitsAccount.getCheated() == 1) {
                                    ffaKitsAccount.setCheated(2);
                                    itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                                }
                                itemStack.setItemMeta(itemMeta);
                                break;
                            default:
                                break;
                        }

                    }
                }

        }

    }
}