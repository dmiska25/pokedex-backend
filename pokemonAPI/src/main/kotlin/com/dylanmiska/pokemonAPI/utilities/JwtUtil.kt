package com.dylanmiska.pokemonAPI.utilities


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class JwtUtil {
    fun extractUsername(token: String): String = extractClaim(token, Claims::getSubject)

    fun extractExpiration(token: String):Date = extractClaim(token, Claims::getExpiration)

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = mapOf<String, Object>()
        return createToken(claims, userDetails.username)
    }

    fun createToken(claims: Map<String, Object>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + TEN_HOURS))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY )
            .compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username.equals(userDetails.username)) && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String):Boolean = extractExpiration(token).before(Date())

    // TODO: Should not store secret key here!
    private val SECRET_KEY = "GHZsk6CVaajXsX2QKlu/hP605wTbIPVUnxsW/T+fFrY="
    private val TEN_HOURS = 1000 * 60 * 60 * 10
}