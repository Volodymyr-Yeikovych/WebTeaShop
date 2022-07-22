package com.spr.dao;

import com.spr.error.InternalFatalError;
import com.spr.exceptions.SQLExceptionWrapper;
import com.spr.model.Tea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresTea")
public class TeaDataAccessService implements TeaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeaDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tea> selectAllTea() {
        return jdbcTemplate.query("SELECT * FROM stock;", this::teaMapper);
    }

    @Override
    public Tea selectTeaById(UUID id) {
        List<Tea> tea = jdbcTemplate.query("SELECT * FROM stock WHERE id = ?;", this::teaMapper, id);
        if (tea.isEmpty()) throw new InternalFatalError("Fatal Error occurred, tea type was deleted while the app was running." +
                "Requested tea no more exist in database.");
        return tea.stream().findFirst().get();
    }

    //TODO: implement method correctly
    @Override
    public int updateTea(Tea tea) {
        return 0;
    }

    private Tea teaMapper(ResultSet rs, int i) {
        try {
            return new Tea(UUID.fromString(rs.getString("id")), rs.getString("name"),
                    rs.getLong("av_kg"), rs.getLong("price_kg"));
        } catch (SQLException e) {
            throw new SQLExceptionWrapper(e.getMessage());
        }
    }
}
