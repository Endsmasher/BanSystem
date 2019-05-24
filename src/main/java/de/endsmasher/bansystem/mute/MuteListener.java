package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.BanSystem;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class MuteListener implements Listener {

    private BanSystem plugin;


    public MuteListener(BanSystem plugin) {
        this.plugin = plugin;
    }



    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        DriveService service = plugin.getMuteService();
        Query query = new Query()
                .addEq()
                .setField("id")
                .setValue(event.getPlayer().getUniqueId().toString())
                .close()
                .build();


        if (Mute.muted.contains(event.getPlayer().getUniqueId().toString())) {
            PlayerMute playerMute = service.getReader().readObject(query, PlayerMute.class);
            if(playerMute.getUnmutedate() <= new Date().getTime()) {
                return;
            } else
                event.getPlayer().sendMessage("§7You'r still muted!");
                event.setCancelled(true);
        }
    }


}
