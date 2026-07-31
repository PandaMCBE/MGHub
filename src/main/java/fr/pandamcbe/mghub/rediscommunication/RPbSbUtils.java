package fr.pandamcbe.mghub.rediscommunication;

import com.google.gson.reflect.TypeToken;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisAccountsUtils;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;

import java.lang.reflect.Type;
import java.util.Map;

public class RPbSbUtils extends RedisAccountsUtils {

    MemAccountsManipulation manipulation = Main.getInstance().manipulation;
    final private MGHub mgHub;
    public RPbSbUtils(MGHub mgHub) {
        this.mgHub = mgHub;
    }

    @Override
    public void subChannels() {
        String[] channels = {AllNames.LOBBY.getName(), AllNames.PROXY.getName()};
        subCore(channels);
    }

    @Override
    public void handleRedisMessage(String channelName, String message) {
        AllNames allNames = AllNames.fromName(channelName);
        if (allNames != null) {
            switch (allNames) {
                case PROXY:
                    Type mapType = new TypeToken<Map<String, Integer>>() {}.getType();
                    Main.getInstance().getProxyMessaging().getServersPlayerCount().replaceAll(Main.getInstance().gson.fromJson(message, mapType));
                    break;
                case LOBBY:
                    break;
            }
        }
    }


}