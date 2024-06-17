package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.SpendRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcSpendRequestDao implements SpendRequestDao{
    private final JdbcTemplate jdbcTemplate;
    public JdbcSpendRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SpendRequest createSpendRequest(SpendRequest spendRequest) {
       SpendRequest newSpendRequest = null;
       String sql = "INSERT INTO spend_request(campaign_id, spend_description, spend_status) VALUES (?, ?, ?) RETURNING spend_request_id";
       try{
           Integer newSpendId = jdbcTemplate.queryForObject(sql, Integer.class, spendRequest.getCampaignId(), spendRequest.getSpendDescription(), spendRequest.getSpendStatus());
        if(newSpendId != null) {
            newSpendRequest = getSpendRequestById(newSpendId);
        } else {
            throw new IllegalStateException("Failed to create spend request.");
        }
    } catch (
    CannotGetJdbcConnectionException e) {
        System.out.println(e.getMessage());
    } catch (
    DataIntegrityViolationException e) {
        System.out.println(e.getMessage());
    }
        return newSpendRequest;

}

    @Override
    public boolean updateSpendRequest(SpendRequest spendRequest) {
        SpendRequest updatedSpend = null;
        String sql = "UPDATE spend_request SET spend_description = ?, spend_status = ?";
        jdbcTemplate.update(sql, spendRequest.getSpendDescription(), spendRequest.getSpendStatus());
        updatedSpend = getSpendRequestById(spendRequest.getSpendRequestId());
        if (updatedSpend != null){
            return true;
        }
        return false;
    }

    @Override
    public SpendRequest getSpendRequestById(int id) {
        String sql = "SELECT * FROM spend_request WHERE spend_request_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToSpendRequest(results);
        } else {
            return null;
        }
    }

    @Override
    public List<SpendRequest> getSpendRequestsByCampaignId( int id) {
        List<SpendRequest> spendRequests = new ArrayList<>();
        String sql = "SELECT * FROM spend_request WHERE campaign_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while(results.next()){
                SpendRequest spendRequest = mapRowToSpendRequest(results);
                spendRequests.add(spendRequest);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return spendRequests;
    }
    private SpendRequest mapRowToSpendRequest(SqlRowSet rs){
        SpendRequest spendRequest = new SpendRequest();
        spendRequest.setSpendRequestId(rs.getInt("spend_request_id"));
        spendRequest.setSpendDescription(rs.getString("spend_description"));
        spendRequest.setCampaignId(rs.getInt("campaign_id"));
        spendRequest.setSpendStatus(rs.getInt("spend_status"));
        return spendRequest;
    }
}
