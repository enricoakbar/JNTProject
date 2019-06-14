package com.ena.managemenapk.Model;

public class Users {
    private String username, nip, password;

    public Users()
    {

    }

    public Users(String username, String nip, String password){
        this.username= username;
        this.nip=nip;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
