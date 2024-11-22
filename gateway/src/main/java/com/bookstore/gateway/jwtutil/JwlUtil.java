package com.bookstore.gateway.jwtutil;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwlUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwlUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getAuthorizationHeader(ServerHttpRequest request){
        String header = request.getHeaders().toString();

        logger.info("JWT Util: header: {}", header);
        List<String> authorizationHeader = request.getHeaders().getOrEmpty("Authorization");
        logger.info("JWT Util: Authorization: {}", authorizationHeader);
        if(authorizationHeader.isEmpty()){
            return null;
        }

        logger.info("JWT Util: AuthorizationHeader get(0): "+ authorizationHeader.get(0));
        return authorizationHeader.get(0);
    }

    public String extractTokenFromHeader(String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            return authorizationHeader.substring(7);
        }
        return null;

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Mono<Void> onForbidden(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return response.setComplete();
    }

    public Mono<Void> onUnauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
