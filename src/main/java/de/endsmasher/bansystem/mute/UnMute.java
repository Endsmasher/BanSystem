package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;


public class UnMute implements CommandExecutor {

    private BanSystem plugin;

    public UnMute(BanSystem plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getMuteService();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);


        if (sender.hasPermission("BanSystem.unban")) {
            if (args.length == 1) {
                Query query = new Query()
                        .addEq()
                        .setField("id")
                        .setValue(target.getUniqueId().toString())
                        .close()
                        .build();


                if (!service.getReader().containsObject(query)) {
                    sender.sendMessage("§cThe Player " + target.getName() + " is not muted!");
                    return true;
                }

                service.getWriter()
                        .delete(new Query()
                                .addEq()
                                .setField("id")
                                .setValue(target.getUniqueId().toString())
                                .close()
                                .build(), 1);
                Mute.muted.remove(target.getUniqueId());


                sender.sendMessage("§a Successful unmuted " + target.getName());
                Bukkit.broadcastMessage("§a" + sender.getName() + " unmuted " + target.getName());
            } else sender.sendMessage("Please use /unmute <player>");

        } else sender.sendMessage("§c You don't have enough permissions");
        return true;
    }
}
