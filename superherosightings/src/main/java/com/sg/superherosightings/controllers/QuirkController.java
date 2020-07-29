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
import com.sg.superherosightings.dtos.Quirk;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
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
public class QuirkController {

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

    @GetMapping("quirks")
    public String displayQuirks(Model mdl) {
        mdl.addAttribute("quirk", new Quirk());
        mdl.addAttribute("quirks", quirkDao.getAllQuirks());
        return "quirks";
    }

    @PostMapping("addquirk")
    public String addQuirk(@Valid Quirk toAdd, BindingResult valResult, Model mdl) {
        if (quirkDao.getAllQuirks().stream().anyMatch(quirk -> quirk.getName().equalsIgnoreCase(toAdd.getName()))) {
            FieldError error = new FieldError("quirk", "name",
                    "Quirk name already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            mdl.addAttribute("quirk", toAdd);
            mdl.addAttribute("quirks", quirkDao.getAllQuirks());
            return "quirks";
        }
        quirkDao.addQuirk(toAdd);
        return "redirect:/quirks";
    }

    @GetMapping("quirk/{id}")
    public String getQuirkById(Model mdl, @PathVariable Integer id) throws NullQuirkDataException {
        Quirk quirk = quirkDao.getQuirkById(id);
        mdl.addAttribute("quirk", new Quirk());
        mdl.addAttribute("validQuirk", quirk);
        mdl.addAttribute("heroes", heroDao.getHeroesForQuirk(quirk));
        return "quirkdetails";
    }

    @PostMapping("editquirk")
    public String editQuirk(@Valid Quirk edited, BindingResult valResult, Model mdl) throws NullQuirkDataException {
        if (quirkDao.getAllQuirks().stream().anyMatch(quirk -> 
                quirk.getName().equalsIgnoreCase(edited.getName()) && 
                        quirk.getId() != edited.getId())) {
            FieldError error = new FieldError("quirk", "name",
                    "Quirk name already exists.");
            valResult.addError(error);
        }
        if (valResult.hasErrors()) {
            Quirk quirk = quirkDao.getQuirkById(edited.getId());
            mdl.addAttribute("quirk", edited);
            mdl.addAttribute("validQuirk", quirk);
            mdl.addAttribute("heroes", heroDao.getHeroesForQuirk(quirk));
            return "quirkdetails";
        }
        quirkDao.updateQuirk(edited);
        return "redirect:/quirk/" + edited.getId();
    }

    @GetMapping("deletequirk/{id}")
    public String deleteQuirk(@PathVariable Integer id) {
        quirkDao.deleteQuirkById(id);
        return "redirect:/quirks";
    }

}
