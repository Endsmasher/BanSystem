package de.endsmasher.bansystem.warn;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerWarn;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class WarnPlayer implements CommandExecutor {

    private BanSystem plugin;


    public WarnPlayer(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getWarnService();
        DriveService servicelog = plugin.getLogService();

        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 2) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(target.getUniqueId().toString())
                    .close()
                    .build();

            if (target == null) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            } else if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage("§c You are not allowed to warn " + target.getName());
                return true;
            }


            service.getWriter()
                    .write(new PlayerWarn(target.getUniqueId().toString()
                            , sender.getName()
                            , args[1]
                            , "ACTIVE"
                            , new Date().getTime()+1000*60*60*24*7*2
                            , new Date().getTime()));


            Bukkit.broadcastMessage("§a " + sender.getName() + " warned " + target.getName() + "(" + args[1] + ")");
            sender.sendMessage("§aSuccessful warned " + target.getName() + " for " + args[1]);

            if (Bukkit.getPlayer(target.getUniqueId()) != null) {

                Bukkit.getPlayer(target.getUniqueId()).kickPlayer(" §c You have been warned for " + args[1]);
            }

        } else sender.sendMessage("§c Please use /warn <player> <reason>");
        return false;
    }
}
