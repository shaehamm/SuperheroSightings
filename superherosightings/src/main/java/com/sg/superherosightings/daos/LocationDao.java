/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface LocationDao {
    
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location) throws NullLocationDataException;
    void updateLocation(Location location) throws NullLocationDataException;
    void deleteLocationById(int id);
    
    List<Location> getLocationsByHero(Hero hero) throws NullHeroDataException;
}
