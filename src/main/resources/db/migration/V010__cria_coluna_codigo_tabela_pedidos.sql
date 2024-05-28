ALTER TABLE pedidos ADD codigo VARCHAR(36) NOT NULL AFTER id;
UPDATE pedidos SET codigo = uuid() WHERE true;
ALTER TABLE pedidos ADD CONSTRAINT uk_pedido_codigo unique (codigo);