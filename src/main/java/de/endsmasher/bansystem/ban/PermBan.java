package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLog;
import de.endsmasher.bansystem.utils.PlayerLogall;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class PermBan implements CommandExecutor {

    private BanSystem plugin;


    public PermBan(BanSystem plugin) {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getBanService();
        DriveService servicelog = plugin.getLogService();
        DriveService servicel = plugin.getlService();


        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
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
                sender.sendMessage("§cUnknown Player " + args[0]);
                return true;

            }
            if (servicelog.getReader().containsObject(query)) {
                sender.sendMessage("§cYou are not allowed to ban " + args[0]);
                return true;
            }
            if (service.getReader().containsObject(query)) {
                sender.sendMessage("§cThe Player " + args[0] + " is already banned");
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
                Bukkit.broadcastMessage("§a" + sender.getName() + " banned " + args[0] + "(" + args[1] + ")");
                sender.sendMessage("§aSuccessful banned " + args[0] + " for " + args[1]);

        } else sender.sendMessage("§cPlease use /ban <Player> <Reason> ");

        return false;
    }
}
