package com.example.olimpiadazueva;


public class OlimGame {
    String num;
    String city;
    String country;
    String flag;
    String year;
    String start;
    String finish;
    String gametype;
    double attitude;
    double longitude;

    //Конструктор
    OlimGame(String num,
             String city,
             String country,
             String flag,
             String year,
             String start,
             String finish,
             String gametype,
             double attitude,
             double longitude){
        this.num = num;
        this.city = city;
        this.year = year;
        this.country = country;
        this.flag = flag;
        this.start = start;
        this.finish = finish;
        this.gametype = gametype;
        this.longitude = longitude;
        this.attitude = attitude;
    }

    @Override
    public String toString() {
        return "OlimGame{" +
                "num='" + num + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", flag='" + flag + '\'' +
                ", year='" + year + '\'' +
                ", start='" + start + '\'' +
                ", finish='" + finish + '\'' +
                ", gametype='" + gametype + '\'' +
                ", attitude=" + attitude +
                ", longitude=" + longitude +
                '}';
    }
}
