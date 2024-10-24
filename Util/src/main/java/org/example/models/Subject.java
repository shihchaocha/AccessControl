package org.example.models;

import org.example.util.NetworkUtils;

import java.net.UnknownHostException;
import java.util.HashMap;

public class Subject {
    private long id;
    private int age;
    private String gender;
    private String sourceip;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSourceip() {
        return sourceip;
    }

    public void setSourceip(String sourceip) {
        this.sourceip = sourceip;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public  boolean isIpInRange(String networkAddress, String mask) throws UnknownHostException {
        return NetworkUtils.isIpInRange(sourceip, networkAddress, mask);
    }

    public static Subject generateSubject(String id, HashMap<String, String> contextInfo) {
        Subject subject = new Subject();

        // 確保 id 可以正常解析
        try {
            subject.setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            // 可根據需求處理無效的 id，例如設置為預設值或拋出自訂異常
            System.err.println("Invalid ID format: " + id);
            subject.setId(-1L); // 預設值，可以根據需求調整
        }

        String age = contextInfo.getOrDefault("subject.age", "0");
        try {
            subject.setAge(Integer.parseInt(age));
        } catch (NumberFormatException e) {
            System.err.println("Invalid Age format: " + age);
            subject.setAge(0); // 預設值，可以根據需求調整
        }

        subject.setGender(contextInfo.getOrDefault("subject.gender", ""));
        subject.setSourceip(contextInfo.getOrDefault("subject.ip", ""));
        subject.setRole(contextInfo.getOrDefault("subject.role", ""));

        return subject;
    }
}
