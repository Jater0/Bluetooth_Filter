package com.tymphany.jater.sdk.filter.bean;

import java.util.List;

import androidx.annotation.NonNull;

public class MacBean {
    private String version;
    private int useful;
    private String mac_host;
    private List<String> mac_address_list;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getUseful() {
        return useful;
    }

    public void setUseful(int useful) {
        this.useful = useful;
    }

    public String getMac_host() {
        return mac_host;
    }

    public void setMac_host(String mac_host) {
        this.mac_host = mac_host;
    }

    public List<String> getMac_address_list() {
        return mac_address_list;
    }

    public void setMac_address_list(List<String> mac_address_list) {
        this.mac_address_list = mac_address_list;
    }

    @NonNull
    @Override
    public String toString() {
        return "{version=" + version + "; useful=" + useful +"; mac_host="+ mac_host +"; mac_address_list=" + mac_address_list.toString() + "}";
    }
}