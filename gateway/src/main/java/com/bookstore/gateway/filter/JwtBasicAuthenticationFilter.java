package com.bookstore.gateway.filter;

import com.bookstore.gateway.jwtutil.JwlUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtBasicAuthenticationFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtBasicAuthenticationFilter.class);

    private final JwlUtil jwlUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authorizationHeader = jwlUtil.getAuthorizationHeader(exchange.getRequest());

        if(authorizationHeader == null){
            return jwlUtil.onForbidden(exchange);
        }

        String token = jwlUtil.extractTokenFromHeader(authorizationHeader);
        if(token == null){
            return jwlUtil.onForbidden(exchange);
        }

        try{
            Claims mapOfClaims = jwlUtil.extractAllClaims(token);
            logger.info(mapOfClaims.toString());
            String issuer = mapOfClaims.getIssuer();

        } catch (Exception e){
            return jwlUtil.onError(exchange);
        }

        return chain.filter(exchange);
    }

}
