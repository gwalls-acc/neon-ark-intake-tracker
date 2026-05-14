INSERT INTO roles (name) VALUES ('ADMIN'), ('WARDEN');

INSERT INTO users (username, password, full_name, email, phone) VALUES
                                                                    ('zog_the_wise', 'starpath123', 'Zog-Xan-Thul', 'zog@neonark.gov', '+99-ST-001'),
                                                                    ('vax_quantum', 'subspace88', 'Vaxine P’tark', 'vax@neonark.gov', '+99-ST-002'),
                                                                    ('commander_kax', 'nebula9', 'Kax the Unflinching', 'kax@neonark.gov', '+99-ST-003'),
                                                                    ('nova_ray', 'supernova', 'Nova Ray-Sun', 'nova@neonark.gov', '+99-ST-004'),
                                                                    ('glitch_01', 'binary7', 'Unit-7742 (Glitch)', 'glitch@neonark.gov', '+99-ST-005');


-- Zog is the Senior Admin
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

-- Vax is a Warden
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);

-- Kax is both an Admin and a Warden (Safety & Security Lead)
INSERT INTO user_roles (user_id, role_id) VALUES (3, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);

-- Nova and Glitch are Wardens
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 2);