package org.example.models;

import java.util.HashMap;

public class DeviceProfileDTO {
    private Long id;
    private String name;
    private String type;

    private HashMap<String, String> properties = new HashMap<String, String>();

    private static HashMap<String, Integer> signatures = new HashMap<String, Integer>() {{
        put("name", 1);
        put("id", 1);
        put("type", 1);
        put("registered", 1);
    }};

    public void setProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public void getProperty(String key) {
        this.properties.get(key);
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    private boolean registered = false;

    // Constructors
    public DeviceProfileDTO() {
        this.registered = false;
    }

    public DeviceProfileDTO(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
        if(id!=null && id>0)
            this.registered = true;
        else
            this.registered = false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        if(id!=null && this.id>0)
            this.registered = true;
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
}

