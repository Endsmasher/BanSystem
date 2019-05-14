package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Unban implements CommandExecutor {


    private BanSystem plugin;


    public Unban(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DriveService service = plugin.getBanService();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);


        if (sender.hasPermission("BanSystem.Team")) {
            if (args.length == 1) {
                Query query = new Query()
                        .addEq()
                        .setField("id")
                        .setValue(target.getUniqueId().toString())
                        .close()
                        .build();

                if (!service.getReader().containsObject(query)) {
                     sender.sendMessage("§cThe Player " + target.getName() + " is not banned!");
                    return true;
                }


                    service.getWriter()
                            .delete(new Query()
                                    .addEq()
                                    .setField("id")
                                    .setValue(target.getUniqueId().toString())
                                    .close()
                                    .build(), 1);
                    sender.sendMessage("§a Successful unbanned " + target.getName());

                } else sender.sendMessage("Please use /unban <player>");

            } else sender.sendMessage("§c You don't have enough permissions");
            return true;
        }

}
