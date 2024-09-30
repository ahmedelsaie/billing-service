Technology used
* java
* spring boot
* hibernate
* jpa
* docker
* mysql
* redis

Running the app
Docker
$ docker-compose up

mysql port 3309
app   port 8080


open swagger on http://localhost:8080/swagger-ui/index.html
* register user using http://localhost:8080/auth/signup
* login with the registered user on http://localhost:8080/auth/login
* use authorize in swagger with the bearer token returned

in order to add user to store either customer, affiliate, or employee just use the api
/users/makeEmployeeToStore
/users/makeAffiliateToStore
/users/makeCustomerToStore


in order to calculate bill
use /calculate api


The system use redis cache to save the calls to external api for currency exchange rate.

