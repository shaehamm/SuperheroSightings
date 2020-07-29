/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controllers;

import com.sg.superherosightings.daos.SightingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author codedchai
 */
@Controller
public class MainController {
    
    @Autowired
    SightingDao sight;
    
    @GetMapping("home")
    public String homePage(Model mdl) {
        mdl.addAttribute("sightings", sight.getLatestSightings(10));
        return "home";
    }
    
}
