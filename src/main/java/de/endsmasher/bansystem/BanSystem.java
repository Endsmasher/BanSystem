package de.endsmasher.bansystem;

import de.endsmasher.bansystem.ban.LoginListener;
import de.endsmasher.bansystem.ban.PermBan;
import de.endsmasher.bansystem.ban.TempBan;
import de.endsmasher.bansystem.ban.Unban;
import de.endsmasher.bansystem.mute.Mute;
import de.endsmasher.bansystem.mute.MuteListener;
import de.endsmasher.bansystem.mute.UnMute;
import de.endsmasher.bansystem.register.ListLogged;
import de.endsmasher.bansystem.register.Register;
import de.endsmasher.bansystem.register.Remove;
import de.endsmasher.bansystem.utils.*;
import de.endsmasher.bansystem.warn.UnWarnPlayer;
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

    public DriveService logService;
    public DriveService getLogService() {return logService;}

    public DriveService lService;
    public DriveService getlService() {return lService;}

    public DriveService WarncountService;
    public DriveService getWarncountService() {return WarncountService;}

    public static BanSystem instance;

    private ConfigHolder configHolder;

    public static BanSystem getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {

        System.out.println("The Plugin is loading ...");

        instance = this;

        configHolder = new ConfigHolder(this);

        // To save the Bans
        DriveSettings settings = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("general")
                .build();
        banService = new DriveServiceFactory().getDriveService(settings);
        ConversionHandler conversion = banService.getConversionHandler();
        conversion.registerClasses(PlayerBan.class);



        // To save the Warns
        DriveSettings settings1 = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("warn")
                .build();
        warnService = new DriveServiceFactory().getDriveService(settings1);
        ConversionHandler conversionWarn = warnService.getConversionHandler();
            WarncountService = new DriveServiceFactory().getDriveService(settings1);
            ConversionHandler conversionWarnCount = WarncountService.getConversionHandler();
        conversionWarn.registerClasses(PlayerWarn.class, PlayerWarnCount.class);



        // To save the Mutes
        DriveSettings settingsmute = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("mute")
                .build();
        muteService = new DriveServiceFactory().getDriveService(settingsmute);
        ConversionHandler conversionmute = muteService.getConversionHandler();
        conversionmute.registerClasses(PlayerMute.class);



        //To save the logged players
        DriveSettings settingslog = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("log")
                .build();
         logService = new DriveServiceFactory().getDriveService(settingslog);
         ConversionHandler conversionlog = logService.getConversionHandler();
         conversionlog.registerClasses(PlayerLog.class);



        //To register new players
        DriveSettings settingsl = DriveSettings.builder()
                .type(DriveSettings.BackendType.MONGO_DB)
                .hostURL("mongodb://localhost:27017")
                .database("BanSystem")
                .table("logNew")
                .build();
        lService = new DriveServiceFactory().getDriveService(settingsl);
        ConversionHandler conversionl = lService.getConversionHandler();
        conversionl.registerClasses(PlayerLogall.class);



        System.out.println("loading ...");

        // Register the Commands

        getCommand("ban").setExecutor(new PermBan(this));
        getCommand("tempban").setExecutor(new TempBan(this));
        getCommand("unban").setExecutor(new Unban(this));

        getCommand("warn").setExecutor(new WarnPlayer(this));
        getCommand("unwarn").setExecutor(new UnWarnPlayer(this));
        getCommand("history").setExecutor(new History(this));

        getCommand("mute").setExecutor(new Mute(this));
        getCommand("unmute").setExecutor(new UnMute(this));

        getCommand("SystemLog").setExecutor(new ListLogged(this));
        getCommand("Register").setExecutor(new Register(this));
        getCommand("Remove").setExecutor(new Remove(this));

        System.out.println("The Plugin registered all Commands!");

        // Register the Events

        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new LoginListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MuteListener(this), this);
        Bukkit.getPluginManager().registerEvents(new de.endsmasher.bansystem.lognew.LoginListener(this), this);
        System.out.println("The Plugin registered all Events!");
        System.out.println("The Plugin started!");
    }

    @Override
    public void onDisable() {
    System.out.println("The Plugin turned off!");
    }

    public ConfigHolder getConfigHolder() {
        return configHolder;
    }
}
