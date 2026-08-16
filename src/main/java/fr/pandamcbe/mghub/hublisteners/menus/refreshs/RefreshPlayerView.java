package fr.pandamcbe.mghub.hublisteners.menus.refreshs;

import fr.pandamcbe.commons.ProfilAccount;
import fr.pandamcbe.mgapi.Main;
import fr.pandamcbe.mgapi.serverutilapi.enums_strings.AccountType;
import fr.pandamcbe.mghub.MGHub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RefreshPlayerView {

    public RefreshPlayerView(final UUID uuid) {

        Bukkit.getScheduler().runTaskLater(MGHub.getInstance(), () -> {
            ProfilAccount profilAccount = (ProfilAccount) Main.getInstance().manipulation.getProfilAccount(MGHub.getInstance().mainMap, uuid, AccountType.PROFIL.getKeyPrefix());
                        for (Player other : Bukkit.getOnlinePlayers()) {
                            Player player = Bukkit.getPlayer(uuid);
                            if (profilAccount.getP_visibility() == 0) {
                                player.hidePlayer(other);
                            } else player.showPlayer(other);
                        }

            }, 5L); // Delai très court pour forcer le rafraîchissement
    }

}
