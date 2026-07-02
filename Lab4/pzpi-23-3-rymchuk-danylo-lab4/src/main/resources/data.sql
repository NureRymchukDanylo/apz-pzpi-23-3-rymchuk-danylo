-- Спочатку створюємо користувача (власника)
INSERT INTO users (username, email, password, role) 
VALUES ('admin', 'admin@hobbyrent.com', 'admin123', 'ROLE_ADMIN');

-- Тепер додаємо спорядження, посилаючись на ID цього користувача (id = 1)
INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Намет Terra Incognita', 'Туризм', 50.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Вудка Shimano FX', 'Риболовля', 35.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Велосипед Giant Talon', 'Спорт', 120.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Фотоапарат Canon EOS', 'Туризм', 200.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Каяк Intex Explorer', 'Туризм', 150.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Сноуборд Burton Custom', 'Спорт', 180.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Палатка 4-місна', 'Туризм', 80.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Спальний мішок', 'Туризм', 40.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('Рюкзак 60л', 'Туризм', 30.0, 'AVAILABLE', 1);

INSERT INTO equipment (title, category, hourly_rate, status, owner_id) 
VALUES ('GPS-трекер Garmin', 'Туризм', 25.0, 'AVAILABLE', 1);