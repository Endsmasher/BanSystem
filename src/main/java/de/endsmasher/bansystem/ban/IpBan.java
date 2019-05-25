package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class IpBan implements CommandExecutor {

    private BanSystem plugin;


    public IpBan(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService serviceban = plugin.getBanService();
        DriveService servicelogteam = plugin.getLogService();
        DriveService servicelogall = plugin.getlService();

        if (!sender.hasPermission("BanSystem.IpBan")) {
            sender.sendMessage("§cYou are not allowed to perform this command!");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("§cPlease use /IpBan <target> <reason>");
            sender.sendMessage("§cKeep in mind IpBan's are permanently!");
            return true;
        }

        Query query = new Query()
                .addEq()
                .setField("name")
                .setValue(args[0])
                .close()
                .build();

        PlayerLogall playerLogall = servicelogall.getReader().readObject(query, PlayerLogall.class);


        if(!servicelogall.getReader().containsObject(query)) {
            sender.sendMessage("§cUnknown Player " + args[0]);
            return true;
        }
        if (playerLogall.getId() == null) {
            sender.sendMessage("§cUnknown Player " + args[0]);
            return true;
        }
        if (servicelogteam.getReader().containsObject(query)) {
            sender.sendMessage("§cYou are not allowed to ban " + args[0]);
            return true;
        }
        if (serviceban.getReader().containsObject(query)) {
            sender.sendMessage("§cThe Player " + args[0] + " is already banned!");
        }
        serviceban.getWriter().write(new PlayerBan(playerLogall.getId()
                , sender.getName()
                , args[1]
                , playerLogall.getAddress()
                , -1
                , new Date().getTime()));

        sender.sendMessage("§aSuccessful ipBanned " + args[0] + " for " + args[1]);
        Bukkit.broadcastMessage("§a" + sender.getName() + " ipBanned " + args[0] + "(" + args[1] + ")");


        return false;
    }
}
