CREATE TABLE IF NOT EXISTS bank (
    id          SERIAL PRIMARY KEY,
    code        VARCHAR(3) NOT NULL,
    name        VARCHAR(200) NOT NULL,
    active      BOOLEAN DEFAULT TRUE
);