package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class UnMute implements CommandExecutor {

    private BanSystem plugin;

    public UnMute(BanSystem plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getMuteService();
        DriveService servicelogall = plugin.getlService();



        if (sender.hasPermission("BanSystem.Team")) {
            if (args.length == 1) {
                Query query = new Query()
                        .addEq()
                        .setField("name")
                        .setValue(args[0])
                        .close()
                        .build();

                PlayerLogall playerLogall = servicelogall.getReader().readObject(query, PlayerLogall.class);

                if (!servicelogall.getReader().containsObject(query)) {
                    sender.sendMessage("§c Unknown Player " + args[0]);
                    return true;
                }
                if (!service.getReader().containsObject(query)) {
                    sender.sendMessage("§cThe Player " + args[0] + " is not muted!");
                    return true;
                }

                service.getWriter()
                        .delete(new Query()
                                .addEq()
                                .setField("id")
                                .setValue(playerLogall.getId())
                                .close()
                                .build(), 1);
                Mute.muted.remove(playerLogall.getId());


                sender.sendMessage("§a Successful unmuted " + args[0]);
                Bukkit.broadcastMessage("§a" + sender.getName() + " unmuted " + args[0]);
            } else sender.sendMessage("Please use /unmute <player>");

        } else sender.sendMessage("§c You don't have enough permissions");
        return true;
    }
}
