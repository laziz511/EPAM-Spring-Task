CREATE TABLE gift_certificates
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)   NOT NULL,
    description      TEXT,
    price            DECIMAL(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP      NOT NULL,
    last_update_date TIMESTAMP      NOT NULL
);


CREATE TABLE tags
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE gift_certificate_tags
(
    id                  SERIAL PRIMARY KEY,
    gift_certificate_id BIGINT REFERENCES gift_certificates (id) ON UPDATE CASCADE ON delete cascade,
    tag_id              BIGINT REFERENCES tags (id) ON UPDATE CASCADE ON delete cascade,
    CONSTRAINT gift_cert_tag_unique UNIQUE (gift_certificate_id, tag_id)
);
