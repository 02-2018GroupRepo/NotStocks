CREATE TABLE stocks(
	id INT NOT NULL,
	ip VARCHAR(255) NOT NULL,
	name VARCHAR(255) NOT NULL,
	cash DECIMAL(20, 2) NOT NULL,
	inventory_value DECIMAL(20, 2),
	total_value DECIMAL(20, 2),
	stock_price DECIMAL(20, 2) NOT NULL
);