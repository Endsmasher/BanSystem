package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class TempBan implements CommandExecutor {


    private BanSystem plugin;


    public TempBan(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getBanService();
        DriveService servicelog = plugin.getLogService();


        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 3) {

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

            }if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage("§c You are not allowed to ban " + target.getName() + " !");
                return true;

            }if (service.getReader().containsObject(query)) {
                sender.sendMessage("§c The target player is already banned!");
                return true;
            }

            // "Translate" args[2] into a number

            int days = 0;
            try {
                days = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                sender.sendMessage("§cThe entered ban time is not a number!" + "\n Please use /tempban <player> <reason> <time(in days)> !");
            }

            // create the database entry

            service.getWriter().write(new PlayerBan(target.getUniqueId().toString(), args[1], new Date().getTime() + 1000 * 60 * 60 * 24* days, new Date().getTime()));
            if (Bukkit.getPlayer(target.getUniqueId()) != null) {
                Bukkit.getPlayer(target.getUniqueId()).kickPlayer("§c§l Chaincraft.ORG"
                + "\n"
                + "\n§r§c You were temporarily banned " + "\n" + "\n§7 Reason: "
                + "§r"
                + args[1]
                + "\n"
                + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG "
                + "\n"
                + "\n§7 Your ban will expire in "
                + days
                + " Day(s)"
                + "\n"
                + " You were banned at "
                + new Date().toString());
            }

            //Broadcast the Message to the Server if you banned a player

            Bukkit.broadcastMessage("§a " + sender.getName() + " temporarily banned " + target.getName() + "(" + args[1] + ")");

            //Sends a ban confirmation to the Command Sender

            sender.sendMessage("§a Successful temp banned " + target.getName() + " for " + args[1]);


        } else sender.sendMessage("§c Please use /tempban <player> <reason> <time(in days)> ");
        return false;

    }
}
