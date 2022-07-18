package com.coffeeshop.loginregis.controller;


import com.coffeeshop.loginregis.model.dto.DefaultResponse;
import com.coffeeshop.loginregis.model.dto.DefaultResponses;
import com.coffeeshop.loginregis.model.dto.LoginDto;
import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import com.coffeeshop.loginregis.repository.LoginRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/byid/{idUser}")
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

    @PostMapping("/save")
    public DefaultResponses<LoginDto> saveLogin(@RequestBody LoginDto loginDto){
        RegisterCoffee registerCoffee = convertDtoToEntity(loginDto);
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        if(optional.isPresent()){
            responses.setMessages("Error, Data Sudah Tersedia");
        } else {
            loginRepository.save(registerCoffee);
            responses.setMessages("Berhasil Simpan Data");
            responses.setData(loginDto);
        }

        return responses;
    }

    @PostMapping("/delete")
    public DefaultResponses<LoginDto> deleteLog(@RequestBody LoginDto loginDto){
        RegisterCoffee registerCoffee =convertDtoToEntity(loginDto);
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        if(optional.isPresent()){
            loginRepository.delete(registerCoffee);
            responses.setMessages("Data sudah terhapus");
        } else {
            responses.setMessages("Data tidak ditemukan.");
        }
        return responses;
    }

    @GetMapping("/views")
    public List<LoginDto> getListLogin() {
        List<LoginDto> list = new ArrayList();
        for (RegisterCoffee m : loginRepository.findAll()) {
            list.add(convertEntityToDto(m));
        }

        return list;
    }

    @PostMapping("/profile")
    public DefaultResponses<LoginDto> profileLog(@RequestBody LoginDto loginDto){
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        if(optional.isPresent()){
            responses.setMessages("Data Ditemukan");
            responses.setData(convertEntityToDto(optional.get()));
        } else {
            responses.setMessages("Data Tidak Ditemukan");
        }
        return responses;
    }

//    @PostMapping("/update")
//    public DefaultResponses<LoginDto> updateLog(@RequestBody LoginDto loginDto){
//        RegisterCoffee registerCoffee = convertDtoToEntity(loginDto);
//        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
//        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
//        if(optional.isPresent()){
//            RegisterCoffee dto = optional.get();
//            dto.getNama()
//
//
//
//        }
//    }

    public RegisterCoffee convertDtoToEntity(LoginDto dto){
        RegisterCoffee registerCoffee = new RegisterCoffee();
        registerCoffee.setIdUser(dto.getIdUser());
        registerCoffee.setAlamat(dto.getAlamat());
        registerCoffee.setNama(dto.getNama());
        registerCoffee.setNoTelp(dto.getNoTelp());
        registerCoffee.setPass(dto.getPass());

        return registerCoffee;
    }

    public LoginDto convertEntityToDto(RegisterCoffee entity){
        LoginDto dto = new LoginDto();
        dto.setIdUser(entity.getIdUser());
        dto.setAlamat(entity.getAlamat());
        dto.setNama(entity.getNama());
        dto.setNoTelp(entity.getNoTelp());
        dto.setPass(entity.getPass());

        return dto;
    }
}
