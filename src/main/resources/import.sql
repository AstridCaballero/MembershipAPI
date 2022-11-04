----1. Populate card table

INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO4L', '1234', 10.00);
INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO5k', '5678', 0.00);

----2. Populate Employee table

INSERT INTO Employee (employeeId, cardId, employeeName, employeeEmail, employeeMobileNumber) VALUES ('ML80101', 'r7jTG7dqBy5wGO4L', 'Magdalena Leon', 'magdalenaleon@example.com', '0111 111112');
INSERT INTO Employee (employeeId, cardId, employeeName, employeeEmail, employeeMobileNumber) VALUES ('ML80102', 'r7jTG7dqBy5wGO5k', 'Petrona Martinez', 'petronamartinez@example.com', '0222 222222');

----3. Populate Product table
INSERT INTO Product (productName, productPrice) VALUES ('Tuna sandwich', 2.00);
INSERT INTO Product (productName, productPrice) VALUES ('Orange juice', 1.00);

----4. Populate OrderEmployee table
INSERT INTO OrderEmployee (orderEmployeeId, cardId, orderTotal) VALUES (default, 'r7jTG7dqBy5wGO4L', 2);

----5. Populate OrderProducts table
INSERT INTO OrderProducts (orderProductsId, orderId, orderProductsPrice, orderProductsQuantity, productId) VALUES (default, 1, 2.0, 1, 1);
