CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    cep VARCHAR(20)
)engine=InnoDB default charset=utf8;

CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    address_id BIGINT,
    CONSTRAINT fk_customer_address FOREIGN KEY (address_id) REFERENCES addresses(id),
    UNIQUE(email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(1024)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(1000),
    category_id BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES products(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    shipping_info_id BIGINT,
    order_time DATETIME NOT NULL,
    status VARCHAR(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS shipping_info (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50),
    tracking_number VARCHAR(255),
    order_id BIGINT,
    estimated_delivery_date DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE orders
ADD CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
ADD CONSTRAINT fk_order_shipping_info FOREIGN KEY (shipping_info_id) REFERENCES shipping_info(id);

ALTER TABLE shipping_info
ADD CONSTRAINT fk_shipping_info_order FOREIGN KEY (order_id) REFERENCES orders(id);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES products(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    payment_time DATETIME NOT NULL,
    order_id BIGINT,
    CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    product_id BIGINT,
    rating INT,
    comment VARCHAR(500),
    review_date DATETIME,
    CONSTRAINT fk_review_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;