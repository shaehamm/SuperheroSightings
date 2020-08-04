/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.daos.HeroDaoDB.HeroMapper;
import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.dtos.Quirk;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author codedchai
 */
@Repository
public class OrgDaoDB implements OrgDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Org getOrgById(int id) {
        final String SELECT_ORG_BY_ID = "SELECT * FROM Org WHERE Id = ?";
        try {
            Org toReturn = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrgMapper(), id);
            toReturn.setHeroes(getHerosForOrg(id));
            return toReturn;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Org> getAllOrgs() {
        final String SELECT_ALL_ORGS = "SELECT * FROM Org ORDER BY name";
        List<Org> toReturn = jdbc.query(SELECT_ALL_ORGS, new OrgMapper());
        associateHeros(toReturn);
        return toReturn;

    }

    @Override
    @Transactional
    public Org addOrg(Org org) {
        final String INSERT_ORG = "INSERT INTO Org (Name, Description, Address, "
                + "ContactInfo) VALUES (?,?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContactInfo());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertHeroOrg(org);
        return org;
    }

    @Override
    @Transactional
    public void updateOrg(Org org) {
        final String UPDATE_ORG = "UPDATE Org SET Name = ?, Description = ?, "
                + "Address = ?, ContactInfo = ? WHERE Id = ?";
        jdbc.update(UPDATE_ORG, 
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContactInfo(),
                org.getId());
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrg WHERE OrgId = ?";
        jdbc.update(DELETE_HERO_ORG, org.getId());
        insertHeroOrg(org);
    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrg WHERE OrgId = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        final String DELETE_ORG = "DELETE FROM Org WHERE Id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    @Transactional
    public List<Org> getOrgsForHero(Hero hero) {
        final String SELECT_ORGS_FOR_HERO = "SELECT Org.* FROM Org "
                + "JOIN HeroOrg h ON Org.Id = h.OrgId "
                + "JOIN Hero ON h.HeroId = Hero.Id WHERE Hero.Id = ?";
        List<Org> toReturn = jdbc.query(SELECT_ORGS_FOR_HERO, new OrgMapper(), 
                hero.getId());
        associateHeros(toReturn);
        return toReturn;

    }

    private List<Hero> getHerosForOrg(int id) {
        final String SELECT_HERO_FOR_ORG = "SELECT Hero.* FROM Hero JOIN "
                + "HeroOrg ON HeroOrg.HeroId = Hero.Id WHERE HeroOrg.OrgId = ?";
        List<Hero> toReturn = jdbc.query(SELECT_HERO_FOR_ORG, new HeroMapper(), id);
        associateQuirk(toReturn);
        return toReturn;
    }

    private void insertHeroOrg(Org org) {
        final String INSERT_HERO_ORG = "INSERT INTO HeroOrg (HeroId, OrgId) "
                + "VALUES (?,?)";
        for (Hero hero: org.getHeroes()) {
            jdbc.update(INSERT_HERO_ORG, hero.getId(), org.getId());
        }
    }
    
    private Quirk getQuirkForHero(int id) {
        final String SELECT_QUIRK_FOR_HERO = "SELECT Quirk.* FROM Quirk JOIN "
                + "Hero ON Hero.QuirkId = Quirk.Id WHERE Hero.Id = ?";
        return jdbc.queryForObject(SELECT_QUIRK_FOR_HERO, new QuirkDaoDB.QuirkMapper(), id);
    }

    public void associateQuirk(List<Hero> heros) {
        for (Hero hero: heros) {
            hero.setQuirk(getQuirkForHero(hero.getId()));
        }
    }

    private void associateHeros(List<Org> orgs) {
        for (Org org: orgs) {
            org.setHeroes(getHerosForOrg(org.getId()));
        }
    }

    private static final class OrgMapper implements RowMapper<Org> {

        @Override
        public Org mapRow(ResultSet rs, int index) throws SQLException {
            Org og = new Org();
            og.setId(rs.getInt("Id"));
            og.setName(rs.getString("Name"));
            og.setDescription(rs.getString("Description"));
            og.setAddress(rs.getString("Address"));
            og.setContactInfo(rs.getString("ContactInfo"));
            return og;
        }

    }

}
