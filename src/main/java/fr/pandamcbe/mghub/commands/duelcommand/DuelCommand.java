package fr.pandamcbe.mghub.commands.duelcommand;

import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) { //si sender n'est pas une instance de la classe Player
            return false; //retourner faux
        }

        Player player = (Player) sender;
        MGHub mgHub = MGHub.getInstance();
        DuelDatasManager duelDatasManager = mgHub.duelDatasManager;
        MemAccountsManipulation manipulation = Main.getInstance().manipulation;
        Player PlayerFirstSender = mgHub.duelDatasManager.getTargetKey().get(player);

        if (label.equalsIgnoreCase("duel")) {

            if (args.length == 0) { //Si la commande n'a aucun argument
                player.sendMessage(duelDatasManager.getPrefix() + "§e§l/duel §b§l<player>");
                player.sendMessage("§e§l/duel §7§l<§a§laccept§7§l/§c§ldeny§7§l>");
            }

            if (args.length == 1) {
                String targetName = args[0];

                if (args[0].equalsIgnoreCase("accept")) {
                    if (duelDatasManager.getPlayerKey().containsValue(player) && duelDatasManager.getTargetKey().containsKey(player)) {
                        duelDatasManager.getPlayerKey().remove(PlayerFirstSender);
                        duelDatasManager.getTargetKey().remove(player);

                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.DUELS.getName(), PlayerFirstSender, player);
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (duelDatasManager.getPlayerKey().containsValue(player) && duelDatasManager.getTargetKey().containsKey(player)) {
                        player.sendMessage(duelDatasManager.getPrefix() + "§b§lMG-Land > §6§lVous avez refusé sa demande de duel. Le pauvre :(");
                        PlayerFirstSender.sendMessage(duelDatasManager.getPrefix() + "Le joueur §a§l " + player.getName() + " §r§la refusé votre demande :(");
                        duelDatasManager.getPlayerKey().remove(PlayerFirstSender);
                        duelDatasManager.getTargetKey().remove(player);
                        manipulation.deleteAccount(mgHub.mainMap, PlayerFirstSender.getUniqueId(), AllNames.DUELPOJO.getName());
                    }

                } else if (Bukkit.getPlayer(targetName) != null) {
                    Player target = Bukkit.getPlayer(targetName);
                    if (target != player) {
                        if(!duelDatasManager.getPlayerKey().containsKey(player)) {
                            if (duelDatasManager.getPlayerKey().containsValue(target) && duelDatasManager.getTargetKey().containsKey(target)) {

                                player.sendMessage("§f§l" + targetName + " §c§la déjà une demande en cour !");
                            } else {
                                duelDatasManager.getPlayerKey().put(player, target);
                                duelDatasManager.getTargetKey().put(target, player);
                                player.openInventory(DuelMenu.open(player));
                            }
                        } else {
                            player.sendMessage(duelDatasManager.getPrefix() + "§cVous avez déjà envoyé une demande en cours !");
                        }
                    } else
                        player.sendMessage(duelDatasManager.getPrefix() + " §cVous ne pouvez pas vous inviter à duel contre vous même !");
                } else
                    player.sendMessage(duelDatasManager.getPrefix() + " §lLe joueur §b§l<§a§l " + targetName + " §l§b> §r§lest §c§lintrouvable/hors ligne");

            }
        }
        return false;
    }
}