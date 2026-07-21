package fr.pandamcbe.mghub.portals;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandPortals implements CommandExecutor {
    private MGHub plugin;

    private Map<String, List<String>> selections = new HashMap<>();

    public CommandPortals(MGHub plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("MGPortals"))
            if (sender.hasPermission("MGPortals.command.MGPortals")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.BLUE + "MGPortals v" + this.plugin.getDescription().getVersion() + " remastered by Panda");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals reload " + ChatColor.RED + "Rafraichir tous les fichiers et les données");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals forcesave " + ChatColor.RED + "Forcer la sauvegarde des portails.");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals select <filter,list> " + ChatColor.RED + "Séléctionnez une zone !");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals clear " + ChatColor.RED + "Clear la séléction");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals create <destination> " + ChatColor.RED + "Créer des portails.");
                    sender.sendMessage(ChatColor.GREEN + "/MGPortals remove <destination> " + ChatColor.RED + "Enlever des portails");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        this.plugin.portalsConfigFile.loadConfigFiles();
                        this.plugin.portalsConfigFile.loadPortalsData();
                        sender.sendMessage(ChatColor.GREEN + "Tous les fichiers de configurations et data ont été rafraîchit");
                    } else if (args[0].equalsIgnoreCase("forcesave")) {
                        this.plugin.portalsConfigFile.savePortalsData();
                        sender.sendMessage(ChatColor.GREEN + "La donnée du portail est sauvegardé!");
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        if (sender instanceof Player) {
                            String playerName = sender.getName();
                            if (this.selections.containsKey(playerName)) {
                                this.selections.remove(playerName);
                                sender.sendMessage(ChatColor.GREEN + "cleared.");
                            } else {
                                sender.sendMessage(ChatColor.RED + "Vous n'avez rien séléctionné!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent utiliser cette commande.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Tapez /MGPortals pour de l'aide.");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("select")) {
                        if (sender instanceof Player) {
                            Player player = (Player)sender;
                            String playerName = player.getName();
                            Selection selection = this.plugin.worldEdit.getSelection(player);
                            if (selection != null) {
                                if (selection instanceof CuboidSelection) {
                                    List<Location> locations = getLocationsFromCuboid((CuboidSelection)selection);
                                    List<String> blocks = new ArrayList<>();
                                    String[] ids = new String[0];
                                    int count = 0;
                                    int filtered = 0;
                                    boolean filter = false;
                                    if (!args[1].equals("0")) {
                                        ids = args[1].split(",");
                                        filter = true;
                                    }
                                    for (Location location : locations) {
                                        Block block = player.getWorld().getBlockAt(location);
                                        if (filter) {
                                            boolean found = false;
                                            for (int i = 0; i < ids.length; i++) {
                                                String[] parts = ids[i].split(":");
                                                if (parts.length == 2) {
                                                    if (parts[0].equals(String.valueOf(block.getTypeId())) && parts[1].equals(String.valueOf(block.getData()))) {
                                                        found = true;
                                                        break;
                                                    }
                                                } else if (parts[0].equals(String.valueOf(block.getTypeId()))) {
                                                    found = true;
                                                    break;
                                                }
                                            }
                                            if (found) {
                                                blocks.add(block.getWorld().getName() + "#" + String.valueOf(block.getX()) + "#" + String.valueOf(block.getY()) + "#" + String.valueOf(block.getZ()));
                                                count++;
                                                continue;
                                            }
                                            filtered++;
                                            continue;
                                        }
                                        blocks.add(block.getWorld().getName() + "#" + String.valueOf(block.getX()) + "#" + String.valueOf(block.getY()) + "#" + String.valueOf(block.getZ()));
                                        count++;
                                    }
                                    this.selections.put(playerName, blocks);
                                    sender.sendMessage(ChatColor.GREEN + String.valueOf(count) + " blocs ont été" + String.valueOf(filtered) + " filtré");
                                    sender.sendMessage(ChatColor.GREEN + "Utiliser la séléction dans la création et enlever les commandes.");
                                } else {
                                    sender.sendMessage(ChatColor.RED + "Doit être une séléction cubique!");
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + "Vous devez d'abord créer une séléction WorldEdit!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent utiliser cette commande.");
                        }
                    } else if (args[0].equalsIgnoreCase("create")) {
                        if (sender instanceof Player) {
                            String playerName = sender.getName();
                            if (this.selections.containsKey(playerName)) {
                                List<String> selection = this.selections.get(playerName);
                                for (String block : selection)
                                    this.plugin.portalData.put(block, args[1]);
                                sender.sendMessage(ChatColor.GREEN + String.valueOf(selection.size()) + " les portails ont été créé");
                            } else {
                                sender.sendMessage(ChatColor.RED + "Vous n'avez rien séléctionné");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent utiliser cette commande.");
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (sender instanceof Player) {
                            String playerName = sender.getName();
                            if (this.selections.containsKey(playerName)) {
                                int count = 0;
                                for (String block : this.selections.get(playerName)) {
                                    if (this.plugin.portalData.containsKey(block)) {
                                        this.plugin.portalData.remove(block);
                                        count++;
                                    }
                                }
                                sender.sendMessage(ChatColor.GREEN + String.valueOf(count) + " les portails ont été enlevé!");
                            } else {
                                sender.sendMessage(ChatColor.RED + "Vous n'avez rien séléctionné");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Seul les joueurs peuvent utiliser cette commande.");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Tapez /MGPortals pour de l'aide!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Tapez /MGPortals pour de l'aide!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande !");
            }
        return true;
    }

    private List<Location> getLocationsFromCuboid(CuboidSelection cuboid) {
        List<Location> locations = new ArrayList<>();
        Location minLocation = cuboid.getMinimumPoint();
        Location maxLocation = cuboid.getMaximumPoint();
        for (int i1 = minLocation.getBlockX(); i1 <= maxLocation.getBlockX(); i1++) {
            for (int i2 = minLocation.getBlockY(); i2 <= maxLocation.getBlockY(); i2++) {
                for (int i3 = minLocation.getBlockZ(); i3 <= maxLocation.getBlockZ(); i3++)
                    locations.add(new Location(cuboid.getWorld(), i1, i2, i3));
            }
        }
        return locations;
    }
}
