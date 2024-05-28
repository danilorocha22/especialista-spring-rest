ALTER TABLE restaurantes ADD COLUMN aberto TINYINT(1) NOT NULL DEFAULT 1;
UPDATE restaurantes SET aberto = 1 WHERE true;