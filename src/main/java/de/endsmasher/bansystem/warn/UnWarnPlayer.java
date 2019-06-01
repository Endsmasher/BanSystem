package de.endsmasher.bansystem.warn;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerWarn;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnWarnPlayer implements CommandExecutor {

    private Ocelot plugin;

    public UnWarnPlayer(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService service = plugin.getWarnService();
        DriveService servicelogall = plugin.getLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Team")) {
            sender.sendMessage(prefix +"You don't have enough permissions to perform this command");
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

        if (args.length == 3) {

            Query queryall = new Query()
                    .addEq()
                        .setField("name")
                        .setValue(args[0])
                    .close()
                    .build();

            PlayerLogall playerLogallname = servicelogall.getReader().readObject(queryall, PlayerLogall.class);

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogallname.getId())
                    .close()
                    .build();

            if (!servicelogall.getReader().containsObject(query)) {
                sender.sendMessage(prefix +"Unknown Player " + args[0]);
                return true;
            }

            Query queryreason = new Query()
                    .addEq()
                    .setField("reason")
                    .setValue(args[1])
                    .close()
                    .build();

            Query query2 = new Query()
                    .addEq()
                        .setField("info")
                        .setValue("INACTIVE")
                    .close()
                    .build();


            if (!service.getReader().containsObject(query)) {
                sender.sendMessage(prefix +"The Player " + args[0] + " is not warned yet");
                return true;
            }
            if (!service.getReader().containsObject(queryreason)) {
                sender.sendMessage(prefix + "Invalid warn");
                return true;
            }
            if (!service.getReader().containsObject(query2)) {
                sender.sendMessage(prefix + "Invalid warn");
                return true;
            }
            PlayerWarn playerwarn = service.getReader().readObject(query, PlayerWarn.class);

            service.getWriter().write(playerwarn, true, query2);

            sender.sendMessage(prefix +"Successful unwarned " + args[0]);
            Bukkit.broadcastMessage(prefix + sender.getName() + " unwarned " + args[0] + " for reason: " + args[2]);
        } else sender.sendMessage(prefix + "§c Please use /unwarn <player> <warn reason> <unwarn reason>");
        return false;
    }
}