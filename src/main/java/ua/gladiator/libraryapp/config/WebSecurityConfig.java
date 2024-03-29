package ua.gladiator.libraryapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ua.gladiator.libraryapp.security.JwtProvider;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtProvider jwtProvider;

    @Autowired
    public WebSecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
;
                /*.and()
                .authorizeRequests()

                .antMatchers("/login").permitAll()
                .antMatchers("/locale/*").permitAll()
                .antMatchers("/registration").permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtProvider));*/
    }
}
