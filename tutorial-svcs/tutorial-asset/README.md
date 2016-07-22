tutorial-asset
==============

A Spring Boot service for querying tutorial asset information.

API Documentation
-----------------

### Swagger
This service generates Swagger documentation for all RESTful endpoints. The Swagger GUI may be accessed via web browser
at /swagger-ui.html.

### Endpoints

#### GET /asset
Returns an array of tutorial assets in JSON format with assetId and assetName. Example:
````
[
  {
    "assetId" : "1",
    "assetName" : "Han Solo"
  },

  {
    "assetId" : "2",
    "assetName" : "Chewbacca"
  }
}
````
