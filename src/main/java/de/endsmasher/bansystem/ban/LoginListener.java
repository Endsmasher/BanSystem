package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.*;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.ChatColor;
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


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)

    public void onPlayerJoin(PlayerLoginEvent event) {
        DriveService service = plugin.getBanService();
        DriveService serviceWarns = plugin.getWarnService();



        Player player = event.getPlayer();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(player.getUniqueId().toString())
                .close()
                .build();

        if (!service.getReader().containsObject(query)) {
            return;
        }

        Query queryip = new Query()
                .addEq()
                    .setField("Address")
                    .setValue(event.getRealAddress().getHostAddress())
                .close()
                .build();


        PlayerBan playerBan = service.getReader().readObject(query, PlayerBan.class);

        if (service.getReader().containsObject(queryip) && !service.getReader().containsObject(query)) {

            service.getWriter().write(new PlayerBan(player.getUniqueId().toString()
                    , "CONSOLE"
                    , "Ban Evading"
                    , player.getAddress().toString()
                    , -1
                    , new Date().getTime()));

            event.setKickMessage(BanScreenStrings.IPBanline1

                    + BanScreenStrings.IPBanline2
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline3
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline4
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline5
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline6
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline7
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline8
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline9
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline10
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline11
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline12
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline13
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline14
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.IPBanline15
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));


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


            event.setKickMessage(BanScreenStrings.PBanline1

                    + BanScreenStrings.PBanline2
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " +playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline3
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " +playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline4
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline5
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline6
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline7
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline8
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline9
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline10
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline11
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline12
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline13
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline14
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " +playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.PBanline15
                    .replace("{REASON}", " " + playerBan.getReason())
                    .replace("{BANDATE}", " " + playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", " " + playerBan.getPrettyUnBanDate()));


            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);

        } else
        if (playerBan.getUnBanDate() != -1) {

            event.setKickMessage(BanScreenStrings.TBanline1

                            + BanScreenStrings.TBanline2
                            .replace("{REASON}", " " + playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline3
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline4
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline5
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline6
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline7
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline8
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline9
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline10
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline11
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline12
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline13
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline14
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + BanScreenStrings.TBanline15
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));


            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }

        if (playerBan.getReason() == "Too Many Warns") {
            List<PlayerWarn> playerWarns = serviceWarns.getReader().readAllObjects(query, PlayerWarn.class);

            event.setKickMessage(BanScreenStrings.TBanline1

                    + BanScreenStrings.TBanline2
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline3
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline4
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline5
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline6
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline7
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline8
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline9
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline10
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline11
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline12
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline13
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline14
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + BanScreenStrings.TBanline15
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));

            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }
    }
}
