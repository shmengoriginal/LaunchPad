package com.techelevator.controller;

import com.techelevator.dao.CampaignDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/campaigns")
@PreAuthorize("isAuthenticated()")
public class CampaignController {

    RestTemplate restTemplate = new RestTemplate();
    private CampaignService campaignService;
    private CampaignDao campaignDao;
    private UserDao userDao;

    public CampaignController(CampaignService campaignService, CampaignDao campaignDao, UserDao userDao) {
        this.campaignService = campaignService;
        this.campaignDao = campaignDao;
        this.userDao = userDao;
    }


    //Need DAO for campaign table both Interface and jdbc implementation
    //Need model/DTO of campaign
    //inject them in.

    //endpoints related to getting campaign data
    //remember annotations
    //get list campaign Two, one for all public campaigns, one for getting list of user only campaigns.
    //get individual campaign based on id
    //post individual campaign
    //put indiviudal campaign based on id


//    List<Campaign> getPublicCampaigns(); //for home page
//    List<Campaign> getCampaignsByManagerId(int id); //for dashboard
//    List<Campaign> getCampaignsByDonorId(int id);
//    List<Campaign> getCampaignsBySearch(String searchTerm); //for home page
//    Campaign getCampaignById(int id);
//    Campaign createCampaign(Campaign campaign);
//    boolean updateCampaign(Campaign campaign);

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Campaign> getFeaturedCampaigns() {
        return campaignService.getFeaturedCampaigns();
    }

    @RequestMapping(path = "/user-campaigns", method = RequestMethod.GET)
    public List<Campaign> getCampaignsByManagerId(Principal userInfo) {
        return campaignService.getCampaignsByManagerId(userInfo);
    }

//    @RequestMapping(path = "/user-campaigns/{userId}", method = RequestMethod.GET)
//    public List<Campaign> getCampaignsByManagerId(@PathVariable int userId) {
//        return campaignDao.getCampaignsByManagerId(userId);
//    }

    @RequestMapping(path = "/donor-campaigns", method = RequestMethod.GET)
    public List<Campaign> getCampaignsByDonorId(Principal userInfo) {
        return campaignService.getCampaignsByDonorId(userInfo);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Campaign> getCampaignsBySearch(@RequestParam String searchTerm) {
        return campaignService.getCampaignsBySearch(searchTerm);
    }
    @RequestMapping(path = "/tag", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public List<Campaign> getCampaignsByTag(@RequestParam String tag) {
        return campaignService.getCampaignsByTag(tag);
    }

    @RequestMapping(path = "/{campaignId}", method = RequestMethod.PUT)
    public boolean updateCampaign(@RequestBody Campaign campaign, @PathVariable int campaignId) {
        try {
            campaignDao.updateCampaign(campaign, campaignId);
            return true;

        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found");
        }
    }
    @RequestMapping(path = "/{campaignId}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public Campaign getCampaignById(@PathVariable int campaignId){
        return campaignDao.getCampaignById(campaignId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignDao.createCampaign(campaign);

    }
    @RequestMapping(path = "/{campaignId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable int campaignId) {
        try {
            campaignDao.deleteCampaign(campaignId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found");
        }
    }

    //not the most secure thing to do but for now whatever
    @PreAuthorize("permitAll")
    @RequestMapping(path = "/username/{managerId}", method = RequestMethod.GET)
    public String getUsernameByManagerId(@PathVariable int managerId){
        if(managerId != 0){
            return userDao.getUserById(managerId).getUsername();
        }
        return "N/A"; //kind of a cop out
    }
}
