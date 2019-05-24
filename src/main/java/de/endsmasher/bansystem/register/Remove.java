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

public class Remove implements CommandExecutor {

    private BanSystem plugin;

    public Remove(BanSystem plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        DriveService service = plugin.getLogService();
        DriveService servicelogall = plugin.getlService();


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
            PlayerLogall playerLogall = servicelogall.getReader().readObject(queryall, PlayerLogall.class);


            if (!servicelogall.getReader().containsObject(queryall)) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogall.getId())
                    .close()
                    .build();

            if (!service.getReader().containsObject(query)) {
                sender.sendMessage("This Player isn't logged yet");
                return true;
            }

                service.getWriter().delete(new Query()
                        .addEq()
                        .setField("id")
                        .setValue(playerLogall.getId())
                        .close()
                        .build(), 1);

                sender.sendMessage("§a Successful removed " + args[0]);


        } else sender.sendMessage("§c Please use /remove <player>");
        return false;
    }
}
