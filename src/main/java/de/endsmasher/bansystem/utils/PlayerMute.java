package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerMute {

    public PlayerMute() {

    }

    @SaveVar
    private String id;

    @SaveVar
    private String reason;

    @SaveVar
    private long mutedate;

    @SaveVar
    private long unmutedate;

    @SaveVar
    private String punisher;

    public PlayerMute(String id, String punisher, String reason, long unmutedate, long mutedate) {

        this.id = id;
        this.reason = reason;
        this.unmutedate = unmutedate;
        this.mutedate = mutedate;
        this.punisher = punisher;

    }

    public String getId() {return id;}

    public String getReason() {return reason;}

    public long getUnmutedate() {return unmutedate;}

    public long getMutedate() {return mutedate;}

    public String getPunisher() { return punisher;}


    public String getPrettyMuteDate() {
        SimpleDateFormat format = new SimpleDateFormat("EEE dd / MM / yyyy");
        return format.format(new Date(getMutedate()));

    }



}
