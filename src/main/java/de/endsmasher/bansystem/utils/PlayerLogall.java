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

    @SaveVar
    public long connections;


    public PlayerLogall(String id, String name, String fjoinAddress,long FirstLogin, long connections) {
        this.id = id;
        this.name = name;
        this.address = fjoinAddress;
        this.connections = connections;
        this.FirstLogin = FirstLogin;

    }

    public String getId() {return id;}

    public String getName() {return name;}

    public String getAddress() {return address;}

    public long getFirstLogin() {return FirstLogin;}

    public long getConnections() {return connections;}

    public PlayerLogall(){}

}
