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
    private String warned_by;

    @SaveVar
    private long unWarnDate;

    @SaveVar
    private long warnDate;


    public PlayerWarn(String id,String warned_by, String reason, String info, long unWarnDate, long warnDate) {

        this.id = id;
        this.warned_by  = warned_by;
        this.reason = reason;
        this.warnDate = warnDate;
        this.unWarnDate = unWarnDate;
        this.info = info;

    }

    public String getId() { return id;}

    public String getWarned_by() {return warned_by ;}

    public String getReason() { return reason;}

    public long getUnWarnDate() {return unWarnDate;}

    public long getWarnDate() { return warnDate;}

    public String getInfo() { return  info;}

    public String getPrettyWarnDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getWarnDate()));

    }

}
