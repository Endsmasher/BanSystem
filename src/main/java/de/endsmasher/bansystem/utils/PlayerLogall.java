package de.endsmasher.bansystem.utils;

import net.endrealm.realmdrive.annotations.SaveVar;

public class PlayerLogall {


    @SaveVar
    public String id;

    @SaveVar
    public long FirstLogin;

    @SaveVar
    public String name;

    @SaveVar
    public String address;


    public PlayerLogall(String id, String name, String fjoinAddress,long FirstLogin) {
        this.id = id;
        this.name = name;
        this.address = fjoinAddress;
        this.FirstLogin = FirstLogin;

    }

    public String getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public long getFirstLogin() {return FirstLogin;}

    public PlayerLogall(){}

}
