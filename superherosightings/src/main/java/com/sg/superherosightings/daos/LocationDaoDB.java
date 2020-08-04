/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

import com.sg.superherosightings.dtos.Hero;
import com.sg.superherosightings.dtos.Location;
import com.sg.superherosightings.exceptions.NullHeroDataException;
import com.sg.superherosightings.exceptions.NullLocationDataException;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        final String SELECT_LOCATION_BY_ID = "SELECT * FROM Location WHERE Id = ?";
        try {
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID,
                    new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location ORDER BY name";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    @Transactional
    public Location addLocation(Location location) throws NullLocationDataException {
        if (location == null) {
            throw new NullLocationDataException("The Location data is invalid");
        }
        final String INSERT_LOCATION = "INSERT INTO Location (Name, Address, "
                + "Latitude, Longitude) VALUES (?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) throws NullLocationDataException {
        if (location == null) {
            throw new NullLocationDataException("The Location data is invalid");
        }
        final String UPDATE_LOCATION = "UPDATE Location SET Name = ?, Address = ?,"
                + " Latitude = ?, Longitude = ? WHERE Id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_SIGHT_BY_LOCATION = "DELETE FROM Sighting WHERE "
                + "LocationId = ?";
        jdbc.update(DELETE_SIGHT_BY_LOCATION, id);
        final String DELETE_LOCATION = "DELETE FROM Location WHERE Id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) throws NullHeroDataException {
        if (hero == null) {
            throw new NullHeroDataException("The Hero data is invalid");
        }
        final String SELECT_LOCATIONS_FOR_HERO = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON l.Id = s.LocationId "
                + "JOIN Hero ON s.HeroId = Hero.Id WHERE Hero.Id = ?";
        return jdbc.query(SELECT_LOCATIONS_FOR_HERO, new LocationMapper(), hero.getId());
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location lc = new Location();
            lc.setId(rs.getInt("Id"));
            lc.setName(rs.getString("Name"));
            lc.setAddress(rs.getString("Address"));
            lc.setLatitude(rs.getDouble("Latitude"));
            lc.setLongitude(rs.getDouble("Longitude"));
            return lc;
        }

    }

}
