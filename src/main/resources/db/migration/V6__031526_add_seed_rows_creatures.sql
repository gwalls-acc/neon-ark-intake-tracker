INSERT INTO CREATURES (name, species, danger_level, condition, notes, habitat_id, created_at)
VALUES
-- Habitat 1 (FOREST — Moss Caverns)
('Nyx', 'Void Fox', 'HIGH', 'QUARANTINED', 'Avoid bright light', 1, NOW()),
('Glimmer', 'Cavern Sprite', 'LOW', 'STABLE', 'Feeds on moss spores', 1, NOW()),
('Thornback', 'Burrowing Bristlebeast', 'MEDIUM', 'STABLE', 'Digs elaborate tunnel webs', 1, NOW()),

-- Habitat 2 (DESERT — Dunespire Mirage Flats)
('Siro', 'Mirage Jackal', 'MEDIUM', 'STABLE', 'Can refract light to appear invisible', 2, NOW()),
('Ashdrift', 'Glass Serpent', 'HIGH', 'CRITICAL', 'Body temperature unstable', 2, NOW()),
('Pebble', 'Sand Hopper', 'LOW', 'STABLE', 'Collects shiny stones', 2, NOW()),

-- Habitat 3 (OCEAN — Sapphire Trench Reaches)
('Luriel', 'Abyssal Siren', 'HIGH', 'QUARANTINED', 'Emits disorienting sonar pulses', 3, NOW()),
('Kelpwing', 'Reef Manta', 'LOW', 'STABLE', 'Glides through kelp forests', 3, NOW()),
('Brineclaw', 'Crustal Titan', 'MEDIUM', 'STABLE', 'Slow but extremely strong', 3, NOW()),

-- Habitat 4 (FOREST — Whisperbark Canopy Realm)
('Whisp', 'Glider Lemurkin', 'LOW', 'STABLE', 'Communicates with soft chirps', 4, NOW()),
('Ravel', 'Canopy Stalker', 'HIGH', 'CRITICAL', 'Aggressive when startled', 4, NOW()),
('Bloomfang', 'Petal Wolf', 'MEDIUM', 'STABLE', 'Floral scent attracts insects', 4, NOW()),

-- Habitat 5 (DESERT — Sunshard Crystal Basin)
('Solin', 'Crystalback Lizard', 'LOW', 'STABLE', 'Reflective scales scatter sunlight', 5, NOW()),
('Raze', 'Sunflare Scorpion', 'HIGH', 'QUARANTINED', 'Venom reacts to UV exposure', 5, NOW()),
('Dustwhirl', 'Grit Wraith', 'MEDIUM', 'STABLE', 'Forms from swirling sand clouds', 5, NOW()),

-- Habitat 6 (OCEAN — Tideworn Coral Labyrinth)
('Coralyn', 'Tide Nymph', 'LOW', 'STABLE', 'Maintains coral structures', 6, NOW()),
('Riptide', 'Current Drake', 'HIGH', 'CRITICAL', 'Unpredictable movement patterns', 6, NOW()),
('Shellspire', 'Barnacle Golem', 'MEDIUM', 'STABLE', 'Slow-moving reef guardian', 6, NOW()),

-- Habitat 7 (FOREST — Emberleaf Grove Sanctuary)
('Emberpaw', 'Ash Wolf', 'MEDIUM', 'STABLE', 'Leaves glowing pawprints', 7, NOW()),
('Flarebloom', 'Ignis Deer', 'LOW', 'STABLE', 'Antlers emit soft heat', 7, NOW()),
('Cindermaw', 'Smolder Bear', 'HIGH', 'QUARANTINED', 'Body temperature dangerously high', 7, NOW()),

-- Habitat 8 (DESERT — Obsidian Wind Barrens)
('Shardhorn', 'Obsidian Ram', 'MEDIUM', 'STABLE', 'Horns sharp enough to cut stone', 8, NOW()),
('Vanta', 'Shadow Viper', 'HIGH', 'CRITICAL', 'Absorbs ambient light', 8, NOW()),
('Dustlet', 'Micro Wisp', 'LOW', 'STABLE', 'Harmless drifting dust organism', 8, NOW()),

-- Habitat 9 (OCEAN — Moonkelp Abyssal Gardens)
('Lunaris', 'Moon Jelly Leviathan', 'HIGH', 'QUARANTINED', 'Bioluminescence attracts predators', 9, NOW()),
('Pearlfin', 'Abyss Guppy', 'LOW', 'STABLE', 'Small but resilient', 9, NOW()),
('Gloomjaw', 'Deepfang Eel', 'MEDIUM', 'STABLE', 'Prefers pitch-black waters', 9, NOW()),

-- Habitat 10 (FOREST — Thornveil Enchanted Thicket)
('Bramble', 'Thornback Hare', 'LOW', 'STABLE', 'Camouflages among thorny roots', 10, NOW()),
('Vexroot', 'Entling Spawn', 'MEDIUM', 'STABLE', 'Slow-moving plant creature', 10, NOW()),
('Nightpetal', 'Shadow Panther', 'HIGH', 'CRITICAL', 'Extremely territorial', 10, NOW());
