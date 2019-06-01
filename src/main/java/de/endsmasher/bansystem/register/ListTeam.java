package de.endsmasher.bansystem.register;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.ConfigHolder;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListTeam implements CommandExecutor {

    private Ocelot plugin;

    public ListTeam(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        DriveService service = plugin.getLogService();
        DriveService servicelogteam = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Admin") || !sender.hasPermission(ConfigHolder.Configs.CONFIG.getConfig().getString("permissions.teamlist"))) {
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

            PlayerLogall playerLogAllName = service.getReader().readObject(queryname, PlayerLogall.class);


            if (!service.getReader().containsObject(queryname)) {
                sender.sendMessage(prefix +"Unknown Player " + args[1]);
                return true;
            }

            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogAllName.getId())
                    .close()
                    .build();

            PlayerLogall playerLogall = service.getReader().readObject(query, PlayerLogall.class);


            if (!servicelogteam.getReader().containsObject(query)) {
                sender.sendMessage(prefix +"This Player isn't logged yet!");
                return true;
            }


            List <PlayerLog> playerLogs = service.getReader().readAllObjects(query, PlayerLog.class);

            for (PlayerLog playerLog : playerLogs) {
                sender.sendMessage("§6 ---------- " + args[1] + " ----------");
                sender.sendMessage(" ");
                sender.sendMessage("§6- UUID:   §7" + playerLog.getid());
                sender.sendMessage("§6- Added by:   §7" + playerLog.getSenderName());
                sender.sendMessage("§6- Date:   §7" + playerLog.getPrettyAddDate());
                sender.sendMessage(" ");
                sender.sendMessage("§6 ---------- " + args[1] + " ----------");
            }

        } else
            sender.sendMessage(prefix + "please use /teamlist");
        return false;
    }
}
