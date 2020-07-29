/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dtos;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author codedchai
 */
public class Org {
    
    private int id;
    
    private List<Hero> heroes;
    
    @NotBlank(message = "Org name must not be empty.")
    @Size(max = 30, message = "Org name must be less than 30 characters.")
    private String name;
    
    @NotBlank(message = "Org description must not be empty.")
    @Size(max = 250, message = "Org description must be less than 250 characters.")
    private String description;
    
    @NotBlank(message = "Org address must not be empty.")
    @Size(max = 200, message = "Org address must be less than 100 characters.")
    private String address;
    
    @NotBlank(message = "Org contact info must not be empty.")
    @Size(max = 50, message = "Org contact info must be less than 50 characters.")
    private String contactInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.heroes);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.address);
        hash = 29 * hash + Objects.hashCode(this.contactInfo);
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
        final Org other = (Org) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.contactInfo, other.contactInfo)) {
            return false;
        }
        if (!Objects.equals(this.heroes, other.heroes)) {
            return false;
        }
        return true;
    }


    
    
}
