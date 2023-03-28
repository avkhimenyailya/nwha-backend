package io.grayproject.nwha.api.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Ilya Avkhimenya
 */
@Slf4j
public class JwtTokenUtils {

    public static String generateAccessToken(String username) {
        long jwtTokenExpirationMs = 300000L * 3; // 15 minutes
        return generateJwtTokenByUsername(username, jwtTokenExpirationMs);
    }

    public static String generateRefreshToken(String username) {
        long jwtTokenExpirationMs = 1209600000L; // 2 week
        return generateJwtTokenByUsername(username, jwtTokenExpirationMs);
    }

    public static boolean verifyJwtToken(String jwtToken) {
        try {
            getJwtParser().parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private static JwtParser getJwtParser() throws JwtException {
        return Jwts.parser().setSigningKey(System.getenv("NWHA_JWT_SECRET"));
    }

    private static String generateJwtTokenByUsername(String username, Long duration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(SignatureAlgorithm.HS256, System.getenv("NWHA_JWT_SECRET"))
                .compact();
    }

    public static String getUsernameFromToken(String jwtToken) throws JwtException {
        return getJwtParser().parseClaimsJws(jwtToken).getBody().getSubject();
    }
}