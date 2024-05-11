# NAHSI microservice PoC

## Starting services

The microservice can be started locally from the IDE. Please note that the Config and Discovery Server must be started before any other application.

You can access the following services at given location:
* Discovery Server - http://localhost:8761
* Config Server - http://localhost:8888
* Services - random port, check Eureka discovery dashboard
* AngularJS frontend (API Gateway) - http://localhost:8080 

To tell the Config Server to use your local Git repository use `native` Spring profile and set the 
`GIT_REPO` environment variable, for example:
`-Dspring.profiles.active=native -DGIT_REPO=/projects/spring-microservices-config`

## poc-service
Simple service with 3 endpoints.

The service loads it's configuration from the configuration server configured in application.properties. 
When started it provides the following endpoints: 

### greetings: 
Simple hello service returning a greeting for testing at: http://localhost:8080/greeting?name=Username

### practitioner:
Serves RESTful FHIR interface for simple busines objects e.g. licence information at: http://localhost:8080/practitioner/Id-3

### patient:
Serves RESTful FHIR interface for simple busines objects e.g. licence information at: http://localhost:8080/patient/Id-3


# TODO
- use parent pom in poc-service pom
- Add service to perform a HTTP requests to external source (e.g., FHIR Server) 
- Test connection with mongo db. Use compass client for mongo management.
- Use redis cache for interprocess state
- Add traceparent header in api-gateway

