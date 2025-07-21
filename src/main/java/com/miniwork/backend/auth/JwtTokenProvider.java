package com.miniwork.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKeyBase64;

    @Value("${jwt.expiration}")
    private long tokenValidityInMillis;
    // 유효 시간 1시간

    private SecretKey secretKey;

    // 빈 생성 직후에 호출해서 초기화
    // Base64 디코딩된 Key 객체 생성
    @PostConstruct
    protected  void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyBase64));
    }

    // 토큰 생성
    public String createToken(String email, String role){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidityInMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //토큰에서 이메일 추출
    public String getEmailFromToken(String token){
        return parseClaims(token).getSubject();
    }

    //토큰 유효성 검증
    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Request에서 토큰 가져오기 (Authorization header)
    public String resolveToken(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && bearer.startsWith("Bearer")){
            return bearer.substring(7);
        }
        return null;
    }

    //내부용 Claims Parse
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
