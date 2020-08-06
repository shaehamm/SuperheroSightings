/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.daos.*;
import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author codedchai
 */
@Controller
public class OrgController {

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

    @GetMapping("orgs")
    public String displayOrgs(Model mdl) {
        mdl.addAttribute("org", new Org());
        mdl.addAttribute("heroes", heroDao.getAllHeroes());
        mdl.addAttribute("orgs", orgDao.getAllOrgs());
        return "orgs";
    }

    @PostMapping("addorg")
    public String addOrg(@Valid Org toAdd, BindingResult valResult, Integer[] heroIds, Model mdl) {
        List<Hero> heroes = new ArrayList<>();
        //add heroes or create error if no heroes selected
        if (heroIds != null) {
            for (Integer id : heroIds) {
                heroes.add(heroDao.getHeroById(id));
            }
        } else {
            FieldError error = new FieldError("org", "heroes",
                    "Must include one hero.");
            valResult.addError(error);
        }
        toAdd.setHeroes(heroes);
        //check if org name is unique
        if (orgDao.getAllOrgs().stream().anyMatch(org -> org.getName().equalsIgnoreCase(toAdd.getName()))) {
            FieldError error = new FieldError("org", "name",
                    "Organization already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            mdl.addAttribute("org", toAdd);
            mdl.addAttribute("heroes", heroDao.getAllHeroes());
            mdl.addAttribute("orgs", orgDao.getAllOrgs());
            return "orgs";
        }
        orgDao.addOrg(toAdd);
        return "redirect:/orgs";
    }

    @GetMapping("org/{id}")
    public String getOrgById(Model mdl, @PathVariable Integer id) throws NullOrganizationDataException {
        Org org = orgDao.getOrgById(id);
        mdl.addAttribute("org", new Org());
        mdl.addAttribute("validOrg", org);
        mdl.addAttribute("orgheroes", heroDao.getHeroesForOrg(org));
        mdl.addAttribute("heroes", heroDao.getAllHeroes());
        return "orgdetails";
    }

    @PostMapping("editorg")
    public String editOrg(@Valid Org edited, BindingResult valResult, Integer[] heroIds, Model mdl) throws NullOrganizationDataException {
        List<Hero> toAdd = new ArrayList<>();
        //add heroes or create error if no heroes selected
        if (heroIds != null) {
            for (Integer id : heroIds) {
                toAdd.add(heroDao.getHeroById(id));
            }
        } else {
            FieldError error = new FieldError("org", "heroes",
                    "Must include one hero.");
            valResult.addError(error);
        }
        edited.setHeroes(toAdd);
        //check if org name is unique
        if (orgDao.getAllOrgs().stream().anyMatch(org ->
                org.getName().equalsIgnoreCase(edited.getName()) &&
                        org.getId() != edited.getId())) {
            FieldError error = new FieldError("org", "name",
                    "Organization already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            Org org = orgDao.getOrgById(edited.getId());
            mdl.addAttribute("org", edited);
            mdl.addAttribute("validOrg", org);
            mdl.addAttribute("orgheroes", heroDao.getHeroesForOrg(org));
            mdl.addAttribute("heroes", heroDao.getAllHeroes());
            return "orgdetails";
        }
        orgDao.updateOrg(edited);
        return "redirect:/org/" + edited.getId();
    }

    @GetMapping("deleteorg/{id}")
    public String deleteOrg(@PathVariable Integer id) {
        orgDao.deleteOrgById(id);
        return "redirect:/orgs";
    }

}
