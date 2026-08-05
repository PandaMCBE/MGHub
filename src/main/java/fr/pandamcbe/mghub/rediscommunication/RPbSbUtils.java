package fr.pandamcbe.mghub.rediscommunication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisAccountsUtils;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;

import java.lang.reflect.Type;
import java.util.Map;

public class RPbSbUtils extends RedisAccountsUtils {

    private final Gson gson = new Gson();

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
                    Main.getInstance().getProxyMessaging().getServersPlayerCount().replaceAll(gson.fromJson(message, mapType));
                    break;
                case LOBBY:
                    break;
            }
        }
    }


}