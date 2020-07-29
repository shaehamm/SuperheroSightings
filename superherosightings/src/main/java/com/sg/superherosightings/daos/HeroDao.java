/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.dtos.Quirk;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface HeroDao {
    
    Hero getHeroById(int id);
    List<Hero> getAllHeroes();
    Hero addHero(Hero hero) throws NullHeroDataException;
    void updateHero(Hero hero) throws NullHeroDataException;
    void deleteHeroById(int id);
    
    List<Hero> getHeroesForQuirk(Quirk quirk) throws NullQuirkDataException;
    List<Hero> getHeroesForLocation(Location location) throws NullLocationDataException;
    List<Hero> getHeroesForOrg(Org org) throws NullOrganizationDataException;
}
