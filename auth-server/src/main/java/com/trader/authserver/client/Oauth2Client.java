package com.trader.authserver.client;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
class Oauth2Client {

    @Id
    private UUID id = UUID.randomUUID();
    private String clientId;
    private String secret;
    private String scope;
    private String authMethod;
    private String grantType;
    private String redirectUri;

    public Oauth2Client(String clientId, String secret, String scope, String authMethod, String grantType, String redirectUri) {
        this.clientId = clientId;
        this.secret = secret;
        this.scope = scope;
        this.authMethod = authMethod;
        this.grantType = grantType;
        this.redirectUri = redirectUri;
    }

}