package com.trader.authserver.client;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
class Oauth2ClientMapper {

    private final PasswordEncoder passwordEncoder;

    RegisteredClient map(Oauth2Client client) {

        RegisteredClient build = RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getSecret())
                .scope(client.getScope())
//                .redirectUri(client.getRedirectUri())
                .redirectUri("http://backend-gateway-client:8088/login/oauth2/code/gateway")
                .redirectUri("http://backend-gateway-client:8088/authorized")
//                .redirectUri(client.getRedirectUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
                .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofDays(1))
                        .build())
                .build();

        System.err.println(build);
        return build;
    }

    //TODO fix it
    Oauth2Client map(RegisteredClient client) {
        return new Oauth2Client(
                client.getClientId(),
                passwordEncoder.encode(client.getClientSecret()),
                client.getScopes().stream().findFirst().orElse(" "),
                client.getClientAuthenticationMethods().stream().findFirst().orElse(null).toString(),
                client.getAuthorizationGrantTypes().stream().findFirst().orElse(null).toString(),
                client.getRedirectUris().stream().findFirst().orElse("")
        );
    }
}
