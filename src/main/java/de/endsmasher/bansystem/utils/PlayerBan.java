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
    private long unBanDate;

    @SaveVar
    private long banDate;

    @SaveVar
    private String banned_by;

    @SaveVar
    private String address;


    public PlayerBan(String id,String Banned_by, String Reason,String Address, long UnbanDate, long BanDate) {
        this.id = id;
        this.banned_by = Banned_by;
        this.reason = Reason;
        this.address = Address;
        this.unBanDate = UnbanDate;
        this.banDate = BanDate;
    }



    public String getId() {
        return id;
    }

    public String getBanned_by() {return banned_by;}

    public String getReason() {
        return reason;
    }

    public String getAddress() {return  address;}

    public long getUnBanDate() {
        return unBanDate;
    }

    public long getBanDate() {
        return banDate;
    }

    public String getPrettyBanDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getBanDate()));
    }
    public String getPrettyUnBanDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getUnBanDate()));
    }
}
