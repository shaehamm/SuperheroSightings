/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.daos.HeroDaoDB.HeroMapper;
import com.sg.superherosightings.daos.LocationDaoDB.LocationMapper;
import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.dtos.Quirk;
import com.sg.superherosightings.dtos.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author codedchai
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting getSightingById(int id) {
        final String SELECT_SIGHT_BY_ID = "SELECT * FROM Sighting WHERE Id = ?";
        try {
            Sighting toReturn = jdbc.queryForObject(SELECT_SIGHT_BY_ID,
                    new SightingMapper(), id);
            toReturn.setHero(getHeroForSighting(id));
            toReturn.setLocation(getLocationForSighting(id));
            return toReturn;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Sighting> getAllSightings() {
        List<Sighting> toReturn = new ArrayList<>();
        final String SELECT_ALL_SIGHT = "SELECT * FROM Sighting ORDER BY date";
        try {
            toReturn = jdbc.query(SELECT_ALL_SIGHT, new SightingMapper());
            associateHeroAndLocation(toReturn);
        } catch (DataAccessException ex) {

        }
        return toReturn;
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHT = "INSERT INTO Sighting (Date, Description, "
                + "LocationId, HeroId) VALUES (?,?,?,?)";
        jdbc.update(INSERT_SIGHT,
                sighting.getDate(),
                sighting.getDescription(),
                sighting.getLocation().getId(),
                sighting.getHero().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHT = "UPDATE Sighting SET Date = ?, Description = ?,"
                + " LocationId = ?, HeroId = ? WHERE Id = ?";
        jdbc.update(UPDATE_SIGHT,
                sighting.getDate(),
                sighting.getDescription(),
                sighting.getLocation().getId(),
                sighting.getHero().getId(),
                sighting.getId());
    }

    @Override
    public void deleteSightingById(int id) {
        final String DELETE_SIGHT = "DELETE FROM Sighting WHERE Id = ?";
        jdbc.update(DELETE_SIGHT, id);
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsByDate(LocalDate date) {
        List<Sighting> toReturn = new ArrayList<>();
        final String SELECT_SIGHTINGS_FOR_DATE = "SELECT s.* FROM Sighting s "
                + "WHERE Date = ?";
        try {
            toReturn = jdbc.query(SELECT_SIGHTINGS_FOR_DATE, new SightingMapper(), date);
            associateHeroAndLocation(toReturn);
        } catch (DataAccessException ex) {

        }
        return toReturn;
    }

    private Hero getHeroForSighting(int id) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT Hero.* FROM Hero JOIN "
                + "Sighting s ON Hero.Id = s.HeroId WHERE s.Id = ?";
        Hero toReturn = jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING,
                new HeroMapper(), id);
        toReturn.setQuirk(getQuirkForHero(toReturn.getId()));
        return toReturn;
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON l.Id = s.LocationId WHERE s.Id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING,
                new LocationMapper(), id);
    }

    private void associateHeroAndLocation(List<Sighting> sightings) {
        for (Sighting sight : sightings) {
            sight.setHero(getHeroForSighting(sight.getId()));
            sight.setLocation(getLocationForSighting(sight.getId()));
        }
    }

    private Quirk getQuirkForHero(int id) {
        final String SELECT_QUIRK_FOR_HERO = "SELECT Quirk.* FROM Quirk JOIN "
                + "Hero ON Hero.QuirkId = Quirk.Id WHERE Hero.Id = ?";
        return jdbc.queryForObject(SELECT_QUIRK_FOR_HERO, new QuirkDaoDB.QuirkMapper(), id);
    }

    @Override
    @Transactional
    public List<Sighting> getLatestSightings(int count) {
        List<Sighting> toReturn = new ArrayList<>();
        final String SELECT_SIGHT_LIMIT = "SELECT * FROM Sighting ORDER BY date DESC"
                + " LIMIT ?";
        try {
            toReturn = jdbc.query(SELECT_SIGHT_LIMIT, new SightingMapper(), count);
            associateHeroAndLocation(toReturn);
        } catch (DataAccessException ex) {

        }
        return toReturn;
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsByLocation(int id) {
        final String SELECT_SIGHTINGS_BY_LOCATION = "SELECT s.* FROM Sighting s "
                + "JOIN Location l On s.LocationId = l.Id WHERE l.Id = ?";
        List<Sighting> toReturn = jdbc.query(SELECT_SIGHTINGS_BY_LOCATION, new SightingMapper(), id);
        associateHeroAndLocation(toReturn);
        return toReturn;
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsForHero(int id) {
        final String SELECT_SIGHTINGS_FOR_HERO = "SELECT s.* FROM Sighting s "
                + "JOIN Hero ON s.HeroId = Hero.Id WHERE Hero.Id = ?";
        List<Sighting> toReturn = jdbc.query(SELECT_SIGHTINGS_FOR_HERO, new SightingMapper(), id);
        associateHeroAndLocation(toReturn);
        return toReturn;
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting st = new Sighting();
            st.setId(rs.getInt("Id"));
            st.setDate(rs.getDate("Date").toLocalDate());
            st.setDescription(rs.getString("Description"));
            return st;
        }

    }

}
