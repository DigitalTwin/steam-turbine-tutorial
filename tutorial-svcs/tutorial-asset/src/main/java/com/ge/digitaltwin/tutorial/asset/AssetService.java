package com.ge.digitaltwin.tutorial.asset;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AssetService {

    @Value("classpath:assets.json")
    private Resource assetResource;

    public List<Asset> findAllAssets() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(assetResource.getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Asset.class));
    }
}
