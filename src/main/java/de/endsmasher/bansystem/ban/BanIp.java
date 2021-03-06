package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.BanScreenStrings;
import de.endsmasher.bansystem.utils.ConfigHolder;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class BanIp implements CommandExecutor {

    private Ocelot plugin;


    public BanIp(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService serviceban = plugin.getBanService();
        DriveService servicelogall = plugin.getLogService();
        DriveService servicelogteam = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Team" ) || !sender.hasPermission(ConfigHolder.Configs.CONFIG.getConfig().getString("permissions.BanIp"))) {

            sender.sendMessage(prefix + "You are not allowed to perform this command");

            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(prefix + "Please use /BanIp <target> <reason>");
            sender.sendMessage(prefix + "Keep in mind IpBan's are permanently");
            return true;
        }

        Query queryname = new Query()
                .addEq()
                .setField("name")
                .setValue(args[0])
                .close()
                .build();

        PlayerLogall playerLogallname = servicelogall.getReader().readObject(queryname, PlayerLogall.class);


        if(!servicelogall.getReader().containsObject(queryname)) {

            sender.sendMessage(prefix + "Unknown Player " + args[0]);

            return true;

        }

        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(playerLogallname.getId())
                .close()
                .build();

        PlayerLogall playerLogall = servicelogall.getReader().readObject(query, PlayerLogall.class);


        if (servicelogteam.getReader().containsObject(query)) {

            sender.sendMessage(prefix + "You are not permitted to ban " + args[0]);

            return true;

        }

        if (serviceban.getReader().containsObject(query)) {

            sender.sendMessage(prefix + "The Player " + args[0] + " is already banned!");

        }

        serviceban.getWriter().write(new PlayerBan(playerLogall.getId()
                , sender.getName()
                , args[1]
                , playerLogall.getAddress()
                , -1
                , new Date().getTime()));


        sender.sendMessage(prefix + "Successful banned " + args[0] + " for " + args[1]);

        PlayerBan playerBan = serviceban.getReader().readObject(query, PlayerBan.class);



        if (!Bukkit.getPlayer(playerLogall.getId()).isOnline()) {
            return true;
        }

        Bukkit.getPlayer(playerLogall.getId()).kickPlayer(BanScreenStrings.IPBanline1

                + BanScreenStrings.IPBanline2
                .replace("{REASON} ", playerBan.getReason())
                .replace("{BANDATE} ", playerBan.getPrettyBanDate())
                .replace("{UNBANDATE} ", playerBan.getPrettyUnBanDate())

                + BanScreenStrings.IPBanline3
                .replace("{REASON} ", playerBan.getReason())
                .replace("{BANDATE} ", playerBan.getPrettyBanDate())
                .replace("{UNBANDATE} ", playerBan.getPrettyUnBanDate())

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


        if (ConfigHolder.Configs.CONFIG.getConfig().getBoolean("settings.BroadcastBan/UnbanMessages") == true) {

            Bukkit.broadcastMessage(prefix + sender.getName() + " banned " + args[0] + " for " + args[1]);

        }

        return false;
    }
}
