package com.careMatrix.backend.Controller;

import com.careMatrix.backend.Dto.LoginRequest;
import com.careMatrix.backend.Dto.RegistrationRequest;
import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.Users;
import java.util.concurrent.atomic.AtomicLong;
import com.careMatrix.backend.Repo.DoctorRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import com.careMatrix.backend.Repo.UserRepo;
import com.careMatrix.backend.Service.JWTService;
import com.careMatrix.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/register")
//    public Users registerUser(@RequestBody Users user) {
//        return service.saveUser(user);
//    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {

        System.out.println(request.toString());
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public Map<String, Object> Verify(@RequestBody Users user) {
        System.out.println("input" + user);
        Users us = userRepo.findByUsername(user.getUsername());

        String token = service.Verify(user);
        System.out.println("user" + us);
        System.out.println("token" + token);
        AtomicLong id = new AtomicLong();

        if ("PATIENT".equalsIgnoreCase(us.getRole())) {
            patientRepo.findByUserId(us.getId())
                    .ifPresent(p -> id.set(p.getId()));
        } else if ("DOCTOR".equalsIgnoreCase(us.getRole())) {
            doctorRepo.findByUserId(us.getId())
                    .ifPresent(d -> id.set(d.getId()));
        }
        System.out.print("id is" + id);
        Map<String, Object> res = new HashMap<>();
        res.put("id", id.get());
        res.put("role", us.getRole());
        res.put("token", token);
        res.put("username", us.getUsername());

        return res;
    }



//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        Users user = userRepo.findByUsername(request.getUsername());
//
//        System.out.println(user.getUsername() + user.getPassword());
//        System.out.println(request.getPassword());
//        if (user == null ||  !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("userId", user.getId());
//        response.put("role", user.getRole());
//
//        if ("PATIENT".equalsIgnoreCase(user.getRole())) {
//            Patient patient = patientRepo.findByUser(user);
//            response.put("patientDetails", patient);
//        } else if ("DOCTOR".equalsIgnoreCase(user.getRole())) {
//            Doctor doctor = doctorRepo.findByUser(user);
//            response.put("doctorDetails", doctor);
//        }
//
//        return ResponseEntity.ok(response);
//    }

}
