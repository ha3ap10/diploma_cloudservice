package ru.netology.diploma_cloudservice.configuration.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import ru.netology.diploma_cloudservice.logger.Logger;
import ru.netology.diploma_cloudservice.model.User;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String jwtSecret = "secret";

    private final Logger logger = Logger.getInstance();

    public JwtTokenUtil() {
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 *1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - " + ex.getMessage());
        }
        return false;
    }

}
