package com.ddh.board.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
  // private String secretKey = "SecretKe3";
  @Value("${secret-key}")
  private String secretKey;

  // jwt 토큰 발급
  public String create(String email) {
    Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
    String jwt = Jwts.builder()
        .signWith(SignatureAlgorithm.ES256, secretKey)
        .setSubject(email).setIssuedAt((new Date())).setExpiration((expiredDate))
        .compact();

    return jwt;
  }

  // jwt 토큰 검증
  public String validate(String jwt) {
    Claims claims = null;

    try {
      claims = Jwts.parser().setSigningKey(secretKey)
          .parseClaimsJws(jwt).getBody();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }
    return claims.getSubject();
  }

}
