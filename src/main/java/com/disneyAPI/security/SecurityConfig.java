package com.disneyAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    private static final String[] PERMIT_ALL = {"/auth/login","/auth/register","/swagger-ui/**","/api/docs","/swagger-ui.html"};

    private static final String[] GET_USER = {"/movies/**","/characters/**","/genders"};

    private static final String[] GET_ADMIN = {"/auth/users"};

    private static final String[] POST_ADMIN = {"/movies","/characters","/genders"};

    private static final String[] DELETE_ADMIN = {"/movies/{id}","/characters/{id}"};

    private static final String[] PUT_ADMIN = {"/movies/{id}","/characters/{id}","/genders/{id}","/genders/**","/characters/**"};

    private static final String[] PUT_USER = {"/auth/{id}"};

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/api/docs"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .and()
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,GET_USER).hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET,GET_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,POST_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,DELETE_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,PUT_ADMIN).hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,PUT_USER).hasAnyAuthority("USER","ADMIN")
                .anyRequest().authenticated();
    }


}
