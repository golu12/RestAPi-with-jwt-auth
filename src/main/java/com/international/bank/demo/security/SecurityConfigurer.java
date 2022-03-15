package com.international.bank.demo.security;

import com.international.bank.demo.filter.JwtFilter;
import com.international.bank.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class allow us to create security configurer which contain create custom username and password and token.
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailService;

    @Autowired
    private JwtFilter jwtFilter;


    /**
     * Below method overrides the property of WebSecurityAdapter allow program to create user auth details.
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Below method allow to call auth authenticate method to generate the token without restriction.
     * Because when you enable spring security it does not allow any apis to access without authentication hence in order to generate accesstoken this apis should be allowed.
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll().
                anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
