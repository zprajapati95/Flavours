CREATE TABLE dish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(8, 2) NOT NULL,
    description TEXT,
    imagePath VARCHAR(255), 
    category VARCHAR(50) 
);


