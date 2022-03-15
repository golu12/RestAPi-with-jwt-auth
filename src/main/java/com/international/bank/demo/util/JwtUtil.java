package com.international.bank.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";

    /**
     * Method used for extractUsername by calling extractClaim method when we supply token.
     * @param token
     * @return
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Method used for extractExpiration by calling extractClaim method when we supply token.
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Method used for extract claims  by we supply token which will return all resolve all claim details of token.
     * @param token
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method used for extract claims  by we supply token.
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Below method is very important method which accept userDetails and call createToken.
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Below method responsible for create Token by supplying some parameter.
     * @param claims this contains some info for the token which we will use later while claim the username.
     * @param subject which is a value that identifies the principal user. This value can be a user ID or userName for which this JWT was generated,
     * @return
     */
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Method used for checking whether the token is valid by we supply token and userdetails.
     * @param token
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
