package de.endsmasher.bansystem.ban;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerLogall;
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
        DriveService servicel = plugin.getlService();

        String prefix = "§7[§6Ocelot§7] ";

        if (sender.hasPermission("BanSystem.Team")) {
            if (args.length == 1) {

                Query query = new Query()
                        .addEq()
                            .setField("name")
                            .setValue(args[0])
                        .close()
                        .build();
                PlayerLogall playerLogall = servicel.getReader().readObject(query, PlayerLogall.class);


                if (!servicel.getReader().containsObject(query)) {
                    sender.sendMessage(prefix +"Unknown Player " + args[0]);
                    return true;
                }

                Query query1 = new Query()
                        .addEq()
                            .setField("id")
                            .setValue(playerLogall.getId())
                        .close()
                        .build();
                if (!service.getReader().containsObject(query1)) {
                     sender.sendMessage(prefix + "The Player " + args[0] + " is not banned");
                    return true;
                }

                    service.getWriter()
                            .delete(new Query()
                                    .addEq()
                                    .setField("id")
                                    .setValue(playerLogall.getId())
                                    .close()
                                    .build(), 1);
                    sender.sendMessage(prefix + " Successful unbanned " + args[0]);

                } else sender.sendMessage(prefix + "Please use /unban <player>");

            } else sender.sendMessage(prefix + " You don't have enough permissions");
            return true;
        }

}
