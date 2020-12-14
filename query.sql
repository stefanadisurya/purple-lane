CREATE TABLE Role (
	roleId int primary key auto_increment,
	roleName varchar(255) not null
);

INSERT INTO `Role` (`roleId`, `roleName`) VALUES
(1, 'Admin'),
(2, 'Member'),
(3, 'Manager'),
(4, 'PromotionTeam');

CREATE TABLE Users (
	userId int primary key auto_increment,
	roleId int not null,
	username varchar(255) not null,
	password varchar(255) not null,
    	foreign key (roleId) references role(roleId) on update CASCADE on DELETE CASCADE
);

CREATE TABLE Promo (
	promoId int primary key auto_increment,
	promoCode varchar(255) not null,
	promoDiscount int not null,
	promoNote varchar(255) not null
);

CREATE TABLE Transaction (
	transactionId int primary key auto_increment,
	transactionDate date not null,
	paymentType varchar(255) not null,
	cardNumber varchar(255) not null,
	promoCode varchar(255) not null,
	userId int,
	foreign key (userId) references users(userId) on update cascade on delete cascade
);

CREATE TABLE Products (
	productId int primary key auto_increment,
	productName varchar(255) not null,
	productAuthor varchar(255) not null,
	productPrice int not null,
	productStock int not null
);

CREATE TABLE DetailTransaction (
	transactionId int not null,
	productId int not null,
	productQty int not null,
	foreign key (transactionId) references Transaction(transactionId) on update cascade on delete cascade,
	foreign key (productId) references Products(productId) on update cascade on delete cascade
);


CREATE TABLE Cart (
	userId int not null,
	productId int not null,
	productQty int not null,
	foreign key (userId) references Users(userId) on update cascade on delete cascade,
	foreign key (productId) references Products(productId) on update cascade on delete cascade
);

INSERT INTO `users` (`userId`, `roleId`, `username`, `password`) VALUES
(1, 1, 'admin', 'admin'),
(3, 3, 'manager', 'manager'),
(4, 4, 'promotion', 'promotion'),
(2, 2, 'member', 'member');

INSERT INTO `products` (`productId`, `productName`, `productAuthor`, `productPrice`, `productStock`) VALUES
(1, 'Keyboard', 'Albert', 150000, 50),
(2, 'Mouse', 'Albert', 150000, 100);

INSERT INTO `cart` (`userId`, `productId`, `productQty`) VALUES
(2, 1, 3),
(2, 2, 5);