package url.genchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import url.genchi.entities.User;
import url.genchi.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2016/6/20.
 */
@Service("MyUserDetailsImpl")
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository repo;
    public MyUserDetailsService(){}
    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
            user = repo.getByUsername(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if(user == null){
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
                gas.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), true, true, true, true, gas);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }
}