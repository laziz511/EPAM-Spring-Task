INSERT INTO tags (name)
SELECT 'Tag ' || generate_series(1, 1000) ::text;

INSERT INTO users (username, email, password)
SELECT 'user' || generate_series(1, 1000)::text,
       'user_' || generate_series(1, 1000)::text || '@gmail.com',
       'password' || generate_series(1, 1000) ::text;

INSERT INTO gift_certificates (name, description, price, duration, created_date, last_updated_date)
SELECT 'Gift Certificate ' || generate_series(1, 10000)::text,
       'Description ' || generate_series(1, 10000)::text,
       random() * 100.0,
       floor(random() * 30 + 1)::int,
       current_date - (random() * 365)::integer,
       current_date - (random() * 365) ::integer;


INSERT INTO gift_certificate_tag (gift_id, tag_id)
SELECT (random() * 1000 + 1)::bigint, generate_series(1, 1000) ::bigint;


INSERT INTO orders (ordered_time, user_id, gift_certificate_id, price)
SELECT current_date - (random() * 365)::integer,
       (random() * 100 + 1)::bigint,
       (random() * 100 + 1)::bigint,
       (SELECT gc.price FROM gift_certificates gc WHERE gc.id = (random() * 100 + 1)::bigint LIMIT 1);



