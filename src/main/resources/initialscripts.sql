-- scripts to initailly load the database

CREATE TABLE IF NOT EXISTS BANKACCOUNT(
BANKACCID INT PRIMARY KEY,
SSID VARCHAR(100),
BANKACCHOLDERNAME VARCHAR(250),
BALANCE DECIMAL(20,2) DEFAULT 0.00,
CURRENCYCODE VARCHAR(3) DEFAULT 'GBP',
EMAILID VARCHAR(50),
CONTACT VARCHAR(20),
ACCOUNTTYPE VARCHAR(4) DEFAULT 'SAV',
STATUS VARCHAR(1) DEFAULT 'A',
CREATE_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
MODIFY_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS BANKTRANSACTION(
TRANSACTIONID VARCHAR(100) PRIMARY KEY,
TYPEOFTRANSACTION VARCHAR(250),
FROMACCOUNT BIGINT,
TOACCOUNT BIGINT,
FROMACCHOLDERNAME VARCHAR(250),
TOACCHOLDERNAME VARCHAR(250),
STATUS VARCHAR(10),
CREATE_DATE DATETIME DEFAULT CURRENT_TIMESTAMP,
MODIFY_DATE DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

commit;
