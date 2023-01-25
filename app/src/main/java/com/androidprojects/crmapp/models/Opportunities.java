package com.androidprojects.crmapp.models;

/**
 * Opportunities Model class which consist of constructors, getters and setters
 */
public class Opportunities {

        int opportunityId;
        int customerId;
        String opportunityStatus;
        String opportunityName;

    public Opportunities(int opportunityId, int customerId, String opportunityStatus, String opportunityName) {
        this.opportunityId = opportunityId;
        this.customerId = customerId;
        this.opportunityStatus = opportunityStatus;
        this.opportunityName = opportunityName;
    }


    public int getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(int opportunityId) {
        this.opportunityId = opportunityId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getOpportunityStatus() {
        return opportunityStatus;
    }

    public void setOpportunityStatus(String opportunityStatus) {
        this.opportunityStatus = opportunityStatus;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }
}
