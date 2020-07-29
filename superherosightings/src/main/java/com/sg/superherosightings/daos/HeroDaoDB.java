/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.daos.QuirkDaoDB.QuirkMapper;
import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.dtos.Org;
import com.sg.superherosightings.dtos.Quirk;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
import com.sg.superherosightings.exceptions.NullOrganizationDataException;
import com.sg.superherosightings.exceptions.NullQuirkDataException;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hero getHeroById(int id) {
        final String SELECT_HERO_BY_ID = "SELECT * FROM Hero WHERE Id = ?";
        try {
            Hero toReturn = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            toReturn.setQuirk(getQuirkForHero(id));
            return toReturn;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROS = "SELECT * FROM Hero ORDER BY name";
        List<Hero> toReturn = jdbc.query(SELECT_ALL_HEROS, new HeroMapper());
        associateQuirk(toReturn);
        return toReturn;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) throws NullHeroDataException {
        if (hero == null) {
            throw new NullHeroDataException("The Hero data is invalid");
        }
        final String INSERT_HERO = "INSERT INTO Hero (Name, Alignment, QuirkId) "
                + "VALUES (?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getAlignment(),
                hero.getQuirk().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) throws NullHeroDataException {
        if (hero == null) {
            throw new NullHeroDataException("The Hero data is invalid");
        }
        final String UPDATE_HERO = "UPDATE Hero SET Name = ?, Alignment = ?, "
                + "QuirkId = ? WHERE Id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getAlignment(),
                hero.getQuirk().getId(),
                hero.getId());
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_HERO_ORG = "DELETE FROM HeroOrg WHERE HeroId = ?";
        jdbc.update(DELETE_HERO_ORG, id);
        final String DELETE_HERO_SIGHTING = "DELETE FROM Sighting WHERE HeroId = ?";
        jdbc.update(DELETE_HERO_SIGHTING, id);
        final String DELETE_HERO = "DELETE FROM Hero WHERE Id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    @Transactional
    public List<Hero> getHeroesForLocation(Location location) throws NullLocationDataException {
        if (location == null) {
            throw new NullLocationDataException("The Location data is invalid");
        }
        final String SELECT_HEROS_FOR_LOCATION = "SELECT Hero.* FROM Hero JOIN "
                + "Sighting s ON Hero.Id = s.HeroId JOIN "
                + "Location l ON s.LocationId = l.Id WHERE l.Id = ?";
        List<Hero> toReturn = jdbc.query(SELECT_HEROS_FOR_LOCATION, new HeroMapper(), 
                location.getId());
        associateQuirk(toReturn);
        return toReturn;
    }

    @Override
    @Transactional
    public List<Hero> getHeroesForOrg(Org org) throws NullOrganizationDataException {
        if (org == null) {
            throw new NullOrganizationDataException("The Organization data is invalid");
        }
        final String SELECT_HEROS_FOR_ORG = "SELECT Hero.* FROM Hero "
                + "JOIN HeroOrg h ON Hero.Id = h.HeroId "
                + "JOIN Org ON Org.Id = h.OrgId WHERE Org.Id = ?";
        List<Hero> toReturn = jdbc.query(SELECT_HEROS_FOR_ORG, new HeroMapper(), 
                org.getId());
        associateQuirk(toReturn);
        return toReturn;
    }

    private Quirk getQuirkForHero(int id) {
        final String SELECT_QUIRK_FOR_HERO = "SELECT Quirk.* FROM Quirk JOIN "
                + "Hero ON Hero.QuirkId = Quirk.Id WHERE Hero.Id = ?";
        return jdbc.queryForObject(SELECT_QUIRK_FOR_HERO, new QuirkMapper(), id);
    }

    public void associateQuirk(List<Hero> heros) {
        for (Hero hero: heros) {
            hero.setQuirk(getQuirkForHero(hero.getId()));
        }
    }

    @Override
    @Transactional
    public List<Hero> getHeroesForQuirk(Quirk quirk) throws NullQuirkDataException {
        if (quirk == null) {
            throw new NullQuirkDataException("The Quirk data is invalid");
        }
        final String SELECT_HEROES_FOR_QUIRK = "SELECT Hero.* FROM Hero JOIN "
                + "Quirk ON Hero.QuirkId = Quirk.Id WHERE Quirk.Id = ?";
        List<Hero> toReturn = jdbc.query(SELECT_HEROES_FOR_QUIRK, new HeroMapper(), quirk.getId());
        associateQuirk(toReturn);
        return toReturn;
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hr = new Hero();
            hr.setId(rs.getInt("Id"));
            hr.setName(rs.getString("Name"));
            hr.setAlignment(rs.getString("Alignment"));
            return hr;
        }

    }

}
