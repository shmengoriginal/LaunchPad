package com.techelevator.controller;

import com.techelevator.dao.DonationDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Donation;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/donations")
//@PreAuthorize("isAuthenticated()")
public class DonationController {
    private DonationDao donationDao;
    private UserDao userDao;

    public DonationController(DonationDao donationDao, UserDao userDao) {
        this.donationDao = donationDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/campaign/{campaignId}", method = RequestMethod.GET)
    public List<Donation> getDonationsByCampaignId(@PathVariable int campaignId){
        return donationDao.getDonationsByCampaignId(campaignId);
    }

    @RequestMapping(path = "/donor/{donorId}", method = RequestMethod.GET)
    public List<Donation> getDonationsByDonorId(@PathVariable int donorId){
        return donationDao.getDonationsByDonorId(donorId);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Donation createDonation(@RequestBody Donation donation){
        return donationDao.createDonation(donation);
    }

    //not the most secure thing to do but for now whatever
    @RequestMapping(path = "/username/{donorId}", method = RequestMethod.GET)
    public String getUsernameByDonorId(@PathVariable int donorId){
        return userDao.getUserById(donorId).getUsername();
    }
}
