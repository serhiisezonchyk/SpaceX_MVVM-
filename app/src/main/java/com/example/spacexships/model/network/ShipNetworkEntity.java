package com.example.spacexships.model.network;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipNetworkEntity {
    @JsonProperty("ship_id")
    private  String ship_id;

    @JsonProperty("ship_name")
    private String ship_name;

    @JsonProperty("year_built")
    private int year_built;

    @JsonProperty("image")
    private String image;

    @JsonProperty("ship_type")
    private String ship_type;


    @JsonProperty("weight_kg")
    private int weight_kg;

    @JsonProperty("home_port")
    private String home_port;

    @JsonProperty("url")
    private String url;


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
