package fr.pandamcbe.mghub.commands.duelcommand;

import fr.pandamcbe.commons.DuelAccount;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DuelRequestManager {

    private final Map<Player, Player> playerKey;
    private final Map<Player, Player> targetKey;
    private final Map<Player, Long> expireAt;

    public DuelRequestManager(){
        this.playerKey = new HashMap<>();
        this.targetKey = new HashMap<>();
        this.expireAt = new HashMap<>();
    }

    public Map<Player, Player> getTargetKey() {
        return targetKey;
    }

    public Map<Player, Player> getPlayerKey() {
        return playerKey;
    }

    public Map<Player, Long> getExpireAt() {
        return expireAt;
    }

    public String getPrefix(){
        return "§6§lMGDuel > §r";
    }

    public void removeFromMap(Player player){
        if(getPlayerKey().containsKey(player)){
            getPlayerKey().remove(player);
        } else {
            getPlayerKey().remove(getTargetKey().get(player));
        }
        if(getTargetKey().containsKey(player)) {
            getTargetKey().remove(player);
        } else {
            getTargetKey().remove(getPlayerKey().get(player));
        }
    }

    public Long expirationAt(Player player){
        if(getExpireAt().containsKey(player) && TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - getExpireAt().get(player)) < 30) {
            return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - getExpireAt().get(player));
        } else {
            getExpireAt().remove(player);
        }
        return null;
    }

    public void removeExpiration(Player player) {
        if(getExpireAt().containsKey(player)){
            getExpireAt().remove(player);
        } else {
            getExpireAt().remove(getTargetKey().get(player));
        }
    }

    public void deleteRequest(Player player){
        removeFromMap(player);
        removeExpiration(player);
    }

    public void sendRequest(Player sender, Player target){
        if (target != sender) {
            if(expirationAt(sender) == null || expirationAt(sender) >= 30) {
                deleteRequest(sender);
                if (getPlayerKey().containsValue(target) && getTargetKey().containsKey(target)) {

                    sender.sendMessage("§f§l" + target.getName() + " §c§la déjà une demande en cour !");
                } else {
                    getPlayerKey().put(sender, target);
                    getTargetKey().put(target, sender);
                    DuelMenu.open(sender);
                }
            } else {
                sender.sendMessage(getPrefix() + "§cVous avez déjà une demande en cours ! Veuillez encore patienter :" + (30 - expirationAt(sender)) + "§cs");
            }
        } else {
            sender.sendMessage(getPrefix() + " §cVous ne pouvez pas vous inviter à duel contre vous même !");
        }
    }

    public void notConfirmedMode(Player sender){
        if (!MGHub.getInstance().mainMap.get(sender.getUniqueId()).containsKey(DuelAccount.class.getName())) {
            if (getPlayerKey().containsKey(sender) && getTargetKey().containsKey(getPlayerKey().get(sender))) {
                deleteRequest(sender);
                sender.sendMessage("§cVous n'avez pas confirmé le mode, alors la demande est annulée !");
            }
        }
    }
}