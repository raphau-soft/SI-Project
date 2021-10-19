package com.example.demo.security.jwt;

import java.util.HashMap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.demo.security.MyUserDetails;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

	private int jwtExpirationMs = 60000000;
    private int refreshExpirationTime = 90000000;
    
    private String jwtSecret = "tempKey";
    
    public String generateJwtToken(Authentication authentication){
        MyUserDetails userPrincipal = (MyUserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userPrincipal.getAuthorities();

        if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String generateRefreshToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpirationTime))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validateJwtToken(String authToken){
        try{
            @SuppressWarnings("unused")
			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }
	
    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public int getRefreshExpirationTime() {
        return refreshExpirationTime;
    }

    public void setRefreshExpirationTime(int refreshExpirationTime) {
        this.refreshExpirationTime = refreshExpirationTime;
    }
    
}




















