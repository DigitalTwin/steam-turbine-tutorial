package com.ge.digitaltwin.tutorial.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void deleteAllResults() {
        jdbcTemplate.execute("TRUNCATE analytic_result");
    }
}
