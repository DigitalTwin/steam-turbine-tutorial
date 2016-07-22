package com.ge.digitaltwin.tutorial.asset;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;

public class AssetControllerTest {

    private AssetController assetController;

    @Mock
    private AssetService assetService;

    @Before
    public void setup() {
        initMocks(this);
        assetController = new AssetController(assetService);
    }

    @Test
    public void findsAllAssets() throws IOException {
        assetController.findAllAssets();
        then(assetService).should().findAllAssets();
    }
}
