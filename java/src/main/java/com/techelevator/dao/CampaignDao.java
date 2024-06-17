package com.techelevator.dao;

import com.techelevator.model.Campaign;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CampaignDao {

    List<Campaign> getFeaturedCampaigns(); //for home page
//    List<Campaign> getUsersCampaigns(int userId);
    List<Campaign> getCampaignsByManagerId(int id); //for dashboard
    List<Campaign> getCampaignsByDonorId(int id); //confused about this
    List<Campaign> getCampaignsBySearch(String searchTerm); //for home page
    Campaign getCampaignById(int id);
    Campaign createCampaign(Campaign campaign);
    boolean updateCampaign(Campaign campaign, int campaignId);
    List<Campaign> getCampaignsByTag(String tag);
    int deleteCampaign(int campaignId);


}
