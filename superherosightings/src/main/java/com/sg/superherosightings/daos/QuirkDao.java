/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Quirk;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface QuirkDao {
    Quirk getQuirkById(int id);
    List<Quirk> getAllQuirks();
    Quirk addQuirk(Quirk quirk);
    void updateQuirk(Quirk quirk);
    void deleteQuirkById(int id);
}
