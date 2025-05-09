package com.careMatrix.backend.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String gender;
    private String bloodGroup;
    public String mobileNumber;
    public String emailAddress;
    public String street;
    public String city;
    public String state;
    public String country;
    public Long zipcode;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Allergy> allergies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrentCondition> currentConditions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrentPrescription> currentPrescriptions;

    @OneToMany(mappedBy = "patient",cascade=CascadeType.ALL)
    private List<InsuranceInformation> insuranceInformations;

    @OneToMany(mappedBy = "patient" ,cascade = CascadeType.ALL)
    private List<SurgicalHistory> surgeries;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<CurrentCondition> getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(List<CurrentCondition> currentConditions) {
        this.currentConditions = currentConditions;
    }

    public List<CurrentPrescription> getCurrentPrescriptions() {
        return currentPrescriptions;
    }

    public void setCurrentPrescriptions(List<CurrentPrescription> currentPrescriptions) {
        this.currentPrescriptions = currentPrescriptions;
    }

    public List<InsuranceInformation> getInsuranceInformations() {
        return insuranceInformations;
    }

    public void setInsuranceInformations(List<InsuranceInformation> insuranceInformations) {
        this.insuranceInformations = insuranceInformations;
    }

    public List<SurgicalHistory> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<SurgicalHistory> surgeries) {
        this.surgeries = surgeries;
    }
}