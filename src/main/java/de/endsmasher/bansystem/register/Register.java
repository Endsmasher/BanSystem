package de.endsmasher.bansystem.register;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Register implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        if (!sender.hasPermission("BanSystem.Admin")) {
            sender.sendMessage("§cYou don't have enough permissions to perform this command!");
            return true;
        }
        if (args.length == 1) {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);


            if (target == null) {
                sender.sendMessage("§c Unknown Player " + args[0]);
                return true;

            }


        }
    }
}

