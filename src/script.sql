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
DELETE TABLE IF EXISTS DepositTransactions;
CREATE TABLE IF NOT EXISTS DepositTransactions(
    depTransactionID VARCHAR(14),
    transactionDate DATE,
    transactionTime TIME,
    accountNumber VARCHAR(14),
    description VARCHAR(30),
    amount DECIMAL(9,2),
    CONSTRAINT PRIMARY KEY (depTransactionID),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber)
);

CREATE TABLE IF NOT EXISTS SavingsAccount(
    accountNumber VARCHAR(14),
    personalBalance DECIMAL(9,2),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber)
);


DESCRIBE DepositTransactions;

CREATE TABLE IF NOT EXISTS WithdrawTransactions(
    withdrawTransactionID VARCHAR(14),
    transactionDate DATE,
    transactionTime TIME,
    accountNumber VARCHAR(14),
    description VARCHAR(30),
    amount DECIMAL(9,2),
    CONSTRAINT PRIMARY KEY (withdrawTransactionID),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber)
);

DESCRIBE WithdrawTransactions;
DROP TABLE WithdrawTransactions;

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
    loanName VARCHAR(20),
    loanName VARCHAR(10),
    forWhom VARCHAR(15),
    minimumAccountBalance DECIMAL(9,2),
    minimumAccountMaturity INT,
    maximumLoanAmount DECIMAL(9,2),
    interestType VARCHAR(20),
    interest DECIMAL(4,2),
    interestCalPeriod VARCHAR(8),
    maximumNoOfInstallments INT,
    notPaidOption1 VARCHAR(15),
    notPaidOption2 VARCHAR(15),
    CONSTRAINT PRIMARY KEY (loanCode)
);



INSERT INTO BankDetails VALUES ('Sanasa Bank','Galthude Sanasa','Galthude','S-G1458','2020-10-26');
    INSERT INTO UserDetails VALUES ('Default User','0702053777','Manager','user','1234');

    SELECT EXISTS(SELECT * from Customer WHERE accountNumber=58170955210931);



WithdrawObjectModel model = new WithdrawObjectModel(
                    txtSearchWithdraw.getText(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:ss:mm")),
                    Double.parseDouble(txtWithdrawAmount.getText()),
                    txtWithdrawDesc.getText(),
                    new NumberGenerator().getWithdrawalID()
            );
            ObjectPasser.setWithdrawObjectModel(model);
            URL resource = getClass().getResource("../view/WithdrawForm.fxml");
            System.out.println(resource);
            assert resource != null;
            Parent load = FXMLLoader.load(resource);
            mainDashboardForm.getChildren().clear();
            mainDashboardForm.getChildren().add(load);

            sudo mysql -u naashnix -h localhost -p
            58170955210931

            SELECT * FROM WithdrawTransactions;
            SELECT * FROM SavingsAccount;
            SELECT * FROM Customer;

            78378973380
            7c7RRG

            SELECT * FROM WithdrawTransactions WHERE accountNumber=58170955210931 ORDER BY transactionDate DESC,transactionTime DESC LIMIT 4;

INSERT INTO LoanDetails VALUES("Instant Loan" ,"L001" ,"Savings Account" ,10000.0 ,5 ,20000.0,"Reducing BS",3,"Monthly" ,5 ,"From Deposit","Legal Action");
INSERT INTO LoanDetails VALUES("Ration Loan" ,"L002" ,"Savings Account" ,10000 ,12 ,50000.0,"Flat Rate",12,"Yearly" ,12 ,"From Deposit","Legal Action");
INSERT INTO LoanDetails VALUES("Loan By Deposit" ,"L003" ,"Savings Account" ,100.0 ,5 ,80,"Reducing BS",4,"Monthly" ,37,"","Legal Action");

ALTER TABLE 


CREATE TABLE LoanByDeposit(
    dLoanNumber VARCHAR(14),
    accountNumber VARCHAR(15),
    dLoanAmount DECIMAL(9,2),
    dIssuedDate DATE,
    dDailyInstallment DECIMAL(9,2),
    dNumberOfInstallments INT,
    dLoanPaidAmount DECIMAL(9,2),
    loanStatus VARCHAR(10),
    loanNumber VARCHAR(14),
    nextInstallmentDate DATE,
    CONSTRAINT PRIMARY KEY (dLoanNumber),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber)
);


CREATE TABLE LoanByDepositTransactions(
    transactionID VARCHAR(14),
    dLoanNumber VARCHAR(14),
    accountNumber VARCHAR(15),
    transactionTime TIME,
    transactionDate DATE,
    amount DECIMAL(9,2),
    CONSTRAINT PRIMARY KEY (transactionID),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber),
    CONSTRAINT FOREIGN KEY (dLoanNumber) REFERENCES LoanByDeposit(dLoanNumber)
);

DROP TABLE InstantLoan;
DROP TABLE InstantLoanTransactions;

ALTER TABLE RationLoan
MODIFY interest
DECIMAL(4,2);

ALTER TABLE LoanByDeposit
ADD interest DECIMAL(3,2);

ALTER TABLE LoanByDeposit
DROP COLUMN loanNumber;

ALTER TABLE LoanByDeposit
RENAME COLUMN dDailyInstallment TO MonthlyInstallment;

ALTER TABLE Customer
MODIFY instantLoan VARCHAR(15),
MODIFY rationLoan VARCHAR(15),
MODIFY loanByDeposit VARCHAR(15);

DELETE FROM DepositTransactions;
DELETE FROM WithdrawTransactions;
DELETE FROM InstantLoan;
DELETE FROM LoanByDeposit;
DELETE FROM RationLoan;
DELETE FROM LoanDetails;
DELETE FROM Customer;

CREATE TABLE MoneyJournal (
    balanceType VARCHAR(14),
    transactionDate DATE,
    transactionTime TIME,
    balance DECIMAL(10,2),
    CONSTRAINT PRIMARY KEY (balanceType)
);

INSERT INTO MoneyJournal VALUES("Main Balance","2021-09-27","12:50:36",0.0);

CREATE TABLE OnHoldDetails (
    accountNumber VARCHAR(14),
    holdedAmount DECIMAL(9,2),
    CONSTRAINT FOREIGN KEY (accountNumber) REFERENCES Customer(accountNumber)
);

ALTER TABLE RationLoan
ADD COLUMN installmentsToBePaid INT AFTER rNumberOfInstallments;

 DELETE FROM DepositTransactions;
 DELETE FROM WithdrawTransactions;
 DELETE FROM InstantLoan;
 DELETE FROM LoanByDeposit;
 DELETE FROM RationLoan;
 DELETE FROM SavingsAccount;
 DELETE FROM OnHoldDetails;
 DELETE FROM Customer;

 CREATE TABLE IF NOT EXISTS Incomes(
    transactionID VARCHAR(14),
    description VARCHAR(30),
    amount DECIMAL(9,2),r
    CONSTRAINT PRIMARY KEY (transactionID)
 );


