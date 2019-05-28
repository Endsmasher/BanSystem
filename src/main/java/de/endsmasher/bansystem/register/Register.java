package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class Register implements CommandExecutor {

    private BanSystem plugin;

    public Register(BanSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        DriveService service = plugin.getLogService();
        DriveService servicelogall = plugin.getlService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("BanSystem.Admin")) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 2) {

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

            Query queryall = new Query().addEq().setField("name").setValue(args[1]).close().build();
            PlayerLogall playerLogall = servicelogall.getReader().readObject(queryall, PlayerLogall.class);

            if (!servicelogall.getReader().containsObject(queryall)) {
                sender.sendMessage(prefix +"Unknown Player " + args[1]);
                return true;
            }

            Query query = new Query().addEq().setField("id").setValue(playerLogall.getId()).close().build();

            if (service.getReader().containsObject(query)) {
                sender.sendMessage(prefix +"This Player is already logged");
                return true;
            }

            service.getWriter().write(new PlayerLog(playerLogall.getId(), sender.getName(), new Date().getTime()));

            sender.sendMessage(prefix +"Successful registered " + args[1]);


        } else sender.sendMessage(prefix +"Please use /register <player>");
        return false;
    }
}

