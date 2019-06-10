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

        if (!(args.length == 0)) {
            sender.sendMessage(prefix + "please use /teamlist");
            return true;
        }


            Query query = new Query()
                    .build();

            List <PlayerLog> playerLogs = servicelogteam.getReader().readAllObjects(query, PlayerLog.class);

                sender.sendMessage("§6 ----------  Team list  ----------");
                sender.sendMessage(" ");

            for (PlayerLog playerLog : playerLogs) {
                sender.sendMessage("" + playerLog.getName());
            }
                sender.sendMessage(" ");
                sender.sendMessage("§6 ----------  Team list  ----------");

        return false;
    }
}
