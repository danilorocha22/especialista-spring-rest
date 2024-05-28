CREATE TABLE `restaurantes_usuarios_responsaveis`
(
    `restaurante_id` BIGINT NOT NULL,
    `usuario_id`     BIGINT NOT NULL,
    PRIMARY KEY (`restaurante_id`, `usuario_id`),
    FOREIGN KEY (`restaurante_id`) REFERENCES `restaurantes` (`id`),
    FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
    CONSTRAINT `uk_restaurante_usuario` UNIQUE (`restaurante_id`, `usuario_id`)
)  engine = InnoDB;