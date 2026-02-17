package com.marcoscondejr.conde_finance_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.marcoscondejr.conde_finance_api.entity.User;
import com.marcoscondejr.conde_finance_api.exception.InvalidTokenException;
import com.marcoscondejr.conde_finance_api.exception.TokenCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("conde-finance-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(this.getExpirationDate())
                    .sign(algorithm);
        } catch (IllegalArgumentException ex) {
            throw new TokenCreationException("Configuração inválida do token (secret).");
        } catch (JWTCreationException ex) {
            throw new TokenCreationException("Erro ao gerar o token.");
        }
    }

    public String validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token ausente.");
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("conde-finance-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException ex) {
            throw new InvalidTokenException("Token expirado.");
        }
            catch (JWTVerificationException ex) {
            throw new InvalidTokenException("Token inválido.");
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime
                .now()
                .plusHours(5)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
