-- 상품 테이블 생성
CREATE TABLE products (
    product_code VARCHAR(255) PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    reg_id VARCHAR(255) NOT NULL,
    reg_date DATE NOT NULL,
    upd_id VARCHAR(255),
    upd_date DATE
);

-- 상품 옵션 테이블 생성
CREATE TABLE product_options (
    product_option_no BIGINT NOT NULL AUTO_INCREMENT,
    product_code VARCHAR(255) NOT NULL,
    size VARCHAR(255) NOT NULL,
    color VARCHAR(255),
    stock INT NOT NULL,
    PRIMARY KEY (product_option_no),
    FOREIGN KEY (product_code) REFERENCES products(product_code)
);

CREATE TABLE orders (
    order_no BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    recipient VARCHAR(100) NOT NULL,
    shipping_cost INT NOT NULL,
    total_amount INT NOT NULL
);

CREATE TABLE order_products (
    order_product_no BIGINT NOT NULL AUTO_INCREMENT,
    order_no BIGINT NOT NULL,
    product_option_no BIGINT NOT NULL,
    order_status char(3) NOT NULL,
    price INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_product_no),
    FOREIGN KEY (order_no) REFERENCES orders(order_no),
    FOREIGN KEY (product_option_no) REFERENCES product_options(product_option_no)
);

-- 상품 데이터
INSERT INTO products (product_code, product_name, price, reg_id, reg_date) VALUES ('111D2PD511_BK', 'Man stretch denim pants', 258000, 'employee1', now());
INSERT INTO products (product_code, product_name, price, reg_id, reg_date) VALUES ('111D2PD511_NA', 'Man stretch denim pants', 258000, 'employee1', now());
INSERT INTO products (product_code, product_name, price, reg_id, reg_date) VALUES ('111D2TO008_GN', 'Man T-shirts', 98000, 'employee2', now());
INSERT INTO products (product_code, product_name, price, reg_id, reg_date) VALUES ('121D1PT052_BG', 'Woman nylon stretch boots pants', 178000, 'employee3', now());
INSERT INTO products (product_code, product_name, price, reg_id, reg_date) VALUES ('121A1PC459_GY', 'Woman skirt', 49000, 'employee2', now());

-- 상품 옵션 데이터
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_BK', '78', 'BLACK', 10);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_BK', '80', 'BLACK', 8);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_BK', '82', 'BLACK', 3);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_NA', '78', 'NAVY', 15);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_NA', '80', 'NAVY', 3);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2PD511_NA', '82', 'NAVY', 2);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2TO008_GN', '95', 'GREEN', 15);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2TO008_GN', '100', 'GREEN', 3);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2TO008_GN', '105', 'GREEN', 2);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('111D2TO008_GN', '110', 'GREEN', 7);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('121D1PT052_BG', '64', 'BEIGE', 20);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('121D1PT052_BG', '67', 'BEIGE', 18);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('121A1PC459_GY', '64', 'GRAY', 15);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('121A1PC459_GY', '67', 'GRAY', 3);
INSERT INTO product_options (product_code, size, color, stock) VALUES ('121A1PC459_GY', '70', 'GRAY', 2);

