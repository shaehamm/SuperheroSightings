/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import com.sg.superherosightings.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * @author codedchai
 */
@Controller
public class LocationController {

    @Autowired
    SuperheroService service;

    @GetMapping("locations")
    public String displayLocations(Model mdl) {
        mdl.addAttribute("location", new Location());
        mdl.addAttribute("locations", service.getAllLocations());
        return "locations";
    }

    @PostMapping("addlocation")
    public String addLocation(@Valid Location toAdd, BindingResult valResult, Model mdl) throws NullLocationDataException {
        //check if location name is unique
        service.uniqueLocationNameCheck(toAdd.getName(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("location", toAdd);
            mdl.addAttribute("locations", service.getAllLocations());
            return "locations";
        }
        service.addLocation(toAdd);
        return "redirect:/locations";
    }

    @GetMapping("location/{id}")
    public String getLocationById(Model mdl, @PathVariable Integer id) {
        Location location = service.getLocationById(id);
        mdl.addAttribute("location", new Location());
        mdl.addAttribute("validLocation", location);
        mdl.addAttribute("sights", service.getSightingsByLocation(location));
        return "locationdetails";
    }

    @PostMapping("editlocation")
    public String editLocation(@Valid Location edited, BindingResult valResult, Model mdl) throws NullLocationDataException {
        //check if location name is unique
        service.uniqueLocationNameCheck(edited.getName(), edited.getId(), valResult);
        if (valResult.hasErrors()) {
            Location location = service.getLocationById(edited.getId());
            mdl.addAttribute("location", edited);
            mdl.addAttribute("validLocation", location);
            mdl.addAttribute("sights", service.getSightingsByLocation(location));
            return "locationdetails";
        }
        service.updateLocation(edited);
        return "redirect:/location/" + edited.getId();
    }

    @GetMapping("deletelocation/{id}")
    public String deleteLocation(@PathVariable Integer id) {
        service.deleteLocationById(id);
        return "redirect:/locations";
    }
}
