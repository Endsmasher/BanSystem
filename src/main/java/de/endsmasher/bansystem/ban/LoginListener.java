package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class LoginListener implements Listener {

    private BanSystem plugin;


    public LoginListener(BanSystem plugin) {
        this.plugin = plugin;
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerLoginEvent event) {
        DriveService service = plugin.getBanService();
        Player player = event.getPlayer();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(player.getUniqueId().toString())
                .setField("kind")
                .setValue("Ban")
                .close()
                .build();

        if (!service.getReader().containsObject(query)) {
            return;
        }
        PlayerBan playerBan = service.getReader().readObject(query, PlayerBan.class);

        if (playerBan.getUnbanDate() <= new Date().getTime() && playerBan.getUnbanDate() != -1) {
            service.getWriter().delete(new Query()
                    .addEq()
                        .setField("id")
                        .setValue(player.getUniqueId().toString())
                        .setField("kind")
                        .setValue("Ban")
                    .close()
                    .build(), 1);
            return;
        } else



        {
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }

            event.setKickMessage("§c§l Chaincraft.ORG"
                    + "\n"
                    + "\n§r§c You were temporarily banned "
                    + "\n"
                    + "\n§7 Reason: "
                    + "§r"
                    + playerBan.getReason()
                    + "\n"
                    + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG "
                    + "\n"
                    + "\n§7 Your ban will expire on "
                    + new Date(playerBan.getUnbanDate())
                    + "\n"
                    + " You were banned at "
                    + new Date(playerBan.getBanDate()));


            event.setKickMessage("§c§l Chaincraft.ORG"
            + "\n"
            + "§r§c You were permanently banned "
            + "\n"
            + "\n§7 Reason: "
            + "§r"
            + playerBan.getReason()
            + "\n"
            + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG "
            + "\n"
            + "\n§7 You were banned at "
            + new Date(playerBan.getBanDate()));
    }
}
