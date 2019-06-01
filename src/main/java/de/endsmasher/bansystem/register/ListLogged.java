package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListLogged implements CommandExecutor {


    private Ocelot plugin;

    public ListLogged(Ocelot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        DriveService service = plugin.getLogService();
        DriveService servicel = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Admin")) {

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

            PlayerLogall playerLogallname = service.getReader().readObject(queryname, PlayerLogall.class);


                if (!servicel.getReader().containsObject(queryname)) {

                    sender.sendMessage(prefix +"Unknown Player " + args[1]);

                    return true;
                }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogallname.getId())
                    .close()
                    .build();

            PlayerLogall playerLogall = service.getReader().readObject(query, PlayerLogall.class);


                PlayerLog playerLogTeam = service.getReader().readObject(query, PlayerLog.class);


                if (!service.getReader().containsObject(query)) {

                    sender.sendMessage(prefix +"This Player isn't logged yet!");

                    return true;
                }

                sender.sendMessage("§6 ---------- " + args[1] + " ----------");
                sender.sendMessage("§6- UUID:   §7" + playerLogTeam.getid());
                sender.sendMessage("§6- Added by:   §7" + playerLogTeam.getSenderName());
                sender.sendMessage("§6- Date:   §7" + playerLogTeam.getPrettyAddDate());

        } else

            sender.sendMessage(prefix + "please use /teamcheck <player> ");

        return true;
    }
}