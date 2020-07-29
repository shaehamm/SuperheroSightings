/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.dtos.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface SightingDao {
    
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForHero(Hero hero);
    List<Sighting> getSightingsByLocation(Location location);
    List<Sighting> getSightingsByDate(LocalDate date);
    List<Sighting> getLatestSightings(int count);
    
}
