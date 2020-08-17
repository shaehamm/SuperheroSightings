package com.sg.superherosightings.service;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.*;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
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

    //hero dao
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    public void addHero(Hero toAdd) throws NullHeroDataException {
        heroDao.addHero(toAdd);
    }

    public Hero getHeroById(Integer id) {
        return heroDao.getHeroById(id);
    }

    public void updateHero(Hero edited) throws NullHeroDataException {
        heroDao.updateHero(edited);
    }

    public void deleteHeroById(Integer id) {
        heroDao.deleteHeroById(id);
    }

    public List<Hero> getHeroesForOrg(Org org) throws NullOrganizationDataException {
        return heroDao.getHeroesForOrg(org);
    }

    public List<Hero> getHeroesForQuirk(Quirk quirk) throws NullQuirkDataException {
        return heroDao.getHeroesForQuirk(quirk);
    }

    //location dao
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public void addLocation(Location toAdd) throws NullLocationDataException {
        locationDao.addLocation(toAdd);
    }

    public Location getLocationById(Integer id) {
        return locationDao.getLocationById(id);
    }

    public void updateLocation(Location location) throws NullLocationDataException {
        locationDao.updateLocation(location);
    }

    public void deleteLocationById(Integer id) {
        locationDao.deleteLocationById(id);
    }

    //org dao
    public List<Org> getAllOrgs() {
        return orgDao.getAllOrgs();
    }

    public void updateOrg(Org org) {
        orgDao.updateOrg(org);
    }

    public Org getOrgById(Integer id) {
        return orgDao.getOrgById(id);
    }

    public List<Org> getOrgsForHero(int id) {
        return orgDao.getOrgsForHero(id);
    }

    public void addOrg(Org toAdd) {
        orgDao.addOrg(toAdd);
    }

    public void deleteOrgById(Integer id) {
        orgDao.deleteOrgById(id);
    }

    //quirk dao
    public List<Quirk> getAllQuirks() {
        return quirkDao.getAllQuirks();
    }

    public Quirk getQuirkById(int id) {
        return quirkDao.getQuirkById(id);
    }

    public void deleteQuirkById(Integer id) {
        quirkDao.deleteQuirkById(id);
    }

    public void updateQuirk(Quirk edited) {
        quirkDao.updateQuirk(edited);
    }

    public void addQuirk(Quirk toAdd) {
        quirkDao.addQuirk(toAdd);
    }

    //sighting dao
    public List<Sighting> getAllSightings() {
        return sightDao.getAllSightings();
    }

    public void addSighting(Sighting toAdd) {
        sightDao.addSighting(toAdd);
    }

    public Sighting getSightingById(Integer id) {
        return sightDao.getSightingById(id);
    }

    public void updateSighting(Sighting edited) {
        sightDao.updateSighting(edited);
    }

    public void deleteSightingById(Integer id) {
        sightDao.deleteSightingById(id);
    }

    public List<Sighting> getSightingsForHero(Hero hero) {
        return sightDao.getSightingsForHero(hero);
    }

    public List<Sighting> getSightingsByLocation(Location location) {
        return sightDao.getSightingsByLocation(location);
    }

    public List<Sighting> getLatestSightings(int count) {
        return sightDao.getLatestSightings(count);
    }

    //unique checks
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

    public void uniqueQuirkNameCheck(String name, BindingResult valResult) {
        if (getAllQuirks().stream().anyMatch(quirk -> quirk.getName().equalsIgnoreCase(name))) {
            FieldError error = new FieldError("quirk", "name",
                    "Quirk name already exists.");
            valResult.addError(error);
        }
    }

    public void uniqueQuirkNameCheck(String name, int id, BindingResult valResult) {
        if (getAllQuirks().stream().anyMatch(quirk -> quirk.getName().equalsIgnoreCase(name) && quirk.getId() != id)) {
            FieldError error = new FieldError("quirk", "name",
                    "Quirk name already exists.");
            valResult.addError(error);
        }
    }

    //update orgs from hero side
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

    //check if heroes are selected for an org
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

}
