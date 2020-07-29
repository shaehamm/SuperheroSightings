/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Org;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface OrgDao {
    
    Org getOrgById(int id);
    List<Org> getAllOrgs();
    Org addOrg(Org org);
    void updateOrg(Org org);
    void deleteOrgById(int id);
    
    List<Org> getOrgsForHero(Hero hero);

    
}
