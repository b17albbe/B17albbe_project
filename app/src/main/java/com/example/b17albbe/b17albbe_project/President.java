package com.example.b17albbe.b17albbe_project;

/**
 * Created by b17albbe on 2018-05-15.
 */


public class President {
    private String name;
    private String birthLocation;
    private String birthDate;
    private String achievement;
    private String served;


    // Constructor(s)
    public President(String inName, String inLocation, String inDate, String inAchievement, String inServed) {
        name = inName;
        birthLocation = inLocation;
        birthDate = inDate;
        achievement = inAchievement;
        served = inServed;
    }

    public President(String inName, String inDate, String inAchievement, String inServed) {
        name = inName;
        birthLocation = "";
        birthDate = inDate;
        achievement = inAchievement;
        served = inServed;
    }

    // member methods

    public String getServed() {return served;}

    public String getAcievement() {return achievement;}

    public String getDate() {return birthDate;}

    public String getLocation() {return birthLocation;}

    public String toString() {return name;}

    public String info(){

        String str=name;
        str+=" was born in ";
        str+=birthLocation;
        str+=" on ";
        str+=birthDate;
        str+=achievement;
        str+=served;
        return str;
    }

    // public void setHeight(int newHeight) {
    //     height=newHeight;
    // }
}
