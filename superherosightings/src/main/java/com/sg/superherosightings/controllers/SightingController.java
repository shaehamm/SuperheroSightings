/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.dtos.Sighting;
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
public class SightingController {

    @Autowired
    SuperheroService service;

    @GetMapping("sightings")
    public String displaySightings(Model mdl) {
        mdl.addAttribute("sighting", new Sighting());
        mdl.addAttribute("sightings", service.getAllSightings());
        mdl.addAttribute("heroes", service.getAllHeroes());
        mdl.addAttribute("locations", service.getAllLocations());
        return "sightings";
    }

    @PostMapping("addsighting")
    public String addSighting(@Valid Sighting toAdd, BindingResult valResult, Model mdl) {
        if (valResult.hasErrors()) {
            mdl.addAttribute("sighting", toAdd);
            mdl.addAttribute("sightings", service.getAllSightings());
            mdl.addAttribute("heroes", service.getAllHeroes());
            mdl.addAttribute("locations", service.getAllLocations());
            return "sightings";
        }
        service.addSighting(toAdd);
        return "redirect:/sightings";
    }

    @GetMapping("sighting/{id}")
    public String getSightingById(Model mdl, @PathVariable Integer id) {
        Sighting sight = service.getSightingById(id);
        mdl.addAttribute("sighting", new Sighting());
        mdl.addAttribute("validSighting", sight);
        mdl.addAttribute("locations", service.getAllLocations());
        mdl.addAttribute("heroes", service.getAllHeroes());
        return "sightingdetails";
    }

    @PostMapping("editsighting")
    public String editSighting(@Valid Sighting edited, BindingResult valResult, Model mdl) {
        if (valResult.hasErrors()) {
            Sighting sight = service.getSightingById(edited.getId());
            mdl.addAttribute("sighting", edited);
            mdl.addAttribute("validSighting", sight);
            mdl.addAttribute("locations", service.getAllLocations());
            mdl.addAttribute("heroes", service.getAllHeroes());
            return "sightingdetails";
        }
        edited.setHero(service.getHeroById(edited.getHero().getId()));
        edited.setLocation(service.getLocationById(edited.getLocation().getId()));
        service.updateSighting(edited);
        return "redirect:/sighting/" + edited.getId();
    }

    @GetMapping("deletesighting/{id}")
    public String deleteSighting(@PathVariable Integer id) {
        service.deleteSightingById(id);
        return "redirect:/sightings";
    }

}
