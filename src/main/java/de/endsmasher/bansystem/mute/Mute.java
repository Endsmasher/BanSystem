package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Mute implements CommandExecutor {

    private BanSystem plugin;

    public static ArrayList<UUID> muted = new ArrayList<>();

    public Mute(BanSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getMuteService();
        DriveService servicelog = plugin.getLogService();

        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 3) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);


            if (target == null) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;

            }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(target.getUniqueId().toString())
                    .close()
                    .build();


            if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage("§c You are not allowed to mute " + target.getName());
                return true;
            }
            if (service.getReader().containsObject(query)) {
                sender.sendMessage("§c The Player " + target.getName() + " is already muted");
                return true;
            }
            int minutes = 0;
            try {
                minutes = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                sender.sendMessage("§cThe entered ban time is not a number!" + "\n Please use /mute <player> <reason> <time(in minutes)> !");
            }
            service.getWriter().write(new PlayerMute(target.getUniqueId().toString(), sender.getName(), args[1], new Date().getTime() + 1000 * 60 * minutes, new Date().getTime()));


            muted.add(target.getUniqueId());

            Bukkit.broadcastMessage("§a " + sender.getName() + " muted " + target.getName() + "(" + args[1] + ") " + args[2] + " minutes");
            sender.sendMessage("§aSuccessful muted " + target.getName() + " for " + args[1]);
        } else sender.sendMessage("§c Please use /mute <player> <reason> <mute duration(minutes)> ");
        return false;
    }
}