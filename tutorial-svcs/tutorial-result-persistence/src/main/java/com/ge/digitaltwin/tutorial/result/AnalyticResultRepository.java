package com.ge.digitaltwin.tutorial.result;

import com.ge.digitaltwin.tutorial.common.AnalyticResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

@SuppressWarnings("unused")
public interface AnalyticResultRepository extends CrudRepository<AnalyticResult, UUID> {

    Long countByAssetId(@Param("assetId") String assetId);

}
