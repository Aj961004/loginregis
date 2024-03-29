package com.coffeeshop.loginregis.controller;


import com.coffeeshop.loginregis.model.dto.*;
import com.coffeeshop.loginregis.model.entity.ProfilePhoto;
import com.coffeeshop.loginregis.model.entity.RegisterCoffee;
import com.coffeeshop.loginregis.model.entity.Roles;
import com.coffeeshop.loginregis.repository.LoginRepository;
import com.coffeeshop.loginregis.repository.RolesRepository;
import com.coffeeshop.loginregis.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffee")
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private StorageService storageService;

    @PostMapping("/login")
    public DefaultResponse login(@RequestBody LoginDto loginDto) {
        DefaultResponse df = new DefaultResponse();
        Optional<RegisterCoffee> optionalRegisterCoffee = loginRepository.findByNoTelpAndPass(loginDto.getNoTelp(), loginDto.getPass());

        if (optionalRegisterCoffee.isPresent()) {
            df.setStatus(Boolean.TRUE);
            df.setMessage("Login Berhasil");
        } else {
            df.setStatus(Boolean.FALSE);
            df.setMessage("Silahkan Register Terlebih Dahulu");
        }
        return df;

    }

    @GetMapping("/byid/{idUser}")
    public DefaultResponse getByIdRegisterCoffee(@PathVariable Integer idUser) {
        DefaultResponse df = new DefaultResponse();
        Optional<RegisterCoffee> loregisOpt = loginRepository.findById(idUser);
        if (loregisOpt.isPresent()) {
            df.setStatus(Boolean.TRUE);
            df.setMessage("Data Terdatar");
        } else {
            df.setStatus(Boolean.FALSE);
            df.setMessage("Data Belum Terdaftar");
        }
        return df;

    }

    @PostMapping("/save")
    public DefaultResponses<LoginDto> saveLogin(@RequestBody LoginDto loginDto) {
        RegisterCoffee registerCoffee = convertDtoToEntity(loginDto);
        Roles roles = new Roles();
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        if (optional.isPresent()) {
            responses.setMessages("Error, Data Sudah Tersedia");
        } else {
            roles.setRole(loginDto.getRole());
            rolesRepository.save(roles);
            loginRepository.save(registerCoffee);
            responses.setMessages("Berhasil Simpan Data");
            responses.setData(loginDto);
        }

        return responses;
    }


    @PostMapping("/upload")
    public ResponseEntity<Responses> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.simpan(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new Responses(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Responses(message));
        }
    }

    @PostMapping("/uploadSem")
    public ResponseEntity<Responses> uploadProfile(@RequestParam("file") MultipartFile file, RegisterCoffee registerCoffee, Roles roles) {
        String message = "";
        try {
            storageService.simpanSemua(file, registerCoffee, roles);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new Responses(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Responses(message));
        }
    }

    @PutMapping("/updateSem")
    public DefaultResponses<RegisterCoffee> updateProf(@RequestParam("file") MultipartFile file, RegisterCoffee registerCoffee, Roles roles) throws IOException {
        DefaultResponses<RegisterCoffee> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(registerCoffee.getIdUser());
        Optional<Roles> optional1 = rolesRepository.findById(roles.getIdRole());
        if(optional.isPresent() && optional1.isPresent()){
            storageService.updateSemua(file, registerCoffee, roles);
            responses.setMessages("Data Berhasil Diupdate");
        } else {
            responses.setMessages("Error. Data tidak dapat di update");
        }
        return responses;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        ProfilePhoto profilePhoto = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profilePhoto.getName() + "\"")
                .body(profilePhoto.getData());
    }

//    @PostMapping("/test")
//    public RegisterCoffee ketikasavegenerate(@RequestBody LoginDto loginDto){
//        RegisterCoffee reg = new RegisterCoffee();
//        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
//        loginRepository.save(reg);
//        responses.setData(loginDto);
//        RegisterCoffee reg2 = new RegisterCoffee();
//        loginRepository.save(reg2);
//
//        return reg;
//    }


    // ini bisa untuk delete semuanya
    @PostMapping("/delete")
    public DefaultResponses<LoginDto> deleteLog(@RequestBody LoginDto loginDto) {
        RegisterCoffee registerCoffee = convertDtoToEntity(loginDto);
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Roles role = convertDtoToEntityRole(loginDto);
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        Optional<Roles> optional1 = rolesRepository.findById(loginDto.getIdRole());
        if (optional.isPresent() && optional1.isPresent()) {
            rolesRepository.delete(role);
            loginRepository.delete(registerCoffee);
            responses.setMessages("Data sudah terhapus");
        } else {
            responses.setMessages("Data tidak ditemukan.");
        }
        return responses;
    }

    @GetMapping("/views")
    public List<LoginDto> getListLogin() {
        List<LoginDto> list = new ArrayList<>();
        for (RegisterCoffee m : loginRepository.findAll()) {
            list.add(convertEntityToDto(m));
        }

        return list;
    }


    @PostMapping("/profile")
    public DefaultResponses<LoginDto> profileLog(@RequestBody LoginDto loginDto) {
        DefaultResponses<LoginDto> responses = new DefaultResponses<>();
        Optional<RegisterCoffee> optional = loginRepository.findById(loginDto.getIdUser());
        if (optional.isPresent()) {
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
//        }
//    }

    public RegisterCoffee convertDtoToEntity(LoginDto dto) {
        RegisterCoffee registerCoffee = new RegisterCoffee();
        registerCoffee.setIdUser(dto.getIdUser());
        registerCoffee.setAlamat(dto.getAlamat());
        registerCoffee.setNama(dto.getNama());
        registerCoffee.setNoTelp(dto.getNoTelp());
        registerCoffee.setPass(dto.getPass());

        return registerCoffee;
    }

    public Roles convertDtoToEntityRole(LoginDto dto){
        Roles roles = new Roles();
        roles.setIdRole(dto.getIdRole());
        roles.setRole(dto.getRole());
        return roles;
    }

    public LoginDto convertEntityToDtoRole(Roles entity){
        LoginDto dto = new LoginDto();
        dto.setIdRole(entity.getIdRole());
        dto.setRole(entity.getRole());
        return dto;
    }

    public LoginDto convertEntityToDto(RegisterCoffee entity) {
        LoginDto dto = new LoginDto();
        dto.setIdUser(entity.getIdUser());
        dto.setAlamat(entity.getAlamat());
        dto.setNama(entity.getNama());
        dto.setNoTelp(entity.getNoTelp());
        dto.setPass(entity.getPass());

        return dto;
    }
}
