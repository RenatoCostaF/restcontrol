INSERT INTO users (id, name, email, login, password, address, role, created_at, updated_at)
SELECT
    '11111111-1111-1111-1111-111111111111',
    'MasterAdmin',
    'masteradmin@testadmin.com',
    'masteradmin',
    '$2y$10$1sEHo2ZZ/JnIQlszDaM.1u/7Gm24i51RoPY2Ri4Mq8JIDM40L13xe',
    'Rua do Master Admin, 100',
    'DONO_RESTAURANTE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE login = 'masteradmin'
);

INSERT INTO users (id, name, email, login, password, address, role, created_at, updated_at)
SELECT
    '22222222-2222-2222-2222-222222222222',
    'ClienteSeed',
    'clienteseed@testadmin.com',
    'clienteseed',
    '$2y$10$WLYp509IT.neK7NKtFIs5OfbGJdNZtN7bbQBeZQ6Ml6vvNL5Xdxbq',
    'Rua do Cliente Seed, 200',
    'CLIENTE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE login = 'clienteseed'
);
