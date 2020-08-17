/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import com.sg.superherosightings.service.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author codedchai
 */
@Controller
public class OrgController {

    @Autowired
    SuperheroService service;

    @GetMapping("orgs")
    public String displayOrgs(Model mdl) {
        mdl.addAttribute("org", new Org());
        mdl.addAttribute("heroes", service.getAllHeroes());
        mdl.addAttribute("orgs", service.getAllOrgs());
        return "orgs";
    }

    @PostMapping("addorg")
    public String addOrg(@Valid Org toAdd, BindingResult valResult, Integer[] heroIds, Model mdl) {
        //add heroes or create error if no heroes selected
        List<Hero> toSet = service.selectedHeroCheck(heroIds, valResult);
        toAdd.setHeroes(toSet);
        //check if org name is unique
        service.uniqueOrgNameCheck(toAdd.getName(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("org", toAdd);
            mdl.addAttribute("heroes", service.getAllHeroes());
            mdl.addAttribute("orgs", service.getAllOrgs());
            return "orgs";
        }
        service.addOrg(toAdd);
        return "redirect:/orgs";
    }

    @GetMapping("org/{id}")
    public String getOrgById(Model mdl, @PathVariable Integer id) throws NullOrganizationDataException {
        Org org = service.getOrgById(id);
        mdl.addAttribute("org", new Org());
        mdl.addAttribute("validOrg", org);
        mdl.addAttribute("orgheroes", service.getHeroesForOrg(id));
        mdl.addAttribute("heroes", service.getAllHeroes());
        return "orgdetails";
    }

    @PostMapping("editorg")
    public String editOrg(@Valid Org edited, BindingResult valResult, Integer[] heroIds, Model mdl) throws NullOrganizationDataException {
        //add heroes or create error if no heroes selected
        List<Hero> toSet = service.selectedHeroCheck(heroIds, valResult);
        edited.setHeroes(toSet);
        //check if org name is unique
        service.uniqueOrgNameCheck(edited.getName(), edited.getId(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("org", edited);
            mdl.addAttribute("validOrg", service.getOrgById(edited.getId()));
            mdl.addAttribute("orgheroes", service.getHeroesForOrg(edited.getId()));
            mdl.addAttribute("heroes", service.getAllHeroes());
            return "orgdetails";
        }
        service.updateOrg(edited);
        return "redirect:/org/" + edited.getId();
    }

    @GetMapping("deleteorg/{id}")
    public String deleteOrg(@PathVariable Integer id) {
        service.deleteOrgById(id);
        return "redirect:/orgs";
    }

}
