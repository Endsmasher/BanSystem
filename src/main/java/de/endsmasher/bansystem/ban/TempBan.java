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

public class TempBan implements CommandExecutor {


    private Ocelot plugin;


    public TempBan(Ocelot plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getBanService();
        DriveService servicelog = plugin.getLogService();
        DriveService servicel = plugin.getTeamLogService();

        String prefix = "§7[§6Ocelot§7] ";

        if (!sender.hasPermission("Ocelot.Team")) {
            sender.sendMessage(prefix + "You don't have enough permissions to perform this command");
            return true;
        }
        if (args.length == 3) {


            Query query = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();

            PlayerLogall playerLogall = servicel.getReader().readObject(query, PlayerLogall.class);

            Query uuidquery = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogall.getId())
                    .close()
                    .build();

            if (!servicelog.getReader().containsObject(uuidquery)) {
                sender.sendMessage(prefix +"Unknown Player " + args[0]);
                return true;

            }
            if (servicel.getReader().containsObject(uuidquery)) {
                sender.sendMessage(prefix + "You are not permitted to ban " + args[0]);
                return true;

            }if (service.getReader().containsObject(uuidquery)) {
                sender.sendMessage(prefix + "The target player is already banned!");
                return true;
            }

            // "Translate" args[2] into a number

            int days = 0;
            try {
                days = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                sender.sendMessage(prefix + "The entered ban time is not a number!");
                sender.sendMessage(prefix + "Please use /tempban <player> <reason> <time(in days)> ");
            }

            // create the database entry

            service.getWriter().write(new PlayerBan(playerLogall.getId(),sender.getName(), args[1],"-1", new Date().getTime() + 1000 * 60 * 60 * 24* days, new Date().getTime()));
            if (Bukkit.getPlayer(playerLogall.getId()) != null) {
                Bukkit.getPlayer(playerLogall.getId()).kickPlayer("§c§l Chaincraft.ORG"
                + "\n"
                + "\n§r§c You were temporarily banned " + "\n" + "\n§7 Reason: "
                + "§r"
                + args[1]
                + "\n"
                + "\n§7 You can appeal at our Reddit: http://reddit.com/r/ChaincraftORG "
                + "\n"
                + "\n§7 Your ban will expire in "
                + days
                + " Day(s)"
                + "\n"
                + " You were banned at "
                + new Date().toString());
            }

            //Broadcast the Message to the Server if you banned a player

            Bukkit.broadcastMessage(prefix + sender.getName() + " temporarily banned " + args[0] + " for " + args[1]);

            //Sends a ban confirmation to the Command Sender

            sender.sendMessage(prefix + "Successful temp banned " + args[0] + " for " + args[1]);


        } else sender.sendMessage(prefix + "Please use /tempban <player> <reason> <time(in days)> ");
        return false;

    }
}
