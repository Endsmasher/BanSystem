package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerWarn {


    public PlayerWarn() {

    }

    @SaveVar
    private String id;

    @SaveVar
    private String reason;

    @SaveVar
    private String info;

    @SaveVar
    private long unwarnDate;

    @SaveVar
    private long warnDate;


    public PlayerWarn(String id, String reason, String info, long unwarnDate, long warnDate) {

        this.id = id;
        this.reason = reason;
        this.warnDate = warnDate;
        this.unwarnDate = unwarnDate;
        this.info = info;

    }

    public String getId() { return id;}

    public String getReason() { return reason;}

    public long getUnwarnDate() {return unwarnDate;}

    public long getWarnDate() { return warnDate;}

    public String getInfo() { return  info;}

    public String getPrettyWarnDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getWarnDate()));

    }

}
