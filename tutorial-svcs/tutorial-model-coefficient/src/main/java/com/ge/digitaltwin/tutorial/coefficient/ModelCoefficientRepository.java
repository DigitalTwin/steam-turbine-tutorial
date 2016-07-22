package com.ge.digitaltwin.tutorial.coefficient;

import com.ge.digitaltwin.tutorial.common.coefficient.ModelCoefficient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

@SuppressWarnings("unused")
public interface ModelCoefficientRepository extends CrudRepository<ModelCoefficient, UUID> {

    ModelCoefficient findByAssetId(@Param("assetId") String assetId);

}
