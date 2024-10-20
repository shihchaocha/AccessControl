package org.example.models;

import org.example.util.NetworkUtils;

import java.net.UnknownHostException;

public class Resource {
    private String networkAddress;
    private String mask;
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String operation;

    public String getNetworkAddress() {
        return networkAddress;
    }

    public void setNetworkAddress(String networkaddress) {
        this.networkAddress = networkaddress;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public  boolean isIpInRange(String ip) throws UnknownHostException {
        return NetworkUtils.isIpInRange(ip, networkAddress, mask);
    }

}
