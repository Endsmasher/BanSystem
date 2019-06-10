package de.endsmasher.bansystem.lognew;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerWarnCount;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class LoginListener implements Listener {

        private Ocelot plugin;

    public LoginListener(Ocelot plugin) {
        this.plugin = plugin;
    }

        @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerLoginEvent event) {
        DriveService service = plugin.getLogService();
        DriveService serviceWarnCount = plugin.getWarncountService();
        Player player = event.getPlayer();


            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(player.getUniqueId().toString())
                    .close()
                    .build();

            Query querynew = new Query()
                    .addEq()
                        .setField("name")
                        .setValue(player.getName())
                        .setField("address")
                        .setValue(event.getAddress().getHostAddress())
                    .close()
                    .build();

            PlayerLogall playerLogAllName = service.getReader().readObject(querynew, PlayerLogall.class);

     if (service.getReader().containsObject(query)) {
         service.getWriter().write(playerLogAllName, true, querynew);
        return;

     } else
         service.getWriter().write(new PlayerLogall(player.getUniqueId().toString()
             , player.getName()
             , event.getAddress().getHostAddress()
             , new Date().getTime()));

        serviceWarnCount.getWriter().write(new PlayerWarnCount(player.getUniqueId().toString()
                , 0));
    }
}
