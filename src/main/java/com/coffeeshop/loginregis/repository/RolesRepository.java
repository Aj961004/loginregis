package com.coffeeshop.loginregis.repository;

import com.coffeeshop.loginregis.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer>{
    Optional<Roles> findByRole(String role);
}
