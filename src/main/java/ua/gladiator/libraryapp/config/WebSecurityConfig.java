package ua.gladiator.libraryapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ua.gladiator.libraryapp.security.JwtConfigurer;
import ua.gladiator.libraryapp.security.JwtProvider;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtProvider jwtProvider;

    @Value("${server.servlet.context-path}")
    private static String CONTEXT_PATH;

    private static String ADMIN_ENDPOINT =  "/admin/**";
    private static String READER_ENDPOINT =  "/reader/**";
    private static String ACCOUNT_ENDPOINT =  "/lib/auth/**";

    @Autowired
    public WebSecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //todo test token life time
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()

                .antMatchers(ADMIN_ENDPOINT).hasAuthority("ADMIN")
                .antMatchers(READER_ENDPOINT).hasAuthority("READER")
                .antMatchers("/", "/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtProvider));
    }
}
