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