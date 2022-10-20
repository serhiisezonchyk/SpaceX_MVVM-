package com.example.spacexships.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.spacexships.model.network.ShipNetworkEntity;

import java.util.List;

@Entity(tableName = "ships")
public class ShipDbEntity {
    @PrimaryKey
    @NonNull
    private String ship_id;

    @ColumnInfo(name = "ship_name")
    private String ship_name;

    @ColumnInfo(name = "year_built")
    private int year_built;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "ship_type")
    private String ship_type;

    @ColumnInfo(name = "weight_kg")
    private int weight_kg;

    @ColumnInfo(name = "home_port")
    private String home_port;

    @ColumnInfo(name = "url")
    private String url;

    public ShipDbEntity() {
    }
    public ShipDbEntity(ShipNetworkEntity entity) {
        this.ship_id = entity.getShip_id();
        this.ship_name = entity.getShip_name();
        this.year_built = entity.getYear_built();
        this.image = entity.getImage();
        this.ship_type = entity.getShip_type();
        this.weight_kg = entity.getWeight_kg();
        this.home_port = entity.getHome_port();
        this.url = entity.getUrl();}

    @NonNull
    public String getShip_id() {
        return ship_id;
    }

    public void setShip_id(@NonNull String ship_id) {
        this.ship_id = ship_id;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public int getYear_built() {
        return year_built;
    }

    public void setYear_built(int year_built) {
        this.year_built = year_built;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShip_type() {
        return ship_type;
    }

    public void setShip_type(String ship_type) {
        this.ship_type = ship_type;
    }

    public int getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(int weight_kg) {
        this.weight_kg = weight_kg;
    }

    public String getHome_port() {
        return home_port;
    }

    public void setHome_port(String home_port) {
        this.home_port = home_port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
