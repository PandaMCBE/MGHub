package fr.pandamcbe.mghub.boards.leaderboard;

import com.google.common.collect.Iterables;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.data.management.sql.database.DbManager;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AllNames;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Objects;

public class Leaderboards {

    private Iterator<String> nextServerName = MGHub.getInstance().getNetworkState().getServerNames().iterator();

    public Leaderboards(MGHub main) {
        this.createLeaderbords();
        main.getServer().getScheduler().runTaskTimer(main, () -> {
            if (Iterables.getFirst(Bukkit.getOnlinePlayers(), null) != null) {
                if (nextServerName.hasNext()) {
                    update(nextServerName.next());
                } else {
                    nextServerName = Main.getInstance().getProxyMessaging().askServersName().iterator();
                    update(nextServerName.next());
                }
            }
        }, 0, 4800L);
    }


    public void createLeaderbords() {
        if (DHAPI.getHologram("Kills") == null && DHAPI.getHologram("Elos") == null && DHAPI.getHologram("Wins") == null) {
            DHAPI.createHologram("Kills", new Location(Bukkit.getWorlds().get(0), 100050.500f, 69, 99992.500f));
            DHAPI.addHologramLine(Hologram.getCachedHologram("Kills"), "§e§lKILLS-LEADERBOARD");
            DHAPI.createHologram("Elos", new Location(Bukkit.getWorlds().get(0), 100048.500f, 69, 99996.500f));
            DHAPI.addHologramLine(Hologram.getCachedHologram("Elos"), "§e§lELOS-LEADERBOARD");
            DHAPI.createHologram("Wins", new Location(Bukkit.getWorlds().get(0), 100046.500f, 69, 99992.500f));
            DHAPI.addHologramLine(Hologram.getCachedHologram("Wins"), "§e§lWINS-LEADERBOARD");
        }
    }

    private void update(String serverName) {
        if(!serverName.equalsIgnoreCase(AllNames.LOBBY.getName())) {
            DHAPI.setHologramLine(Hologram.getCachedHologram("Wins"), 0, 0, "§e§lKILLS-LEADERBOARD-§c§l" + serverName);
            DHAPI.setHologramLine(Hologram.getCachedHologram("Elos"), 0, 0, "§e§lELOS-LEADERBOARD-§c§l" + serverName);
            DHAPI.setHologramLine(Hologram.getCachedHologram("Kills"), 0, 0, "§e§lWINS-LEADERBOARD-§c§l" + serverName);

            while (Objects.requireNonNull(DHAPI.getHologram("Kills")).getPage(0).size() > 1 && Objects.requireNonNull(DHAPI.getHologram("Elos")).getPage(0).size() > 1 && Objects.requireNonNull(DHAPI.getHologram("Wins")).getPage(0).size() > 1) {
                DHAPI.removeHologramLine(Hologram.getCachedHologram("Kills"), 1);
                DHAPI.removeHologramLine(Hologram.getCachedHologram("Elos"), 1);
                DHAPI.removeHologramLine(Hologram.getCachedHologram("Wins"), 1);
            }
            String SqlKillsLb = "SELECT " + AllNames.PSEUDO_PLAYER.getName() + ", " + AllNames.KILLS.getName() + " FROM " + AllNames.statsTable(serverName) + " ORDER BY " + AllNames.KILLS.getName() + " DESC LIMIT 10";
            String SqlElosLb = "SELECT " + AllNames.PSEUDO_PLAYER.getName() + ", " + AllNames.ELOS.getName() + " FROM " + AllNames.statsTable(serverName) + " ORDER BY " + AllNames.ELOS.getName() + " DESC LIMIT 10";
            String SqlWinsLb = "SELECT " + AllNames.PSEUDO_PLAYER.getName() + ", " + AllNames.WINS.getName() + " FROM " + AllNames.statsTable(serverName) + " ORDER BY " + AllNames.WINS.getName() + " DESC LIMIT 10";

            Bukkit.getScheduler().runTaskAsynchronously(MGHub.getInstance(), () -> {
                try (Connection connection = DbManager.MGDB.getDatabaseAccess().getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(SqlKillsLb);
                     PreparedStatement preparedStatement1 = connection.prepareStatement(SqlElosLb);
                     PreparedStatement preparedStatement2 = connection.prepareStatement(SqlWinsLb);
                     ResultSet resultSet = preparedStatement.executeQuery();
                     ResultSet resultSet1 = preparedStatement1.executeQuery();
                     ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                    int i = 0;

                    while (resultSet.next() && resultSet1.next() && resultSet2.next()) {
                        i++;
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Kills"), "§a#" + (i) + " §b" + resultSet.getString("pseudo_player") + "§7-" + resultSet.getInt("kills"));
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Elos"), "§a#" + (i) + " §b" + resultSet1.getString("pseudo_player") + "§7-" + resultSet1.getInt("elos"));
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Wins"), "§a#" + (i) + " §b" + resultSet2.getString("pseudo_player") + "§7-" + resultSet2.getInt("wins"));
                    }

                    for (; i < 10; i++) {
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Kills"), "§8...");
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Elos"), "§8...");
                        DHAPI.addHologramLine(Hologram.getCachedHologram("Wins"), "§8...");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }


}