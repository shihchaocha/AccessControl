package org.example.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;

    private HashMap<String, String> properties = new HashMap<String, String>();

    private static HashMap<String, Integer> signatures = new HashMap<String, Integer>() {{
        put("securityLevel", 1);
        put("age", 1);
        put("role", 1);
        put("id", 1);
        put("username", 1);
        put("email", 1);
        put("risk", 2);
    }};

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public void getProperty(String key) {
        this.properties.get(key);
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    private int securityLevel;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private int age;
    private String role;

    // Constructors
    public UserProfileDTO() {
    }

    public UserProfileDTO(Long id, String username, String email, LocalDate dateOfBirth, String gender, String role, int securityLevel) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
        this.age = calculateAge();
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
        this.age = calculateAge();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int calculateAge() {
        if (this.dateOfBirth != null) {
            return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
        } else {
            return 0;  // Return 0 if dateOfBirth is not set
        }
    }
}
