package fr.pandamcbe.mghub.duelcommand;

import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.data.management.exceptions.AccountNotFoundException;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DuelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) { //si sender n'est pas une instance de la classe Player
            return false; //retourner faux
        }

        Player player = (Player) sender; //Récupération de l'envoyeur grâce à la superclass CommandSender en commun grâce au cast
        MGHub mgHub = MGHub.getInstance(); //Récupération de l'instance de la classe Main
        DuelDatasManager duelDatasManager = mgHub.duelDatasManager; //Récupération de la classe DuelDatasManager via l'instance mghub
        MemAccountsManipulation manipulation = Main.getInstance().manipulation; //Récupération de la classe pour la mémoire interne via l'instance de la classe Main de MGApi
        Player PlayerFirstSender = mgHub.duelDatasManager.getTargetKey().get(player); //Récupération du premier sender en utilisant la map où le target est la clé

        if (label.equalsIgnoreCase("duel")) {

            if (args.length == 0) { //Si la commande n'a aucun argument
                player.sendMessage(duelDatasManager.getPrefix() + "§e§l/duel §b§l<player>");
                player.sendMessage("§e§l/duel §7§l<§a§laccept§7§l/§c§ldeny§7§l>");
            }

            if (args.length == 1) { //Si la longueur de la commande est égale à un argument
                String targetName = args[0]; //La chaîne de caractère du nom de la cible est égale au premier argument

                if (args[0].equalsIgnoreCase("accept")) { //Si le premier argument est égale à "accept"
                    //Attention, ici, comme il s'agit de la réponse du target l'envoyeur, donc player est bien déjà le target, on ne peut pas le récupérer avec targetName, car il n'est plus disponible
                    if (duelDatasManager.getPlayerKey().containsValue(player) && duelDatasManager.getTargetKey().containsKey(player)) { //Si la map playerKey contient l'entrée où se trouvent
                        // l'envoyeur en valeur et la map targetKey contient une entrée où se trouve la clé de l'envoyeur.
                        duelDatasManager.getPlayerKey().remove(PlayerFirstSender); //suppression de la clé du sender originel
                        duelDatasManager.getTargetKey().remove(player); //suppression de la clé du target
//                            duelDatasManager.setDuelKit(PlayerFirstSender, player);
                        UUID uuid = PlayerFirstSender.getUniqueId(); //Récupération de la uuid du sender originel
                        UUID uuid2 = player.getUniqueId(); //Récupération de la uuid du target
                        Bukkit.getScheduler().runTaskAsynchronously(mgHub, () -> {
                            try {
                                MGHub.getInstance().subToChannels.publishGlobalDatas(uuid2, AllNames.DUELS.getName()); //Tp aux duels pour le target
                                MGHub.getInstance().subToChannels.publishGlobalDatas(uuid, AllNames.DUELS.getName()); //Tp aux duels pour le sender
                                //Cet ordre de téléportation est important pour le bon fonctionnement des duels
                            } catch (AccountNotFoundException e) {
                                e.printStackTrace();
                            }
                        });

//                        ByteArrayOutputStream a = new ByteArrayOutputStream();
//                        Main.getInstance().gameMechanics.changingServerRequest(a, AllNames.DUELS.getName());
//                        PlayerFirstSender.sendPluginMessage(MGHub.getInstance(), AllNames.BUNGEECORD.getName(), a.toByteArray());
//                        player.sendPluginMessage(MGHub.getInstance(), AllNames.BUNGEECORD.getName(), a.toByteArray());
                    }
                } else if (args[0].equalsIgnoreCase("deny")) { //si le premier argument est un refus
                    //Attention, ici, comme il s'agit de la réponse du target l'envoyeur, donc player est bien déjà le target, on ne peut pas le récupérer avec targetName, car il n'est plus disponible
                    if (duelDatasManager.getPlayerKey().containsValue(player) && duelDatasManager.getTargetKey().containsKey(player)) { //Si la map playerKey contient l'entrée où se trouvent
                        // l'envoyeur en valeur et la map targetKey contient une entrée où se trouve la clé de l'envoyeur.
                        player.sendMessage(duelDatasManager.getPrefix() + "§b§lMG-Land > §6§lVous avez refusé sa demande de duel. Le pauvre :(");
                        PlayerFirstSender.sendMessage(duelDatasManager.getPrefix() + "Le joueur §a§l " + player.getName() + " §r§la refusé votre demande :(");
                        duelDatasManager.getPlayerKey().remove(PlayerFirstSender); //suppression de la clé du sender originel
                        duelDatasManager.getTargetKey().remove(player); //suppression de la clé du target
                        manipulation.deleteAccount(mgHub.mainMap, PlayerFirstSender.getUniqueId(), AllNames.DUELPOJO.getName()); //suppression de l'emplacement du pojo dans la grande map de données JVM
                    }

                } else if (Bukkit.getPlayer(targetName) != null) { //Si la méthode getPlayer de Bukkit retrouve le joueur sur le serveur via son nom
                    Player target = Bukkit.getPlayer(targetName); //Initialisation d'un objet pour le joueur cible qui égale à ce que retourne Bukkit grâce à son nom
                    if (target != player) {//si le joueur cible n'est pas l'envoyeur (pour éviter de se demander en duel)
                        if(!duelDatasManager.getPlayerKey().containsKey(player)) { //Si le joueur a déjà une demande en cours
                            if (duelDatasManager.getPlayerKey().containsValue(target) && duelDatasManager.getTargetKey().containsKey(target)) { //Si la map playerKey contient l'entrée où se trouvent
                                // l'envoyeur en clé et le target en valeur et la map targetKey contient une entrée où se trouve la clé du target
                                player.sendMessage("§f§l" + targetName + " §c§la déjà une demande en cour !"); //Dire au joueur qui fait la demande, que le target a déjà une demande en cour
                            } else {
                                duelDatasManager.getPlayerKey().put(player, target); //Dans la map où la clé est le sender, sa valeur est le target
                                duelDatasManager.getTargetKey().put(target, player); //Dans la map où la clé est le target, sa valeur est le sender
                                player.openInventory(DuelMenu.open(player)); //Ouvrir le menu des modes de duels au joueur envoyeur
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