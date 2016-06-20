package url.genchi.authentication;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import url.genchi.password.Password;

import java.util.Collection;

/**
 * Created by mac on 2016/6/20.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("12333333");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }
        //加密过程在这里体现
        if (!Password.encoder.encode(password).equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

//        WebAuthenticationDetails wad = null;
//        String userIPAddress         = null;
//        boolean isAuthenticatedByIP  = false;
//        wad = (WebAuthenticationDetails) authentication.getDetails();
//        userIPAddress = wad.getRemoteAddress();
    }

    public boolean supports(Class<? extends Object> authentication)
    {
        // copied it from AbstractUserDetailsAuthenticationProvider
        return(UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
