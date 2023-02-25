SELECT * FROM transaction 
JOIN account ON transaction.trs_from_account=account.acc_number
JOIN customer ON account.acc_owner=customer.cust_id
WHERE CONCAT(customer.cust_firstname, " ", customer.cust_lastname)="John Michael"
ORDER BY account.acc_number AND transaction.trs_date;