--1. Populate Card table

INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO4L', '1234', 0.00);
INSERT INTO card (cardId, cardPassCode, cardBalance) VALUES ('r7jTG7dqBy5wGO5k', '1234', 10.00);

--2. Create Employee table
CREATE TABLE employee (
    employeeId varchar(255),
    cardId varchar(255),
    name varchar(255),
    email varchar(255),
    primary key (employeeId),
    foreign key (cardId) references card(cardId)
);

INSERT INTO employee (employeeId, cardId, name, email) VALUES ('ML80101', 'r7jTG7dqBy5wGO4L', 'Magdalena Leon', 'magdalenaleon@example.com');
INSERT INTO employee (employeeId, cardId, name, email) VALUES ('ML80102', 'r7jTG7dqBy5wGO5k', 'Petrona Martinez', 'petronamartinez@example.com');


