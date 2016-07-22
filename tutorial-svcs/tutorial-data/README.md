Tutorial Data Service
=====================

Configuration
-------------

security.oauth2.resource.token-info-uri - URL for token validation
security.oauth2.client.client-id - Client to use for token validation
security.oauth2.client.client-secret - Client secret to use for token validation
com.ge.dt.ptsc.token-endpoint - URL from which to obtain token used for time series service
com.ge.dt.ptsc.client-id - Client used to obtain token for time series service
com.ge.dt.ptsc.client-secret - Clietn secret used to obtain token for time series service
com.ge.dt.ptsc.proxy-host - Proxy host used for time series service ingestion
com.ge.dt.ptsc.proxy-port - Proxy port used for time series service ingestion
com.ge.dt.ptsc.query-endpoint - Time series service query endpoint
com.ge.dt.ptsc.ingestion-endpoint - Time series service ingestion endpoint
com.ge.dt.ptsc.zone-id - Time series service zone ID
com.ge.digitaltwin.tutorial.data.coefficient.coefficient-service - URL of model coefficient service. e.g. https://<YOUR_OWN_COEFFICIENT_SERVICE_PREFIX>-dt-tutorial-model-coefficient.run.aws-usw02-pr.ice.predix.io/persistence/modelCoefficients/search/findByAssetId

Swagger UI
----------
Swagger UI documenting endpoints is available at /swagger-ui.html.