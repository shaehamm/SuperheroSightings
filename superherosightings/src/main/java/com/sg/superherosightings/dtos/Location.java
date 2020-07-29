/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dtos;

import java.util.Objects;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author codedchai
 */
public class Location {

    private int id;
    
    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 50, message = "Location name must be less than 50 characters.")
    private String name;
    
    @NotBlank(message = "Location address must not be empty.")
    @Size(max = 50, message = "Location address must be less than 50 characters.")
    private String address;
    
    //@NotBlank(message = "Location latitude must not be empty.")
    @Min(value = -90, message = "Latitude must be between -90 and 90.")
    @Max(value = 90, message = "Latitude must be between -90 and 90.")
//    @Digits(integer=2, fraction=6, message = "Location latitude must be no more than"
//            + " 2 whole numbers and 6 fractions.")
    private double latitude;
    
    //@NotBlank(message = "Location longitude must not be empty.")
    @Min(value = -180, message = "Longitude must be between -180 and 180.")
    @Max(value = 180, message = "Longitude must be between -180 and 180.")
//    @Digits(integer=3, fraction=6, message = "Location longitude must be no more than"
//            + " 3 whole numbers and 6 fractions.")
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    
    
}
