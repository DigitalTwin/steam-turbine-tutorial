# tutorial-timeseries-util
Tutorial Timeseries Utility - This utility reads steam turbine data from a local CSV file which contains timestamp, rpm, and actual
temperature values and pushes that data to a Predix Timeseries Service instance.  Example input file here (and also in the resources directory):
```
timestamp,rpm,temperature
35269201000,500,501.57855029380073
35269202000,500,500.0512064418933
35269203000,500,501.5429791929606
35269204000,500,501.64609451559187
35269205000,500,501.42537209804453
35269206000,500,501.4978736145987
```
### Installation
Clone this repository, adjust the application.yml file and the relevant constants in the
com.ge.digitaltwin.tutorial.util.IngestionService class as appropriate for your environment,
and then "mvn clean install" it to build your executable jar file.  

### Executing the Utility
You can "java -jar target/tutorial-timeseries-util-1.0-SNAPSHOT.jar" to execute the
com.ge.digitaltwin.tutorial.util.TutorialTimeseriesUtilApplication class with the following VM
options (being mindful of your proxy settings):
```
-Dhttps.proxyHost=<HOSTNAME>
-Dhttps.proxyPort=<PORT>
-Dcom.ge.dt.ptsc.clientSecret=<client-secret-for-tutorial-svcs-client>
-Dconfig.steamTurbineId=<steam turbine id>
-Dconfig.csvFilename=<csv filename in resources directory or path/filename on the local file system>
```


The console will echo something similar to this:
```
##### Configuration: TimeseriesUtilConfig{steamTurbineId='steamTurbine-007', csvFilename='d:\Temp\data_points_turbine_test.csv'}
##### TutorialTimeseriesUtilController: up and running...
##### IngestionService instance: com.ge.digitaltwin.tutorial.util.IngestionService@2cab9998
##### resource d:\Temp\data_points_turbine_test.csv not found, looking for file...
##### found file: d:\Temp\data_points_turbine_test.csv
##### Found 6 SteamTurbineDataPoints in d:\Temp\data_points_turbine_test.csv for steamTurbineId=steamTurbine-007
##### sending steam turbine data payload: 1
##### Done, processed 6 steam turbines.
```

### Verification
By convention, testing data is between the years 1970 and 1979 (too bad we can't delete from a Timeseries service instance).
To verify the success of the data push, use Postman (or some similar tool) to hit the /v1/datapoints path
on your timeseries service instance.  A POST request with the following body should show your data (adjust the start and
end values as needed).
```
{
	"start": "50y-ago",
	"end": "25y-ago",
	"tags": [{
		"name": "rpm",
		"limit": 1000,
		"order": "desc"
	},{
		"name": "actualTemperature",
		"limit": 1000,
		"order": "desc"
	}]
}
```
