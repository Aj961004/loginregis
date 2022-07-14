package com.coffeeshop.loginregis.controller;


import com.coffeeshop.loginregis.model.dto.DefaultResponse;
import com.coffeeshop.loginregis.model.dto.LoginDto;
import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import com.coffeeshop.loginregis.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/coffee")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping("/login")
    public DefaultResponse login(@RequestBody LoginDto loginDto){
        DefaultResponse df = new DefaultResponse();
        Optional<RegisterCoffee> optionalRegisterCoffee = loginRepository.findByNoTelpAndPass(loginDto.getNoTelp(), loginDto.getPass());

        if(optionalRegisterCoffee.isPresent()){
            df.setStatus(Boolean.TRUE);
            df.setMessage("Login Berhasil");
        } else {
            df.setStatus(Boolean.FALSE);
            df.setMessage("Silahkan Register Terlebih Dahulu");
        }
        return df;

    }

    @GetMapping ("/byid/{idUser}")
    public DefaultResponse getByIdRegisterCoffee(@PathVariable Integer idUser){
        DefaultResponse df = new DefaultResponse();
        Optional<RegisterCoffee> loregisOpt = loginRepository.findById(idUser);
        if(loregisOpt.isPresent()){
            df.setStatus(Boolean.TRUE);
            df.setMessage("Data Terdatar");
        } else {
            df.setStatus(Boolean.FALSE);
            df.setMessage("Data Belum Terdaftar");
        }
        return df;

    }
}
