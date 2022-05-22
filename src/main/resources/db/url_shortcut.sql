CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(30),
    password VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS websites (
    id SERIAL PRIMARY KEY,
    host_name VARCHAR(30),
    user_id INT REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS routes (
    id SERIAL PRIMARY KEY,
    url VARCHAR(1000),
    shortcut_url VARCHAR(10),
    request_counter INT,
    website_id INT REFERENCES websites(id)

);

CREATE OR REPLACE FUNCTION select_route_and_increment_counter_func (shortcutUrl VARCHAR(10))
RETURNS routes AS '
    DECLARE rt routes;
    BEGIN
    UPDATE routes SET request_counter = routes.request_counter + 1 WHERE shortcut_url = $1;
    SELECT * INTO rt FROM routes WHERE shortcut_url = $1;
    RETURN rt;
END;
' LANGUAGE plpgsql;
