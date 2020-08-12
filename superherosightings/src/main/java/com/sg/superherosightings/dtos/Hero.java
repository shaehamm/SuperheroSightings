/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author codedchai
 */
public class Hero {

    private int id;

    private int quirkId;

    private Quirk quirk;

    @NotBlank(message = "Hero name must not be empty.")
    @Size(max = 30, message = "Hero name must be less than 30 characters.")
    private String name;

    @NotBlank(message = "Hero alignment must not be empty.")
    @Size(max = 15, message = "Hero alignment must be less than 15 characters.")
    private String alignment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quirk getQuirk() {
        return quirk;
    }

    public void setQuirk(Quirk quirk) {
        this.quirk = quirk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getQuirkId() {
        return quirkId;
    }

    public void setQuirkId(int quirkId) {
        this.quirkId = quirkId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.quirk);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.alignment);
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
        final Hero other = (Hero) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.alignment, other.alignment)) {
            return false;
        }
        if (!Objects.equals(this.quirk, other.quirk)) {
            return false;
        }
        return true;
    }

}
