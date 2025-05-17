CREATE TABLE owner_table (
                             owner_id SERIAL PRIMARY KEY,
                             owner_name VARCHAR(255) NOT NULL
);

CREATE TABLE category_table (
                                ship_id SERIAL PRIMARY KEY,
                                ship_type VARCHAR(255) NOT NULL,
                                ship_tonnage INTEGER NOT NULL
);

CREATE TABLE ship_table (
                            id SERIAL PRIMARY KEY,
                            ship_name VARCHAR(255) NOT NULL,
                            imo_number VARCHAR(255) NOT NULL UNIQUE,
                            ship_id BIGINT REFERENCES category_table(ship_id),
                            owner_id BIGINT REFERENCES owner_table(owner_id)
);

CREATE TABLE owner_ships (
                             owner_id BIGINT REFERENCES owner_table(owner_id),
                             ship_id BIGINT REFERENCES category_table(ship_id),
                             PRIMARY KEY (owner_id, ship_id)
);
