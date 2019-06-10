package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerLog {


    @SaveVar
    private String name;

    @SaveVar
    private String id;

    @SaveVar
    private String SenderName;

    @SaveVar
    private long AddTime;


    public PlayerLog(String id, String name, String SenderName, long AddTime) {
        this.name = name;
        this.id = id;
        this.SenderName = SenderName;
        this.AddTime = AddTime;
    }

    public String getid() {
        return id;
    }

    public String getName() {return name;}

    public String getSenderName() {
        return SenderName;
    }

    public long getAddtime() {
        return AddTime;
    }
    public PlayerLog() {
    }

    public String getPrettyAddDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getAddtime()));

    }
}
