package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

public class PlayerLog {

    public PlayerLog() {
    }

    @SaveVar
    private String Targetid;

    @SaveVar
    private String Senderid;

    @SaveVar
    private boolean AddTime;


    public PlayerLog(String Targetid, String Senderid, boolean AddTime) {
        this.Targetid = Targetid;
        this.Senderid = Senderid;
        this.AddTime = AddTime;
    }

    public String getTargetid() { return Targetid;}
    public String getSenderid() { return Senderid;}
    public boolean getAddtime() { return AddTime;}

}
