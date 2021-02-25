package dev.houston.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Client {
    private String fname;
    private String lname;
    private int id;

    public Client(String fname, String lname, int id) {
        this.fname = fname;
        this.lname = lname;
        this.id = id;
    }
    public Client(){
    }


    public String getName(){
        String fullName = this.fname + " " + this.lname;
        System.out.println(fullName);
        return fullName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", id=" + id +
                '}';
    }
}
