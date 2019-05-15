package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLog;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;
import java.util.List;

public class ListLogged implements CommandExecutor {


    private BanSystem plugin;

    public ListLogged(BanSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        DriveService service = plugin.getLogService();


        if (!sender.hasPermission("BanSystem.Admin")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 1) {

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

            PlayerLog playerLog = service.getReader().readObject(query, PlayerLog.class);

            if (!service.getReader().containsObject(query)) {
                sender.sendMessage("§c This Player isn't logged yet!");
                return true;
            }


            sender.sendMessage("§6 ---------- History of " + target.getName() + " ----------");
            sender.sendMessage("§6- UUID:   §7" + playerLog.getid());
            sender.sendMessage("§6- Added by:   §7" + playerLog.getSenderName());
            sender.sendMessage("§6- Date:   §7" + playerLog.getPrettyBanDate());


        } else sender.sendMessage("§c Please use /SystemLog <player> !");
        return false;
    }
}