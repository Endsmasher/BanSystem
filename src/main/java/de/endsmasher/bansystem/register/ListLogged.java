package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.utils.JsonUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListLogged implements CommandExecutor {


    private BanSystem plugin;

    public ListLogged(BanSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        DriveService service = plugin.getLogService();
        DriveService servicel = plugin.getlService();


        if (!sender.hasPermission("BanSystem.Admin")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 1) {

            Query queryall = new Query()
                    .addEq()
                        .setField("name")
                        .setValue(args[0])
                    .close()
                    .build();

            System.out.println(servicel.getReader().readObject(queryall));

            PlayerLogall playerLogall = servicel.getReader().readObject(queryall, PlayerLogall.class);


            if (!servicel.getReader().containsObject(queryall)) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            }

            Query query = new Query()
                    .addEq()
                        .setField("id")
                        .setValue(playerLogall.getId())
                    .close()
                    .build();
            PlayerLog playerLog = service.getReader().readObject(query, PlayerLog.class);

            if (!service.getReader().containsObject(query)) {
                sender.sendMessage("§c This Player isn't logged yet!");
                return true;
            }


            sender.sendMessage("§6 ---------- " + args[0] + " ----------");
            sender.sendMessage("§6- UUID:   §7" + playerLog.getid());
            sender.sendMessage("§6- Added by:   §7" + playerLog.getSenderName());
            sender.sendMessage("§6- Date:   §7" + playerLog.getPrettyAddDate());


        } else sender.sendMessage("§c Please use /SystemLog <player> !");
        return false;
    }
}