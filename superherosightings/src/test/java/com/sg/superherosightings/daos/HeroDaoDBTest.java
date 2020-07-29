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
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
public class HeroDaoDBTest {
    
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
    
    
    public HeroDaoDBTest() {
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
     * Test of getHeroById method, of class HeroDaoDB.
     * Test of addHero method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHeroGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        Hero toCheck = heroDao.addHero(hero);
        
        Hero fromDao = heroDao.getHeroById(toCheck.getId());
        assertEquals(toCheck, fromDao);
        //set hero variable's Id and check if hero equals toCheck or fromDao?
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroesGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);
        
        Quirk quirk2 = new Quirk();
        quirk2.setName("Quirk Name2");
        quirk2.setDescription("Does something.");
        quirk2 = quirkDao.addQuirk(quirk2);
        
        Hero hero2 = new Hero();
        hero2.setName("Test Hero2");
        hero2.setAlignment("Neutral-Good");
        hero2.setQuirk(quirk2);
        hero2 = heroDao.addHero(hero2);
        
        List<Hero> toCheck = heroDao.getAllHeroes();
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(hero));
        assertTrue(toCheck.contains(hero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHeroGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        Hero toCheck = heroDao.addHero(hero);
        
        Hero fromDao = heroDao.getHeroById(toCheck.getId());
        assertEquals(toCheck, fromDao);
        
        quirk.setDescription("Does something really cool!");
        quirkDao.updateQuirk(quirk);
        toCheck.setQuirk(quirk);
        toCheck.setName("New Name");
        
        heroDao.updateHero(toCheck);
        assertNotEquals(toCheck, fromDao);
        
        fromDao = heroDao.getHeroById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of deleteHeroById method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroByIdGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        Hero toCheck = heroDao.addHero(hero);
        
        Hero fromDao = heroDao.getHeroById(toCheck.getId());  
        assertEquals(toCheck, fromDao);
        
        heroDao.deleteHeroById(toCheck.getId());
        fromDao = heroDao.getHeroById(toCheck.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getHeroesForLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesForLocationGoldenPath() throws NullHeroDataException, NullLocationDataException {
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
        
        List<Hero> toCheck = heroDao.getHeroesForLocation(location);
        assertEquals(1, toCheck.size());
        assertTrue(toCheck.contains(hero));
    }

    /**
     * Test of getHeroesForOrg method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesForOrgGoldenPath() throws NullHeroDataException, NullOrganizationDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);
        
        Quirk quirk2 = new Quirk();
        quirk2.setName("Quirk Name2");
        quirk2.setDescription("Does something.");
        quirk2 = quirkDao.addQuirk(quirk2);
        
        Hero hero2 = new Hero();
        hero2.setName("Test Hero2");
        hero2.setAlignment("Neutral-Good");
        hero2.setQuirk(quirk2);
        hero2 = heroDao.addHero(hero2);
        
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);
        heros.add(hero2);
        
        Org org = new Org();
        org.setName("Heros Of America (HOA)");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Group for heros in America");
        org.setHeroes(heros);
        org = orgDao.addOrg(org);
        
        List<Hero> toCheck = heroDao.getHeroesForOrg(org);
        
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(hero));
        assertTrue(toCheck.contains(hero2));
    }
    
    /**
     * Test of getHeroesForQuirk method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesForQuirkGoldenPath() throws NullHeroDataException, NullQuirkDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);
        
        Quirk quirk2 = new Quirk();
        quirk2.setName("Quirk Name2");
        quirk2.setDescription("Does something.");
        quirk2 = quirkDao.addQuirk(quirk2);
        
        Hero hero2 = new Hero();
        hero2.setName("Test Hero2");
        hero2.setAlignment("Neutral-Good");
        hero2.setQuirk(quirk2);
        hero2 = heroDao.addHero(hero2);
        
        Hero hero3 = new Hero();
        hero3.setName("Test Hero3");
        hero3.setAlignment("Neutral");
        hero3.setQuirk(quirk);
        hero3 = heroDao.addHero(hero3);
        
        List<Hero> toCheck = heroDao.getHeroesForQuirk(quirk);
        
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(hero));
        assertTrue(toCheck.contains(hero3));
    }
    
}
