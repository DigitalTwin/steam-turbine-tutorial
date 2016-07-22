package com.ge.digitaltwin.tutorial.result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.eq;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminServiceTest {

    private AdminService adminService;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        initMocks(this);
        adminService = new AdminService(jdbcTemplate);
    }

    @Test
    public void deletesAllResults() {
        adminService.deleteAllResults();
        then(jdbcTemplate).should().execute(eq("TRUNCATE analytic_result"));
    }
}
