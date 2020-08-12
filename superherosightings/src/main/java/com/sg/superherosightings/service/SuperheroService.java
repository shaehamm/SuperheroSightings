package com.sg.superherosightings.service;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.*;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Org> getOrgsForHero(int id) {
        return orgDao.getOrgsForHero(id);
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

    public void uniqueHeroNameCheck(String name, BindingResult valResult) {
        if (getAllHeroes().stream().anyMatch(hero -> hero.getName().equalsIgnoreCase(name))) {
            FieldError error = new FieldError("hero", "name",
                    "Hero name already exists.");
            valResult.addError(error);
        }
    }

    public void uniqueHeroNameCheck(String name, int id, BindingResult valResult) {
        if (getAllHeroes().stream().anyMatch(hero -> hero.getName().equalsIgnoreCase(name) && hero.getId() != id)) {
            FieldError error = new FieldError("hero", "name",
                    "Hero name already exists.");
            valResult.addError(error);
        }
    }

    public void updateOrgsForHero(Hero edited, Integer[] orgIds) {
        for (Org org : getOrgsForHero(edited.getId())) {
            //removes orgs if they are no longer selected
            if (Arrays.stream(orgIds).anyMatch(id -> id != org.getId())) {
                org.getHeroes().remove(edited);
                updateOrg(org);
            }
        }
        for (Integer orgId : orgIds) {
            Org toUpdate = getOrgById(orgId);
            //check if hero is added to org already (add new hero if not already a member)
            if (toUpdate.getHeroes().stream().allMatch(h -> h.getId() != edited.getId())) {
                toUpdate.getHeroes().add(edited);
            }
            updateOrg(toUpdate);
        }
    }

    public void addLocation(Location toAdd) throws NullLocationDataException {
        locationDao.addLocation(toAdd);
    }

    public Location getLocationById(Integer id) {
        return locationDao.getLocationById(id);
    }

    public List<Sighting> getSightingsByLocation(Location location) {
        return sightDao.getSightingsByLocation(location);
    }

    public void updateLocation(Location location) throws NullLocationDataException {
        locationDao.updateLocation(location);
    }

    public void deleteLocationById(Integer id) {
        locationDao.deleteLocationById(id);
    }

    public void uniqueLocationNameCheck(String name, BindingResult valResult) {
        if (getAllLocations().stream().anyMatch(location -> location.getName().equalsIgnoreCase(name))) {
            FieldError error = new FieldError("location", "name",
                    "Location already exists.");
            valResult.addError(error);
        }
    }

    public void uniqueLocationNameCheck(String name, int id, BindingResult valResult) {
        if (getAllLocations().stream().anyMatch(location -> location.getName().equalsIgnoreCase(name) &&
                location.getId() != id)) {
            FieldError error = new FieldError("location", "name",
                    "Location already exists.");
            valResult.addError(error);
        }
    }

    public List<Sighting> getLatestSightings(int count) {
        return sightDao.getLatestSightings(count);
    }

    public void addOrg(Org toAdd) {
        orgDao.addOrg(toAdd);
    }

    public List<Hero> getHeroesForOrg(Org org) throws NullOrganizationDataException {
        return heroDao.getHeroesForOrg(org);
    }

    public void deleteOrgById(Integer id) {
        orgDao.deleteOrgById(id);
    }

    public List<Hero> selectedHeroCheck(Integer[] heroIds, BindingResult valResult) {
        List<Hero> toReturn = new ArrayList<>();
        if (heroIds != null) {
            for (Integer id : heroIds) {
                toReturn.add(getHeroById(id));
            }
        } else {
            FieldError error = new FieldError("org", "heroes",
                    "Must include one hero.");
            valResult.addError(error);
        }
        return toReturn;
    }

    public void uniqueOrgNameCheck(String name, BindingResult valResult) {
        if (getAllOrgs().stream().anyMatch(org -> org.getName().equalsIgnoreCase(name))) {
            FieldError error = new FieldError("org", "name",
                    "Organization already exists.");
            valResult.addError(error);
        }
    }

    public void uniqueOrgNameCheck(String name, int id, BindingResult valResult) {
        if (getAllOrgs().stream().anyMatch(org -> org.getName().equalsIgnoreCase(name) && org.getId() != id)) {
            FieldError error = new FieldError("org", "name",
                    "Organization already exists.");
            valResult.addError(error);
        }
    }
}
