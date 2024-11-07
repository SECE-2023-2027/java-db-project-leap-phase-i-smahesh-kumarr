-- create database bankingApp;
-- use bankingApp;

-- create table Bank(bank_id INT PRIMARY KEY AUTO_INCREMENT, 
-- bank_name VARCHAR(100) NOT NULL ,
-- bank_branch varchar(100) NOT NULL);

-- INSERT INTO Bank (bank_name,bank_branch) values ('ABC','Main branch');
-- INSERT INTO Bank (bank_name,bank_branch) values ('XYZ','Main branch');

-- SELECT  * FROM Bank;

-- create table Account (account_id INT PRIMARY KEY UNIQUE AUTO_INCREMENT,customer_id INT NOT NULL,bank_id INT,account_type varchar(50) NOT NULL,balance DECIMAL(15,2) NOT NULL,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY (bank_id) REFERENCES Bank(bank_id));

-- create table SavingsAccount (account_id INT PRIMARY KEY,interest_rate DECIMAL(5,2) NOT NULL,FOREIGN KEY (account_id) REFERENCES Account(account_id));


-- create table CurrentAccount (account_id INT PRIMARY KEY,over_draft DECIMAL(5,2) NOT NULL,FOREIGN KEY (account_id) REFERENCES Account(account_id));


-- ALTER TABLE CurrentAccount MODIFY over_draft decimal(15,2);

-- create table Transaction(
-- 	transaction_id INT PRIMARY KEY auto_increment,
--     account_id INT NOT NULL,
--     transaction_type VARCHAR(50) NOT NULL,
--     amount DECIMAL(15,2) NOT NULL,
--     transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     FOREIGN KEY(account_id) REFERENCES Account(account_id)
-- );

-- create table DepositTransaction(
-- 	transaction_id INT PRIMARY KEY,
--     deposit_method VARCHAR(50) NOT NULL,
--     FOREIGN KEY(transaction_id) REFERENCES Transaction(transaction_id)
-- );

-- create table WithdrawalTransaction(
-- 	transaction_id INT PRIMARY KEY,
--     withdrawal_method VARCHAR(50) NOT NULL,
--     FOREIGN KEY(transaction_id) REFERENCES Transaction(transaction_id)
-- );

create database demo;
use demo;
	create table users (
	username varchar(100),
	password varchar(100),
	userid int 
	);
show tables;

select * from users;



