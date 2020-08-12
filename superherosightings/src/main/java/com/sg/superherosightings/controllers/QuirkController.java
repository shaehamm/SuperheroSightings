/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.dtos.Quirk;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
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
public class QuirkController {

    @Autowired
    SuperheroService service;

    @GetMapping("quirks")
    public String displayQuirks(Model mdl) {
        mdl.addAttribute("quirk", new Quirk());
        mdl.addAttribute("quirks", service.getAllQuirks());
        return "quirks";
    }

    @PostMapping("addquirk")
    public String addQuirk(@Valid Quirk toAdd, BindingResult valResult, Model mdl) {
        //check if quirk name is unique
        service.uniqueQuirkNameCheck(toAdd.getName(), valResult);
        if (valResult.hasErrors()) {
            mdl.addAttribute("quirk", toAdd);
            mdl.addAttribute("quirks", service.getAllQuirks());
            return "quirks";
        }
        service.addQuirk(toAdd);
        return "redirect:/quirks";
    }

    @GetMapping("quirk/{id}")
    public String getQuirkById(Model mdl, @PathVariable Integer id) throws NullQuirkDataException {
        Quirk quirk = service.getQuirkById(id);
        mdl.addAttribute("quirk", new Quirk());
        mdl.addAttribute("validQuirk", quirk);
        mdl.addAttribute("heroes", service.getHeroesForQuirk(quirk));
        return "quirkdetails";
    }

    @PostMapping("editquirk")
    public String editQuirk(@Valid Quirk edited, BindingResult valResult, Model mdl) throws NullQuirkDataException {
        //check if quirk name is unique
        service.uniqueQuirkNameCheck(edited.getName(), edited.getId(), valResult);
        if (valResult.hasErrors()) {
            Quirk quirk = service.getQuirkById(edited.getId());
            mdl.addAttribute("quirk", edited);
            mdl.addAttribute("validQuirk", quirk);
            mdl.addAttribute("heroes", service.getHeroesForQuirk(quirk));
            return "quirkdetails";
        }
        service.updateQuirk(edited);
        return "redirect:/quirk/" + edited.getId();
    }

    @GetMapping("deletequirk/{id}")
    public String deleteQuirk(@PathVariable Integer id) {
        service.deleteQuirkById(id);
        return "redirect:/quirks";
    }

}
