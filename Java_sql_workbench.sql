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

-- create database demo;
-- use demo;
-- 	create table users (
-- 	username varchar(100),
-- 	password varchar(100),
-- 	userid int 
-- 	);
-- show tables;

-- select * from users;
use bankingapp;

DELIMITER //

create procedure transfer_funds(IN from_account INT, IN to_account INT, IN from_amount DECIMAL(15,2))
BEGIN 
	DECLARE from_balance DECIMAL(15,2);
	SELECT balance into from_balance from Account where account_id = from_account;
	IF from_balance >= from_amount THEN
		update account set balance = balance - from_amount where account_id = from_account;
        update account set balance = balance + from_amount where account_id = to_account;
        insert into transaction(account_id,transaction_type,amount) values (from_account, "transfer out", from_amount);
        insert into transaction(account_id,transaction_type,amount) values (to_account, "transfer in", from_amount);
	ELSE
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient balance in from_account';
	END IF;
END //
drop procedure if exists withdraw_procedure;

DELIMITER //
create procedure withdraw(IN cus_account INT, IN amount DECIMAL(15,2))
BEGIN
    DECLARE current_balance DECIMAL(15,2);
    DECLARE account_count INT;
    
    select count(*) into account_count from account where account_id = cus_account;
    IF account_count = 0 THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Account does not exist';
	END IF;
    
    Select balance into current_balance from Account where account_id = cus_account;
    IF current_balance >= amount THEN
		update account set balance = balance - amount where account_id = cus_account;
        insert into transaction(account_id,transaction_type,amount) values (cus_account,"Withdrawal", amount);
	ELSE
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Insufficient balance in from_account";
	END IF;
END //


DELIMITER ;

DELIMITER %
CREATE PROCEDURE deposit_procedure(IN account_id INT, IN amount DECIMAL(15,2))
BEGIN
    DECLARE account_exists INT;
    SELECT COUNT(*) INTO account_exists 
    FROM account 
    WHERE account_id = account_id;
    IF account_exists > 0 THEN
        UPDATE account
        SET balance = balance + amount
        WHERE account_id = account_id;
        INSERT INTO transaction(account_id, transaction_type, amount)
        VALUES (account_id, 'credit', amount);
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Account does not exist';
    END IF;
END %
DELIMITER ;
desc Account;
use bankingapp;
create view BankAccountTable as 
select a.account_id, a.customer_id, b.bank_id, b.bank_name, b.bank_branch, a.account_type, a.balance from Account a join Bank b on a.bank_id = b.bank_id;
