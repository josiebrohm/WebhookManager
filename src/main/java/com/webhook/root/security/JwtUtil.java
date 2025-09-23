package com.webhook.root.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private int jwtExpirationMs;

	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	// generate JWT token
	public String generateToken(UserDetails userDetails) {
		String role = userDetails.getAuthorities().stream()
						.findFirst()
						.map(GrantedAuthority::getAuthority)
						.orElse("ROLE_PUBLISHER");

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(key)
				.compact();
	}

	// get username from jwt
	public String getUsernameFromToken(String token) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	// get role from token
	public SimpleGrantedAuthority getAuthorityFromToken(String token) {
		return new SimpleGrantedAuthority(
						Jwts.parser()
						.verifyWith(key)
						.build()
						.parseSignedClaims(token)
						.getPayload()
						.get("role", String.class)
					);
	}

	// validate jwt
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token);

			return true;
		} catch (SecurityException e) {
			System.err.println("Invalid JWT signature: " + e.getMessage());
		} catch (MalformedJwtException e) {
			System.err.println("Invalid JWT token: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			System.err.println("JWT token is expired: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.err.println("JWT token is unsupported: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.err.println("JWT claims string is empty: " + e.getMessage());
		}
		return false;
	}

}
