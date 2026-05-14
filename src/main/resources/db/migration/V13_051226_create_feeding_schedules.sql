CREATE TABLE feeding_schedules (
                                   id SERIAL PRIMARY KEY,
                                   creature_id INT NOT NULL REFERENCES creatures(id) ON DELETE CASCADE,
                                   feeding_time TIME NOT NULL -- This stores just HH:MM:SS
);