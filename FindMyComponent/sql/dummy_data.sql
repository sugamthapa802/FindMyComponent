INSERT IGNORE INTO roles (id, name, description) VALUES
                                              (1, 'ADMIN',  'Full system access'),
                                              (2, 'SELLER', 'Can manage own products and view own orders'),
                                              (3, 'BUYER',  'Can browse and purchase products');

INSERT IGNORE INTO categories (name, description, is_active) VALUES
                                                          ('CPUs', 'Computer processors', TRUE),
                                                          ('Graphics Cards', 'GPUs for gaming', TRUE),
                                                          ('RAM', 'Memory modules', TRUE),
                                                          ('Floppy Drives', 'Legacy storage (discontinued)', FALSE),
                                                          ('CD/DVD Drives', 'Optical drives (legacy)', FALSE),
                                                          ('Motherboards', 'Mainboards', TRUE),
                                                          ('Power Supplies', 'PSUs', TRUE);

Delete from users;
ALTER TABLE users AUTO_INCREMENT = 1;
-- ADMIN User (role_id = 1)
INSERT IGNORE INTO users (username, email, password_hash, role_id, first_name, last_name, phone, is_active, is_email_verified) VALUES
    ('admin', 'admin@findmycomponent.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Super', 'Admin', '9800000000', TRUE, TRUE),
       ('tech_seller', 'tech@neocomputers.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 2, 'Rajesh', 'Sharma', '9812345678', TRUE, TRUE),
    ('gaming_store', 'gaming@gamehub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 2, 'Sita', 'Gurung', '9823456789', TRUE, TRUE),
    ('pc_parts', 'parts@pchaven.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 2, 'Bikash', 'Thapa', '9834567890', TRUE, TRUE),
    ('seller_inactive', 'old@deleted.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 2, 'Inactive', 'Seller', '9845678901', FALSE, FALSE),
    ('john_doe', 'john@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'John', 'Doe', '9856789012', TRUE, TRUE),
     ('jane_smith', 'jane@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Jane', 'Smith', '9867890123', TRUE, TRUE),
      ('ram_kc', 'ram@hotmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Ram', 'KC', '9878901234', TRUE, FALSE),
     ('shyam_pOKhrel', 'shyam@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Shyam', 'Pokhrel', '9889012345', TRUE, TRUE),
      ('gita_dahal', 'gita@yahoo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Gita', 'Dahal', '9890123456', TRUE, TRUE),
       ('buyer_inactive', 'inactive@buyer.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrF6KkYKxZ5cuPG/pZOZqGqXZ6nSdq', 3, 'Inactive', 'Buyer', '9801234567', FALSE, FALSE);                                                                                                               (2, 'test4',  'test4@gmail.com',  '$2b$12$I529O.HS4Xlg5jqATnhq9esOxUS4ccUmYYDjV/z3GnfkJ5qfZlQ8G', 'David',  'Brown',    '9800000004', TRUE,  TRUE),
