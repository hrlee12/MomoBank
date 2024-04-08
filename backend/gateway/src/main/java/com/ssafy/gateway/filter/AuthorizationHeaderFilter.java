package com.ssafy.gateway.filter;

import com.ssafy.gateway.exception.CustomException;
//import com.ssafy.gateway.exception.ErrorCode;
import com.ssafy.gateway.exception.ErrorCode;
import com.ssafy.gateway.util.JwtUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Getter
    @Setter
    public static class Config {
    // application.yml 파일에서 지정한 filter의 Argument값을 받는 부분
    }
    @Override
    public OrderedGatewayFilter apply(Config config) {
        return new OrderedGatewayFilter((exchange, chain) -> {

            String token = null;
            log.info("Hello");

            try {
                // 헤더의 토큰 파싱 (Bearer 제거)
                token = exchange.getRequest().getHeaders().get("Authorization").get(0).substring(7);
            } catch(NullPointerException e) {
                throw new CustomException(ErrorCode.NO_JWT_TOKEN);
            } catch(IndexOutOfBoundsException e) {
                throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
            }


            String memberId = null;
            if (token != null && jwtUtil.validateAccessToken(token)) {
                memberId = jwtUtil.getMemberId(token);
            }

            addAuthorizationHeaders(exchange.getRequest(), memberId);

            return chain.filter(exchange);
        }, Ordered.HIGHEST_PRECEDENCE) ;
    }


    private void addAuthorizationHeaders(ServerHttpRequest request, String memberId) {
        request.mutate()
                .header("X-Authorization-Id", memberId)
                .build();
    }


//    @Bean
//    public ErrorWebExceptionHandler tokenValidation() {
//        return new JwtTokenExceptionHandler();
//    }


//    // 실제 토큰이 null, 만료 등 예외 상황에 따른 예외처리
//    public class JwtTokenExceptionHandler implements ErrorWebExceptionHandler {
//        private String getErrorCode(int errorCode) {
//            return "{\\"errorCode\\":" + errorCode + "}";
//        }
//
//        @Override
//        public Mono<Void> handle (
//                ServerWebExchange exchange, Throwable ex) {
//            int errorCode = 500;
//            if (ex.getClass() == NullPointerException.class) {
//                errorCode = 100;
//            } else if (ex.getClass() == ExpiredJwtException.class) {
//                errorCode = 200;
//            }
//
//            byte[] bytes = getErrorCode(errorCode).getBytes(StandardCharsets.UTF_8);
//            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//            return exchange.getResponse().writeWith(Flux.just(buffer));
//        }
//
//    }
}
