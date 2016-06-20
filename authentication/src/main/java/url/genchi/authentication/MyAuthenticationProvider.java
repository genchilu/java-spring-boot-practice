package url.genchi.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import url.genchi.password.Password;
import url.genchi.service.MyUserDetailsService;

import java.util.Collection;

/**
 * Created by mac on 2016/6/20.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    private MyUserDetailsService myUserDetailsService;
    public MyAuthenticationProvider(){}
    public MyAuthenticationProvider(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("i'm here");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = myUserDetailsService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }
        if (!Password.encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    public boolean supports(Class<?> authentication) {
        return true;
    }
}
