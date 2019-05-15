package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

public class PlayerLog {

    public PlayerLog() {
    }

    @SaveVar
    private String Targetid;

    @SaveVar
    private String SenderName;

    @SaveVar
    private long AddTime;


    public PlayerLog(String Targetid, String SenderName, long AddTime) {

        this.Targetid = Targetid;
        this.SenderName = SenderName;
        this.AddTime = AddTime;

    }

    public String getTargetid() { return Targetid;}
    public String getSenderName() { return SenderName;}
    public long getAddtime() { return AddTime;}

}
