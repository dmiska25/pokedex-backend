package com.dylanmiska.pokemonAPI.security

import com.dylanmiska.pokemonAPI.services.TrainerService
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@EnableWebSecurity
class SecurityConfigurer(
    private val myUserDetailsService: TrainerService,
    private val jwtRequestFilter: JwtRequestFilter,
) : WebSecurityConfigurerAdapter() {

    val swaggerWhitelist = listOf (
        "/api/swagger-ui/**",
        "/swagger-config",
        "/api-docs/swagger-config",
        "/v3/api-docs/**",
        "/v2/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/configuration/**",
        "/webjars/**",
        "/api-docs/**",
        "/swagger-ui/index.html"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(myUserDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/pokemon-api/trainer/authenticate").permitAll()
            .antMatchers("/pokemon-api/trainer/register").permitAll()
            .antMatchers("/pokemon-api/pokemon").permitAll()
            .antMatchers("/pokemon-api/pokemon/{id}").permitAll()
            .antMatchers("/pokemon-api/image/{id}").permitAll()
            .antMatchers(*swaggerWhitelist.toTypedArray()).permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer? {
        return object : WebMvcConfigurerAdapter() {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
            }
        }
    }

    @Override
    @Bean
    override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

    companion object {
        @Bean
        fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}