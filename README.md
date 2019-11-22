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


### Features:
	For every event transactions are maintained
	The response corresponds to the transaction that has been triggered
	Once transaction is processed the status changed to processed.

### Swagger url
    http://localhost:8080/api/swagger.json


### Initiation Class of the project
    com.revolut.banking.BankingStarter

### Operations

| Operation           | Method         |   Url                                              |     Response status       | Comments                                           |
|---------------------|----------------|----------------------------------------------------|---------------------------|----------------------------------------------------|
| ACCOUNT  |  |  |  |  |
| CREATE ACCOUNT      | POST           |   /api/account/                                    |     201                   | Create an account and send transaction as response |
| GET ACCOUNT DETAILS | GET            |   /api/account/{accId}                             |     200                   | Get information of account with account id (accId) |
| DELETE ACCOUNT      | DELETE         |   /api/account/{accId}                             |     200                   | Deletes an account with account Id (accId) provided|
| UPDATE BALANCE      | PUT            |   /api/account/{accId}                             |     200                   | Add amount to balance                              |
| TRANSFER FUND       | POST           |   /api/account/from/{fromAccount}/to/{toAccount}   |     200                   | Transfer fund from one account to another          |
| TRANSACTION |  |  |  |  |
| GET ALL TRANSACTION  | GET            |   /api/transaction/                                |     200                   | get information of all transactions                |


### Example
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

