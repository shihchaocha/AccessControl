package org.example.models;

import org.example.util.NetworkUtils;

import java.net.UnknownHostException;
import java.util.HashMap;

public class Resource {
    private long id;

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    private String zone;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  boolean isIpInRange(String networkAddress, String mask) throws UnknownHostException {
        return NetworkUtils.isIpInRange(ip, networkAddress, mask);
    }

    public static Resource generateResource(String id, HashMap<String, String> contextInfo) {
        Resource resource = new Resource();

        // 確保 id 可以正常解析
        try {
            resource.setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            // 可根據需求處理無效的 id，例如設置為預設值或拋出自訂異常
            System.err.println("Invalid ID format: " + id);
            resource.setId(-1L); // 預設值，可以根據需求調整
        }

        // 使用 getOrDefault 確保 map 中沒有 key 時返回預設值
        resource.setZone(contextInfo.getOrDefault("resource.zone", ""));
        resource.setType(contextInfo.getOrDefault("resource.type", ""));
        resource.setIp(contextInfo.getOrDefault("resource.ip", ""));

        return resource;
    }

}
