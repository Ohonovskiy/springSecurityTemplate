package spring.security.main.rest.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spring.security.main.rest.config.SecurityConfig;
import spring.security.main.rest.enums.Role;

//@Service
public class MainUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        if(username.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password(new SecurityConfig().passwordEncoder().encode("admin"))
                    .authorities(Role.ADMIN.getAuthorities())
                    .build();
        }
        else if(username.equals("user")) {
            return User.builder()
                    .username("user")
                    .password(new SecurityConfig().passwordEncoder().encode("user"))
                    .authorities(Role.USER.getAuthorities())
                    .build();
        } else throw new UsernameNotFoundException("User cannot be founded");

    }


}
