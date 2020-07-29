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
import com.sg.superherosightings.dtos.Sighting;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author codedchai
 */
@Controller
public class SightingController {

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

    @GetMapping("sightings")
    public String displaySightings(Model mdl) {
        mdl.addAttribute("sighting", new Sighting());
        mdl.addAttribute("sightings", sightDao.getAllSightings());
        mdl.addAttribute("heroes", heroDao.getAllHeroes());
        mdl.addAttribute("locations", locationDao.getAllLocations());
        return "sightings";
    }

    @PostMapping("addsighting")
    public String addSighting(@Valid Sighting toAdd, BindingResult valResult, Model mdl) {
        if (valResult.hasErrors()) {
            mdl.addAttribute("sighting", toAdd);
            mdl.addAttribute("sightings", sightDao.getAllSightings());
            mdl.addAttribute("heroes", heroDao.getAllHeroes());
            mdl.addAttribute("locations", locationDao.getAllLocations());
            return "sightings";
        }
        sightDao.addSighting(toAdd);
        return "redirect:/sightings";
    }

    @GetMapping("sighting/{id}")
    public String getSightingById(Model mdl, @PathVariable Integer id) {
        Sighting sight = sightDao.getSightingById(id);
        mdl.addAttribute("sighting", new Sighting());
        mdl.addAttribute("validSighting", sight);
        mdl.addAttribute("locations", locationDao.getAllLocations());
        mdl.addAttribute("heroes", heroDao.getAllHeroes());
        return "sightingdetails";
    }

    @PostMapping("editsighting")
    public String editSighting(@Valid Sighting edited, BindingResult valResult, Model mdl) {
        if (valResult.hasErrors()) {
            Sighting sight = sightDao.getSightingById(edited.getId());
            mdl.addAttribute("sighting", edited);
            mdl.addAttribute("validSighting", sight);
            mdl.addAttribute("locations", locationDao.getAllLocations());
            mdl.addAttribute("heroes", heroDao.getAllHeroes());
            return "sightingdetails";
        }
        edited.setHero(heroDao.getHeroById(edited.getHero().getId()));
        edited.setLocation(locationDao.getLocationById(edited.getLocation().getId()));
        sightDao.updateSighting(edited);
        return "redirect:/sighting/" + edited.getId();
    }

    @GetMapping("deletesighting/{id}")
    public String deleteSighting(@PathVariable Integer id) {
        sightDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

}
