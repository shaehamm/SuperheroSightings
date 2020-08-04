/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.daos;

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
public class QuirkDaoDB implements QuirkDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Quirk getQuirkById(int id) {
        final String SELECT_QUIRK_BY_ID = "SELECT * FROM Quirk WHERE Id = ?";
        try {
            return jdbc.queryForObject(SELECT_QUIRK_BY_ID, new QuirkMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Quirk> getAllQuirks() {
        final String SELECT_ALL_QUIRKS = "SELECT * FROM Quirk ORDER BY name";
        return jdbc.query(SELECT_ALL_QUIRKS, new QuirkMapper());
    }

    @Override
    @Transactional
    public Quirk addQuirk(Quirk quirk) {
        final String INSERT_QUIRK = "INSERT INTO Quirk (Name, Description)"
                + " VALUES (?,?)";
        jdbc.update(INSERT_QUIRK,
                quirk.getName(),
                quirk.getDescription());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        quirk.setId(newId);
        return quirk;

    }

    @Override
    public void updateQuirk(Quirk quirk) {
        final String UPDATE_QUIRK = "UPDATE Quirk SET Name = ?, Description = ? "
                + "WHERE Id = ?";
        jdbc.update(UPDATE_QUIRK,
                quirk.getName(),
                quirk.getDescription(),
                quirk.getId());
    }

    @Override
    @Transactional
    public void deleteQuirkById(int id) {
        final String DELETE_SIGHT_BY_QUIRK = "DELETE s.* FROM Sighting s JOIN Hero"
                + " ON s.HeroId = Hero.Id WHERE Hero.QuirkId = ?";
        jdbc.update(DELETE_SIGHT_BY_QUIRK, id);
        final String DELETE_HERO_ORG_BY_QUIRK = "DELETE HeroOrg.* FROM HeroOrg "
                + "JOIN Hero ON Hero.Id = HeroOrg.HeroId WHERE Hero.QuirkId = ?";
        jdbc.update(DELETE_HERO_ORG_BY_QUIRK, id);
        final String DELETE_HERO_BY_QUIRK = "DELETE FROM Hero WHERE QuirkId = ?";
        jdbc.update(DELETE_HERO_BY_QUIRK, id);
        final String DELETE_QUIRK = "DELETE FROM Quirk WHERE Id = ?";
        jdbc.update(DELETE_QUIRK, id);
    }

    public static final class QuirkMapper implements RowMapper<Quirk> {

        @Override
        public Quirk mapRow(ResultSet rs, int index) throws SQLException {
            Quirk qk = new Quirk();
            qk.setId(rs.getInt("Id"));
            qk.setName(rs.getString("Name"));
            qk.setDescription(rs.getString("Description"));
            return qk;
        }

    }

}
