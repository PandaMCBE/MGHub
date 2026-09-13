package fr.pandamcbe.mghub.hublisteners.menus.mainmenu;

import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.NetworkState;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.CustomHeadUtil;
import fr.pandamcbe.mgapi.serverutilapi.utils.items.ItemBuilder;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.hublisteners.menus.boutique.menushop.BoutiqueMenu;
import fr.pandamcbe.mghub.hublisteners.menus.preferences.PrefsItems;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MGMenu implements Listener {

    public static final String invMain = "§8§l⨠ Menu Principal";

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(p, 36, invMain);

        final User user = MGHub.getInstance().luckPerms.getPlayerAdapter(Player.class).getUser(p);
        UUID uuid = p.getUniqueId();

       NetworkState networkState = MGHub.getInstance().getNetworkState();

       ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getProfilAccount(MGHub.getInstance().mainMap, uuid, AccountType.PROFIL.getKeyPrefix());//

                ItemStack out = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 5).setName("").toItemStack();

                inv.setItem(0, out);

                inv.setItem(9, out);

                inv.setItem(18, out);

                inv.setItem(27, out);

                ItemStack out1 = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 1).setName("").toItemStack();
                inv.setItem(8, out1);
                inv.setItem(17, out1);
                inv.setItem(26, out1);
                inv.setItem(35, out1);

                ItemStack out2 = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((byte) 7).setName("").toItemStack();
                inv.setItem(3, out2);
                inv.setItem(4, out2);
                inv.setItem(5, out2);
                inv.setItem(30, out2);
                inv.setItem(31, out2);
                inv.setItem(32, out2);

                String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYxNTlhMWNiZTE3M2Q5MjczOTJmYTY1ZmNmZTc4NTUzZDgxYWE1ZGMyOTQ5NWI5ZmJiYWRlMzYyZjhiZjkifX19";
                ItemStack customHead = CustomHeadUtil.getCustomSkull(texture);

//                inv.setItem(12, new ItemBuilder(Material.GOLDEN_APPLE).setName("§8■ §a§lSG").setLore("§8Type: Mini-jeu x PvP", "", "§d? §7Allez explorer la map", "§7pour trouver de nombreux", "§7coffres, avec du stuff", "§7pour survivre et gagner", "§7tous vos combats!", "§7Soyez le dernier ", "§7en vie pour gagner!", "§e§lEn ligne §6§l:§b§l " + SG, "§c[1.8+]", "", "§aCliques pour jouer").toItemStack());
                inv.setItem(12, new ItemBuilder(customHead).setName("§8■ §a§lO.N.A.P").setLore("§8Type: Mini-jeu", "", "§d? §7De son vrai nom :", "§7§fOne Night At Panda's§7,", "§7il s'agit en effet d'un", "§7mini-jeu dont le gameplay est", "§7basé sur la très célèbre", "§7franchise de jeux d'horreur", "§7(Fnaf). Dans ce jeu vous", "§7devrez donc vous protéger", "§7des autres malicieux joueurs", "§7en balade durant la nuit", "§7(dans le rôle de vilains animatroniques)",  "§7en tant que gardien de", "§7nuit chez PandaFun'sPizza ! ", "§fDécouvrez des maps inédites !", "§e§lEn ligne §6§l:§b§l " + 0, "§c[1.8+]", "", "§aClique pour jouer").toItemStack());
                inv.setItem(13, new ItemBuilder(Material.EGG).setName("§8■ §a§lSPLEGG").setLore("§8Type: Mini-jeu", "", "§d? §7Détruisez les blocks", "§7sous vos énemies pour", "§7les faire tomber", "§7à l'aide de votre ", "§7EggGun ! Soyez le  ", "§7dernier en vie ", "§7pour gagner!", "§e§lEn ligne §6§l:§b§l " + 0, "§c[1.8+]", "", "§aClique pour jouer").toItemStack());
                inv.setItem(14, new ItemBuilder(Material.BOW).setName("§8■ §a§lPERFECTSHOT").setLore("§8Type: Mini-jeu", "", "§d? §7Eliminez en un coup ", "§7vos énemies avec", "§7votre arc surcheaté!", "§7Soyez le dernier", "§7pour gagner!", "§e§lEn ligne §6§l:§b§l " + networkState.getServersPlayerCount().get(AllNames.PFTSHOT.getName()), "§c[1.8+]", "§aCliques pour jouer").toItemStack());
                inv.setItem(21, new ItemBuilder(Material.CACTUS).setName("§8■ §c§lCJFFA").setLore("§8Type: PvP", "", "§d? §7Prenez un kit, sautez ", "§7très haut grâce", "§7aux cactus et ", "§7fumez tous vos", "§7adversaires jusqu'à  ", "§7ce que mort ", "§7s'en suive..", "§e§lEn ligne §6§l:§b§l " + networkState.getServersPlayerCount().get(AllNames.CJFFA.getName()), "§c[1.8+]", "", "§aClique pour jouer").toItemStack());
                inv.setItem(22, new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(p).setDurability((byte) 3).setName("§e§lVOUS :").setLore("", "§f" + p.getName() + " §8: §r" + user.getCachedData().getMetaData().getPrefix(), "§6MGCoins §8: §7" + profilAccount.getCoins(), "", "§aClique pour observer", "§ates stats sur chaque jeu").toItemStack());
                inv.setItem(23, new ItemBuilder(Material.SANDSTONE).setName("§8■ §9§lR§6§lT§c§lF").setLore("§8Type: Mini-jeu x PvP", "", "§d? §7Allez rush l'équipe ", "§7adverse pour récupérer", "§7leur drapeau et ", "§7le ramener à", "§7votre base. Gagnez", "§7le plus de manches", "§7avec votre équipe", "§7pour gagner !", "§e§lEn ligne §6§l:§b§l " + 0, "§c[1.8+]", "", "§aClique pour jouer").toItemStack());
                inv.setItem(31, new ItemBuilder(Material.DARK_OAK_DOOR_ITEM).setName("§a↩ Retour").setLore("§7Sortir du menu").toItemStack());
                inv.setItem(34, new ItemBuilder(Material.WRITTEN_BOOK).setName("§7§l⨠ §6§lMg'sShop").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());
                inv.setItem(28, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§7§l⨠ §d§lMg'sPrefs").setLore("§7<Clique pour ouvrir le Menu>").toItemStack());

    p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null && event.getAction() != null) return;
        if (event.getView().getTitle().equals(invMain)) {
            event.setCancelled(true);
            p.updateInventory();
            if(event.getCurrentItem().getItemMeta() != null) {
                switch (event.getCurrentItem().getItemMeta().getDisplayName()) {

                    case "§a↩ Retour":
                        p.closeInventory();
                        break;
                    case "§7§l⨠ §6§lMg'sShop":
                        new BoutiqueMenu().open(p);
                        break;
                    case "§7§l⨠ §d§lMg'sPrefs":
                        new PrefsItems().open(p);
                        break;
                    case "§8■ §a§lO.N.A.P":
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.ONAP.getName(), p);
                        break;
                    case "§e§lVOUS :":
                        new StatsMenu().open(p);
                        break;
                    case "§8■ §a§lSPLEGG":
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.SPLEEG.getName(), p);
                        break;
                    case "§8■ §a§lPERFECTSHOT":
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.PFTSHOT.getName(), p);
                        break;
                    case "§8■ §c§lCJFFA":
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.CJFFA.getName(), p);
                        break;
                    case "§8■ §9§lR§6§lT§c§lF":
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.RTF.getName(), p);
                        break;
                    default:
                        break;

                }
            }
        }
    }
}
