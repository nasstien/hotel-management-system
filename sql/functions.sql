-- Get guests who ordered a given service
CREATE OR REPLACE FUNCTION get_guests_by_service_order(h_id INTEGER, s_name VARCHAR(50))
RETURNS TABLE(
	guest_id INTEGER,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR,
	service_id INTEGER,
	service_name VARCHAR(50),
	category VARCHAR(50),
	price NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        g.id AS guest_id,
        g.first_name,
        g.last_name,
        g.email,
        s.id AS service_id,
        s.name AS service_name,
        s.category,
        s.price
    FROM service_orders AS so
    JOIN services AS s ON s.id = so.service_id
    JOIN bookings AS b ON b.id = so.booking_id
    JOIN guests AS g ON g.id = b.guest_id
    WHERE s.name = s_name AND s.hotel_id = h_id
    ORDER BY g.last_name;
END;
$$ LANGUAGE plpgsql;

-- Get users whose email address ends with a given email domain
CREATE OR REPLACE FUNCTION get_users_by_email_domain(h_id INTEGER, email_domain VARCHAR)
RETURNS TABLE(
	id INTEGER,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR,
	user_position VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id,
        u.first_name,
        u.last_name,
        u.email,
        u.user_position
    FROM users AS u
    WHERE u.hotel_id = h_id AND u.email LIKE email_domain;
END;
$$ LANGUAGE plpgsql;

-- Get payments made during the given date interval
CREATE OR REPLACE FUNCTION get_payments_by_date_interval(h_id INTEGER, start_date DATE, end_date DATE)
RETURNS TABLE(
	id INTEGER,
	total_sum NUMERIC,
	room_charges NUMERIC,
	service_charges NUMERIC,
	payment_method VARCHAR(10),
    guest_id INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.id,
        p.total_sum,
        p.room_charges,
        p.service_charges,
        p.payment_method,
        p.guest_id
    FROM payments AS p
    WHERE p.hotel_id = h_id
    AND p.created_at BETWEEN start_date
    AND end_date;
END;
$$ LANGUAGE plpgsql;

-- Get hotel's total income for the last week
CREATE OR REPLACE FUNCTION get_hotel_week_income(h_id INTEGER)
RETURNS TABLE(
	week_income NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT SUM(total_sum) AS week_income
    FROM payments
    WHERE hotel_id = h_id
    AND created_at BETWEEN CURRENT_DATE - INTERVAL '1 week'
    AND CURRENT_DATE;
END;
$$ LANGUAGE plpgsql;

-- How many guests ordered each service
CREATE OR REPLACE FUNCTION get_guests_per_service(h_id INTEGER)
RETURNS TABLE(
	service_name VARCHAR(50),
	guest_count BIGINT
) AS $$
BEGIN
    RETURN QUERY
    SELECT s.name AS service_name, COUNT(*) AS guest_count
    FROM services s
    JOIN service_orders so ON so.service_id = s.id
    WHERE so.hotel_id = h_id
    GROUP BY s.name;
END;
$$ LANGUAGE plpgsql;

-- Get employees who earn more than all employees of a given position
CREATE OR REPLACE FUNCTION get_highest_paid_employees_by_position(h_id INTEGER, employee_position VARCHAR)
RETURNS TABLE(
	id INTEGER,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR,
	phone_num VARCHAR(20),
	passport_num VARCHAR,
	role VARCHAR(20),
    user_position VARCHAR,
	salary NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.id,
        u.first_name,
        u.last_name,
        u.email,
        u.phone_num,
        u.passport_num,
        u.role,
        u.user_position,
        u.salary
    FROM users AS u
    WHERE u.hotel_id = h_id
    AND u.salary > ALL (
        SELECT us.salary
        FROM users AS us
        WHERE us.user_position = employee_position
    );
END;
$$ LANGUAGE plpgsql;

-- Get highest price per service for each service category
CREATE OR REPLACE FUNCTION get_max_price_per_service_category(h_id INTEGER)
RETURNS TABLE(
	category VARCHAR(50),
	max_price NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT s1.category, s1.price AS max_price
    FROM services AS s1
    WHERE s1.hotel_id = h_id
    AND s1.price = (
        SELECT MAX(s2.price)
        FROM services AS s2
        WHERE s1.category = s2.category
    )
    ORDER BY max_price;
END;
$$ LANGUAGE plpgsql;

-- Get services that haven't been ordered in a given date interval (week, month etc.)
CREATE OR REPLACE FUNCTION get_unordered_services_by_date_interval(h_id INTEGER, interval_val INTERVAL)
RETURNS TABLE(
	id INTEGER,
	name VARCHAR(50),
	description VARCHAR,
	category VARCHAR(50),
	start_time TIME,
	end_time TIME,
	price NUMERIC
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        s.id,
        s.name,
        s.description,
        s.category,
        s.start_time,
        s.end_time,
        s.price
    FROM services AS s
    WHERE s.id IN (
        SELECT service_id
        FROM service_orders AS so
        WHERE so.hotel_id = h_id
        AND so.created_at NOT BETWEEN CURRENT_DATE - interval_val
        AND CURRENT_DATE
        OR so.service_date IS NULL
    );
END;
$$ LANGUAGE plpgsql;

-- Get guests with comments "Ordered the maximum number of services", "Has ordered services", "Did not order any service"
CREATE OR REPLACE FUNCTION get_guests_with_max_orders(h_id INTEGER)
RETURNS TABLE(
    booking_id INTEGER,
    guest_id INTEGER,
    comment TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT b.id AS booking_id, b.guest_id, 'Ordered the maximum number of services' AS comment
    FROM bookings AS b
    JOIN (
        SELECT so.booking_id
        FROM service_orders AS so
        WHERE so.booking_id IN (
            SELECT subquery.b_id
            FROM (
                SELECT so1.booking_id AS b_id, COUNT(*) AS service_count
                FROM service_orders AS so1
                WHERE so1.hotel_id = h_id
                GROUP BY so1.booking_id
            ) AS subquery
            WHERE subquery.service_count = (
                SELECT MAX(inner_subquery.service_count)
                FROM (
                    SELECT COUNT(*) AS service_count
                    FROM service_orders AS so2
                    WHERE so2.hotel_id = h_id
                    GROUP BY so2.booking_id
                ) AS inner_subquery
            )
        )
        GROUP BY so.booking_id
    ) t ON t.booking_id = b.id
    JOIN guests g ON g.id = b.guest_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_guests_without_orders(h_id INTEGER)
RETURNS TABLE(
	booking_id INTEGER,
	guest_id INTEGER,
	comment TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT b.id AS booking_id, b.guest_id, 'Did not order any service' AS comment
    FROM bookings AS b
    LEFT JOIN service_orders AS so ON so.booking_id = b.id
    WHERE b.hotel_id = h_id AND so.service_id IS NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_guests_with_orders(h_id INTEGER)
RETURNS TABLE(
	booking_id INTEGER,
	guest_id INTEGER,
	comment TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT b.id AS booking_id, b.guest_id, 'Has ordered services' AS comment
    FROM bookings AS b
    WHERE b.hotel_id = h_id
    AND b.id NOT IN (
        SELECT t.booking_id
        FROM get_guests_with_max_orders(h_id) AS t
    )
    AND b.id NOT IN (
        SELECT t.booking_id
        FROM get_guests_without_orders(h_id) AS t
    );
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_guests_with_comments(h_id INTEGER)
RETURNS TABLE(
	id INTEGER,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR,
	comment TEXT
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        g.id,
        g.first_name,
        g.last_name,
        g.email,
        t.comment
    FROM guests AS g
    JOIN (
        SELECT * FROM get_guests_with_max_orders(h_id)
        UNION
        SELECT * FROM get_guests_with_orders(h_id)
        UNION
        SELECT * FROM get_guests_without_orders(h_id)
    ) AS t ON t.guest_id = g.id
    ORDER BY g.id;
END;
$$ LANGUAGE plpgsql;

