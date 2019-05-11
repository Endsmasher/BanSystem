package de.endsmasher.bansystem;

import de.endsmasher.bansystem.ban.LoginListener;
import de.endsmasher.bansystem.ban.PermBan;
import de.endsmasher.bansystem.ban.TempBan;
import de.endsmasher.bansystem.ban.Unban;
import de.endsmasher.bansystem.mute.Mute;
import de.endsmasher.bansystem.mute.MuteListener;
import de.endsmasher.bansystem.mute.UnMute;
import de.endsmasher.bansystem.utils.PlayerBan;
import de.endsmasher.bansystem.utils.PlayerMute;
import de.endsmasher.bansystem.utils.PlayerWarn;
import de.endsmasher.bansystem.warn.UnWarnPlayer;
import de.endsmasher.bansystem.warn.WarnHistory;
import de.endsmasher.bansystem.warn.WarnPlayer;
import net.endrealm.realmdrive.factory.DriveServiceFactory;
import net.endrealm.realmdrive.interfaces.ConversionHandler;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.utils.DriveSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BanSystem extends JavaPlugin {

    private DriveService banService;

    public DriveService getBanService() {
        return banService;
    }

    private DriveService warnService;

    public DriveService getWarnService() { return warnService;}

    private DriveService muteService;

    public DriveService getMuteService() {return muteService;}

    @Override
    public void onEnable() {


        DriveSettings settings = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("general")
                .build();
        banService = new DriveServiceFactory().getDriveService(settings);
        ConversionHandler conversion = banService.getConversionHandler();
        conversion.registerClasses(PlayerBan.class);




        DriveSettings settings1 = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("warn")
                .build();
        warnService = new DriveServiceFactory().getDriveService(settings1);
        ConversionHandler conversionWarn = warnService.getConversionHandler();
        conversionWarn.registerClasses(PlayerWarn.class);




        DriveSettings settingsmute = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("mute")
                .build();
        muteService = new DriveServiceFactory().getDriveService(settingsmute);
        ConversionHandler conversionmute = muteService.getConversionHandler();
        conversionmute.registerClasses(PlayerMute.class);




        getCommand("ban").setExecutor(new PermBan(this));
        getCommand("tempban").setExecutor(new TempBan(this));
        getCommand("unban").setExecutor(new Unban(this));

        getCommand("warn").setExecutor(new WarnPlayer(this));
        getCommand("unwarn").setExecutor(new UnWarnPlayer(this));
        getCommand("history").setExecutor(new WarnHistory(this));

        getCommand("mute").setExecutor(new Mute(this));
        getCommand("unmute").setExecutor(new UnMute(this));



        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);

        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);

        Bukkit.getPluginManager().registerEvents(new MuteListener(this), this);



        //Folgender code kann auch wo anders sein

        /*banService.getWriter().write(new PlayerBan("IDOskhösfjhsdfhöesfesf", "Hacking", new Date().getTime()+1000*60*60*24, new Date().getTime()));
        PlayerBan playerBan = banService.getReader().readObject(
                new Query()
                    .addEq()
                        .setField("id")
                        .setValue("IDOskhösfjhsdfhöesfesf")
                    .close()
                .build(), PlayerBan.class);
        banService.getWriter().delete(new Query()
                .addEq()
                .setField("id")
                .setValue("IDOskhösfjhsdfhöesfesf")
                .close()
                .build(), 1 wie viele einträge ich löschen will); */

    }

    @Override
    public void onDisable() {

    }
}
