package de.endsmasher.bansystem.warn;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerMute;
import de.endsmasher.bansystem.utils.PlayerWarn;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnWarnPlayer implements CommandExecutor {

    private BanSystem plugin;


    public UnWarnPlayer(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getWarnService();

        if (!sender.hasPermission("BanSystem.UnWarn")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 3) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(target.getUniqueId().toString())
                    .close()
                    .build();


            Query query1 = new Query()
                    .addEq()
                    .setField("reason")
                    .setValue(args[1])
                    .close()
                    .build();


            if (target == null) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            }
            if (!service.getReader().containsObject(query)) {
                sender.sendMessage("§c The Player " + args[0] + " is not warned yet");
                return true;
            }
            if (!service.getReader().containsObject(query1)) {
                sender.sendMessage("§c Invalid warn!");
                return true;
            }
            PlayerWarn playerwarn = service.getReader().readObject(query, PlayerWarn.class);

            service.getWriter().write(new PlayerWarn(target.getUniqueId().toString(), playerwarn.getReason(), "INACTIVE", playerwarn.getUnwarnDate(), playerwarn.getWarnDate()));


            service.getWriter()
                    .delete(new Query()
                            .addEq()
                            .setField("id")
                            .setValue(target.getUniqueId().toString())
                            .setField("reason")
                            .setValue(args[1])
                            .setField("info")
                            .setValue("ACTIVE")
                            .close()
                            .build(), 1);

            sender.sendMessage("§a Successful unwarned " + target.getName());
            Bukkit.broadcastMessage("§a " + sender.getName() + " unwarned " + target.getName() + "(" + args[2] + ")");
        } else sender.sendMessage("§c Please use /unwarn <player> <warn reason> <unwarn reason>");
        return false;
    }
}