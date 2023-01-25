package com.androidprojects.crmapp.models;

/**
 * CustomerInformation Model class which consist of constructors, getters and setters
 */

public class CustomerInformation {
    int customerId;
    String createdDate;
    String name;
    String email;
    String phoneNumber;
    String status;

    public CustomerInformation(int customerId, String createdDate, String name, String email, String phoneNumber, String status) {
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
