package com.example.spacexships.model;

import com.example.spacexships.model.db.ShipDbEntity;

import java.util.List;

public class Ship {
    private final String ship_id;
    private String ship_name;
    private int year_built;
    private String image;
    private String ship_type;
    private int weight_kg;
    private String home_port;
    private String url;

    public Ship(String ship_id, String ship_name, int year_built, String image, String ship_type, int weight_kg, String home_port,String url) {
        this.ship_id = ship_id;
        this.ship_name = ship_name;
        this.year_built = year_built;
        this.image = image;
        this.ship_type = ship_type;
        this.weight_kg = weight_kg;
        this.home_port = home_port;
        this.url = url;
    }

    public  Ship(ShipDbEntity entity){
        this(
                entity.getShip_id(),
                entity.getShip_name(),
                entity.getYear_built(),
                entity.getImage(),
                entity.getShip_type(),
                entity.getWeight_kg(),
                entity.getHome_port(),
                entity.getUrl()
        );
    }

    public String getShip_id() {
        return ship_id;
    }

    public String getShip_name() {
        return ship_name;
    }

    public int getYear_built() {
        return year_built;
    }

    public String getImage() {
        return image;
    }

    public String getShip_type() {
        return ship_type;
    }


    public int getWeight_kg() {
        return weight_kg;
    }

    public String getHome_port() {
        return home_port;
    }

    public String getUrl() {
        return url;
    }


}
