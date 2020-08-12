package com.sg.superherosightings.service;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.*;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroService {

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

    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public List<Org> getAllOrgs() {
        return orgDao.getAllOrgs();
    }

    public List<Quirk> getAllQuirks() {
        return quirkDao.getAllQuirks();
    }

    public void addHero(Hero toAdd) throws NullHeroDataException {
        heroDao.addHero(toAdd);
    }

    public Hero getHeroById(Integer id) {
        return heroDao.getHeroById(id);
    }

    public List<Sighting> getSightingsForHero(Hero hero) {
        return sightDao.getSightingsForHero(hero);
    }

    public List<Org> getOrgsForHero(Hero hero) {
        return orgDao.getOrgsForHero(hero);
    }


    public Quirk getQuirkById(int id) {
        return quirkDao.getQuirkById(id);
    }

    public void updateOrg(Org org) {
        orgDao.updateOrg(org);
    }

    public Org getOrgById(Integer id) {
        return orgDao.getOrgById(id);
    }

    public void updateHero(Hero edited) throws NullHeroDataException {
        heroDao.updateHero(edited);
    }

    public void deleteHeroById(Integer id) {
        heroDao.deleteHeroById(id);
    }
}
