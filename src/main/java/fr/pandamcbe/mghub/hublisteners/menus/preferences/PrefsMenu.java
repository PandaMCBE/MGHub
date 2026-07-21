package fr.pandamcbe.mghub.hublisteners.menus.preferences;

import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.mainmenu.MGMenu;
import fr.pandamcbe.mghub.hublisteners.menus.refreshs.RefreshPlayerView;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class PrefsMenu implements Listener {

    private final MGHub mgHub = MGHub.getInstance();

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        UUID uuid = p.getUniqueId();
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(PrefsItems.invPrefs)) {
            event.setCancelled(true);
            ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getAccount(mgHub.mainMap, uuid, AllNames.PROFILPOJO.getName());
            ItemStack updateItem = event.getCurrentItem();
            ItemMeta updateMeta = updateItem.getItemMeta();
            switch (event.getCurrentItem().getType()) {
                //Prefs

                case DARK_OAK_DOOR_ITEM:
                    p.closeInventory();
                    break;
                case ENDER_PEARL:
                    new MGMenu().open(p);
                    break;
                case FEATHER:
                    if (profilAccount.getMsg_allow() == 0) {
                        profilAccount.setMsg_allow(1);
                        p.sendMessage("§eDEMANDES D'MP §aACTIVES");
                        updateMeta.setDisplayName("§6Messages privÉs §7» §aON");

                    } else {
                        profilAccount.setMsg_allow(0);
                        p.sendMessage("§eDEMANDES D'MP §cDESACTIVES");
                        updateMeta.setDisplayName("§6Messages privÉs §7» §cOFF");

                    }
                    updateItem.setItemMeta(updateMeta);

                    break;
                case BOOK:
                    if (profilAccount.getC_visibility() == 0) {
                        profilAccount.setC_visibility(1);
                        p.sendMessage("§eVISIBILITEE DU CHAT GLOBAL §aACTIVE");
                        updateMeta.setDisplayName("§6Chat Global §7» §aON");
                    } else {
                        profilAccount.setC_visibility(0);
                        p.sendMessage("§eVISIBILITEE DU CHAT GLOBAL §cDESACTIVE");
                        updateMeta.setDisplayName("§6Chat Global §7» §cOFF");

                    }
                    updateItem.setItemMeta(updateMeta);
                    break;
//                                    case RED_ROSE:
//                                        if (!(a2.getF_allow() == 1)) {
//                                            a2.setF_allow(1);
//                                            p.sendMessage("§e§lDEMANDES D'AMIS §a§lACTIVES");
//                                        } else if (a2.getF_allow() == 1) {
//                                            a2.setF_allow(0);
//                                            p.sendMessage("§e§lDEMANDES D'AMIS §a§lDESACTIVES");
//                                        }
//                                        break;
                case SKULL_ITEM:
                    if (profilAccount.getP_visibility() == 0) {
                        profilAccount.setP_visibility(1);
                        p.sendMessage("§eVISIBILITEE DES JOUEURS AU LOBBY §aACTIVE");
                        updateMeta.setDisplayName("§6VisibilitÉe des joueurs §7» §aON");

                    } else {
                        profilAccount.setP_visibility(0);
                        p.sendMessage("§eVISIBILITEE DES JOUEURS AU LOBBY §cDESACTIVE");
                        updateMeta.setDisplayName("§6VisibilitÉe des joueurs §7» §cOFF");

                    }
                    updateItem.setItemMeta(updateMeta);

                    new RefreshPlayerView(uuid);
                    break;

                case YELLOW_FLOWER:
                    if (profilAccount.getDuel_allow() == 0) {
                        profilAccount.setDuel_allow(1);
                        p.sendMessage("§eDEMANDES DE DUELS §aACTIVES");
                        updateMeta.setDisplayName("§6Demandes de duels §7» §aON");
                    } else {
                        profilAccount.setDuel_allow(0);
                        p.sendMessage("§eDEMANDES DE DUELS §cDESACTIVES");
                        updateMeta.setDisplayName("§6Demandes de duels §7» §cOFF");
                    }

                    updateItem.setItemMeta(updateMeta);
                    break;
                default:
                    break;
            }
        }
    }

}