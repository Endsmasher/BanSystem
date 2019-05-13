package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import net.endrealm.realmdrive.interfaces.DriveService;
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


        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 3) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if (target == null) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            } else if (target.isOp()) {
                sender.sendMessage("§c You are not allowed to ban " + target.getName());
                return true;
            }

            int days = 0;
            try {
                days = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                sender.sendMessage("§cThe entered ban time is not a number!" + "\n Please use /tempban <player> <reason> <time(in days)> !");
            }
            service.getWriter().write(new PlayerBan(target.getUniqueId().toString(), args[1], "Ban", new Date().getTime() + 1000 * 60 * 60 * 24* days, new Date().getTime()));
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
            Bukkit.broadcastMessage("§a " + sender.getName() + " temporarily banned " + target.getName() + "(" + args[1] + ")");
            sender.sendMessage("§a Successful temp banned " + target.getName() + " for " + args[1]);


        } else sender.sendMessage("§c Please use /tempban <player> <reason> <time(in days)> ");
        return false;

    }
}
