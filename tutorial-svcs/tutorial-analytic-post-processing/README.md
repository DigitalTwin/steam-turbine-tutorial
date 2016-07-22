# tutorial-analytic-post-processing
This service takes the JSON output from the analytic and converts it to a new JSON format that can be easily POSTed to the tutorial-result-persistence service to be persisted.

### Installation
Clone this repository and then "mvn clean package" it.  It'll build a jar file in the "target" directory.

### Post-process your data
You will POST to the '/transform/{assetId}' endpoint of this service, you must replace '{assetId}' with the id of the asset you are transforming the data for. Your POST request will need the following headers: Authorization, Predix-Zone-Id, and Content-Type (application/json).  The request body contains the JSON payload that is output from the analytic.  For example:
```
{
  "result": {
    "series": [
      {
        "timestamp": 1467747958000,
        "rpm": 3000,
        "actualTemperature": 201,
        "expectedTemperature": 199,
        "delta": 2
      },
      {
        "timestamp": 1467748018000,
        "rpm": 3000,
        "actualTemperature": 204,
        "expectedTemperature": 199,
        "delta": 5
      }
    ],
    "slope": 0.065,
    "intercept": 4
  }
}
```

The response will look something like this:
```
[
  {
    "id": null,
    "expectedTemperature": 199,
    "actualTemperature": 201,
    "timestamp": 1467747958000,
    "assetId": "1",
    "rpm": 3000,
    "temperatureDelta": 2
  },
  {
    "id": null,
    "expectedTemperature": 199,
    "actualTemperature": 204,
    "timestamp": 1467748018000,
    "assetId": "1",
    "rpm": 3000,
    "temperatureDelta": 5
  }
]
```

### Important Note
There are two different ways of executing an analytic in the analytic catalog service, as shown in the
[Predix documentation](https://www.predix.io/docs/#A5cFZF2V). This post-processing service assumes that you are
executing the analytic through the **\<analytic-uri\>/api/v1/analytic/execution** endpoint. If you are executing the
analytic through the **\<catalog_uri\>/api/v1/catalog/analytics/{id}/execution** endpoint, the response payload will
not have the JSON format that this service expects.


