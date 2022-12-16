package com.codeWithAshith.SpringSecurity.repository;

import com.codeWithAshith.SpringSecurity.model.ContactUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactUserRepository extends JpaRepository<ContactUser, Integer> {
    Optional<ContactUser> findByName(String name);

}