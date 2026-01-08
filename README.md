# Consumption charging

## How to run
- Run the container from docker-compose.yml
- Start the CongestionTaxApplication
- The app comes already with some predefined data which is inserted in the DataInitializer class

## Focus

My focus for this task was to complete the business logic so I can demo at least a part of it in the interview.
There are multiple improvements that could and should have been made, but since I was time-limited, I focused solely on
the business logic. I can explain more in the interview. Below are the suggested improvements from my side, order by the
importance.

## Improvements/things to do in Consumption Charging

### 1. Authorization/Authentication
- Add authorization/authentication so that the app is not available to everyone
- Add Security Filter Chain bean so that we control which endpoints should be secured, and which not

### 2. Add support for profiles
- For the deployment to different environments, we need to create different profiles so that the env variables are loaded differently
- Create new application.yaml files for it

### 3. Error handling
- Need to add Controller Advice for handling the custom exceptions. Maybe adding exception code as well.
- Return different HTTP status codes depending on the error

### 4. Field validations
- We should use jakarta validations where we can so that we reject invalid requests quickly

### 5. Logging
- Logs are inconsistent and should be streamlined
- Needing multiple level of logs to be more detailed

### 6. Add caching
- I would add caching into often fetched records

### 7. Potentially split into microservices
- There is a potential for splitting this service into multiple microservices
- I would split it similarly to the package naming in the service layer

### 8. Create additional modules
- We should add more modules so that the app can, for example, expose dtos to other services
- As another example, we can create a client module which would define an http client if we want to call our microservice with http

### 8. Add checkstyle
- Checkstyle or something similar should be added so that the code conventions are streamlined
