INSERT INTO app_user (id, email, password, role) VALUES ('b0f7d62e-e3c4-41ae-89b0-369b27c735e2', 'test-user@test.com', '$2a$05$H2wTQJltpga/sJ2DcYq0FOmbm5YZIYMNdiApubO5PvjdQiq.z8ZPW', 'ROLE_REGULAR_USER')
INSERT INTO account (id, user_id, money) VALUES ('69a51c62-d6b3-4afd-9665-ba0a131531a7', 'b0f7d62e-e3c4-41ae-89b0-369b27c735e2', 10000)
INSERT INTO asset (account_id, amount, cryptocurrency) VALUES ('69a51c62-d6b3-4afd-9665-ba0a131531a7', 2.5, 'BTC')
INSERT INTO asset (account_id, amount, cryptocurrency) VALUES ('69a51c62-d6b3-4afd-9665-ba0a131531a7', 11.22, 'ETH')