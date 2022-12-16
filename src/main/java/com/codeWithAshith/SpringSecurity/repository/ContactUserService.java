package com.codeWithAshith.SpringSecurity.repository;

import com.codeWithAshith.SpringSecurity.model.ContactUser;
import com.codeWithAshith.SpringSecurity.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ContactUserService {

    @Autowired
    private ContactUserRepository contactUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ContactUser register(ContactUser contactUser) {
        contactUser.setPassword(bCryptPasswordEncoder.encode(contactUser.getPassword()));
        Role userRole = roleRepository.findByName(Role.USER);
        contactUser.setRoles(Set.of(userRole));
        return contactUserRepository.save(contactUser);
    }

    public ContactUser login(ContactUser contactUser) {
        Optional<ContactUser> loggedInUser = contactUserRepository.findByName(contactUser.getName());

        if (loggedInUser.isPresent()) {
            System.out.println(!bCryptPasswordEncoder.matches(contactUser.getPassword(),
                    loggedInUser.get().getPassword()));
            System.out.println(contactUser.getPassword());
            System.out.println(loggedInUser.get().getPassword());
        }

        return loggedInUser.get();

    }
}
