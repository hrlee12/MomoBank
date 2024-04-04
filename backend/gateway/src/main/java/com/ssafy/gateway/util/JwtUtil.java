package com.ssafy.gateway.util;

import com.ssafy.gateway.exception.CustomException;
import com.ssafy.gateway.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;


//    @Value("${jwt.access.expiration_time}")
//    private long accessTokenExptime;
//    @Value("${jwt.refresh.expiration_time}")
//    private long refreshTokenExptime;




//    public String createAccessToken(String memberId) {
//        Claims claims = Jwts.claims();
//        claims.put("memberId", memberId);
//
//        ZonedDateTime now = ZonedDateTime.now();
//        ZonedDateTime tokenValidity = now.plusSeconds(accessTokenExptime);
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(Date.from(now.toInstant()))
//                .setExpiration(Date.from(tokenValidity.toInstant()))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }



//    public String createRefreshToken(String memberId) {
//        Claims claims = Jwts.claims();
//        claims.put("memberId", memberId);
//
//        ZonedDateTime now = ZonedDateTime.now();
//        ZonedDateTime tokenValidity = now.plusSeconds(refreshTokenExptime);
//
//        String refreshToken =  Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(Date.from(now.toInstant()))
//                .setExpiration(Date.from(tokenValidity.toInstant()))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//
//        if (redisUtil.existKey(memberId)){
//            redisUtil.deleteValues(memberId);
//        }
//        redisUtil.setValues(memberId, refreshToken, Duration.ofMillis(refreshTokenExptime));
//
//        return refreshToken;
//    }


    public String getMemberId(String token) {
        return parseClaims(token).get("memberId", String.class);
    }



    public boolean validateAccessToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.JWT_EXCEPTION);
        }
    }


    public Claims parseClaims(String accessToken) {
        System.out.println(accessToken);
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }



//    public String resolveToken(ServerHttpRequest req) {
//        String bearerToken = req.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//
//        return null;
//    }

}
