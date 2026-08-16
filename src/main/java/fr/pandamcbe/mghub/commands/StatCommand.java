package fr.pandamcbe.mghub.commands;

import fr.pandamcbe.mgapi.serverutilapi.commands.getPlayerStatsCmd;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        new getPlayerStatsCmd(sender, cmd, args, MGHub.getInstance().mainMap, player);

        return true;
    }
}