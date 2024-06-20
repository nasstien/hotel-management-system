CREATE TABLE hotels (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    address VARCHAR NOT NULL,
    contact_num VARCHAR(20) NOT NULL UNIQUE,
    created_at DATE NOT NULL,
    updated_at DATE
);

CREATE TABLE guests (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR NOT NULL,
    phone_num VARCHAR(20) NOT NULL,
    passport_num VARCHAR NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT g_unique_hotel_and_email UNIQUE (hotel_id, email),
    CONSTRAINT g_unique_hotel_and_phone_num UNIQUE (hotel_id, phone_num),
    CONSTRAINT g_unique_hotel_and_passport_num UNIQUE (hotel_id, passport_num)
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR NOT NULL,
    phone_num VARCHAR(20) NOT NULL,
    passport_num VARCHAR NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('admin', 'employee')),
    user_position VARCHAR NOT NULL,
    salary NUMERIC,
    password VARCHAR NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT u_unique_hotel_and_email UNIQUE (hotel_id, email),
    CONSTRAINT u_unique_hotel_and_phone_num UNIQUE (hotel_id, phone_num),
    CONSTRAINT u_unique_hotel_and_passport_num UNIQUE (hotel_id, passport_num)
);

CREATE TABLE room_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR NOT NULL,
    price_per_night NUMERIC NOT NULL,
    capacity INTEGER NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT rt_unique_hotel_and_name UNIQUE (hotel_id, name)
);

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    occupied BOOLEAN NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    type_id INTEGER NOT NULL REFERENCES room_types(id) ON DELETE CASCADE
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    total_sum NUMERIC NOT NULL,
    room_charges NUMERIC NOT NULL,
    service_charges NUMERIC NOT NULL,
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('card', 'cash')),
    paid BOOLEAN NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    guest_id INTEGER NOT NULL REFERENCES guests(id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    room_id INTEGER NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    guest_id INTEGER NOT NULL REFERENCES guests(id) ON DELETE CASCADE,
    payment_id INTEGER NOT NULL REFERENCES payments(id) ON DELETE CASCADE
);

CREATE TABLE services (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR NOT NULL,
    category VARCHAR(50) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    price NUMERIC NOT NULL,
    available BOOLEAN NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    CONSTRAINT s_unique_hotel_and_name UNIQUE (hotel_id, name)
);

CREATE TABLE service_orders (
    id SERIAL PRIMARY KEY,
    service_time TIME NOT NULL,
    service_date DATE NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    hotel_id INTEGER NOT NULL REFERENCES hotels(id) ON DELETE CASCADE,
    service_id INTEGER NOT NULL REFERENCES services(id) ON DELETE CASCADE,
    booking_id INTEGER NOT NULL REFERENCES bookings(id) ON DELETE CASCADE,
    payment_id INTEGER NOT NULL REFERENCES payments(id) ON DELETE CASCADE
);