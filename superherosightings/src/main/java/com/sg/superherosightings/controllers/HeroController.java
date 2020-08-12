/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author codedchai
 */
@Controller
public class HeroController {

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

    @GetMapping("heroes")
    public String displayHeroes(Model mdl) {
        mdl.addAttribute("hero", new Hero());
        mdl.addAttribute("heroes", heroDao.getAllHeroes());
        mdl.addAttribute("quirks", quirkDao.getAllQuirks());
        return "heroes";
    }

    @PostMapping("addhero")
    public String addHero(@Valid Hero toAdd, BindingResult valResult, Model mdl) throws NullHeroDataException {
        //check if hero name is unique
        if (heroDao.getAllHeroes().stream().anyMatch(hero -> hero.getName().equalsIgnoreCase(toAdd.getName()))) {
            FieldError error = new FieldError("hero", "name",
                    "Hero name already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            mdl.addAttribute("hero", toAdd);
            mdl.addAttribute("heroes", heroDao.getAllHeroes());
            mdl.addAttribute("quirks", quirkDao.getAllQuirks());
            return "heroes";
        }
        heroDao.addHero(toAdd);
        return "redirect:/heroes";
    }

    @GetMapping("hero/{id}")
    public String getHeroById(Model mdl, @PathVariable Integer id) {
        Hero hero = heroDao.getHeroById(id);
        mdl.addAttribute("hero", new Hero());
        mdl.addAttribute("validHero", hero);
        mdl.addAttribute("heroquirk", hero.getQuirk());
        mdl.addAttribute("quirks", quirkDao.getAllQuirks());
        mdl.addAttribute("orgs", orgDao.getOrgsForHero(hero));
        mdl.addAttribute("allOrgs", orgDao.getAllOrgs());
        mdl.addAttribute("sightings", sightDao.getSightingsForHero(hero));
        return "herodetails";
    }

    @PostMapping("edithero")
    public String editHero(@Valid Hero edited, BindingResult valResult, Integer[] orgIds, Model mdl) throws NullHeroDataException {
        edited.setQuirk(quirkDao.getQuirkById(edited.getQuirk().getId()));
        Hero hero = heroDao.getHeroById(edited.getId());
        //check if hero name is unique
        if (heroDao.getAllHeroes().stream().anyMatch(h ->
                h.getName().equalsIgnoreCase(edited.getName()) &&
                        hero.getId() != edited.getId())) {
            FieldError error = new FieldError("hero", "name",
                    "Hero name already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            mdl.addAttribute("hero", edited);
            mdl.addAttribute("validHero", hero);
            mdl.addAttribute("heroquirk", hero.getQuirk());
            mdl.addAttribute("quirks", quirkDao.getAllQuirks());
            mdl.addAttribute("orgs", orgDao.getOrgsForHero(hero));
            mdl.addAttribute("allOrgs", orgDao.getAllOrgs());
            mdl.addAttribute("sightings", sightDao.getSightingsForHero(hero));
            return "herodetails";
        }
        for (Org org : orgDao.getOrgsForHero(hero)) {
            //removes orgs if they are no longer selected
            if (Arrays.stream(orgIds).anyMatch(id -> id != org.getId())) {
                org.getHeroes().remove(edited);
                orgDao.updateOrg(org);
            }
        }
        for (Integer orgId : orgIds) {
            Org toUpdate = orgDao.getOrgById(orgId);
            //check if hero is added to org already (add new hero if not already a member)
            if (toUpdate.getHeroes().stream().allMatch(h -> h.getId() != edited.getId())) {
                toUpdate.getHeroes().add(edited);
            }
            orgDao.updateOrg(toUpdate);
        }
        heroDao.updateHero(edited);
        return "redirect:/hero/" + edited.getId();
    }

    @GetMapping("deletehero/{id}")
    public String deleteHero(@PathVariable Integer id) {
        heroDao.deleteHeroById(id);
        return "redirect:/heroes";
    }
}
