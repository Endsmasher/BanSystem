package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

public class PlayerWarnCount {

    public PlayerWarnCount() {

    }

    @SaveVar
    public String id;

    @SaveVar
    public long count;

    public PlayerWarnCount(String id, long count) {

        this.id = id;
        this.count = count;

    }

    public String getId() {return id;}

    public long getCount() {return count;}

}
