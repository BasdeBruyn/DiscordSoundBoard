package nl.basdebruyn.soundboardapi;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.session.ConcurrentSessionFilter;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    TokenAuthenticationFilter tokenAuthenticationFilter;
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.addFilterAfter(tokenAuthenticationFilter, ConcurrentSessionFilter.class);
//        httpSecurity.authorizeRequests()
//                .antMatchers("/auth/**").permitAll()
//                .anyRequest().authenticated();
//    }
//}
