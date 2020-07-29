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
import com.sg.superherosightings.dtos.Sighting;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author codedchai
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrgDao orgDao;

    @Autowired
    QuirkDao quirkDao;

    @Autowired
    SightingDao sightDao;

    public LocationDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        List<Hero> heros = heroDao.getAllHeroes();
        for (Hero hero : heros) {
            heroDao.deleteHeroById(hero.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Org> orgs = orgDao.getAllOrgs();
        for (Org org : orgs) {
            orgDao.deleteOrgById(org.getId());
        }

        List<Quirk> quirks = quirkDao.getAllQuirks();
        for (Quirk quirk : quirks) {
            quirkDao.deleteQuirkById(quirk.getId());
        }

        List<Sighting> sightings = sightDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightDao.deleteSightingById(sighting.getId());
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getLocationById method, of class LocationDaoDB. Test of
     * addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocationGoldenPath() throws NullLocationDataException {
        Location location = new Location();
        location.setName("Test Facility");
        location.setAddress("Fake Address");
        location.setLatitude(89.234);
        location.setLongitude(-2.323);

        Location toCheck = locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocationsGoldenPath() throws NullLocationDataException {
        Location location = new Location();
        location.setName("Test Facility");
        location.setAddress("Fake Address");
        location.setLatitude(89.234);
        location.setLongitude(-2.323);
        location = locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setName("Test Facility2");
        location2.setAddress("Fake Address2");
        location2.setLatitude(9.234);
        location2.setLongitude(-25.323);
        location2 = locationDao.addLocation(location2);

        List<Location> toCheck = locationDao.getAllLocations();
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(location));
        assertTrue(toCheck.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocationGoldenPath() throws NullLocationDataException {
        Location location = new Location();
        location.setName("Test Facility");
        location.setAddress("Fake Address");
        location.setLatitude(89.234);
        location.setLongitude(-2.323);
        location = locationDao.addLocation(location);

        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);

        location.setName("New Name");

        locationDao.updateLocation(location);
        assertNotEquals(location, fromDao);

        fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationByIdGoldenPath() throws NullLocationDataException {
        Location location = new Location();
        location.setName("Test Facility");
        location.setAddress("Fake Address");
        location.setLatitude(89.234);
        location.setLongitude(-2.323);

        Location toCheck = locationDao.addLocation(location);
        Location fromDao = locationDao.getLocationById(toCheck.getId());
        assertEquals(toCheck, fromDao);

        locationDao.deleteLocationById(toCheck.getId());
        fromDao = locationDao.getLocationById(toCheck.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getLocationsByHero method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsByHeroGoldenPath() throws NullHeroDataException, NullLocationDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);

        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Test Facility");
        location.setAddress("Fake Address");
        location.setLatitude(89.234);
        location.setLongitude(-2.323);
        location = locationDao.addLocation(location);

        Sighting sight = new Sighting();
        sight.setHero(hero);
        sight.setLocation(location);
        sight.setDate(LocalDate.of(2020, 1, 15));
        sight.setDescription("Was walking around.");
        sight = sightDao.addSighting(sight);
        
        Location location2 = new Location();
        location2.setName("Test Field");
        location2.setAddress("Fake Address2");
        location2.setLatitude(9.234);
        location2.setLongitude(-27.323);
        location2 = locationDao.addLocation(location2);

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location2);
        sight2.setDate(LocalDate.of(2020, 2, 24));
        sight2.setDescription("Saw with another person.");
        sight2 = sightDao.addSighting(sight2);
        
        List<Location> toCheck = locationDao.getLocationsByHero(hero);
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(location));
        assertTrue(toCheck.contains(location2));
        

    }

}
