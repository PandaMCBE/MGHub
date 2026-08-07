package fr.pandamcbe.mghub.boards.samaboardsclasses;

import fr.pandamcbe.mgapi.serverutilapi.scoreboards.ObjectiveSign;
import fr.pandamcbe.mghub.MGHub;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PersonalScoreboard extends fr.pandamcbe.mgapi.serverutilapi.scoreboards.PersonalScoreboard {

    public PersonalScoreboard(Player player){
        this.player = player;

        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "§6§l§nMG-Land");
        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData() {
    }
    public void setLines(String ip) {
        User user = MGHub.getInstance().luckPerms.getPlayerAdapter(Player.class).getUser(player);
        this.objectiveSign.setLine(1, "§8§m----------------");
        this.objectiveSign.setLine(2, "§7Uniques : §f" + Bukkit.getServer().getOnlinePlayers().size());
        this.objectiveSign.setLine(3, "§7Connectés : §f" + MGHub.getInstance().getNetworkState().getTotalPlayers());
        this.objectiveSign.setLine(4, "§7Pseudo : §f" + player.getName());
        this.objectiveSign.setLine(5, "§7Grade : §f" + user.getCachedData().getMetaData().getPrefix());
        this.objectiveSign.setLine(6, "§8§m--------------");
        this.objectiveSign.setLine(7, ip);
        this.objectiveSign.updateLines();
    }
}