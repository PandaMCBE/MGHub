package fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop;

import fr.pandamcbe.commons.FFAKitsAccount;
import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.commons.StatsAccount;
import fr.pandamcbe.commons.WornAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.core.rediscache.RedisAccountsManager;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons.SonsItems;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.games.ffa.FFAItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ConfirmMenu implements Listener {

    public static final String invConfirm = "§8§l⨠ Confirmation";
    private static final String confirmOption = "§a§l[Confirmer l'achat]";
    private static final String cancelOption = "§c§l[Annuler l'achat]";
    private final MGHub main = MGHub.getInstance();
    private final RedisAccountsManager connections = new RedisAccountsManager();
    private final MemAccountsManipulation manipulation = Main.getInstance().manipulation;

    public static Map<UUID, HashMap<String, ItemBuilder>> confirm= new HashMap<>();

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, invConfirm);

        HashMap<String, ItemBuilder> subMap = confirm.get(p.getUniqueId());
        inv.setItem(2, new ItemBuilder(Material.WOOL).setDurability((byte) 5).setName(confirmOption).toItemStack());
        inv.setItem(4, subMap.get(SonsItems.invSound).toItemStack());
        inv.setItem(6, new ItemBuilder(Material.WOOL).setDurability((byte) 14).setName(cancelOption).toItemStack());

        p.openInventory(inv);
    }

    private static String getMenuName(HashMap<String, ItemBuilder> map){
       return map.keySet().iterator().next();
    }

    private void whichMenu(String key, String name, String displayName, Player p){
        UUID uuid = p.getUniqueId();
        String buyingCancelled = "§b§lMG-Land> §cL'achat a été annulé !";
        switch (key) {
            case FFAItems.invFFA:
                if(displayName.equalsIgnoreCase(confirmOption)) {
                    proceedBuyKits(name, uuid, connections);
                } else if (displayName.equalsIgnoreCase(cancelOption)) {
                    p.sendMessage(buyingCancelled);
//                    Main.getInstance().gameMechanics.changeMenu(p, new FFAItems().open(p));
                    new FFAItems().open(p);
                }
                break;
            case SonsItems.invSound:
                if(displayName.equalsIgnoreCase(confirmOption)) {
                    proceedBuySound(name, uuid, connections);
                } else if (displayName.equalsIgnoreCase(cancelOption)) {
                    p.sendMessage(buyingCancelled);
                    new SonsItems().open(p);
                }
                break;
            default:
                break;
        }
        confirm.remove(p.getUniqueId());
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invConfirm)) {
            event.setCancelled(true);

                HashMap<String, ItemBuilder> subMap = confirm.get(p.getUniqueId());
                ItemBuilder itemStack = subMap.get(getMenuName(subMap));
                String name = itemStack.getName();
                ItemMeta itemMeta = event.getCurrentItem().getItemMeta();

                if (itemMeta != null) {
                    String displayName = itemMeta.getDisplayName();
                    if (displayName != null) {
                        whichMenu(getMenuName(subMap), name, displayName, p);
                    }
                }
        }
    }

    private void proceedBuyKits(String name, UUID uuid, RedisAccountsManager connections) {
        Bukkit.getScheduler().runTask(main, () -> {

            ProfilAccount coins = (ProfilAccount) Main.getInstance().manipulation.getProfilAccount(main.getMainMap(), uuid, AccountType.PROFIL.getKeyPrefix());
            StatsAccount statsAccount = (StatsAccount) Main.getInstance().manipulation.getStatsAccount(main.getMainMap(), uuid, AccountType.statsKey(AllNames.CJFFA.getName()));
            FFAKitsAccount ffaKitsAccount = (FFAKitsAccount) Main.getInstance().manipulation.getShopAccounts(main.getMainMap(), uuid, AccountType.CJFFAKITS.getKeyPrefix());
            String[] keys = {AccountType.PROFIL.getKeyPrefix(), AccountType.statsKey(AllNames.CJFFA.getName()), AccountType.CJFFAKITS.getKeyPrefix()};
            Player p = Bukkit.getPlayer(uuid);
            switch (name) {
                case "§8■ §bArcher":
                    coins.setCoins(coins.getCoins() - 1500);
                    ffaKitsAccount.setArcher(1);
                    p.sendMessage("§b§lMG-Land> §aLe Kit §6" + AllNames.KIT_ARCHER.getName() + " §aa été acheté avec succès ! §c-1500,\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.");
                    break;
                case "§8■ §bAssassin":
                    coins.setCoins(coins.getCoins() - 2000);
                    ffaKitsAccount.setAssassin(1);
                    p.sendMessage("§b§lMG-Land> §aLe Kit §6" + AllNames.KIT_ASSASSIN.getName() + " §aa été acheté avec succès ! §c-2000,\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.");
                    break;
                case "§8■ §bPuncher":
                    coins.setCoins(coins.getCoins() - 2500);
                    ffaKitsAccount.setPuncher(1);
                    p.sendMessage("§b§lMG-Land> §aLe Kit §6" + AllNames.KIT_PUNCHER.getName() + " §aa été acheté avec succès ! §c-160,\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.");
                    break;
                case "§8■ §bCheaté":
                    statsAccount.setElos(statsAccount.getElos() - 800);
                    ffaKitsAccount.setCheated(1);
                    p.sendMessage("§b§lMG-Land> §aLe Kit §6" + AllNames.KIT_CHEATED.getName() + " §aa été acheté avec succès ! §c-500,\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.");
                    break;
                default:
                    break;
            }
            new FFAItems().open(p);
            Bukkit.getScheduler().runTaskAsynchronously(main, () ->
                    connections.sendAccounts(uuid, keys, coins, statsAccount, ffaKitsAccount));
        });



    }
    @SuppressWarnings("unchecked")
    private void proceedBuySound(String name, UUID uuid, RedisAccountsManager connections) {
        ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getProfilAccount(main.getMainMap(), uuid, AccountType.PROFIL.getKeyPrefix());
        Player p = Bukkit.getPlayer(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), ()-> {

            Set<Integer> getCosme = (Set<Integer>) connections.getProfilAccount(uuid, AccountType.cosmeKey(AllNames.SOUND_ID.getName()));

            switch (name) {
                case "§fHat §8[NOTEBLOCK]":
                    getCosme.add(10);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 1500);
                        p.sendMessage(youOwnItWithCoins(name, 1500));
                    });
//                connections.sendWearedToRedis(a);
                    break;
                case "§fBd §8[NOTEBLOCK]":
                    getCosme.add(20);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
//                connections.sendWearedToRedis(a);
                    break;
                case "§fBass §8[NOTEBLOCK]":
                    getCosme.add(30);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 2000);
                        p.sendMessage(youOwnItWithCoins(name, 2000));
                    });
                    break;
                case "§fPling §8[NOTEBLOCK]":
                    getCosme.add(40);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
//                        Main.getInstance().gameMechanics.changeMenu(p, open(p));
                    });
                    break;
                case "§fHarp §8[NOTEBLOCK]":
                    getCosme.add(50);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 1000);
                        p.sendMessage(youOwnItWithCoins(name, 1000));
                    });
                    break;
                case "§fSnare §8[NOTEBLOCK]":
                    getCosme.add(60);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§fWolf's Death §8[MOB]":
                    getCosme.add(70);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§fSkeleton's Death §8[MOB]":
                    getCosme.add(80);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 2500);
                        p.sendMessage(youOwnItWithCoins(name, 2500));
                    });
                    break;
                case "§fVillager's Death §8[MOB]":
                    getCosme.add(90);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 3000);
                        p.sendMessage(youOwnItWithCoins(name, 3000));
                    });
                    break;
                case "§fBat's Death §8[MOB]":
                    getCosme.add(100);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 3500);
                        p.sendMessage(youOwnItWithCoins(name, 3500));
                    });
                    break;
                case "§fBlaze's Death §8[MOB]":
                    getCosme.add(110);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 4000);
                        p.sendMessage(youOwnItWithCoins(name, 4000));
                    });
                    break;
                case "§fWither's Death §8[MOB]":
                    getCosme.add(120);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§fEnderman's Death §8[MOB]":
                    getCosme.add(130);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§fClick §8[OTHERS]":
                    getCosme.add(140);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 4500);
                        p.sendMessage(youOwnItWithCoins(name, 4500));
                    });
                    break;
                case "§fGlass §8[OTHERS]":
                    getCosme.add(150);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 5000);
                        p.sendMessage(youOwnItWithCoins(name, 5000));
                    });
                    break;
                case "§fLevel Up §8[OTHERS]":
                    getCosme.add(160);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§f§fExplode §8[OTHERS]":
                    getCosme.add(170);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 5500);
                        p.sendMessage(youOwnItWithCoins(name, 5500));
                    });
                    break;
                case "§fHit §8[OTHERS]":
                    getCosme.add(180);
                    Bukkit.getScheduler().runTask(main, () -> {
                        profilAccount.setCoins(profilAccount.getCoins() - 6000);
                        p.sendMessage(youOwnItWithCoins(name, 6000));
                    });
                    break;
                case "§fFirework §8[OTHERS]":
                    getCosme.add(190);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§fBurp  §8[OTHERS]":
                    getCosme.add(200);
                    Bukkit.getScheduler().runTask(main, () -> {
                        p.sendMessage(youOwnItWithRank(name));
                    });
                    break;
                case "§cDéséquiper":
                    Bukkit.getScheduler().runTask(main, ()-> {
                        WornAccount wearedAccount = (WornAccount) manipulation.getShopAccounts(MGHub.getInstance().getMainMap(), uuid, AccountType.COSMEWORN.getKeyPrefix());
                        wearedAccount.setSound_id(1);
                        p.sendMessage("§b§lMG-Land> §aTous les cosmétiques de la catégories des sons ont été retiré avec succès !");
                    });
                    break;
                default:
                    break;
            }
            new SonsItems().open(p);
            connections.sendAccountToRedisType(uuid, AccountType.PROFIL.getKeyPrefix(), profilAccount);

        });

    }

    private String youOwnItWithCoins(String name, int price){
        return "§b§lMG-Land> §aLe cosmétique §6" + name + " §aa été acheté avec succès ! §c-"+price+",\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.";
    }

    private String youOwnItWithRank(String name){
        return "§b§lMG-Land> §aLe cosmétique §6" + name + " §aa été acheté avec succès !,\n§eVous pouvez à présent aller l'équiper, §6§nc'est votre choix.";

    }
}