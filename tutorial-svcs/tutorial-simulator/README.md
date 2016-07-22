tutorial-simulator
==============

A Spring Boot service to run the Digital Twin tutorial simulation - that is, run the workflow over a simulated time period..

API Documentation
-----------------

### Swagger
This service generates Swagger documentation for all RESTful endpoints. The Swagger GUI may be accessed via web browser
at /swagger-ui.html.

### Endpoints

#### POST /simulate?assetId=asset&startTime=startTime&endTime=endTime&intervalMilliseconds=intervalMilliseconds
Runs the demo workflow multiple times starting with startTime and incrementing by intervalMilliseconds until endTime is reached
Example:
/simulate?assetId=asset123&startTime=1436234000L&endTime=1436834000L&intervalMilliseconds=60000

When completed use the GET endpoint from the tutorial-result-persistence app to see stored results.
