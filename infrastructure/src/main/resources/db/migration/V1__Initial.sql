
CREATE TABLE category(
    id BINARY(36) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(9) NOT NULL,
    updated_at DATETIME(9) NOT NULL,
    deleted_at DATETIME(9) NULL
);