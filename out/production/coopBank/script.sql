CREATE DATABASE IF NOT EXISTS SanasaBank;
SHOW DATABASES;

USE SanasaBank;

CREATE TABLE IF NOT EXISTS UserDetails(
    name VARCHAR(50),
    telephone VARCHAR(12),
    bankPosition VARCHAR(60),
    userName VARCHAR(10),
    password VARCHAR(12),
    CONSTRAINT PRIMARY KEY (userName)
);

DESC UserDetails;
DESC LoginDetails;

CREATE TABLE IF NOT EXISTS LoginDetails(
    userName VARCHAR(10),
    password VARCHAR(10),
    CONSTRAINT FOREIGN KEY (userName) REFERENCES UserDetails(userName) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS BankDetails(
    bankName VARCHAR(25),
    branchName VARCHAR(15),
    branch VARCHAR(20),
    bankRegNumber VARCHAR(15),
    systemDepDate DATE
);

DESC BankDetails;

CREATE TABLE IF NOT EXISTS Customer(
    accountNumber VARCHAR(14),
    customerName VARCHAR(70),
    customerAge INT,
    sex VARCHAR(7),
    accountType VARCHAR(20),
    customerAddress VARCHAR(50),
    telephoneNumber VARCHAR(12),
    customerBirthday DATE,
    joinedDate DATE,
    customerNIC VARCHAR(15),
    customerEmail VARCHAR(30),
    rationLoan VARCHAR(10),
    loanByDeposit VARCHAR(10),
    instantLoan VARCHAR(10),
    CONSTRAINT PRIMARY KEY (accountNumber)
);

DESCRIBE Customer;

CREATE TABLE IF NOT EXISTS DepositTransactions(
    depTransactionID VARCHAR(8),
    transactionDate DATE,
    transactionTime TIME,
    accountID VARCHAR(10),
    amount DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (depTransactionID),
    CONSTRAINT FOREIGN KEY (accountID) REFERENCES Customer(accountID)
);

DESCRIBE DepositTransactions;

CREATE TABLE IF NOT EXISTS WithdrawTransactions(
    withdrawTransactionID VARCHAR(8),
    transactionDate DATE,
    transactionTime TIME,
    accountID VARCHAR(10),
    amount DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (withdrawTransactionID),
    CONSTRAINT FOREIGN KEY (accountID) REFERENCES Customer(accountID)
);
DESCRIBE WithdrawTransactions;

CREATE TABLE IF NOT EXISTS MoneyJournal(
    mjTransactionID VARCHAR(8),
    transactionType VARCHAR(10),
    transactionTime TIME,
    transactionDate DATE,
    balance DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (mjTransactionID)
);

CREATE TABLE IF NOT EXISTS Expenditures(
    receiptNumber VARCHAR(8),
    description VARCHAR(15),
    transactionTime TIME,
    transactionDate DATE,
    amount DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (receiptNumber)
);

CREATE TABLE IF NOT EXISTS Investments(
    investmentTransactionID VARCHAR(8),
    description VARCHAR(15),
    transactionDate DATE,
    investmentClosedStatus BOOLEAN,
    amount DECIMAL(8,2),
    expiryDate DATE,
    income DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (investmentTransactionID)
);

CREATE TABLE IF NOT EXISTS LoanDetails(
    loanID VARCHAR(4),
    
);

INSERT INTO BankDetails VALUES ('Sanasa Bank','Galthude Sanasa','Galthude','S-G1458','2020-10-26');
    INSERT INTO UserDetails VALUES ('Default User','0702053777','Manager','user','1234');