/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.exceptions.NullHeroDataException;
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
public class HeroController {

    @Autowired
    SuperheroService service;

    @GetMapping("heroes")
    public String displayHeroes(Model mdl) {
        mdl.addAttribute("hero", new Hero());
        mdl.addAttribute("heroes", service.getAllHeroes());
        mdl.addAttribute("quirks", service.getAllQuirks());
        return "heroes";
    }

    @PostMapping("addhero")
    public String addHero(@Valid Hero toAdd, BindingResult valResult, Model mdl) throws NullHeroDataException {
        //check if hero name is unique, adds error if not
        service.uniqueHeroNameCheck(toAdd.getName(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("hero", toAdd);
            mdl.addAttribute("heroes", service.getAllHeroes());
            mdl.addAttribute("quirks", service.getAllQuirks());
            return "heroes";
        }
        service.addHero(toAdd);
        return "redirect:/heroes";
    }

    @GetMapping("hero/{id}")
    public String getHeroById(Model mdl, @PathVariable Integer id) {
        Hero hero = service.getHeroById(id);
        mdl.addAttribute("hero", new Hero());
        mdl.addAttribute("validHero", hero);
        mdl.addAttribute("heroquirk", hero.getQuirk());
        mdl.addAttribute("quirks", service.getAllQuirks());
        mdl.addAttribute("orgs", service.getOrgsForHero(id));
        mdl.addAttribute("allOrgs", service.getAllOrgs());
        mdl.addAttribute("sightings", service.getSightingsForHero(id));
        return "herodetails";
    }

    @PostMapping("edithero")
    public String editHero(@Valid Hero edited, BindingResult valResult, Integer[] orgIds, Model mdl) throws NullHeroDataException {
        //add completed quirk info to the edited hero
        edited.setQuirk(service.getQuirkById(edited.getQuirk().getId()));
        //check if hero name is unique
        service.uniqueHeroNameCheck(edited.getName(), edited.getId(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("hero", edited);
            mdl.addAttribute("validHero", service.getHeroById(edited.getId()));
            mdl.addAttribute("heroquirk", edited.getQuirk());
            mdl.addAttribute("quirks", service.getAllQuirks());
            mdl.addAttribute("orgs", service.getOrgsForHero(edited.getId()));
            mdl.addAttribute("allOrgs", service.getAllOrgs());
            mdl.addAttribute("sightings", service.getSightingsForHero(edited.getId()));
            return "herodetails";
        }
        //removes or adds orgs if associated orgs were changed
        service.updateOrgsForHero(edited, orgIds);
        service.updateHero(edited);
        return "redirect:/hero/" + edited.getId();
    }

    @GetMapping("deletehero/{id}")
    public String deleteHero(@PathVariable Integer id) {
        service.deleteHeroById(id);
        return "redirect:/heroes";
    }
}
