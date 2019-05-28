package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Date;

public class PermBan implements CommandExecutor {

    private Ocelot plugin;


    public PermBan(Ocelot plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getBanService();
        DriveService servicelog = plugin.getLogService();
        DriveService servicel = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";


        if (!sender.hasPermission("Ocelot.Team")) {
            sender.sendMessage(prefix + "You don't have enough permissions");
            return true;
        }
        if (args.length == 2) {


            Query query = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();


            PlayerLogall playerLogall = servicel.getReader().readObject(query, PlayerLogall.class);

            if (!servicel.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "Unknown Player " + args[0]);
                return true;

            }
            if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "You are not permitted to ban " + args[0]);
                return true;
            }
            if (service.getReader().containsObject(query)) {
                sender.sendMessage(prefix + "The Player " + args[0] + " is already banned");
                return true;
            }

                service.getWriter().write(new PlayerBan(playerLogall.getId()
                        ,sender.getName()
                        , args[1]
                        , "-1"
                        , -1,
                        new Date().getTime()));

            if (Bukkit.getPlayer(playerLogall.getId()) != null) {
                Bukkit.getPlayer(playerLogall.getId()).kickPlayer("§c§l Chaincraft.ORG"
                        + "\n"
                        + "\n§r§c You were permanently banned "
                        + "\n"
                        + "\n§7 Reason: "
                        + "§r" + args[1]
                        + "\n"
                        + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG "
                        + "\n");
                }
                Bukkit.broadcastMessage(prefix + sender.getName() + " banned " + args[0] + " for " + args[1]);
                sender.sendMessage(prefix + "Successful banned " + args[0] + " for " + args[1]);

        } else sender.sendMessage(prefix + "Please use /ban <Player> <Reason> ");

        return false;
    }
}
