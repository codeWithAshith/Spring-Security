package com.codeWithAshith.SpringSecurity.service;

import com.codeWithAshith.SpringSecurity.model.ContactUser;
import com.codeWithAshith.SpringSecurity.model.Role;
import com.codeWithAshith.SpringSecurity.repository.ContactUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ContactUserDetailsService implements UserDetailsService {

    @Autowired
    private ContactUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ContactUser user = userRepository.findByName(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found with username: " + username);
        });
        if (user != null) {
            return new User(user.getName(), user.getPassword(),
                    buildSimpleGrantedAuthorities(user.getRoles()));
        }
        return null;
    }

    private static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }
}
