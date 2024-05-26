package com.dylanmiska.pokemonAPI.security

import com.dylanmiska.pokemonAPI.services.TrainerService
import com.dylanmiska.pokemonAPI.utilities.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val AUTHORIZATION = "Authorization"

@Component
class JwtRequestFilter(
    private val userDetailsService: TrainerService,
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

            val authorizationHeader = request.getHeader(AUTHORIZATION)

            var username: String? = null
            lateinit var jwt: String

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7)
                username = jwtUtil.extractUsername(jwt)
            }

            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = this.userDetailsService.loadUserByUsername(username)

                if (jwtUtil.validateToken(jwt, userDetails)) {

                    val upat = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    upat.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = upat
                }
            }
            filterChain.doFilter(request, response)

    }
}