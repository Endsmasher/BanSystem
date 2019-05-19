package de.endsmasher.bansystem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        if (event.getMessage().equalsIgnoreCase("pardon-ip")
                || event.getMessage().equalsIgnoreCase("/ban-ip")
                || event.getMessage().equalsIgnoreCase("/pardon")) {
            event.setCancelled(true);

        }

    }
}
