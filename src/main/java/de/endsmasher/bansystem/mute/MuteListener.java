package de.endsmasher.bansystem.mute;

import de.endsmasher.bansystem.Ocelot;
import de.endsmasher.bansystem.utils.PlayerMute;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class MuteListener implements Listener {

    private Ocelot plugin;


    public MuteListener(Ocelot plugin) {
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

        PlayerMute playerMute = service.getReader().readObject(query, PlayerMute.class);
        if (playerMute != null) {
            if(playerMute.getUnmutedate() <= new Date().getTime()) {
                service.getWriter().delete(query, 1);
                return;
            } else
                event.getPlayer().sendMessage("§e Mute reason§7: §c" + playerMute.getReason());
                event.getPlayer().sendMessage("§a You will be unbanned at " + playerMute.getPrettyUnmuteDate());
                event.setCancelled(true);
        }
    }


}
