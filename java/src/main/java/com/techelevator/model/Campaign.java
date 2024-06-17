package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Campaign {
//  data we need
//    String title, LocalDate endDate, BigDecimal goal, int managerId, String imgURL, BigDecimal funding, boolean isPublic, String description
    int campaignId;
    String title;
    LocalDate endDate;
    BigDecimal goal;
    int managerId;
    String imgURL;
    BigDecimal funding;
    String description;
    boolean isPublic;

    public Campaign() {}

    public Campaign(int campaignId, String title, LocalDate endDate, BigDecimal goal, int managerId, String imgURL, BigDecimal funding, String description, boolean isPublic) {
        this.campaignId = campaignId;
        this.title = title;
        this.endDate = endDate;
        this.goal = goal;
        this.managerId = managerId;
        this.imgURL = imgURL;
        this.funding = funding;
        this.description = description;
        this.isPublic = isPublic;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public BigDecimal getFunding() {
        return funding;
    }

    public void setFunding(BigDecimal funding) {
        this.funding = funding;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getisPublic() {
        return isPublic;
    }

    public void setisPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
