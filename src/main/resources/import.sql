----2. Populate card table

INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO4L', '1234', 0.00);
INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO5k', '1234', 0.00);

----2. Populate Employee table

INSERT INTO employee (employeeId, cardId_F, employeeName, employeeEmail, employeeMobileNumber) VALUES ('ML80101', 'r7jTG7dqBy5wGO4L', 'Magdalena Leon', 'magdalenaleon@example.com', '0111 111112');
INSERT INTO employee (employeeId, cardId_F, employeeName, employeeEmail, employeeMobileNumber) VALUES ('ML80102', 'r7jTG7dqBy5wGO5k', 'Petrona Martinez', 'petronamartinez@example.com', '0222 222222');

