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

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("BanSystem.Admin")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }

        if(!(args[1] == "check") || !(args[1] == "add") || !(args[1] == "remove" )|| !(args[1] == "check" ))  {
            sender.sendMessage("§7----------§6 Ocelot §7----------");
            sender.sendMessage(" ");
            sender.sendMessage("§6oc log <player>§7    : §3Shows you the Team");
            sender.sendMessage("§6oc add <player>§7    : §3Allows you to add the Target player to the Team");
            sender.sendMessage("§6oc remove <player>§7 : §3Allows you to remove the Target player from the Team");
            sender.sendMessage("§6oc check <player>§7  : §3Shows you the Warns/Bans/Mutes of the Target player");
            sender.sendMessage(" ");
            sender.sendMessage("§7--------------------------------");

            return true;
        }

        if (args.length == 2) {


            Query queryall = new Query()
                    .addEq()
                        .setField("name")
                        .setValue(args[0])
                    .close()
                    .build();

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


        } else
            sender.sendMessage(prefix + "please use /oc check <player> ");

        return false;
    }
}