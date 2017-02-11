package me.gurpreetsk.medihelp.model;

/**
 * Created by gurpreet on 11/02/17.
 */

public class UserData {

    private String userName, address, bloodGroup, email, mac;

    public UserData() {
    }

    public UserData(String userName, String address, String bloodGroup, String email, String mac) {

        this.userName = userName;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.email = email;
        this.mac = mac;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
