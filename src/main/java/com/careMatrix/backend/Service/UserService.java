package com.careMatrix.backend.Service;

import com.careMatrix.backend.Dto.RegistrationRequest;
import com.careMatrix.backend.Entity.Doctor;
import com.careMatrix.backend.Entity.Patient;
import com.careMatrix.backend.Entity.Users;
import com.careMatrix.backend.Repo.DoctorRepo;
import com.careMatrix.backend.Repo.PatientRepo;
import com.careMatrix.backend.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Transactional
    public String register(RegistrationRequest request) {
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        Users savedUser = userRepository.save(user);

        if ("PATIENT".equalsIgnoreCase(request.getRole())) {
            Patient patient = new Patient();
            patient.setName(request.getName());
            patient.setAge(request.getAge());
            patient.setGender(request.getGender());
            patient.setBloodGroup(request.getBloodGroup());
            patient.setMobileNumber(request.getMobileNumber());
            patient.setEmailAddress(request.getEmailAddress());
            patient.setCity(request.getCity());
            patient.setStreet(request.getStreet());
            patient.setCountry(request.getCountry());
            patient.setState(request.getState());
            patient.setZipcode(request.getZipcode());
            patient.setDateOfBirth(request.getDateOfBirth());
            patient.setUser(savedUser);
            patientRepo.save(patient);
        } else if ("DOCTOR".equalsIgnoreCase(request.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setName(request.getName());
            doctor.setAge(request.getAge());
            doctor.setGender(request.getGender());
            doctor.setMobileNumber(request.getMobileNumber());
            doctor.setExperience(request.getExperience());
            doctor.setCity(request.getCity());
            doctor.setStreet(request.getStreet());
            doctor.setCountry(request.getCountry());
            doctor.setState(request.getState());
            doctor.setZipcode(request.getZipcode());
            doctor.setLicenseNumber(request.getLicenseNumber());
            doctor.setDateOfBirth(request.getDateOfBirth());
            doctor.setUser(savedUser);
            doctorRepo.save(doctor);
        }
//        if("PATIENT".equalsIgnoreCase(request.getRole())){
//            return
//        }
    return "User registered successfully.";
    }


//
//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    public Users saveUser(Users user){
//        user.setPassword(encoder.encode(user.getPassword()));
//        return userRepo.save(user);
//    }

    public String Verify(Users user){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken( user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        return "fail";
    }

}
