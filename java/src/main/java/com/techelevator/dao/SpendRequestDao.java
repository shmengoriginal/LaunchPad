package com.techelevator.dao;

import com.techelevator.model.SpendRequest;

import java.util.List;

public interface SpendRequestDao {
    SpendRequest createSpendRequest(SpendRequest spendRequest);
    boolean updateSpendRequest(SpendRequest spendRequest);
    SpendRequest getSpendRequestById(int id);
    List<SpendRequest> getSpendRequestsByCampaignId(int id);


}
