package com.trader.authserver.client;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class Oauth2ClientService implements RegisteredClientRepository {

    private final Oauth2ClientRepository oauth2ClientRepository;
    private final Oauth2ClientMapper mapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        Oauth2Client client = mapper.map(registeredClient);
        oauth2ClientRepository.save(client);
    }

    @Override
    public RegisteredClient findById(String id) {
        Oauth2Client client = oauth2ClientRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return mapper.map(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Oauth2Client client = oauth2ClientRepository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return mapper.map(client);
    }
}
