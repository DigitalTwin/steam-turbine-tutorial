# steam-turbine-tutorial
Steam Turbine Tutorial

### Postman Collection
For your convenience, a Postman collection has been created and can be found in the file  *DigitalTwinTutorial.postman_collection.json*.
This collection can be used as a guide for how to use the RESTful endpoints on the tutorial services. To use this collection
you must first import the file into Postman. Since there are a few strings that are frequently used, the collection is configured
to read 3 different variables from your Postman environment:

- token = \<bearer token\>
    - Used in the header of requests
- analytic-zone-id = \<Predix analytics catalog zone id\>
    - Used in the Predix-Zone-Id header of requests to the analytic catalog service
- credentials = \<Base64(client-id:client-secret)\>
    - Used in the Authorization header of the request to get your bearer token

Links:
- Importing a collection into Postman: https://www.getpostman.com/docs/collections
- Setting up Postman environment: https://www.getpostman.com/docs/environments
- Base64 conversion: https://www.base64encode.org/
