package de.endsmasher.bansystem;

import de.endsmasher.bansystem.utils.ConfigHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.EventListener;

public class JoinListener implements EventListener, Listener {


    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (event.getPlayer().isOp() && ConfigHolder.Configs.CONFIG.getConfig().getString("settings.AutoMessageOnJoin") == "true") {
            event.getPlayer().sendMessage("§a This Plugin is Developed by Endsmasher " +
                    "\nhttps://www.spigotmc.org/members/endsmasher.508762/ and Gerolmed " +
                    "https://www.spigotmc.org/members/gerolmed.92991/");

        }
    }
}
