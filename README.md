# NAHSI microservice PoC

## Starting services

The microservices can be started locally from the IDE. Please note that the Config and Discovery Server must be started before any other application.

You can access the following services at given location:
* Discovery Server - http://localhost:8761
* Config Server - http://localhost:8888
* Services - random port, check Eureka discovery dashboard
* Admin Server - http://localhost:9090/

To tell the Config Server to use your local Git repository use `native` Spring profile and set the 
`GIT_REPO` environment variable, for example:
`-Dspring.profiles.active=native -DGIT_REPO=/projects/spring-microservices-config`

## poc-service

The service loads it's configuration from the configuration server configured in application.properties. 
When started it provides the following endpoints: 

The poc service endpoints are:

### greetings: 
Simple hello service returning a greeting for testing at: http://localhost:8080/greeting?name=Username

### practitioner:
Serves RESTful FHIR interface for simple busines objects e.g. licence information at: http://localhost:8080/practitioner/Id-3

### patient:
Serves RESTful FHIR interface for simple busines objects e.g. licence information at: http://localhost:8080/patient/Id-3

### retrieve:
Calls external REST interfaces for data, e.g., from https://randomuser.me at http://localhost:8080/retrieve. 



