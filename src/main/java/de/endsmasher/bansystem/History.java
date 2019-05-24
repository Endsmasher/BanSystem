package de.endsmasher.bansystem;

import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerLogall;
import de.endsmasher.bansystem.utils.PlayerMute;
import de.endsmasher.bansystem.utils.PlayerWarn;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class History implements CommandExecutor {

    private BanSystem plugin;

    public History(BanSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        DriveService warnService = plugin.getWarnService();
        DriveService banService = plugin.getBanService();
        DriveService muteService = plugin.getMuteService();
        DriveService servicelogall = plugin.getlService();

        if (!sender.hasPermission("BanSystem.Team")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 1) {

            Query queryall = new Query()
                    .addEq()
                    .setField("name")
                    .setValue(args[0])
                    .close()
                    .build();

            PlayerLogall playerLogall = servicelogall.getReader().readObject(queryall, PlayerLogall.class);

            if (!servicelogall.getReader().containsObject(queryall)) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;
            }
            Query query = new Query()
                    .addEq()
                    .setField("id")
                    .setValue(playerLogall.getId())
                    .close()
                    .build();


            List <PlayerWarn> playerWarns = warnService.getReader().readAllObjects(query, PlayerWarn.class);
            List <PlayerBan> playerBans = banService.getReader().readAllObjects(query, PlayerBan.class);
            List <PlayerMute> playerMutes = muteService.getReader().readAllObjects(query, PlayerMute.class);


            sender.sendMessage("§6 ---------- History of " + args[0] + " ---------- ");
            sender.sendMessage("§6 UUID: §7" + playerLogall.getId());
            sender.sendMessage("§6 Bans: §7");
            for (PlayerBan playerBan : playerBans) {
                sender.sendMessage("§7 - " + playerBan.getPrettyBanDate());
                sender.sendMessage("§6   > Reason: §7" + playerBan.getReason());
                sender.sendMessage("§6   > Banned by: §7" + playerBan.getBanned_by());
                sender.sendMessage(" ");
            }

            sender.sendMessage( "§6 Warns: §7");
            for (PlayerWarn playerWarn : playerWarns) {
                sender.sendMessage("§7 - " + playerWarn.getPrettyWarnDate());
                sender.sendMessage("   §6> Reason: §7" + playerWarn.getReason());
                sender.sendMessage("   §6> Info: §7" + playerWarn.getInfo());
                sender.sendMessage(" ");
            }

            sender.sendMessage("§6 Mutes: §7");
            for (PlayerMute playerMute : playerMutes) {
                sender.sendMessage("§7 - " + playerMute.getPrettyMuteDate());
                sender.sendMessage("   §6> Reason: §7" + playerMute.getReason());
                sender.sendMessage(" ");
            }
        } else sender.sendMessage("§c Please use /history <player>");
        return false;
    }
}