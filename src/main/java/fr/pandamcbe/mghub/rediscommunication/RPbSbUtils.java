package fr.pandamcbe.mghub.rediscommunication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.pandamcbe.commons.*;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.core.localmemory.MemAccountsManipulation;
import fr.pandamcbe.mgapi.data.management.exceptions.AccountNotFoundException;
import fr.pandamcbe.mgapi.data.management.redis.connection.RedisAccess;
import fr.pandamcbe.mgapi.data.management.redis.datas_manipulation.RedisMessage;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RPbSbUtils {

    MemAccountsManipulation manipulation = Main.getInstance().manipulation;

    public RPbSbUtils() {
        System.out.println("Subscription Processing...");
        RedissonClient redisson = RedisAccess.INSTANCE.getRedissonClient();
        if (redisson == null) {
            System.out.println("Erreur: RedissonClient est null !");
            return;
        }

        String channel = AllNames.SAVESIGNAL.getName();

        RTopic topic = redisson.getTopic(channel);
        topic.addListener(String.class, (channelName, message) -> handleRedisMessage(channelName.toString(), message));

        System.out.println("Subscription method finished");
    }

    private void handleRedisMessage(String channelName, String message) {
        MGHub mgHub = MGHub.getInstance();

        AllNames allNames = AllNames.fromName(channelName);
        System.out.println(channelName + " " + message);
        if(allNames != null) {
            if (allNames.equals(AllNames.SAVESIGNAL)) {
                if (message.equalsIgnoreCase("SAVING NOW!")) {
                    sendAccountsToProxy(mgHub);
                }
            }
        }
    }

    public void sendAccountsToProxy(MGHub main){
        Gson gson = Main.getInstance().gson;
        Map<String, String> mainMapJson = new HashMap<>();
        Map<String, JsonObject> subMapJson = new HashMap<>();
        for(UUID uuid : main.mainMap.keySet()){
            Map<String, Object> subMap = main.mainMap.get(uuid);
            for (String accountsName : subMap.keySet()) {
                if(!accountsName.startsWith(AllNames.STATSPOJO.getName())) {
                    JsonObject JsonAccounts = gson.toJsonTree(subMap.get(accountsName)).getAsJsonObject();
                    subMapJson.put(accountsName, JsonAccounts);
                }
            }
            String uuidString = uuid.toString();
            mainMapJson.put(uuidString, gson.toJson(subMapJson));
        }
//        System.out.println(gson.toJson(new RedisMessage(mainMapJson)));
        Main.getInstance().redisPbSb.publishMessage(AllNames.SAVESIGNAL.getName(), gson.toJson(new RedisMessage(mainMapJson)));
    }

    public void publishGlobalDatas(UUID uuid, String server) throws AccountNotFoundException {
        Main main = Main.getInstance();
        MGHub mgHub = MGHub.getInstance();

        StatsAccount statsAccount = (StatsAccount) manipulation.getAccount(mgHub.mainMap, uuid, AllNames.statsPojo(server));
        WornAccount wornAccount = (WornAccount) manipulation.getAccount(mgHub.mainMap, uuid, AllNames.WORNPOJO.getName());

        Bukkit.getScheduler().runTaskAsynchronously(MGHub.getInstance(), () -> {

                ProfilAccount coins = (ProfilAccount) Main.getInstance().connections.getAccount(uuid, AllNames.PROFILKEY.getName());
                //Important pour appliquer toutes les modifications des coins au lobby dans l'un des comptes de stats
                Bukkit.getScheduler().runTask(MGHub.getInstance(), () -> {
                    statsAccount.setCoins(coins.getCoins());
                });
                Main.getInstance().connections.sendAccount(uuid, AllNames.statsKey(server), statsAccount);

        });
        statsAccount.setKills(0);
        statsAccount.setKillstreaks(0);

        Map<String, JsonObject> accounts = new HashMap<>();
        RedisMessage redisMessage = new RedisMessage(uuid, accounts);
        JsonObject statsJson = main.gson.toJsonTree(statsAccount).getAsJsonObject();
        JsonObject wornJson = main.gson.toJsonTree(wornAccount).getAsJsonObject();
        redisMessage.getAccounts().put(AllNames.statsPojo(server), statsJson);
        redisMessage.getAccounts().put(AllNames.WORNKEY.getName(), wornJson);
        AllNames allNames = AllNames.fromName(server);

        switch (allNames) {
            case PFTSHOT:
                main.redisPbSb.publishMessage(server, main.gson.toJson(redisMessage));
                break;
            case CJFFA:
                FFAKitsAccount ffaKitsAccount = (FFAKitsAccount) manipulation.getAccount(mgHub.mainMap, uuid, AllNames.FFAKITSPOJO.getName());
                JsonObject ffakitsJson = main.gson.toJsonTree(ffaKitsAccount).getAsJsonObject();
                redisMessage.getAccounts().put(AllNames.FFAKITSPOJO.getName(), ffakitsJson);
                main.redisPbSb.publishMessage(server, main.gson.toJson(redisMessage));
                break;
            case DUELS:
                if(manipulation.getAccount(mgHub.mainMap, uuid, AllNames.DUELPOJO.getName()) != null) {
                    DuelAccount duelAccount = (DuelAccount) manipulation.getAccount(mgHub.mainMap, uuid, AllNames.DUELPOJO.getName());
                    JsonObject duelJson = main.gson.toJsonTree(duelAccount).getAsJsonObject();
                    redisMessage.getAccounts().put(AllNames.DUELPOJO.getName(), duelJson);
                    main.redisPbSb.publishMessage(server, main.gson.toJson(redisMessage));
                }
                break;
            default:
                break;

        }

            ByteArrayOutputStream d = new ByteArrayOutputStream();
            Main.getInstance().gameMechanics.changingServerRequest(d, server);
            Bukkit.getPlayer(uuid).sendPluginMessage(MGHub.getInstance(), AllNames.BUNGEECORD.getName(), d.toByteArray());
    }
}