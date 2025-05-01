package hr.java.sport.event.sporteventapp.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final HandlerMappingIntrospector introspector;
    private final DataSource dataSource;

    @Autowired
    public SecurityConfiguration(HandlerMappingIntrospector introspector,
        DataSource dataSource) {
        this.introspector = introspector;
        this.dataSource = dataSource;
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(mvc(introspector).pattern("/h2**")).anonymous()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self' 'unsafe-inline';")
                )
            )
            .formLogin(login -> login
                .defaultSuccessUrl("/welcome", true)
                .failureUrl("/login.html?error=true")
            )
            .logout(logout -> logout
                .deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/login.html")
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
        PasswordEncoder passwordEncoder) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from users where username = ?")
            .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
            .passwordEncoder(passwordEncoder);
    }


}
