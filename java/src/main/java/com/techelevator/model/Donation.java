package com.techelevator.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Donation {
    int donationId;
    int donorId;
    int campaignId;
    BigDecimal donationAmount;

    //vvv added (getters setters, constructor passes) as per schema
    Timestamp dateTime; //curious if this needs to be LocalDateTime instead
    public Donation(){}

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public BigDecimal getAmount() {
        return donationAmount;
    }

    public void setAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Donation(int donationId, int donorId, int campaignId, BigDecimal donationAmount,Timestamp dateTime) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.campaignId = campaignId;
        this.donationAmount = donationAmount;
        this.dateTime = dateTime;
    }


}
