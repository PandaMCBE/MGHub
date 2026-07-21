package fr.pandamcbe.mghub.duelcommand;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DuelDatasManager {

    private final Map<Player, Player> playerKey;
    private final Map<Player, Player> targetKey;

    public DuelDatasManager (){
        this.playerKey = new HashMap<>();
        this.targetKey = new HashMap<>();
    }

    public Map<Player, Player> getTargetKey() {
        return targetKey;
    }

    public Map<Player, Player> getPlayerKey() {
        return playerKey;
    }


    //    public void setDuelKit(Player player, Player PlayerTarget){
//        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
//
//            final CreateRedisConnections connections = new CreateRedisConnections(PlayerTarget);
//            final RMap<UUID, String> duelDatasRMap = connections.getDuelAccountFromRedis();
//            final CreateRedisConnections connections2 = new CreateRedisConnections(player);
//            final RMap<UUID, String> duelDatasRMap2 = connections2.getDuelAccountFromRedis();
//            duelDatasRMap.put(PlayerTarget.getUniqueId(), player.getUniqueId().toString());
//            if (duelDatasRMap2.containsValue(DuelModesManager.SG.getName())) duelDatasRMap.put(player.getUniqueId(), DuelModesManager.SG.getName());
//            if (duelDatasRMap2.containsValue(DuelModesManager.BUILDUHC.getName())) duelDatasRMap.put(player.getUniqueId(), DuelModesManager.BUILDUHC.getName());
//            if (duelDatasRMap2.containsValue(DuelModesManager.SUMO.getName())) duelDatasRMap.put(player.getUniqueId(), DuelModesManager.SUMO.getName());
//            if (duelDatasRMap2.containsValue(DuelModesManager.PVBOT.getName())) duelDatasRMap.put(player.getUniqueId(), DuelModesManager.PVBOT.getName());
//            if (duelDatasRMap2.containsValue(DuelModesManager.MLGRUSH.getName())) duelDatasRMap.put(player.getUniqueId(), DuelModesManager.MLGRUSH.getName());
//        });
//    }

    public String getPrefix(){
        return "§6§lMGDuel > §r";
    }
}
