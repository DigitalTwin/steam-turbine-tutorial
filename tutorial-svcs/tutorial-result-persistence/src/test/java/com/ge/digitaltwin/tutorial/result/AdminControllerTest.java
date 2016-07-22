package com.ge.digitaltwin.tutorial.result;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminControllerTest {

    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Before
    public void setup() {
        initMocks(this);
        adminController = new AdminController(adminService);
    }

    @Test
    public void deletesAllResults() {
        adminController.deleteAllResults();
        then(adminService).should().deleteAllResults();
    }

}
