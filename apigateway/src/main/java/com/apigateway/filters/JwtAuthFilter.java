package com.apigateway.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class JwtAuthFilter  implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

       String token= exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
       if(token==null||!token.startsWith("Bearer ")){
           exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
           return exchange.getResponse().setComplete();
       }
        return chain.filter(exchange);
    }
}
