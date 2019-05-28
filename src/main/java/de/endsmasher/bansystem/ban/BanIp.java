package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class BanIp implements CommandExecutor {

    private Ocelot plugin;


    public BanIp(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService serviceban = plugin.getBanService();
        DriveService servicelogteam = plugin.getLogService();
        DriveService servicelogall = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.BanIp")) {
            sender.sendMessage(prefix + "You are not allowed to perform this command");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage(prefix + "Please use /BanIp <target> <reason>");
            sender.sendMessage(prefix + "Keep in mind BanIp's are permanently");
            return true;
        }

        Query mainquery = new Query()
                .addEq()
                .setField("name")
                .setValue(args[0])
                .close()
                .build();

        PlayerLogall playerLogall = servicelogall.getReader().readObject(mainquery, PlayerLogall.class);


        Query uuidquery = new Query()
                .addEq()
                .setField("id")
                .setValue(playerLogall.getId())
                .close()
                .build();


        if(!servicelogall.getReader().containsObject(uuidquery)) {
            sender.sendMessage(prefix + "Unknown Player " + args[0]);
            return true;
        }
        if (servicelogteam.getReader().containsObject(uuidquery)) {
            sender.sendMessage(prefix + "You are not permitted to ban " + args[0]);
            return true;
        }
        if (serviceban.getReader().containsObject(uuidquery)) {
            sender.sendMessage(prefix + "The Player " + args[0] + " is already banned!");
        }
        serviceban.getWriter().write(new PlayerBan(playerLogall.getId()
                , sender.getName()
                , args[1]
                , playerLogall.getAddress()
                , -1
                , new Date().getTime()));

        sender.sendMessage(prefix + "Successful banned " + args[0] + " for " + args[1]);
        Bukkit.broadcastMessage(prefix + sender.getName() + " banned " + args[0] + " for " + args[1]);

        return false;
    }
}
