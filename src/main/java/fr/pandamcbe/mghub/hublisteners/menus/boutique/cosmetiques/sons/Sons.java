package fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons;

import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.commons.WornAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.DirtyManager;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.core.rediscache.RedisAccountsManager;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.cosmemenu.CosmetiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.ConfirmMenu;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static fr.pandamcbe.mghub.hublisteners.menus.boutique.cosmetiques.sons.SonsItems.invSound;

public class Sons implements Listener {
    private final MGHub main = MGHub.getInstance();
    MemAccountsManipulation memAM = Main.getInstance().manipulation;
    private final DirtyManager dirtyManager = new DirtyManager();
    //Partie sons
    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        UUID uuid = p.getUniqueId();
        User user = main.luckPerms.getPlayerAdapter(Player.class).getUser(p);
        Group group = main.luckPerms.getGroupManager().getGroup(user.getPrimaryGroup());

        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invSound)) {
            event.setCancelled(true);
            String NotEnoughCoins = "§b§lMG-Land> §cVous n'avez pas assez de MGCoins pour acheter ceci !";
            String NotRightRank = "§b§lMG-Land> §cVous n'avez pas le grade requis pour acheter ceci !";

            HashMap<String, ItemBuilder> subMap = new HashMap<>();
            ProfilAccount profilAccount = (ProfilAccount) memAM.getProfilAccount(MGHub.getInstance().mainMap, uuid, AccountType.PROFIL.getKeyPrefix());
            WornAccount wornAccount = (WornAccount) memAM.getShopAccounts(MGHub.getInstance().mainMap, uuid, AccountType.COSMEWORN.getKeyPrefix());
            Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
                RedisAccountsManager connections = new RedisAccountsManager();

                    Set<Integer> getCosme = (Set<Integer>) connections.getShopAccount(uuid, AccountType.cosmeKey(AllNames.SOUND_ID.getName()));
                Bukkit.getScheduler().runTask(main, ()-> {

                    ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
                    if (itemMeta != null) {
                        String displayName = itemMeta.getDisplayName();

                        if (itemMeta.hasDisplayName()) {

                            if (displayName != null) {

                                switch (displayName) {
                                    case "§cDéséquiper":
                                        Bukkit.getScheduler().runTask(main, () -> {
                                            subMap.put(invSound, new ItemBuilder(Material.BARRIER).setName("§cDéséquiper").setLore("§eCliquez pour aucun son."));
                                            ConfirmMenu.confirm.put(uuid, subMap);
                                            new ConfirmMenu().open(p);
                                        });
                                        break;
                                    case "§a↩ Retour":
                                        Bukkit.getScheduler().runTask(main, () ->
                                                new CosmetiqueMenu().open(p));
                                        break;
                                    case "§fHat §8[NOTEBLOCK]":
                                        if (profilAccount.getCoins() > 1500 && !getCosme.contains(10)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHat §8[NOTEBLOCK]").setLore("§fRareté : §aCommun"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 1500 && !getCosme.contains(10)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(10)) {
                                            wornAccount.setSound_id(10);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fBd §8[NOTEBLOCK]":
                                        if (group.getWeight().getAsInt() >= 30 && !getCosme.contains(20)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBd §8[NOTEBLOCK").setLore("§fRareté : LÉgendaire"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 30 && !getCosme.contains(20)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(20)) {
                                            wornAccount.setSound_id(20);
                                            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                                                new CosmetiqueMenu().open(p);
                                            });
                                        }
                                        break;
                                    case "§fBass §8[NOTEBLOCK]":
                                        if (profilAccount.getCoins() > 2000 && !getCosme.contains(30)) {
                                            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fBass §8[NOTEBLOCK]").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 2000 && !getCosme.contains(30)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(30)) {
                                            wornAccount.setSound_id(30);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fPling §8[NOTEBLOCK]":
                                        if (!getCosme.contains(40)) {
//                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fPling §8[NOTEBLOCK]").setLore("§fRareté : §aCommun").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
//                                            });
                                        }
                                        if (getCosme.contains(40)) {
                                            wornAccount.setSound_id(40);
//                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p);
                                        }
                                        break;
                                    case "§fHarp §8[NOTEBLOCK]":
                                        if (profilAccount.getCoins() > 1000 && !getCosme.contains(50)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fHarp §8[NOTEBLOCK]").setLore("§fRareté : §aCommun"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 1000 && !getCosme.contains(50)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(50)) {
                                            wornAccount.setSound_id(50);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fSnare §8[NOTEBLOCK]":
                                        if (group.getWeight().getAsInt() >= 20 && !getCosme.contains(60)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.NOTE_BLOCK).setName("§fSnare §8[NOTEBLOCK]").setLore("§fRareté : §5Épique"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 20 && !getCosme.contains(60)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(60)) {
                                            wornAccount.setSound_id(60);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fWolf's Death §8[MOB]":
                                        if (!getCosme.contains(70)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 95).setName("§fWolf's Death §8[MOB]").setLore("§fRareté : §aCommun"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        }
                                        if (getCosme.contains(70)) {
                                            wornAccount.setSound_id(70);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fSkeleton's Death §8[MOB]":
                                        if (profilAccount.getCoins() > 2500 && !getCosme.contains(80)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 51).setName("§fSkeleton's Death §8[MOB]").setLore("§fRareté : §aCommun"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 2500 && !getCosme.contains(80)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(80)) {
                                            wornAccount.setSound_id(80);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fVillager's Death §8[MOB]":
                                        if (profilAccount.getCoins() > 3000 && !getCosme.contains(90)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 120).setName("§fVillager's Death §8[MOB]").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 3000 && !getCosme.contains(90)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(90)) {
                                            wornAccount.setSound_id(90);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fBat's Death §8[MOB]":
                                        if (profilAccount.getCoins() > 3500 && !getCosme.contains(100)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 65).setName("§fBat's Death §8[MOB]").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 3500 && !getCosme.contains(100)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(100)) {
                                            wornAccount.setSound_id(100);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fBlaze's Death §8[MOB]":
                                        if (profilAccount.getCoins() > 4000 && !getCosme.contains(110)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 61).setName("§fBlaze's Death §8[MOB]").setLore("§fRareté : §5Épique"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 4000 && !getCosme.contains(110)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(110)) {
                                            wornAccount.setSound_id(110);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fWither's Death §8[MOB]":
                                        if (group.getWeight().getAsInt() >= 30 && !getCosme.contains(120)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.SKULL_ITEM).setDurability((byte) 1).setName("§fRareté : §6LÉgendaire"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 30 || group.getWeight().getAsInt() == 4 && !getCosme.contains(120)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(120)) {
                                            wornAccount.setSound_id(120);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fEnderman's Death §8[MOB]":
                                        if (group.getWeight().getAsInt() >= 20 && !getCosme.contains(130)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MONSTER_EGG).setDurability((byte) 58).setName("§fEnderman's Death §8[MOB]").setLore("§fRareté : §6LÉgendaire"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 20 && !getCosme.contains(130)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(130)) {
                                            wornAccount.setSound_id(130);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fClick §8[OTHERS]":
                                        if (profilAccount.getCoins() > 4500 && !getCosme.contains(140)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.LEVER).setName("§fClick §8[OTHERS]").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 4500 && !getCosme.contains(140)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(140)) {
                                            wornAccount.setSound_id(140);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fGlass §8[OTHERS]":
                                        if (profilAccount.getCoins() > 5000 && !getCosme.contains(150)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.GLASS).setName("§fGlass §8[OTHERS]").setLore("§fRareté : §9Rare"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 5000 && !getCosme.contains(150)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(150)) {
                                            wornAccount.setSound_id(150);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fLevel Up §8[OTHERS]":
                                        if (!getCosme.contains(160)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.MAGMA_CREAM).setName("§fLevel Up §8[OTHERS]").setLore("§fRareté : §aCommun"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        }
                                        if (getCosme.contains(160)) {
                                            wornAccount.setSound_id(160);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fExplode §8[OTHERS]":
                                        if (profilAccount.getCoins() > 5500 && !getCosme.contains(170)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.TNT).setName("§fExplode §8[OTHERS]").setLore("§fRareté : §5Épique"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 5500 && !getCosme.contains(170)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(170)) {
                                            wornAccount.setSound_id(170);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fHit §8[OTHERS]":
                                        if (profilAccount.getCoins() > 6000 && !getCosme.contains(180)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.ARROW).setName("§fHit §8[OTHERS]").setLore("§fRareté : §5Épique"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (profilAccount.getCoins() < 6000 && !getCosme.contains(180)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotEnoughCoins));
                                        }
                                        if (getCosme.contains(180)) {
                                            wornAccount.setSound_id(180);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fFirework §8[OTHERS]":
                                        if (group.getWeight().getAsInt() >= 20 && !getCosme.contains(190)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.FIREWORK).setName("§fFirework §8[OTHERS]").setLore("§fRareté : §6LÉgendaire"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 20 && !getCosme.contains(190)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(190)) {
                                            wornAccount.setSound_id(190);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    case "§fBurp §8[OTHERS]":
                                        if (group.getWeight().getAsInt() >= 30 && !getCosme.contains(200)) {
                                            Bukkit.getScheduler().runTask(main, () -> {
                                                subMap.put(invSound, new ItemBuilder(Material.COOKED_BEEF).setName("§fBurp §8[OTHERS]").setLore("§fRareté : §6LÉgendaire"));
                                                ConfirmMenu.confirm.put(uuid, subMap);
                                                new ConfirmMenu().open(p);
                                            });
                                        } else if (group.getWeight().getAsInt() < 30 && !getCosme.contains(200)) {
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    p.sendMessage(NotRightRank));
                                        }
                                        if (getCosme.contains(200)) {
                                            wornAccount.setSound_id(200);
                                            Bukkit.getScheduler().runTask(main, () ->
                                                    new CosmetiqueMenu().open(p));
                                        }
                                        break;
                                    default:
                                        break;

                                }
                                dirtyManager.markDirty(uuid, AccountType.COSMEWORN.getKeyPrefix(), wornAccount);
                            }
                        }
//                    connections.sendAccounts(uuid, keysName, profilAccount, wearedAccount);
                    }
                });

            });
        }
    }
}