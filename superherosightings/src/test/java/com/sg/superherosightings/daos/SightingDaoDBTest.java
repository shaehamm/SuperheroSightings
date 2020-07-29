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
import java.time.Month;
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
public class SightingDaoDBTest {

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

    public SightingDaoDBTest() {
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
     * Test of getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSightingGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        Sighting toCheck = sightDao.addSighting(sight);

        Sighting fromDao = sightDao.getSightingById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingsGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        location2.setLongitude(-22.323);
        location2 = locationDao.addLocation(location2);

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location2);
        sight2.setDate(LocalDate.of(2020, 3, 25));
        sight2.setDescription("Was talking to someone.");
        sight2 = sightDao.addSighting(sight2);

        List<Sighting> toCheck = sightDao.getAllSightings();
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(sight));
        assertTrue(toCheck.contains(sight2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSightingGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        Sighting toCheck = sightDao.addSighting(sight);

        Sighting fromDao = sightDao.getSightingById(toCheck.getId());
        assertEquals(toCheck, fromDao);

        Location location2 = new Location();
        location2.setName("Test Field");
        location2.setAddress("Fake Address2");
        location2.setLatitude(9.234);
        location2.setLongitude(-22.323);
        location2 = locationDao.addLocation(location2);
        toCheck.setLocation(location2);
        toCheck.setDescription("Was running quickly!");

        sightDao.updateSighting(toCheck);
        assertNotEquals(toCheck, fromDao);

        fromDao = sightDao.getSightingById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingByIdGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        Sighting toCheck = sightDao.addSighting(sight);

        Sighting fromDao = sightDao.getSightingById(toCheck.getId());
        assertEquals(toCheck, fromDao);

        sightDao.deleteSightingById(toCheck.getId());
        fromDao = sightDao.getSightingById(toCheck.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByDateGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        location2.setLongitude(-22.323);
        location2 = locationDao.addLocation(location2);

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location2);
        sight2.setDate(LocalDate.of(2020, 1, 15));
        sight2.setDescription("Was talking to someone.");
        sight2 = sightDao.addSighting(sight2);

        Sighting sight3 = new Sighting();
        sight3.setHero(hero);
        sight3.setLocation(location2);
        sight3.setDate(LocalDate.of(2020, 2, 15));
        sight3.setDescription("Was talking to someone.");
        sight3 = sightDao.addSighting(sight3);

        List<Sighting> toCheck = sightDao.getSightingsByDate(LocalDate.of(2020, 1, 15));
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(sight));
        assertTrue(toCheck.contains(sight2));

    }

    /**
     * Test of getLatestSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetLatestSightingsGoldenPath() throws NullHeroDataException, NullLocationDataException {
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

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location);
        sight2.setDate(LocalDate.of(2020, 3, 25));
        sight2.setDescription("Was talking to someone.");
        sight2 = sightDao.addSighting(sight2);

        Sighting sight3 = new Sighting();
        sight3.setHero(hero);
        sight3.setLocation(location);
        sight3.setDate(LocalDate.of(2019, 11, 2));
        sight3.setDescription("Was talking to noone.");
        sight3 = sightDao.addSighting(sight3);

        List<Sighting> toCheck = sightDao.getLatestSightings(2);
        assertEquals(2, toCheck.size());
        assertEquals(sight2, toCheck.get(0));
        assertEquals(sight, toCheck.get(1));
    }

    /**
     * Test of getSightingsByLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByLocationGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        location2.setLongitude(-22.323);
        location2 = locationDao.addLocation(location2);

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location2);
        sight2.setDate(LocalDate.of(2020, 3, 25));
        sight2.setDescription("Was talking to someone.");
        sight2 = sightDao.addSighting(sight2);

        List<Sighting> toCheck = sightDao.getSightingsByLocation(location);
        assertEquals(1, toCheck.size());
        assertTrue(toCheck.contains(sight));
    }

    /**
     * Test of getSightingsForHero method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForHeroGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        location2.setLongitude(-22.323);
        location2 = locationDao.addLocation(location2);

        Sighting sight2 = new Sighting();
        sight2.setHero(hero);
        sight2.setLocation(location2);
        sight2.setDate(LocalDate.of(2020, 3, 25));
        sight2.setDescription("Was talking to someone.");
        sight2 = sightDao.addSighting(sight2);

        List<Sighting> toCheck = sightDao.getSightingsForHero(hero);
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(sight));
        assertTrue(toCheck.contains(sight2));
    }

}
