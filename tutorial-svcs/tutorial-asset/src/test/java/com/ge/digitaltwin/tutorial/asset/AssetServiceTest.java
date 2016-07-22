package com.ge.digitaltwin.tutorial.asset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AssetApplication.class)
@WebIntegrationTest(randomPort = true)
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @Test
    public void findsAllAssets() throws IOException {
        final List<Asset> assets = assetService.findAllAssets();
        assertThat(assets.size(), is(6));
        assertThat(assets.get(0).getAssetId(), is("1"));
        assertThat(assets.get(0).getAssetName(), is("GE ST-T360 01"));
    }
}
