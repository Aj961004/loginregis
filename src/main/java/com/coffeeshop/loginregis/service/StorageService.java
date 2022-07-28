package com.coffeeshop.loginregis.service;

import com.coffeeshop.loginregis.model.entity.ProfilePhoto;
import com.coffeeshop.loginregis.repository.ProfileRepository;
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

    public ProfilePhoto simpan(MultipartFile file) throws IOException{
        String namaFile = StringUtils.cleanPath(file.getOriginalFilename());
        ProfilePhoto ProfilePhoto = new ProfilePhoto(namaFile, file.getContentType(),file.getBytes());

        return profileRepository.save(ProfilePhoto);
    }

    public ProfilePhoto getFile(String id){
        return profileRepository.findById(id).get();
    }

    public Stream<ProfilePhoto> getAllFiles(){
        return profileRepository.findAll().stream();
    }

}
