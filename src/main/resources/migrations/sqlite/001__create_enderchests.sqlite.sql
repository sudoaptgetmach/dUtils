CREATE TABLE IF NOT EXISTS player_enderchests (
                                                  uuid TEXT NOT NULL,
                                                  chest_id INTEGER NOT NULL,
                                                  items TEXT NOT NULL,
                                                  PRIMARY KEY (uuid, chest_id)
);