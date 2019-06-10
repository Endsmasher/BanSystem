package de.endsmasher.bansystem.utils;

import org.bukkit.ChatColor;
import sun.security.krb5.Config;

public class BanScreenStrings {



    public static String IPBanline1 = "Invalid config1";

    public static String IPBanline2 = "Invalid config";

    public static String IPBanline3 = "Invalid config";

    public static String IPBanline4 = "Invalid config";

    public static String IPBanline5 = "Invalid config";

    public static String IPBanline6 = "Invalid config";

    public static String IPBanline7 = "Invalid config";

    public static String IPBanline8 = "Invalid config";

    public static String IPBanline9 = "Invalid config";

    public static String IPBanline10 = "Invalid config";

    public static String IPBanline11 = "Invalid config";

    public static String IPBanline12 = "Invalid config";

    public static String IPBanline13 = "Invalid config";

    public static String IPBanline14 = "Invalid config";

    public static String IPBanline15 = "Invalid config";



    public static String PBanline1 = "Invalid config2";

    public static String PBanline2 = "Invalid config";

    public static String PBanline3 = "Invalid config";

    public static String PBanline4 = "Invalid config";

    public static String PBanline5 = "Invalid config";

    public static String PBanline6 = "Invalid config";

    public static String PBanline7 = "Invalid config";

    public static String PBanline8 = "Invalid config";

    public static String PBanline9 = "Invalid config";

    public static String PBanline10 = "Invalid config";

    public static String PBanline11 = "Invalid config";

    public static String PBanline12 = "Invalid config";

    public static String PBanline13 = "Invalid config";

    public static String PBanline14 = "Invalid config";

    public static String PBanline15 = "Invalid config";



    public static String TBanline1 = "Invalid config3";

    public static String TBanline2 = "Invalid config";

    public static String TBanline3 = "Invalid config";

    public static String TBanline4 = "Invalid config";

    public static String TBanline5 = "Invalid config";

    public static String TBanline6 = "Invalid config";

    public static String TBanline7 = "Invalid config";

    public static String TBanline8 = "Invalid config";

    public static String TBanline9 = "Invalid config";

    public static String TBanline10 = "Invalid config";

    public static String TBanline11 = "Invalid config";

    public static String TBanline12 = "Invalid config";

    public static String TBanline13 = "Invalid config";

    public static String TBanline14 = "Invalid config";

    public static String TBanline15 = "Invalid config";




    public static void init() {

        IPBanline1 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line1", IPBanline1);
        IPBanline2 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line2", IPBanline2);
        IPBanline3 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line3", IPBanline3);
        IPBanline4 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line4", IPBanline4);
        IPBanline5 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line5", IPBanline5);
        IPBanline6 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line6", IPBanline6);
        IPBanline7 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line7", IPBanline7);
        IPBanline8 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line8", IPBanline8);
        IPBanline9 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line9", IPBanline9);
        IPBanline10 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line10", IPBanline10);
        IPBanline11 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line11", IPBanline11);
        IPBanline12 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line12", IPBanline12);
        IPBanline13 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line13", IPBanline13);
        IPBanline14 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line14", IPBanline14);
        IPBanline15 = getFromConfig(ConfigHolder.Configs.BanScreen, "IPBan.line15", IPBanline15);

        PBanline1 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line1", PBanline1);
        PBanline2 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line2", PBanline2);
        PBanline3 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line3", PBanline3);
        PBanline4 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line4", PBanline4);
        PBanline5 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line5", PBanline5);
        PBanline6 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line6", PBanline6);
        PBanline7 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line7", PBanline7);
        PBanline8 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line8", PBanline8);
        PBanline9 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line9", PBanline9);
        PBanline10 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line10", PBanline10);
        PBanline11 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line11", PBanline11);
        PBanline12 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line12", PBanline12);
        PBanline13 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line13", PBanline13);
        PBanline14 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line14", PBanline14);
        PBanline15 = getFromConfig(ConfigHolder.Configs.BanScreen, "PermBan.line15", PBanline15);

        TBanline1 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line1", TBanline1);
        TBanline2 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line2", TBanline2);
        TBanline3 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line3", TBanline3);
        TBanline4 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line4", TBanline4);
        TBanline5 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line5", TBanline5);
        TBanline6 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line6", TBanline6);
        TBanline7 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line7", TBanline7);
        TBanline8 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line8", TBanline8);
        TBanline9 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line9", TBanline9);
        TBanline10 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line10", TBanline10);
        TBanline11 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line11", TBanline11);
        TBanline12 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line12", TBanline12);
        TBanline13 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line13", TBanline13);
        TBanline14 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line14", TBanline14);
        TBanline15 = getFromConfig(ConfigHolder.Configs.BanScreen, "TempBan.line15", TBanline15);

    }
    private static String getFromConfig(ConfigHolder.Configs config, String key, String defaultw) {

        if (config.getConfig().getString(key) == null) {
            return defaultw;

        }
        return ChatColor.translateAlternateColorCodes('&',config.getConfig().getString(key));
    }






}
