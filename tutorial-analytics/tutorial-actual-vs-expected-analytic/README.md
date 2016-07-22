# tutorial-actual-vs-expected-analytic
Actual vs Expected Analytic - This analytic accepts a slope and y-intercept as model parameters.  It also expects a timeseries of RPM and actual temperatures values.  The model computes the expected temperature and a delta from the actual temperature, adds this to the timeseries, and returns it.

### Installation
Clone this repository and then "mvn clean package" it.  It'll build a jar file in the "target" directory.

Using an instance of Predix Analytics Catalog, create a new catalog entry, upload your previously built jar file from your target directory, validate the analytic, and then execute it.

### Executing the analytic
Get the analytic's id from the Predix Analytics Catalog and construct the URL to the execution API endpoint.  Your POST request will need the following headers: Authorization, Predix-Zone-Id, and Content-Type (application/json).  The request body contains the JSON payload that serves as the input data to the analytic.  For example:
```
{
	"series": [{
		"timestamp": 1466308800000,
		"rpm": 3000,
		"actualTemperature": 201
	}, {
		"timestamp": 1466308801000,
		"rpm": 3000,
		"actualTemperature": 203
	}, {
		"timestamp": 1466308802000,
		"rpm": 3000,
		"actualTemperature": 208
	}, {
		"timestamp": 1466308803000,
		"rpm": 3000,
		"actualTemperature": 211
	}],
	"slope": 0.065,
	"intercept": 4.0
}
```
### Analytic Results

There are two different ways to execute an analytic, as documented in the [Predix documentation on the analytics catalog](https://www.predix.io/docs/#A5cFZF2V).

Our implementation of the **tutorial-analytic-post-processing** service expects that you will be executing the analytic from the *\<analytic-uri\>/api/v1/analytic/execution* endpoint (URL). The response that you will receive will look something like this:
```
{
  "result": {
    "series": [
      {
        "timestamp": 1466308800000,
        "rpm": 3000,
        "actualTemperature": 201,
        "expectedTemperature": 199,
        "delta": 2
      },
      {
        "timestamp": 1466308801000,
        "rpm": 3000,
        "actualTemperature": 203,
        "expectedTemperature": 199,
        "delta": 4
      },
      {
        "timestamp": 1466308802000,
        "rpm": 3000,
        "actualTemperature": 208,
        "expectedTemperature": 199,
        "delta": 9
      },
      {
        "timestamp": 1466308803000,
        "rpm": 3000,
        "actualTemperature": 211,
        "expectedTemperature": 199,
        "delta": 12
      }
    ],
    "slope": 0.065,
    "intercept": 4
  }
}
```

Another way to execute the analytic is to use the *\<catalog_uri\>/api/v1/catalog/analytics/{id}/execution* endpoint (URL).
The **tutorial-analytic-post-processing** service is not set up to handle the JSON payload structure returned from this request. If you
would like to use this endpoint, you must add functionality into the **tutorial-analytic-post-processing** service.


The response will look something like this:
```
{
  "createdTimestamp": "2016-07-08 16:53:50.637",
  "analyticId": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
  "updatedTimestamp": "2016-07-08 16:53:50.749",
  "inputData": "{\r\n \"series\": [{\r\n   \"timestamp\": 1466308800000,\r\n   \"rpm\": 3000,\r\n   \"actualTemperature\": 201\r\n   }, {\r\n   \"timestamp\": 1466308801000,\r\n   \"rpm\": 3000,\r\n   \"actualTemperature\": 203\r\n   }, {\r\n   \"timestamp\": 1466308802000,\r\n   \"rpm\": 3000,\r\n   \"actualTemperature\": 208\r\n   }, {\r\n   \"timestamp\": 1466308803000,\r\n   \"rpm\": 3000,\r\n   \"actualTemperature\": 211\r\n }],\r\n \"slope\": 0.065,\r\n \"intercept\": 4.0\r\n}",
  "status": "COMPLETED",
  "message": "Analytic executed successfully.",
  "result": "{\"result\":{\"series\":[{\"timestamp\":1466308800000,\"rpm\":3000,\"actualTemperature\":201,\"expectedTemperature\":199.0,\"delta\":2.0},{\"timestamp\":1466308801000,\"rpm\":3000,\"actualTemperature\":203,\"expectedTemperature\":199.0,\"delta\":4.0},{\"timestamp\":1466308802000,\"rpm\":3000,\"actualTemperature\":208,\"expectedTemperature\":199.0,\"delta\":9.0},{\"timestamp\":1466308803000,\"rpm\":3000,\"actualTemperature\":211,\"expectedTemperature\":199.0,\"delta\":12.0}],\"slope\":0.065,\"intercept\":4.0}}"
}
```


