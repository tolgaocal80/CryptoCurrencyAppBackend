package com.tolgaocal80.bayzat.config;


import com.tolgaocal80.bayzat.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import static javax.servlet.http.HttpServletResponse.*;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder encoder;
    private final UserService userService;

    private static final String[] AUTH_WHITELIST = { //
            "/h2-console", //
            "/h2-console/**", //
            "/v2/api-docs", //
            "/swagger-resources", //
            "/swagger-resources/**", //
            "/configuration/ui", //
            "/configuration/security", //
            "/swagger-ui.html", //
            "/webjars/**", //
            "/graphiql", //
            "/api/graphql", //
    };

    public WebSecurityConfiguration(PasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().hasAnyRole("ADMIN", "USER")
                .and()
                .exceptionHandling()
                .accessDeniedHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN)) // if someone tries to access protected resource but doesn't have enough permissions
                .authenticationEntryPoint((req, resp, ex) -> resp.setStatus(SC_UNAUTHORIZED)).and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler((req, resp, auth) -> resp.setStatus(SC_OK)) // success authentication
                .failureHandler((req, resp, ex) -> resp.setStatus(SC_FORBIDDEN))
                .and() // bad credentials
                .sessionManagement()
                .invalidSessionStrategy((req, resp) -> resp.setStatus(SC_UNAUTHORIZED))
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .csrf()
                .disable();
    }



}
