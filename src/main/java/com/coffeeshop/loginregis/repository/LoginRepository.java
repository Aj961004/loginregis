package com.coffeeshop.loginregis.repository;

import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<RegisterCoffee,Integer> {
    Optional<RegisterCoffee> findByNoTelpAndPass (String noTelp, String pass);
}
