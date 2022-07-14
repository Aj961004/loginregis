package com.coffeeshop.loginregis.repository;

import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<RegisterCoffee,Integer> {
}
