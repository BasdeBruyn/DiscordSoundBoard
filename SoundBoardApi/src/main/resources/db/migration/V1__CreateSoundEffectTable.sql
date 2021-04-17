DROP TABLE IF EXISTS "sound_effect";
CREATE TABLE IF NOT EXISTS "sound_effect"
(
    "name" VARCHAR(20)  NOT NULL,
    "url"  VARCHAR(50) NOT NULL,
    PRIMARY KEY ("name")
);