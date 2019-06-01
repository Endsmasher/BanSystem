package de.endsmasher.bansystem.warn;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.*;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.List;

public class WarnPlayer implements CommandExecutor {

    private Ocelot plugin;


    public WarnPlayer(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService service = plugin.getWarnService();
        DriveService servicelogall = plugin.getLogService();
        DriveService servicelogteam = plugin.getTeamLogService();
        DriveService serviceWarnCount = plugin.getWarncountService();
        DriveService serviceBan = plugin.getBanService();

        String prefix = "§7[§6Ocelot§7] ";
        String count = ConfigHolder.Configs.CONFIG.getConfig().getString("settings.AutoBan");

        int bancount = 0;
        try {
            bancount = Integer.parseInt(count);
        } catch (Exception ex) {
            ConfigHolder.Configs.CONFIG.getConfig().set("settings.AutoBan", "This isn't a valid number");
        }

        if (!sender.hasPermission("Ocelot.Team")) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command");
            return true;
        }
        if (args.length == 2) {

            Query queryall = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();

            PlayerLogall playerLogallname = servicelogall.getReader().readObject(queryall, PlayerLogall.class);

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogallname.getId())
                    .close()
                    .build();

            PlayerLogall playerLogall = servicelogall.getReader().readObject(query, PlayerLogall.class);


            if (!servicelogall.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "Unknown Player " + args[0]);
                return true;

            }


            Query queryCount = new Query()
                    .addEq()
                        .setField("id")
                        .setValue(playerLogall.getId())
                        .setField("count")
                        .setValue(+1)
                    .close()
                    .build();

            if (servicelogteam.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "You are not allowed to warn " + args[0]);
                return true;
            }


            service.getWriter()
                    .write(new PlayerWarn(playerLogall.getId()
                            , sender.getName()
                            , args[1]
                            , "ACTIVE"
                            , new Date().getTime()+1000*60*60*24*7*2
                            , new Date().getTime()));

            Bukkit.broadcastMessage(prefix + sender.getName() + " warned " + args[0] + " for " + args[1]);
            sender.sendMessage(prefix + "Successful warned " + args[0] + " for " + args[1]);
            PlayerWarnCount playerWarnCount = serviceWarnCount.getReader().readObject(query, PlayerWarnCount.class);
            List <PlayerWarn> playerWarns = service.getReader().readAllObjects(query, PlayerWarn.class);
            PlayerWarn playerwarn = service.getReader().readObject(query, PlayerWarn.class);


            if (serviceWarnCount.getReader().containsObject(query)) {
                serviceWarnCount.getWriter().write(playerWarnCount, true, queryCount);
            } else {
                serviceWarnCount.getWriter().write(new PlayerWarnCount(playerLogall.getId(), 1));
            }

            if (Bukkit.getPlayer(playerLogall.getId()) != null && playerWarnCount.getCount() != bancount) {

                Bukkit.getPlayer(playerLogall.getId()).kickPlayer(prefix + "You have been warned for " + args[1]);
                return true;

            } else
                serviceBan.getWriter().write(new PlayerBan(playerLogall.getId(),
                        "CONSOLE",
                        "To Many Warns",
                        "-1",
                        new Date().getTime() + 1000 * 60 * 60 * 24 * 1,
                        new Date().getTime()));

        } else sender.sendMessage(prefix +"Please use /warn <player> <reason>");
        return false;
    }
}
