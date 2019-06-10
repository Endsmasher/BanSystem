package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.ConfigHolder;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class UnMute implements CommandExecutor {

    private Ocelot plugin;

    public UnMute(Ocelot plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService service = plugin.getMuteService();

        DriveService servicelogall = plugin.getLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Team") || !sender.hasPermission(ConfigHolder.Configs.CONFIG.getConfig().getString("permissions.Unmute"))) {

            sender.sendMessage(prefix + "You are not allowed to perform this command");

            return true;
        }

            if (args.length == 1) {

                Query queryname = new Query()
                        .addEq()
                        .setField("name")
                        .setValue(args[0])
                        .close()
                        .build();

                PlayerLogall playerLogallname = servicelogall.getReader().readObject(queryname, PlayerLogall.class);


                if (!servicelogall.getReader().containsObject(queryname)) {

                    sender.sendMessage(prefix +"Unknown Player " + args[0]);

                    return true;
                }

                Query query = new Query()
                        .addEq()
                        .setField("id")
                        .setValue(playerLogallname.getId())
                        .close()
                        .build();


                PlayerLogall playerLogall = servicelogall.getReader().readObject(query, PlayerLogall.class);


                if (!service.getReader().containsObject(query)) {

                    sender.sendMessage(prefix +"The Player " + args[0] + " is not muted");

                    return true;
                }


                service.getWriter()
                        .delete(new Query()
                                .addEq()
                                .setField("id")
                                .setValue(playerLogall.getId())
                                .close()
                                .build(), 1);



                sender.sendMessage(prefix + "Successful unmuted " + args[0]);

                Bukkit.broadcastMessage(prefix + sender.getName() + " unmuted " + args[0]);

            } else sender.sendMessage(prefix + "Please use /unmute <player>");

        return true;
    }
}
