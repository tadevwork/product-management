CREATE TABLE IF NOT EXISTS products
(
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    handle       VARCHAR(255),
    vendor       VARCHAR(255),
    product_type VARCHAR(100),
    images       JSONB
);

CREATE TABLE IF NOT EXISTS variants
(
    id                INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id        INTEGER NOT NULL REFERENCES products (id),
    title             VARCHAR(255),
    option1           VARCHAR(100),
    option2           VARCHAR(100),
    option3           VARCHAR(100),
    sku               VARCHAR(100),
    requires_shipping BOOLEAN,
    taxable           BOOLEAN,
    available         BOOLEAN,
    price             NUMERIC(10, 2),
    grams             INTEGER,
    featured_image    JSONB
);