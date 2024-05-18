ALTER TABLE restaurantes ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1;
UPDATE restaurantes SET ativo = 1 WHERE true;