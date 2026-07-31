package fr.pandamcbe.mghub.hublisteners.mainevents;

import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.boards.samaboardsclasses.PersonalScoreboard;
import fr.pandamcbe.mghub.hublisteners.menus.refreshs.RefreshPlayerView;
import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinListeners implements Listener {

    private final MGHub mgHub = MGHub.getInstance();
    private final MemAccountsManipulation memAccountsManipulation = Main.getInstance().manipulation;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        Location location = p.getLocation();
        mgHub.getScoreboardManager().onLogin(new PersonalScoreboard(p), p);
        User user = mgHub.luckPerms.getPlayerAdapter(Player.class).getUser(p);
        if (p.getGameMode() != GameMode.CREATIVE) p.setGameMode(GameMode.ADVENTURE);
        p.getInventory().clear();
        event.setJoinMessage(user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§a§l+§8§l]");
        p.teleport(new Location(location.getWorld(), 100000.500f, 57, 100000.500f, -90.0f, -1.0f));
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        p.setHealth(20.0);
        LoadItems(p);
//        p.setLevel(796); //petite ref à PuceronPanda796
        String[] keys = {AllNames.PROFILPOJO.getName(),AllNames.WORNPOJO.getName(), AllNames.FFAKITSPOJO.getName()};
        memAccountsManipulation.getFromRedisSendToMemory(mgHub.mainMap, uuid, keys);
        p.sendMessage("§3§lMG-Land §rNOUVEAU : MGConstructorAPI\n" + "§l§8§m-----§r§l§5BON JEU !§8§m-----");
        p.sendTitle("§a§lMG§7§l-§6§lLand", "§6§lServeur §d§lPvP§a§lx§d§lMini-jeux");

        if(!p.hasPlayedBefore()) {
            Bukkit.broadcastMessage("§b§lNOUVEAU: §f§lSouhaitez tous la bienvenue à " + p.getName() + " §f§lsur §a§lMG§7§l-§6§lLand §7§l(§e§lPandaFun 6.0§7§l) §f§l!");
            mgHub.getFireworksManager().spawnFirework(p.getLocation(), "fw1");
        }
        new RefreshPlayerView(uuid);
    }

  private void LoadItems(Player p){
      p.getInventory().setItem(2, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§8§l[§7§l⨠ §d§lMg'sPrefs§8§l]").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
      p.getInventory().setItem(4, new ItemBuilder(Material.ENDER_PEARL).setName("§8§l[§7§l⨠ §b§lMg'sMain§8§l]").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
      p.getInventory().setItem(6, new ItemBuilder(Material.WRITTEN_BOOK).setName("§8§l[§7§l⨠ §6§lMg'sShop§8§l]").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
      p.updateInventory();
  }

    //Le message quand le joueur quitte
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        MGHub mgHub = MGHub.getInstance();
        User user = mgHub.luckPerms.getPlayerAdapter(Player.class).getUser(p);
        event.setQuitMessage(user.getCachedData().getMetaData().getPrefix() + " §r" + p.getName() + " §8§l[§c§l-§8§l]");
        mgHub.getScoreboardManager().onLogout(p);
        Main.getInstance().sendMemToCache.saveDatasToCache(p.getUniqueId(), mgHub.mainMap);
        Main.getInstance().manipulation.deleteAccount(p.getUniqueId(), mgHub.mainMap);
    }

}