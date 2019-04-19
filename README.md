## Build and run the service

You will need two terminals open.

In the first terminal run

    ./gradlew clean build
    ./gradlew bootRun

Use the second terminal to test the service using curl commands

## /user
- HTTP GET method
- gets the three most frequently visited merchants for a caller specified user id.
- returns error if an insufficient number of transactions have been processed for the user
- the solution loads data from the csv file and this data can be used to validate the service
- returns 404 when the user is not found

###### Examples
```
curl localhost:8080/user/1
curl localhost:8080/user/2
curl localhost:8080/user/3
```

## /transaction
- accepting a JSON message as an HTTP POST.

###### Examples
```
curl -X POST localhost:8080/transaction -H 'Content-type:application/json' -d '{"user-id": "1","purchase-date" : "2019-01-01T22:45:40","merchant": "Acme Inc"}'

```

## Other endpoints
- Find health of the application, make a GET request to /actuator/health
```
curl localhost:8080/actuator/health
```
- Find the environment detail, make a GET request to /actuator/env
```
curl localhost:8080/actuator/env
```
- Retrieves metrics
```
curl localhost:8080/actuator/env
```