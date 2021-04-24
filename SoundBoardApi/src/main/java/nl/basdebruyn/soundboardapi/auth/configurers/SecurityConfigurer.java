package nl.basdebruyn.soundboardapi.auth.configurers;

import nl.basdebruyn.soundboardapi.auth.filters.JwtAuthenticationFilter;
import nl.basdebruyn.soundboardapi.auth.filters.ServiceAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationFilter jwtFilter;
    private final ServiceAuthenticationFilter serviceFilter;

    public SecurityConfigurer(JwtAuthenticationFilter jwtFilter, ServiceAuthenticationFilter serviceFilter) {
        this.jwtFilter = jwtFilter;
        this.serviceFilter = serviceFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        configureCors(httpSecurity);
        configureSessionManagement(httpSecurity);
        addAuthentication(httpSecurity);
        disableCsrf(httpSecurity);
    }

    private void disableCsrf(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
    }

    private void configureCors(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors();
    }

    private void configureSessionManagement(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void addAuthentication(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterAfter(serviceFilter, ConcurrentSessionFilter.class)
                .addFilterAfter(jwtFilter, ServiceAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/auth/token").permitAll()
                .anyRequest().authenticated();
    }
}
