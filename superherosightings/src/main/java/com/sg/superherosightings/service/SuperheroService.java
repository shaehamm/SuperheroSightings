package com.sg.superherosightings.service;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.*;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
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

    //Hero Dao
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

    public List<Hero> getHeroesForOrg(Integer id) {
        return heroDao.getHeroesForOrg(id);
    }

    public List<Hero> getHeroesForQuirk(Integer id) {
        return heroDao.getHeroesForQuirk(id);
    }

    //Location Dao
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    public void addLocation(Location toAdd, BindingResult valResult) throws NullLocationDataException {
        boolean uniqueName = uniqueLocationNameCheck(toAdd.getName(), valResult);
        boolean validLat = validLatitudeCheck(toAdd.getLatitude(), valResult);
        boolean validLong = validLongitudeCheck(toAdd.getLongitude(), valResult);
        if (validLat && validLong && uniqueName) {
            locationDao.addLocation(toAdd);
        }
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

    //Organization Dao
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

    //Quirk Dao
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

    //Sighting Dao
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

    public List<Sighting> getSightingsForHero(Integer id) {
        return sightDao.getSightingsForHero(id);
    }

    public List<Sighting> getSightingsByLocation(Integer id) {
        return sightDao.getSightingsByLocation(id);
    }

    public List<Sighting> getLatestSightings(int count) {
        return sightDao.getLatestSightings(count);
    }

    //Unique Checks
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

    public boolean uniqueLocationNameCheck(String name, BindingResult valResult) {
        boolean unique = true;
        if (getAllLocations().stream().anyMatch(location -> location.getName().equalsIgnoreCase(name))) {
            FieldError error = new FieldError("location", "name",
                    "Location already exists.");
            valResult.addError(error);
            return !unique;
        }
        return unique;
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

    //Update orgs from hero side
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

    //Check if heroes are selected for an org
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

    //Check if latitude and longitude are valid
    private boolean validLongitudeCheck(Double longitude, BindingResult valResult) {
        boolean validLong = true;
        if (longitude > 90 || longitude < -90 || longitude == null) {
//            FieldError error = new FieldError("location", "longitude",
//                    "Longitude must be between -180 and 180.");
//            valResult.addError(error);
            return !validLong;
        } else {
            return validLong;
        }
    }

    private boolean validLatitudeCheck(Double latitude, BindingResult valResult) {
        boolean validLat = true;
        if (latitude > 90 || latitude < -90 || latitude == null) {
//            FieldError error = new FieldError("location", "latitude",
//                    "Latitude must be between -90 and 90.");
//            valResult.addError(error);
            return !validLat;
        } else {
            return validLat;
        }
    }

}
