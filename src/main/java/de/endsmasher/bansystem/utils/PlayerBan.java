package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerBan  {


    public PlayerBan() {
    }

    @SaveVar
    private String id;

    @SaveVar
    private String reason;

    @SaveVar
    private long unbanDate;

    @SaveVar
    private long banDate;


    public PlayerBan(String id, String reason, long unbanDate, long banDate) {
        this.id = id;
        this.reason = reason;
        this.unbanDate = unbanDate;
        this.banDate = banDate;
    }



    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public long getUnbanDate() {
        return unbanDate;
    }

    public long getBanDate() {
        return banDate;
    }

    public String getPrettyBanDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getBanDate()));

    }

}
