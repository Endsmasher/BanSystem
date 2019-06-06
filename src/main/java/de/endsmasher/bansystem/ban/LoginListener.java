package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.ConfigHolder;
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

            event.setKickMessage(ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line1")

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line2")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line3")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line4")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line5")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line6")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line7")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line8")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line9")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line10")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line11")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line12")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line13")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line14")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line15")
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


            event.setKickMessage(ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line1")

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line2")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line3")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line4")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line5")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line6")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line7")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line8")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line9")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line10")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line11")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line12")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line13")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line14")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line15")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));


            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);

        }
        if (playerBan.getUnBanDate() != -1) {

            event.setKickMessage(
                    ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line1")

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line2")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line3")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line4")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line5")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line6")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line7")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line8")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line9")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line10")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line11")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line12")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line13")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line14")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                            + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line15")
                            .replace("{REASON}", playerBan.getReason())
                            .replace("{BANDATE}", playerBan.getPrettyBanDate())
                            .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));


            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }

        if (playerBan.getReason() == "Too Many Warns") {
            List<PlayerWarn> playerWarns = serviceWarns.getReader().readAllObjects(query, PlayerWarn.class);

            event.setKickMessage(ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line1")

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line2")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line3")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line4")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line5")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line6")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line7")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line8")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line9")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line10")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line11")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line12")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line13")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line14")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate())

                    + ConfigHolder.Configs.CONFIG.getConfig().getString("BanScreen.line15")
                    .replace("{REASON}", playerBan.getReason())
                    .replace("{BANDATE}", playerBan.getPrettyBanDate())
                    .replace("{UNBANDATE}", playerBan.getPrettyUnBanDate()));

            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }
    }
}
