package fr.pandamcbe.mghub.commands.duelcommand;

import com.google.gson.Gson;
import fr.pandamcbe.commons.DuelAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisMessage;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import fr.pandamcbe.mghub.rediscommunication.RedisSubCatch;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommand implements CommandExecutor {

    private final RedisSubCatch subCatch = new RedisSubCatch();
    private final Gson gson = new Gson();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) { //si sender n'est pas une instance de la classe Player
            return false; //retourner faux
        }

        Player player = (Player) sender;
        MGHub mgHub = MGHub.getInstance();
        DuelRequestManager duelDatasManager = mgHub.duelDatasManager;
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
                        DuelAccount duelAccount = (DuelAccount) Main.getInstance().manipulation.getAccountFromMemory(MGHub.getInstance().mainMap, PlayerFirstSender.getUniqueId(), DuelAccount.class.getName());
                        subCatch.publishMessage(AllNames.DUEL.getName(), new RedisMessage(PlayerFirstSender.getUniqueId(), gson.toJson(duelAccount)));
                        Main.getInstance().getProxyMessaging().connectToAnotherServer(AllNames.DUELS.getName(), PlayerFirstSender, player);
                    }
                } else if (args[0].equalsIgnoreCase("deny")) {
                    if (duelDatasManager.getPlayerKey().containsValue(player) && duelDatasManager.getTargetKey().containsKey(player)) {
                        player.sendMessage(duelDatasManager.getPrefix() + "§b§lMG-Land > §6§lVous avez refusé sa demande de duel. Le pauvre :(");
                        PlayerFirstSender.sendMessage(duelDatasManager.getPrefix() + "Le joueur §a§l " + player.getName() + " §r§la refusé votre demande :(");
                        duelDatasManager.deleteRequest(player);
                    }

                } else if (Bukkit.getPlayer(targetName) != null) {
                    Player target = Bukkit.getPlayer(targetName);
                    duelDatasManager.sendRequest(player, target);
                } else
                    player.sendMessage(duelDatasManager.getPrefix() + " §lLe joueur §b§l<§a§l " + targetName + " §l§b> §r§lest §c§lintrouvable/hors ligne");

            }
        }
        return false;
    }
}