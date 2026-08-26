package fr.pandamcbe.mghub.rediscommunication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.DirtyManager;
import fr.pandamcbe.mgapi.core.rediscache.RedisAccountsManager;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisPubSubManager;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisMessage;
import fr.pandamcbe.mgapi.serverutilapi.NetworkState;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.util.UUID;

public class RedisSubReception extends RedisPubSubManager {

    private final Gson gson = new Gson();


    @Override
    public void subChannels() {
        String[] channels = {AllNames.TELEPORTSPAWN.getName(), AllNames.NETWORKSTATE.getName(), AllNames.STATSCOMMAND.getName()};
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
                    UUID uuid = redisMessage.getUuid();
                    String accountName = redisMessage.getString();
                    String json = (String) redisMessage.getObject();
                    if(MGHub.getInstance().mainMap.containsKey(uuid)) {
                        new DirtyManager().cancelPending(uuid, accountName);
                        Main.getInstance().manipulation.sendAccountToMemory(MGHub.getInstance().mainMap, uuid, gson.fromJson(json, AccountType.fromName(RedisAccountsManager.ifContainsCategory(accountName)).getType()), accountName);
                    }
                    break;
            }
        }
    }


}