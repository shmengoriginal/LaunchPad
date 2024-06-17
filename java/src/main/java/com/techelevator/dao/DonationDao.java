package com.techelevator.dao;

import com.techelevator.model.Donation;

import java.math.BigDecimal;
import java.util.List;

public interface DonationDao {
//    Donation createDonation(BigDecimal amount, int donorId, int campaignId);
    Donation createDonation(Donation donation);
    List<Donation> getDonationsByCampaignId(int id);
    List<Donation> getDonationsByDonorId(int id);
}
