/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.*;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author codedchai
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrgDaoDBTest {

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

    public OrgDaoDBTest() {
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
     * Test of getOrgById method, of class OrgDaoDB.
     */
    @Test
    public void testAddAndGetOrgGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);

        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);

        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        Org org = new Org();
        org.setName("Test Org");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Test org for unit tests.");
        org.setHeroes(heros);
        Org toCheck = orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(toCheck.getId());
        assertEquals(toCheck, fromDao);
    }

    /**
     * Test of getAllOrgs method, of class OrgDaoDB.
     */
    @Test
    public void testGetAllOrgsGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);

        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);

        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        Org org = new Org();
        org.setName("Test Org");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Test org for unit tests.");
        org.setHeroes(heros);
        org = orgDao.addOrg(org);

        Org org2 = new Org();
        org2.setName("Test Org2");
        org2.setAddress("Test Address");
        org2.setContactInfo("Test Contact");
        org2.setDescription("Second test org for unit tests.");
        org2.setHeroes(heros);
        org2 = orgDao.addOrg(org2);

        List<Org> toCheck = orgDao.getAllOrgs();
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(org));
        assertTrue(toCheck.contains(org2));
    }

    /**
     * Test of updateOrg method, of class OrgDaoDB.
     */
    @Test
    public void testUpdateOrgGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);

        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);

        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        Org org = new Org();
        org.setName("Test Org");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Test org for unit tests.");
        org.setHeroes(heros);
        Org toCheck = orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(toCheck.getId());
        assertEquals(toCheck, fromDao);

        Quirk quirk2 = new Quirk();
        quirk2.setName("Quirk Name2");
        quirk2.setDescription("Does something.");
        quirk2 = quirkDao.addQuirk(quirk2);

        Hero hero2 = new Hero();
        hero2.setName("Test Hero2");
        hero2.setAlignment("Neutral-Good");
        hero2.setQuirk(quirk2);
        hero2 = heroDao.addHero(hero2);
        heros.add(hero2);

        toCheck.setContactInfo("New Contact");
        toCheck.setHeroes(heros);

        orgDao.updateOrg(toCheck);
        assertNotEquals(toCheck, fromDao);

        fromDao = orgDao.getOrgById(toCheck.getId());
        assertEquals(toCheck, fromDao);


    }

    /**
     * Test of deleteOrgById method, of class OrgDaoDB.
     */
    @Test
    public void testDeleteOrgByIdGoldenPath() throws NullHeroDataException {
        Quirk quirk = new Quirk();
        quirk.setName("Quirk Name");
        quirk.setDescription("Does something cool.");
        quirk = quirkDao.addQuirk(quirk);

        Hero hero = new Hero();
        hero.setName("Test Hero");
        hero.setAlignment("Good");
        hero.setQuirk(quirk);
        hero = heroDao.addHero(hero);

        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        Org org = new Org();
        org.setName("Test Org");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Test org for unit tests.");
        org.setHeroes(heros);
        Org toCheck = orgDao.addOrg(org);

        Org fromDao = orgDao.getOrgById(toCheck.getId());
        assertEquals(toCheck, fromDao);

        orgDao.deleteOrgById(toCheck.getId());
        fromDao = orgDao.getOrgById(toCheck.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getOrgsForHero method, of class OrgDaoDB.
     */
    @Test
    public void testGetOrgsForHeroGoldenPath() throws NullHeroDataException {
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

        List<Hero> heros2 = new ArrayList<>();
        heros2.add(hero);

        Org org = new Org();
        org.setName("Test Org");
        org.setAddress("Fake Address");
        org.setContactInfo("Fake Contact");
        org.setDescription("Test org for unit tests.");
        org.setHeroes(heros);
        org = orgDao.addOrg(org);

        Org org2 = new Org();
        org2.setName("Test Org2");
        org2.setAddress("Test Address");
        org2.setContactInfo("Test Contact");
        org2.setDescription("Second test org for unit tests.");
        org2.setHeroes(heros2);
        org2 = orgDao.addOrg(org2);

        List<Org> toCheck = orgDao.getOrgsForHero(hero.getId());
        assertEquals(2, toCheck.size());
        assertTrue(toCheck.contains(org));
        assertTrue(toCheck.contains(org2));

        toCheck = orgDao.getOrgsForHero(hero2.getId());
        assertEquals(1, toCheck.size());
        assertTrue(toCheck.contains(org));
    }

}
