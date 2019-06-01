package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.ConfigHolder;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Remove implements CommandExecutor {

    private Ocelot plugin;

    public Remove(Ocelot plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        DriveService servicelogall = plugin.getLogService();
        DriveService servicelogteam = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";


        if (!sender.hasPermission("Ocelot.Admin") || !sender.hasPermission(ConfigHolder.Configs.CONFIG.getConfig().getString("permissions.Remove"))) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command!");
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


            if (!servicelogteam.getReader().containsObject(query)) {
                sender.sendMessage(prefix +"This Player isn't logged yet");
                return true;
            }

                servicelogteam.getWriter().delete(new Query()
                        .addEq()
                        .setField("id")
                        .setValue(playerLogall.getId())
                        .close()
                        .build(), 1);

                sender.sendMessage(prefix +"Successful removed " + args[0]);

        } else sender.sendMessage(prefix +"Please use /remove <player>");
        return false;
    }
}
