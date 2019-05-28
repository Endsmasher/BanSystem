package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class Register implements CommandExecutor {

    private Ocelot plugin;

    public Register(Ocelot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        DriveService service = plugin.getLogService();
        DriveService servicelogall = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Admin")) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 1) {

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

