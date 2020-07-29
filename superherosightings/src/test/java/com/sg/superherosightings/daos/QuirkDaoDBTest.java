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
public class QuirkDaoDBTest {
    
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
    
    public QuirkDaoDBTest() {
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
     * Test of getQuirkByName method, of class QuirkDaoDB.
     */
    @Test
    public void testAddAndGetQuirkGoldenPath() {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        Quirk toCheck = quirkDao.addQuirk(quirk);
        
        Quirk fromDao = quirkDao.getQuirkById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of getAllQuirks method, of class QuirkDaoDB.
     */
    @Test
    public void testGetAllQuirksGoldenPath() {   
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);
        
        Quirk quirk2 = new Quirk();
        quirk2.setName("Quirk Name2");
        quirk2.setDescription("Does something.");
        quirk2 = quirkDao.addQuirk(quirk2);
        
        List<Quirk> toCheck = quirkDao.getAllQuirks();
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(quirk));
        assertTrue(toCheck.contains(quirk2));
        
    }

    /**
     * Test of updateQuirk method, of class QuirkDaoDB.
     */
    @Test
    public void testUpdateQuirkGoldenPath() {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        Quirk toCheck = quirkDao.addQuirk(quirk);
        
        Quirk fromDao = quirkDao.getQuirkById(toCheck.getId());
        assertEquals(toCheck, fromDao);
        
        toCheck.setDescription("Does something super cool!");
        
        quirkDao.updateQuirk(toCheck);
        assertNotEquals(toCheck, fromDao);
        
        fromDao = quirkDao.getQuirkById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of deleteQuirkByName method, of class QuirkDaoDB.
     */
    @Test
    public void testDeleteQuirkByIdGoldenPath() {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        Quirk toCheck = quirkDao.addQuirk(quirk);
        
        Quirk fromDao = quirkDao.getQuirkById(toCheck.getId());
        assertEquals(toCheck, fromDao);
        
        quirkDao.deleteQuirkById(toCheck.getId());
        fromDao = quirkDao.getQuirkById(toCheck.getId());
        assertNull(fromDao);
    }
    
}
