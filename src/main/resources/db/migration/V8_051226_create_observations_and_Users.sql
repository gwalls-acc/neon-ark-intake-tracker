CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       full_name VARCHAR(100) NOT NULL
);

CREATE TABLE observations (
                              id SERIAL PRIMARY KEY,
                              creature_id INT NOT NULL REFERENCES creatures(id),
                              user_id INT NOT NULL REFERENCES users(id),
                              content TEXT NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_creature FOREIGN KEY(creature_id) REFERENCES creatures(id) ON DELETE CASCADE,
                              CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
);