package com.codeWithAshith.SpringSecurity.dataloader;

import com.codeWithAshith.SpringSecurity.model.ContactUser;
import com.codeWithAshith.SpringSecurity.model.Role;
import com.codeWithAshith.SpringSecurity.repository.ContactUserRepository;
import com.codeWithAshith.SpringSecurity.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private ContactUserRepository contactUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

//         Create user roles
        Role userRole = createRoleIfNotFound(Role.USER);
        createUserIfNotFound("test", "test", userRole);

        alreadySetup = true;
    }

    @Transactional
    private Role createRoleIfNotFound(final String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    private ContactUser createUserIfNotFound(final String name,final String password, final Role role) {
        Optional<ContactUser> optionalUser = contactUserRepository.findByName(name);
        ContactUser user = null;
        if (optionalUser.isEmpty()) {
            user = new ContactUser(name, bCryptPasswordEncoder.encode(password));
            user.setRoles(Set.of(role));
            user = contactUserRepository.save(user);
        }
        return user;
    }
}
