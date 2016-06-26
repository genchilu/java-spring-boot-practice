package url.genchi.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * Created by mac on 2016/6/21.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home", "/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .defaultsDisabled()
                .contentTypeOptions()
                .and()
                .httpStrictTransportSecurity()
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000)
                .and()
                .httpPublicKeyPinning()
                .includeSubDomains(true)
                .reportUri("http://example.net/pkp-report")
                .addSha256Pins("d6qzRu9zOECb90Uez27xWltNsj0e1Md7GkYYkVoZWmM=", "E9CZ9INDbd+2eRQozYqqbQ2yXLVKB9+xcprMF+44U1g=")
                .and().contentSecurityPolicy("script-src 'self'; object-src 'self'")
                .and()
                .cacheControl().disable()
                .addHeaderWriter(new StaticHeadersWriter("Cache-Control", "no-cache=\"Set-Cookie, Set-Cookie2\""))
                //.xssProtection()  //notify browser to assist in reflected XSS attacks protection, defaule is enable
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
