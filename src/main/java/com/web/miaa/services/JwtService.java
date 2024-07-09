package com.web.miaa.services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode("y39K8rMXgJTL6uXuLYXw4YTFpczER9j88fXSE7CmsIg"));
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    //Expiration (1 day)
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        // Calculando la fecha de caducidad en 6 meses (180 días)
        long expirationTimeMillis = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 180);
        Date expirationDate = new Date(expirationTimeMillis);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void expireToken(String token) {

        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        claims.setExpiration(new Date());

        String newToken = Jwts.builder()
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getKey() {
        return SECRET_KEY;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    public boolean isTokenValidFromClient(String token, String userNameClient){
        final String userNameToken = getUsernameFromToken(token);
        return ( userNameToken.equals(userNameClient) && isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Claims getClaimsFromToken(String token) {
        return getAllClaims(token);
    }


    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return !getExpiration(token).before(new Date());
    }
}