package fr.pandamcbe.mghub.rediscommunication;

import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisAccountsUtils;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;

public class RPbSbUtils extends RedisAccountsUtils {

    MemAccountsManipulation manipulation = Main.getInstance().manipulation;
    final private MGHub mgHub;
    public RPbSbUtils(MGHub mgHub) {
        this.mgHub = mgHub;
    }

    @Override
    public void subChannels() {
        String[] channels = {AllNames.LOBBY.getName()};
        subCore(channels);
    }

    @Override
    public void handleRedisMessage(String channelName, String message) {
        AllNames allNames = AllNames.fromName(channelName);
        if(allNames != null) {
            if (allNames.equals(AllNames.LOBBY)) {
                
            }
        }
    }
}