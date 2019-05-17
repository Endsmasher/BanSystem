package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

public class PlayerLogall {


    @SaveVar
    public String id;

    @SaveVar
    public long FirstLogin;

    @SaveVar
    public String name;


    public PlayerLogall(String id, String name, long FirstLogin) {
        this.id = id;
        this.name = name;
        this.FirstLogin = FirstLogin;

    }

    public String getId() {return id;}

    public String getName() {return name;}

    public long getFirstLogin() {return FirstLogin;}

    public PlayerLogall(){}

}
