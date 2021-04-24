package nl.basdebruyn.soundboardapi.auth.properties;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationProperties {
    private Service service;
    private JwtAuthentication jwtAuthentication;

    public static class Service {
        @Length(min = 50)
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class JwtAuthentication {
        @Length(min = 30)
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public JwtAuthentication getJwt() {
        return jwtAuthentication;
    }

    public void setJwt(JwtAuthentication jwtAuthentication) {
        this.jwtAuthentication = jwtAuthentication;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
