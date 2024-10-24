package org.example.models;

import java.util.HashMap;

public class ResourceProfileDTO {
    private Long id;
    private String name;
    private String type;
    private String ip;
    private String zone;

    private HashMap<String, String> properties = new HashMap<String, String>();

    private static HashMap<String, Integer> signatures = new HashMap<String, Integer>() {{
        put("id", 1);
        put("type", 1);
        put("name", 1);
        put("ip", 1);
        put("zone", 1);
    }};

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public void getProperty(String key) {
        this.properties.get(key);
    }


    // Constructors
    public ResourceProfileDTO() {
    }

    public ResourceProfileDTO(Long id, String name, String type, String ip, String zone) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.ip = ip;
        this.zone = zone;
    }

    // Getters and Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}

