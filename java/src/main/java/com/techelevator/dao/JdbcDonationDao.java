package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.model.Donation;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDonationDao implements DonationDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcDonationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Donation createDonation(Donation donation) {
//        Donation donation = null;
        String sql = "INSERT INTO donations(donation_amount, donor_id, campaign_id, donation_date_time) VALUES (?, ?, ?, ?) RETURNING donation_id";
        try{
            Integer newDonationId = jdbcTemplate.queryForObject(sql, Integer.class,  donation.getAmount(), donation.getDonorId(), donation.getCampaignId(), donation.getDateTime());
            if(newDonationId != null) {
                //newDonation = getDonationById(newDonationId); // would need to create a new method, so keeping out for now
                donation.setDonationId(newDonationId);
            } else {
                throw new IllegalStateException("Failed to create transfer.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return donation;
    }

    @Override
    public List<Donation> getDonationsByCampaignId(int id) {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donations WHERE campaign_id = ? ORDER BY donation_amount DESC";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Donation donation = mapRowToDonation(results);
                donations.add(donation);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return donations;
    }

    @Override
    public List<Donation> getDonationsByDonorId(int id) {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM donations WHERE donor_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Donation donation = mapRowToDonation(results);
                donations.add(donation);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return donations;
    }
    private Donation mapRowToDonation(SqlRowSet rs){
        Donation donation = new Donation();
//        donation.setAmount(rs.getBigDecimal("amount"));
        //vvv changed name as per schema, ^^^original
        donation.setAmount(rs.getBigDecimal("donation_amount"));
        donation.setCampaignId(rs.getInt("campaign_id"));
        donation.setDonationId(rs.getInt("donation_id"));
        donation.setDonorId(rs.getInt("donor_id"));
        //added as per schema vvvv
        donation.setDateTime(rs.getTimestamp("donation_date_time"));
        return donation;
    }
}
