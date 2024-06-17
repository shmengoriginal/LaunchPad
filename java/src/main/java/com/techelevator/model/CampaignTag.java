package com.techelevator.model;

public class CampaignTag {
    int tagId;
    int campaignId;

    public CampaignTag(int tagId, int campaignId) {
        this.tagId = tagId;
        this.campaignId = campaignId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }
}
