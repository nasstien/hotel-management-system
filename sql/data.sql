INSERT INTO hotels (name, address, contact_num, created_at, updated_at)
VALUES
    ('5 Stars', '123 Sunny Street, Sunnytown', '123-456-7890', '2024-01-10', '2024-01-10'),
    ('Luxury Stay', '456 Ocean Drive, Beachtown', '987-654-3210', '2024-01-15', '2024-01-15');

-- User Password: password
INSERT INTO users (hotel_id, first_name, last_name, email, phone_num, passport_num, role, user_position, salary, password, created_at, updated_at)
VALUES
    (1, 'Alice', 'Johnson', 'alice.johnson@example.com', '111-222-3333', 'A1234567', 'admin', 'Manager', 60000, '5f4dcc3b5aa765d61d8327deb882cf99', '2024-05-20', '2024-05-20'),
    (1, 'Bob', 'Smith', 'bob.smith@example.com', '222-333-4444', 'B2345678', 'employee', 'Receptionist', 35000, '5f4dcc3b5aa765d61d8327deb882cf99', '2024-05-21', '2024-05-21'),
    (1, 'Charlie', 'Brown', 'charlie.brown@example.com', '333-444-5555', 'C3456789', 'employee', 'Cleaner', 28000, '5f4dcc3b5aa765d61d8327deb882cf99', '2024-05-22', '2024-05-22'),
    (2, 'David', 'Wilson', 'david.wilson@example.com', '444-555-6666', 'D4567890', 'admin', 'Assistant Manager', 55000, '5f4dcc3b5aa765d61d8327deb882cf99', '2024-05-23', '2024-05-23'),
    (2, 'Eve', 'Davis', 'eve.davis@example.com', '555-666-7777', 'E5678901', 'employee', 'Chef', 40000, '5f4dcc3b5aa765d61d8327deb882cf99', '2024-05-24', '2024-05-24');

INSERT INTO room_types (hotel_id, name, description, price_per_night, capacity, created_at, updated_at)
VALUES
    (1, 'Standard Room', 'A basic room with essential amenities.', 100.00, 2, '2024-04-10', '2024-04-10'),
    (1, 'Deluxe Room', 'A spacious room with additional features.', 200.00, 4, '2024-04-11', '2024-04-11'),
    (1, 'Suite', 'A luxurious suite with premium facilities.', 400.00, 6, '2024-04-12', '2024-04-12'),
    (1, 'Executive Room', 'A comfortable room with executive amenities.', 150.00, 3, '2024-04-13', '2024-04-13'),
    (2, 'Family Room', 'A large room suitable for families.', 250.00, 5, '2024-04-14', '2024-04-14'),
    (2, 'Presidential Suite', 'An extravagant suite with top-notch facilities.', 1000.00, 8, '2024-04-15', '2024-04-15'),
    (2, 'Penthouse', 'A top-floor suite with exclusive features.', 800.00, 4, '2024-04-16', '2024-04-16'),
    (2, 'Single Room', 'A room designed for single occupancy.', 75.00, 1, '2024-04-17', '2024-04-17');

INSERT INTO rooms (hotel_id, type_id, occupied, created_at, updated_at)
VALUES
    (1, 1, TRUE, '2024-06-01', '2024-06-01'),
    (1, 2, TRUE, '2024-06-02', '2024-06-02'),
    (1, 3, TRUE, '2024-06-03', '2024-06-03'),
    (1, 4, TRUE, '2024-06-04', '2024-06-04'),
    (1, 1, TRUE, '2024-06-05', '2024-06-05'),
    (1, 2, FALSE, '2024-06-06', '2024-06-06'),
    (1, 3, FALSE, '2024-06-07', '2024-06-07'),
    (1, 4, FALSE, '2024-06-08', '2024-06-08'),
    (1, 1, FALSE, '2024-06-09', '2024-06-09'),
    (1, 2, FALSE, '2024-06-10', '2024-06-10'),
    (2, 5, TRUE, '2024-06-11', '2024-06-11'),
    (2, 6, TRUE, '2024-06-12', '2024-06-12'),
    (2, 7, TRUE, '2024-06-13', '2024-06-13'),
    (2, 8, TRUE, '2024-06-14', '2024-06-14'),
    (2, 5, TRUE, '2024-06-15', '2024-06-15'),
    (2, 6, FALSE, '2024-06-16', '2024-06-16'),
    (2, 7, FALSE, '2024-06-17', '2024-06-17'),
    (2, 8, FALSE, '2024-06-18', '2024-06-18'),
    (2, 5, FALSE, '2024-06-19', '2024-06-19'),
    (2, 6, FALSE, '2024-06-20', '2024-06-20');

INSERT INTO services (hotel_id, name, description, category, start_time, end_time, price, available, created_at, updated_at)
VALUES
    (1, 'Room Service', '24/7 room service with a wide selection of meals.', 'Dining', '00:00', '23:59', 20.00, TRUE, '2024-04-01', '2024-04-01'),
    (1, 'Laundry', 'Same-day laundry service for all guests.', 'Housekeeping', '08:00', '20:00', 10.00, TRUE, '2024-04-01', '2024-04-01'),
    (1, 'Spa', 'Full-service spa with massages and treatments.', 'Wellness', '09:00', '21:00', 100.00, TRUE, '2024-04-01', '2024-04-01'),
    (1, 'Fitness Center', 'Fully equipped gym with personal trainers available.', 'Fitness', '06:00', '22:00', 0.00, TRUE, '2024-04-01', '2024-04-01'),
    (1, 'Airport Shuttle', 'Complimentary shuttle service to and from the airport.', 'Transportation', '05:00', '23:00', 0.00, TRUE, '2024-04-01', '2024-04-01'),
    (2, 'Valet Parking', '24/7 valet parking service.', 'Parking', '00:00', '23:59', 25.00, TRUE, '2024-04-01', '2024-04-01'),
    (2, 'Business Center', 'Business center with computers, printers, and internet.', 'Business', '07:00', '22:00', 0.00, TRUE, '2024-04-01', '2024-04-01'),
    (2, 'Concierge', 'Concierge services for reservations and recommendations.', 'Guest Services', '07:00', '23:00', 0.00, TRUE, '2024-04-01', '2024-04-01'),
    (2, 'Babysitting', 'Babysitting services for guests with children.', 'Family', '08:00', '20:00', 15.00, TRUE, '2024-04-01', '2024-04-01'),
    (2, 'Wi-Fi', 'High-speed wireless internet access throughout the hotel.', 'Connectivity', '00:00', '23:59', 0.00, TRUE, '2024-04-01', '2024-04-01');

INSERT INTO guests (hotel_id, first_name, last_name, email, phone_num, passport_num, check_in_date, check_out_date, created_at, updated_at)
VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '123-456-7890', 'A12345678', '2024-06-01', '2024-06-10', '2024-06-01', '2024-06-01'),
    (1, 'Jane', 'Smith', 'jane.smith@example.com', '234-567-8901', 'B23456789', '2024-06-02', '2024-06-08', '2024-06-02', '2024-06-02'),
    (1, 'Alice', 'Johnson', 'alice.johnson@example.com', '345-678-9012', 'C34567890', '2024-06-03', '2024-06-08', '2024-06-03', '2024-06-03'),
    (1, 'Bob', 'Brown', 'bob.brown@example.com', '456-789-0123', 'D45678901', '2024-06-04', '2024-06-06', '2024-06-04', '2024-06-04'),
    (1, 'Charlie', 'Davis', 'charlie.davis@example.com', '567-890-1234', 'E56789012', '2024-06-05', '2024-06-10', '2024-06-05', '2024-06-05'),
    (2, 'David', 'Wilson', 'david.wilson@example.com', '678-901-2345', 'F67890123', '2024-06-06', '2024-06-12', '2024-06-06', '2024-06-06'),
    (2, 'Eve', 'Miller', 'eve.miller@example.com', '789-012-3456', 'G78901234', '2024-06-07', '2024-06-09', '2024-06-07', '2024-06-07'),
    (2, 'Frank', 'Garcia', 'frank.garcia@example.com', '890-123-4567', 'H89012345', '2024-06-08', '2024-06-13', '2024-06-08', '2024-06-08'),
    (2, 'Grace', 'Martinez', 'grace.martinez@example.com', '901-234-5678', 'I90123456', '2024-06-09', '2024-06-11', '2024-06-09', '2024-06-09'),
    (2, 'Henry', 'Lopez', 'henry.lopez@example.com', '012-345-6789', 'J01234567', '2024-06-10', '2024-06-15', '2024-06-10', '2024-06-10');

INSERT INTO payments (hotel_id, total_sum, room_charges, service_charges, payment_method, paid, guest_id, created_at, updated_at)
VALUES
    (1, 350.00, 300.00, 50.00, 'card', TRUE, 1, '2024-06-01', '2024-06-01'),
    (1, 450.00, 400.00, 50.00, 'cash', TRUE, 2, '2024-06-02', '2024-06-02'),
    (1, 200.00, 150.00, 50.00, 'card', FALSE, 3, '2024-06-03', '2024-06-03'),
    (1, 550.00, 500.00, 50.00, 'card', TRUE, 4, '2024-06-04', '2024-06-04'),
    (1, 300.00, 250.00, 50.00, 'cash', FALSE, 5, '2024-06-05', '2024-06-05'),
    (2, 350.00, 300.00, 50.00, 'card', TRUE, 6, '2024-06-06', '2024-06-06'),
    (2, 450.00, 400.00, 50.00, 'cash', TRUE, 7, '2024-06-07', '2024-06-07'),
    (2, 200.00, 150.00, 50.00, 'card', FALSE, 8, '2024-06-08', '2024-06-08'),
    (2, 550.00, 500.00, 50.00, 'card', TRUE, 9, '2024-06-09', '2024-06-09'),
    (2, 300.00, 250.00, 50.00, 'cash', FALSE, 10, '2024-06-10', '2024-06-10');

INSERT INTO bookings (hotel_id, room_id, guest_id, payment_id, created_at, updated_at)
VALUES
    (1, 1, 1, 1, '2024-06-01', '2024-06-01'),
    (1, 2, 2, 2, '2024-06-02', '2024-06-02'),
    (1, 3, 3, 3, '2024-06-03', '2024-06-03'),
    (1, 4, 4, 4, '2024-06-04', '2024-06-04'),
    (1, 5, 5, 5, '2024-06-05', '2024-06-05'),
    (2, 11, 6, 6, '2024-06-06', '2024-06-06'),
    (2, 12, 7, 7, '2024-06-07', '2024-06-07'),
    (2, 13, 8, 8, '2024-06-08', '2024-06-08'),
    (2, 14, 9, 9, '2024-06-09', '2024-06-09'),
    (2, 15, 10, 10, '2024-06-10', '2024-06-10');

INSERT INTO service_orders (hotel_id, service_id, booking_id, payment_id, service_date, service_time, created_at, updated_at)
VALUES
    (1, 1, 1, 1, '2024-06-01', '08:00', '2024-06-01', '2024-06-02'),
    (1, 2, 2, 2, '2024-06-02', '09:00', '2024-06-02', '2024-06-02'),
    (1, 3, 3, 3, '2024-06-03', '10:00', '2024-06-03', '2024-06-02'),
    (1, 4, 4, 4, '2024-06-04', '11:00', '2024-06-04', '2024-06-02'),
    (1, 5, 5, 5, '2024-06-05', '12:00', '2024-06-05', '2024-06-02'),
    (2, 6, 6, 6, '2024-06-06', '13:00', '2024-06-06', '2024-06-06'),
    (2, 7, 7, 7, '2024-06-07', '14:00', '2024-06-07', '2024-06-07'),
    (2, 8, 8, 8, '2024-06-08', '15:00', '2024-06-08', '2024-06-08'),
    (2, 9, 9, 9, '2024-06-09', '16:00', '2024-06-09', '2024-06-09'),
    (2, 10, 10, 10, '2024-06-10', '17:00', '2024-06-10', '2024-06-10');

