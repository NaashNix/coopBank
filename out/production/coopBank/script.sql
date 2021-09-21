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
    loanID VARCHAR(4),
    
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