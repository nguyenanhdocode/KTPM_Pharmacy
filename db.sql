CREATE DATABASE PharmacyDB;

USE PharmacyDB;

-- Bảng User hệ thống
CREATE TABLE Users(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Username NVARCHAR(100) UNIQUE NOT NULL,
    Password NVARCHAR(1000) NOT NULL,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    Gender TINYINT DEFAULT 0, -- 0: Nam, 1: Nữ
    Address NVARCHAR(1000)
);

-- Bảng khách hàng đăng ký tích điểm
CREATE TABLE Members(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50) NOT NULL,
    Phone NCHAR(20) NOT NULL,
    Address NVARCHAR(1000) NOT NULL,
	Point INT DEFAULT 0
);

-- Đơn vị tính
CREATE TABLE Units(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

-- Thuốc
CREATE TABLE Medicines(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    BrandName NVARCHAR(250) NOT NULL, 			-- Tên thuốc
    ChemicalName NVARCHAR(250) NOT  NULL,		-- Tên hoạt chất
    UnitID INT NOT NULL,
    UnitInStock INT DEFAULT 0 NOT NULL,
    UnitPrice FLOAT DEFAULT 0 NOT NULL,
    AllowedUnitInStock INT DEFAULT 0 NOT NULL,	-- Số lượng tồn kho tối đa cho phép
    ProducingCountry NVARCHAR(50),
    
    FOREIGN KEY (UnitID) REFERENCES Units(ID)
); 

-- Bán thuốc
CREATE TABLE Sell_Medicines(
	MedicineID INT NOT NULL,
    UserID INT NOT NULL,
    Date DATETIME DEFAULT NOW(),
    UnitPrice FLOAT NOT NULL,
    Quantity INT NOT NULL,
    
    PRIMARY KEY(MedicineID, UserID, Date),
    FOREIGN KEY (MedicineID) REFERENCES Medicines(ID),
    FOREIGN KEY (UserID) REFERENCES Users(ID)
);