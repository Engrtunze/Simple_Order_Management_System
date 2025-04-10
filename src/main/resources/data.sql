-- Insert users
INSERT INTO users (id, name, email, created_at, updated_at) VALUES
(UUID(), 'John Doe', 'john@example.com',NOW(), NOW()),
(UUID(), 'Jane Smith', 'jane@example.com',NOW(), NOW());

-- Insert products
INSERT INTO products (ID, name, description, price, stock_quantity, is_Deleted, created_at, updated_at) VALUES
(UUID(), 'Laptop', 'High-performance laptop', 999.99, 50, FALSE, NOW(), NOW()),
(UUID(), 'Smartphone', 'Latest model smartphone', 699.99, 100, FALSE, NOW(), NOW()),
(UUID(), 'Tablet', '10-inch tablet', 349.99, 75, FALSE, NOW(), NOW()),
(UUID(), 'Headphones', 'Noise-cancelling headphones', 149.99, 200, FALSE, NOW(), NOW());