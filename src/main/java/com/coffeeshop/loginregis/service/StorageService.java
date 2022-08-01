package com.coffeeshop.loginregis.service;

import com.coffeeshop.loginregis.model.entity.ProfilePhoto;
import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import com.coffeeshop.loginregis.model.entity.Roles;
import com.coffeeshop.loginregis.repository.LoginRepository;
import com.coffeeshop.loginregis.repository.ProfileRepository;
import com.coffeeshop.loginregis.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class StorageService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public ProfilePhoto simpan(MultipartFile file) throws IOException{
        String namaFile = StringUtils.cleanPath(file.getOriginalFilename());
        ProfilePhoto ProfilePhoto = new ProfilePhoto(namaFile, file.getContentType(),file.getBytes());

        return profileRepository.save(ProfilePhoto);
    }

    public RegisterCoffee simpanSemua(MultipartFile file, RegisterCoffee registerCoffee, Roles roles) throws IOException{
        String namaFile = StringUtils.cleanPath(file.getOriginalFilename());
        RegisterCoffee registerCoffee1 = new RegisterCoffee(namaFile, file.getContentType(),file.getBytes());
        Roles roles1 = new Roles();
        roles1.setRole(roles.getRole());
        rolesRepository.save(roles);
        registerCoffee1.setNama(registerCoffee.getNama());
        registerCoffee1.setPass(registerCoffee.getPass());
        registerCoffee1.setAlamat(registerCoffee.getAlamat());
        registerCoffee1.setNoTelp(registerCoffee.getNoTelp());
        return loginRepository.save(registerCoffee1);
    }

    public ProfilePhoto getFile(String id){
        return profileRepository.findById(id).get();
    }

    public Stream<ProfilePhoto> getAllFiles(){
        return profileRepository.findAll().stream();
    }

}
