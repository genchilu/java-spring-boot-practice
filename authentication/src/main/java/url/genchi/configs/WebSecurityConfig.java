package url.genchi.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import url.genchi.authentication.MyAuthenticationProvider;
import url.genchi.password.Password;
import url.genchi.repositories.UserRepository;
import url.genchi.service.MyUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    private UserRepository _userRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/signin", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
        ;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(Password.encoder)
//                .usersByUsernameQuery(
//                        "SELECT USERNAME, PASSWORD, 1 AS ENABLE FROM AUTH_USER WHERE USERNAME=?")
//                .authoritiesByUsernameQuery(
//                        "SELECT USERNAME, ROLES FROM AUTH_USER WHERE USERNAME=?");
//    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new MyUserDetailsService(_userRepo);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
        auth.authenticationProvider(new MyAuthenticationProvider((MyUserDetailsService)userDetailsServiceBean()));
    }
}