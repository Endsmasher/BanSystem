package de.endsmasher.bansystem.mute;

import com.sun.xml.internal.bind.v2.TODO;
import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Date;

public class Mute implements CommandExecutor {

    private Ocelot plugin;

    public static ArrayList<String> muted = new ArrayList<>();

    public Mute(Ocelot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getMuteService();
        DriveService servicelog = plugin.getLogService();
        DriveService servicel = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Team")) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command");
            return true;
        }

        if (args.length == 3) {

            Query querylog = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();
            PlayerLogall playerLogall = servicel.getReader().readObject(querylog, PlayerLogall.class);

            if (!servicel.getReader().containsObject(querylog)) {
                sender.sendMessage(prefix + "Unknown Player " + args[0]);
                return true;

            }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogall.getId())
                    .close()
                    .build();


            if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "You are not allowed to mute " + args[0]);
                return true;
            }
            if (service.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "The Player " + args[0] + " is already muted");
                return true;
            }
            int minutes = 0;
            try {
                minutes = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                sender.sendMessage(prefix + "The entered ban time is not a number!");
                sender.sendMessage(prefix +"Please use /mute <player> <reason> <time(in minutes)> ");
            }
            service.getWriter().write(new PlayerMute(playerLogall.getId(), sender.getName(), args[1], new Date().getTime() + 1000 * 60 * minutes, new Date().getTime()));

            Bukkit.broadcastMessage(prefix + sender.getName() + " muted " + args[0] + " for " + args[1] + " " + args[2] + " minutes");
            sender.sendMessage(prefix +"Successful muted " + args[0] + " for " + args[1]);
        } else sender.sendMessage("§c Please use /mute <player> <reason> <mute duration(minutes)> ");
        return false;
    }
}