INSERT INTO app_user (id, email, password, role) VALUES ('b0f7d62e-e3c4-41ae-89b0-369b27c735e2', 'test-user@test.com', '$2a$05$ufo6HjX/Fr7JKB0viTLjHONnHMzt1VzifcqfDs5NdMjDFBkdfIWk.', 'ROLE_REGULAR_USER'); --password123
-- INSERT INTO oauth2client (id, auth_method, client_id, grant_type, redirect_uri, scope, secret) VALUES ('e3998a82-c2d0-4b8a-89bd-512ba7aca766', 'client_secret_basic', 'client', 'authorization_code', 'https://springone.io/authorized', 'openid',  '$2a$05$ufo6HjX/Fr7JKB0viTLjHONnHMzt1VzifcqfDs5NdMjDFBkdfIWk.');
INSERT INTO oauth2client (id, auth_method, client_id, grant_type, redirect_uri, scope, secret) VALUES ('688d6e2c-b69f-4a30-83ef-8264b990cd28', 'client_secret_basic', 'gateway-client-id', 'authorization_code', 'http://backend-gateway-client:8088/authorized', 'openid',  '$2a$05$ufo6HjX/Fr7JKB0viTLjHONnHMzt1VzifcqfDs5NdMjDFBkdfIWk.');



