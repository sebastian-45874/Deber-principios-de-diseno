CREATE TABLE Customers(CustomerID varchar(5) PRIMARY KEY, CompanyName varchar(100) NOT NULL);
CREATE TABLE Products(ProductID int PRIMARY KEY, ProductName varchar(100) NOT NULL);
CREATE TABLE Orders(OrderID int PRIMARY KEY, CustomerID varchar(5) REFERENCES Customers(CustomerID), OrderDate timestamp);
CREATE TABLE OrderDetails(OrderID int REFERENCES Orders(OrderID), ProductID int REFERENCES Products(ProductID), UnitPrice decimal(19,4) NOT NULL, Quantity smallint NOT NULL, Discount real NOT NULL, PRIMARY KEY(OrderID,ProductID));