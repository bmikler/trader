package com.trader.authserver.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;
import java.util.UUID;

interface Oauth2ClientRepository extends JpaRepository<Oauth2Client, UUID> {

    Optional<Oauth2Client> findByClientId(String clientId);
}
