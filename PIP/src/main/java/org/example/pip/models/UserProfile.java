package org.example.pip.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private LocalDate dateOfBirth;

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Column(nullable = true)
    private int securityLevel;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(nullable = true)
    private String role;

    @Column(nullable = true)
    private String gender;


    // Constructors
    public UserProfile() {
    }

    public UserProfile(String username, String email, LocalDate dateOfBirth, String address, String profilePictureUrl, String bio, String gender, String role, int securityLevel) {
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
        this.securityLevel = securityLevel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
