package com.epam.esm.service.security;

import com.epam.esm.core.dto.AuthenticationResponse;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.AuthException;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.epam.esm.core.constants.ErrorMessageConstants.USER_NOT_FOUND_ERROR_MESSAGE;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:jwt.properties")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.access}")
    private long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.expiration.refresh}")
    private long REFRESH_TOKEN_EXPIRATION;

    private final UserRepository userRepository;

    public AuthenticationResponse generateToken(User user) {
        final String accessToken = generateAccessToken(user);
        final String refreshToken = generateRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        if (Boolean.TRUE.equals(isTokenExpired(refreshToken))) {
            throw new AuthException("Refresh token has expired");
        }

        String username = extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + username, User.class));

        String newAccessToken = generateAccessToken(user);
        String newRefreshToken = generateRefreshToken(user);

        return new AuthenticationResponse(newAccessToken, newRefreshToken);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername(), ACCESS_TOKEN_EXPIRATION);
    }

    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername(), REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Key signKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
