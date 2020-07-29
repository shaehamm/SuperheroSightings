/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.daos.HeroDao;
import com.sg.superherosightings.daos.LocationDao;
import com.sg.superherosightings.daos.OrgDao;
import com.sg.superherosightings.daos.QuirkDao;
import com.sg.superherosightings.daos.SightingDao;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author codedchai
 */
@Controller
public class LocationController {

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

    @GetMapping("locations")
    public String displayLocations(Model mdl) {
        mdl.addAttribute("location", new Location());
        mdl.addAttribute("locations", locationDao.getAllLocations());
        return "locations";
    }

    @PostMapping("addlocation")
    public String addLocation(@Valid Location toAdd, BindingResult valResult, Model mdl) throws NullLocationDataException {
        if (locationDao.getAllLocations().stream().anyMatch(location -> location.getName().equalsIgnoreCase(toAdd.getName()))) {
            FieldError error = new FieldError("location", "name",
                    "Location already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            mdl.addAttribute("location", toAdd);
            mdl.addAttribute("locations", locationDao.getAllLocations());
            return "locations";
        }
        locationDao.addLocation(toAdd);
        return "redirect:/locations";
    }

    @GetMapping("location/{id}")
    public String getLocationById(Model mdl, @PathVariable Integer id) {
        Location location = locationDao.getLocationById(id);
        mdl.addAttribute("location", new Location());
        mdl.addAttribute("validLocation", location);
        mdl.addAttribute("sights", sightDao.getSightingsByLocation(location));
        return "locationdetails";
    }

    @PostMapping("editlocation")
    public String editLocation(@Valid Location edited, BindingResult valResult, Model mdl) throws NullLocationDataException {
        if (locationDao.getAllLocations().stream().anyMatch(location -> 
                location.getName().equalsIgnoreCase(edited.getName()) && 
                        location.getId() != edited.getId())) {
            FieldError error = new FieldError("location", "name",
                    "Location already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            Location location = locationDao.getLocationById(edited.getId());
            mdl.addAttribute("location", edited);
            mdl.addAttribute("validLocation", location);
            mdl.addAttribute("sights", sightDao.getSightingsByLocation(location));
            return "locationdetails";
        }
        locationDao.updateLocation(edited);
        return "redirect:/location/" + edited.getId();
    }

    @GetMapping("deletelocation/{id}")
    public String deleteLocation(@PathVariable Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }
}
