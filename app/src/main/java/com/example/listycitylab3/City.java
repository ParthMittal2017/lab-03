package com.example.listycitylab3;
import java.io.Serializable;

/*
 * Simple class I made to represent a city object.
 * Has two things: the city name and the province or state.
 * Serializable so we can pass it around like into a fragment.
 */
public class City implements Serializable {
    private String cityName;
    private String provinceName;
    // constructor
    public City(String cName, String prov) {
        this.cityName = cName;
        this.provinceName = prov;
    }

    // getters
    public String getName() {
        return cityName;
    }
    public String getProvince() {
        return provinceName;
    }
    // setters (so we can edit later)
    public void setName(String newName) {
        this.cityName = newName;
    }
    public void setProvince(String newProv) {
        this.provinceName = newProv;
    }
}
