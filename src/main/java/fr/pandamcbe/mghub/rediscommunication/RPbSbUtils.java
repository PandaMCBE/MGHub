package fr.pandamcbe.mghub.rediscommunication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pandamcbe.mgapi.core.localmemory.DirtyManager;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisAccountsUtils;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisMessage;
import fr.pandamcbe.mgapi.serverutilapi.NetworkState;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

public class RPbSbUtils extends RedisAccountsUtils {

    private final Gson gson = new Gson();

    @Override
    public void subChannels() {
        String[] channels = {AllNames.TELEPORTSPAWN.getName(), AllNames.NETWORKSTATE.getName()};
        subCore(channels);
    }

    @Override
    public void handleRedisMessage(String channelName, String message) {
        AllNames allNames = AllNames.fromName(channelName);
        RedisMessage redisMessage = gson.fromJson(message, RedisMessage.class);

        if (allNames != null) {
            switch (allNames) {
                case NETWORKSTATE:
                    Type type = new TypeToken<NetworkState>() {
                    }.getType();
                    MGHub.getInstance().networkState = gson.fromJson(message, type);
                    break;
                case TELEPORTSPAWN:
                    Bukkit.getScheduler().runTask(MGHub.getInstance(), ()-> {
                        UUID uuid = redisMessage.getUuid();
                        Player player = Bukkit.getPlayer(uuid);
                        player.teleport(new Location(Bukkit.getWorld("worldlobby"), 100000.500f, 57, 100000.500f, -90.0f, -1.0f));
                        player.sendMessage("§7Vous avez été téléporté au lobby..");
                        if (player.getGameMode() != GameMode.ADVENTURE) {
                            player.setGameMode(GameMode.ADVENTURE);
                        }
                        player.setHealth(20.0);
                    });

                    break;
                case STATSCOMMAND:
                    Object account = redisMessage.getObject();
                    UUID uuid = redisMessage.getUuid();
                    Map<String, Object> accountsMap = MGHub.getInstance().mainMap.get(uuid);
                    String accoutName = account.getClass().getName();
                    new DirtyManager().cancelPending(uuid, accoutName);
                    accountsMap.put(accoutName, account);
                    break;
            }
        }
    }


}