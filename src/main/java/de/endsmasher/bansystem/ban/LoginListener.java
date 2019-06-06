package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerWarn;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;
import java.util.List;

public class LoginListener implements Listener {

    private Ocelot plugin;

    public LoginListener(Ocelot plugin) {
        this.plugin = plugin;
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)

    public void onPlayerJoin(PlayerLoginEvent event) {
        DriveService service = plugin.getBanService();
        DriveService service1og = plugin.getLogService();
        DriveService serviceWarns = plugin.getWarnService();

        Player player = event.getPlayer();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(player.getUniqueId().toString())
                .close()
                .build();

        PlayerLogall playerLogall = service1og.getReader().readObject(query, PlayerLogall.class);


        Query queryip = new Query()
                .addEq()
                    .setField("Address")
                    .setValue(playerLogall.getAddress())
                .close()
                .build();


        PlayerBan playerBan = service.getReader().readObject(query, PlayerBan.class);

        if (service.getReader().containsObject(queryip) && !service.getReader().containsObject(query)) {

            service.getWriter().write(new PlayerBan(player.getUniqueId().toString(), "CONSOLE", "Ban Evading", player.getAddress().toString(), -1, new Date().getTime()));

            /// TODO: 28.05.2019 Change the Ban messages !
            event.setKickMessage("§c§l Chaincraft.ORG" + "\n" + "§r§c You were permanently banned " + "\n" + "\n§7 Reason: " + "§r" + playerBan.getReason() + "\n" + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG " + "\n" + "\n§7 You were banned at " + new Date(playerBan.getBanDate()));

            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);

        }

        if (!service.getReader().containsObject(query)) {
            return;
        }

        if (playerBan.getUnBanDate() <= new Date().getTime() && playerBan.getUnBanDate() != -1) {
            service.getWriter().delete(new Query().addEq().setField("id").setValue(player.getUniqueId().toString()).close().build(), 1);
            return;
        }
        if (playerBan.getUnBanDate() == -1) {

            event.setKickMessage("§c§l Chaincraft.ORG" + "\n" + "§r§c You were permanently banned " + "\n" + "\n§7 Reason: " + "§r" + playerBan.getReason() + "\n" + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG " + "\n" + "\n§7 You were banned at " + new Date(playerBan.getBanDate()));
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);

        }
        if (playerBan.getUnBanDate() != -1) {

            event.setKickMessage("§c§l Chaincraft.ORG" + "\n" + "\n§r§c You were temporarily banned " + "\n" + "\n§7 Reason: " + "§r" + playerBan.getReason() + "\n" + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG " + "\n" + "\n§7 Your ban will expire on " + playerBan.getUnBanDate() + "\n" + " You were banned at " + new Date(playerBan.getBanDate()));
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }

        if (playerBan.getReason() == "Too Many Warns") {
            List<PlayerWarn> playerWarns = serviceWarns.getReader().readAllObjects(query, PlayerWarn.class);

            event.setKickMessage("§c§l Chaincraft.ORG" + "\n" + "\n§r§c You were temporarily banned " + "\n" + "\n§7 Reason: " + "§r" + playerBan.getReason() + "\n" + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG " + "\n" + "\n§7 Your ban will expire on " + playerBan.getUnBanDate() + "\n" + " You were banned at " + new Date(playerBan.getBanDate()));
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }
    }
}
