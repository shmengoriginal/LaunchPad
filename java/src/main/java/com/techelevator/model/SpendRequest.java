package com.techelevator.model;

public class SpendRequest {
    int spendRequestId;
    int campaignId;
    String spendDescription;
    int spendStatus = 0;
    public SpendRequest(){};

    public SpendRequest(int spendRequestId, int campaignId, String spendDescription, int spendStatus) {
        this.spendRequestId = spendRequestId;
        this.campaignId = campaignId;
        this.spendDescription = spendDescription;
        this.spendStatus = spendStatus;
    }

    public int getSpendRequestId() {
        return spendRequestId;
    }

    public void setSpendRequestId(int spendRequestId) {
        this.spendRequestId = spendRequestId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getSpendDescription() {
        return spendDescription;
    }

    public void setSpendDescription(String spendDescription) {
        this.spendDescription = spendDescription;
    }

    public int getSpendStatus() {
        return spendStatus;
    }

    public void setSpendStatus(int spendStatus) {
        this.spendStatus = spendStatus;
    }
}
