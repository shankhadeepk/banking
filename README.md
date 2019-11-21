# The project is about maintaining and transferring money from one account to other.

### Technologies:
    - Jersey 2.29.1
	- H2 Database
	- Log 4j (Logging)
	- Swagger 2.0
	- Jetty Server
	- Junit 4
	- JerseyTest 2.29.1

### Run the project:

    mvn clean install
    mvn exec:java


Features:
	For every transfer transactions are maintained


Swagger url
http://localhost:8080/api/swagger.json

Create Account

Post
http://localhost:8080/api/account

Request
{
	"bankAccHolderName":"Shankha",
	"balance":"1000",
	"currencyCode":"EUR",
	"emailId":"shankha@gmail.com",
	"SSID":"TTTT",
	"contact":"+918787667676",
	"strAccountType":"SAV"
}

Response

{
    "transactionId": "2011201923080323246",
    "typeOfTransaction": "CREATE_ACCOUNT",
    "fromAccount": 1,
    "toAccount": 0,
    "fromAccHolderName": "Shankha",
    "toAccountHolderName": null,
    "dateOfCreation": "20-11-2019 23:08:03",
    "dateOfModification": "20-11-2019 23:08:03",
    "status": "P"
}

