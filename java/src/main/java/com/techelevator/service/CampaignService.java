package com.techelevator.service;

import com.techelevator.dao.CampaignDao;
import com.techelevator.dao.DonationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
public class CampaignService {
    private RestTemplate restTemplate = new RestTemplate();
    private CampaignDao campaignDao;
    private UserDao userDao;
    private DonationDao donationDao;
    public CampaignService(CampaignDao campaignDao, UserDao userDao, DonationDao donationDao){
        this.campaignDao = campaignDao;
        this.userDao = userDao;
        this.donationDao = donationDao;
    }

    public List<Campaign> getFeaturedCampaigns(){
        return campaignDao.getFeaturedCampaigns();
    }

    public List<Campaign> getCampaignsByManagerId(Principal userInfo){
        int userId = userDao.getUserByUsername(userInfo.getName()).getId();
        return campaignDao.getCampaignsByManagerId(userId);
    }
    public List<Campaign> getCampaignsByDonorId(Principal userInfo){
        int userId = userDao.getUserByUsername(userInfo.getName()).getId();
        return campaignDao.getCampaignsByDonorId(userId);
    }
    public List<Campaign> getCampaignsBySearch(String searchTerm){
        return campaignDao.getCampaignsBySearch(searchTerm);
    }
    public List<Campaign> getCampaignsByTag(String tag){
        return campaignDao.getCampaignsByTag(tag);
    }

}
