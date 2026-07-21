package fr.pandamcbe.mghub.hublisteners.mainevents;

import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.BoutiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.mainmenu.MGMenu;
import fr.pandamcbe.mghub.hublisteners.menus.preferences.PrefsItems;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParameterListeners implements Listener {
    @EventHandler
    public void onTakeDamages(EntityDamageEvent event) {

        //Si l'entité est une instance de player
        if (event.getEntity() instanceof Player) {

            //Player = entity
            Player p = (Player) event.getEntity();
            //Si le nom du monde est worldlobby
            if (p.getWorld().getName().equalsIgnoreCase("worldlobby")) {
                //on annule l'action des damages sur le player
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // Annule le changement de niveau de faim du joueur
            event.setCancelled(true);

            // Définit le niveau de faim du joueur à 20 (maximum)
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Location spawn = new Location(p.getWorld(), 100000.500f, 57, 100000.500f, -90.0f, -1.0f);

        // Vérifiez si le joueur est en dessous ou à la couche Y spécifiée (par exemple, Y <= 20).
        if (p.getGameMode() != GameMode.CREATIVE && p.getLocation().getY() <= 20) {
            // Si oui, téléportez-le au lobby (utilisez votre variable spawn).
            p.teleport(spawn);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
//                if (event.getRightClicked() instanceof ItemFrame || event.getRightClicked() instanceof Painting || event.getRightClicked() instanceof ) {
//                    event.setCancelled(true);
//                }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void OnProjectile(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof EnderPearl) {
            e.setCancelled(true);
        }
    }

    //on annule le drop des items
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Entity entity = event.getItemDrop();
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (player.getItemInHand().getType().equals(Material.WRITTEN_BOOK) || player.getItemInHand().getType().equals(Material.ENDER_PEARL) || !player.getItemInHand().getType().equals(Material.REDSTONE_COMPARATOR)) {
            event.setCancelled(true);
        } else entity.remove();

//        for (Entity e : player.getWorld().getEntities()) {
//            if (e instanceof Item) {
//                Item item = (Item) e;
//                if (!item.getItemStack().getType().equals(Material.WRITTEN_BOOK) || item.getItemStack().getType().equals(Material.ENDER_PEARL) || !item.getItemStack().getType().equals(Material.REDSTONE_COMPARATOR)) {
//                    item.remove();
//                }
//            }
//        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onSelect(InventoryClickEvent event) {
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem != null) {
            // Vérifiez ici si l'item doit être annulé ou non.
            if (clickedItem.getItemMeta() != null && clickedItem.getItemMeta().getDisplayName() != null && (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8§l[§7§l⨠ §d§lMg'sPrefs§8§l]") || clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8§l[§7§l⨠ §b§lMg'sMain§8§l]") || clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("§8§l[§7§l⨠ §6§lMg'sShop§8§l]"))) {
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action action = event.getAction();
        ItemStack it = event.getItem();
        if (it != null) {
            ItemMeta meta = it.getItemMeta();
            String displayname = meta.getDisplayName();
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                if (displayname != null) {
                    switch (displayname) {
                        case "§8§l[§7§l⨠ §d§lMg'sPrefs§8§l]":
                            event.setCancelled(true);
                            new PrefsItems().open(p); //J'ai switché la position des deux lignes pour voir si l'enderpearl se stop avant
                            break;
                        case "§8§l[§7§l⨠ §b§lMg'sMain§8§l]":
                            event.setCancelled(true);
                            new MGMenu().open(p);
                            break;
                        case "§8§l[§7§l⨠ §6§lMg'sShop§8§l]":
                            event.setCancelled(true);
                            new BoutiqueMenu().open(p);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        if (event.getClickedBlock() != null) {
            if(action == Action.RIGHT_CLICK_BLOCK) {
                BlockState bs = event.getClickedBlock().getState();
                if (bs instanceof Sign) {
                    Sign s = (Sign) bs;
                    if (s.getLine(0).equalsIgnoreCase("§b§lDonnez nous")) {
                        p.getInventory().addItem(new ItemBuilder(Material.BOOK_AND_QUILL).toItemStack());
                        p.updateInventory();
                    }
                }
                if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                    Block clickedBlock = event.getClickedBlock();
                    Location location = new Location(Bukkit.getWorld("worldlobby"), 100043, 58, 99989);
                    Block specifictrapdoor = location.getBlock();
                    if (!clickedBlock.getType().equals(Material.CHEST)) {
                        event.setCancelled(true);
                    } else if (clickedBlock.getType().equals(Material.TRAP_DOOR) && !clickedBlock.getLocation().getBlock().equals(specifictrapdoor)) {
                            event.setCancelled(true);
                    }
                }
            } else if (action == Action.LEFT_CLICK_BLOCK) {
                if (!p.getGameMode().equals(GameMode.CREATIVE)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        Player p = event.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player) {
            Player player = (Player) event.getRemover();
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemFrameDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ItemFrame && event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            // Autoriser les joueurs en mode créatif
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }

            // Bloquer les dégâts aux cadres (empêche le retrait et la rotation des objets)
            event.setCancelled(true);
        }
    }

}