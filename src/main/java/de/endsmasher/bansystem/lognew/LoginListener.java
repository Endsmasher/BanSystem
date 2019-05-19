package de.endsmasher.bansystem.lognew;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Date;

public class LoginListener implements Listener {

        private BanSystem plugin;

    public LoginListener(BanSystem plugin) {
        this.plugin = plugin;
    }

        @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        DriveService service = plugin.getlService();
        Player player = event.getPlayer();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(player.getUniqueId().toString())
                .close()
                .build();

     if (service.getReader().containsObject(query)) {
         return;
     }
     service.getWriter().write(new PlayerLogall(player.getUniqueId().toString(), player.getName(),player.getAddress().toString(), new Date().getTime()));

    }
}
