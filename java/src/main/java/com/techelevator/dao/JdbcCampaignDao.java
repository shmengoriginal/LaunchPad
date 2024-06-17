package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class JdbcCampaignDao implements CampaignDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCampaignDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Campaign> getFeaturedCampaigns() {
        List<Campaign> campaigns = new ArrayList<>();

        String sql = "SELECT * FROM campaigns WHERE is_public = true ORDER BY funding LIMIT 7";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Campaign campaign = mapRowToCampaign(results);
                campaigns.add(campaign);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return campaigns;
    }

    //wasn't sure if this was for the current user or for searching up a particular managers campaigns, It would be cool to have that searchable as well
    @Override
    public List<Campaign> getCampaignsByManagerId(int id) {
        List<Campaign> campaigns = new ArrayList<>();


        String sql = "SELECT * FROM campaigns WHERE manager_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Campaign campaign = mapRowToCampaign(results);
                campaigns.add(campaign);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return campaigns;
    }

    @Override
    public List<Campaign> getCampaignsByDonorId(int id) { //confused about this
        List<Campaign> campaigns = new ArrayList<>();


        String sql = "SELECT * FROM campaigns JOIN donations ON campaigns.campaign_id = donations.campaign_id " +
                "WHERE donor_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Campaign campaign = mapRowToCampaign(results);
                campaigns.add(campaign);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return campaigns;
    }

    @Override
    public List<Campaign> getCampaignsBySearch(String searchTerm) {
        List<Campaign> campaigns = new ArrayList<>();
        searchTerm = '%' + searchTerm.toLowerCase() + '%';

        String sql = "SELECT * FROM campaigns WHERE is_public = true AND (LOWER (description) LIKE ? OR LOWER(title) LIKE ?)";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchTerm, searchTerm);
            while (results.next()) {
                Campaign campaign = mapRowToCampaign(results);
                campaigns.add(campaign);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return campaigns;
    }

    @Override
    public Campaign getCampaignById(int id) {
        String sql = "SELECT * FROM campaigns WHERE campaign_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToCampaign(results);
        } else {
            return null;
        }
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        Campaign newCampaign = null;
        String sql = "INSERT INTO campaigns(title, end_date, goal, manager_id, image_url, funding, is_public, description) VALUES (?,?,?,?,?,?,?, ?) RETURNING campaign_id";
        try {
            Integer newCampaignId = jdbcTemplate.queryForObject(sql, Integer.class, campaign.getTitle(),
                    campaign.getEndDate(), campaign.getGoal(), campaign.getManagerId(), campaign.getImgURL(),
                    campaign.getFunding(), campaign.getisPublic(), campaign.getDescription());
            if (newCampaignId != null) {
                newCampaign = getCampaignById(newCampaignId);
            } else {
                throw new IllegalStateException("Failed to create campaign.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return newCampaign;

    }

    @Override
    public boolean updateCampaign(Campaign campaign, int campaignId) {
//        Campaign updatedCampaign = null;
        String sql = "UPDATE campaigns SET title = ?, end_date = ?, goal = ?, image_url = ?, is_public = ?, description = ?, funding = ? WHERE campaign_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, campaign.getTitle(), campaign.getEndDate(), campaign.getGoal(),
                    campaign.getImgURL(),
                    campaign.getisPublic(), campaign.getDescription(), campaign.getFunding(), campaignId);
//        updatedCampaign = getCampaignById(campaign.getCampaignId());
            return rowsAffected > 0;
        }catch (CannotGetJdbcConnectionException e) {

            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {

            throw new DaoException("Data integrity violation", e);
        } catch (Exception e) {

            throw new DaoException("Unexpected error occurred", e);
        }

    }

    @Override
    public List<Campaign> getCampaignsByTag(String tag) {
        List<Campaign> campaigns = new ArrayList<>();
        tag = '%' + tag.toLowerCase() + '%';

        String sql = "SELECT * FROM campaigns JOIN campaign_tag ON campaigns.campaign_id = campaign_tag.campaign_id JOIN tag ON campaign_tag.tag_id = tag.tag_id WHERE LOWER (tag_description) LIKE ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tag);
            while (results.next()) {
                Campaign campaign = mapRowToCampaign(results);
                campaigns.add(campaign);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return campaigns;
    }

    @Override
    public int deleteCampaign(int id) {
        int rowCount = 0;
        String deleteCampaignSql = "DELETE FROM campaigns WHERE campaign_id = ?";
        try {
            rowCount = jdbcTemplate.update(deleteCampaignSql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowCount;
    }

    private Campaign mapRowToCampaign(SqlRowSet rs) {
        Campaign campaign = new Campaign();
        campaign.setCampaignId(rs.getInt("campaign_id"));
        campaign.setTitle(rs.getString("title"));
        if (rs.getDate("end_date").toLocalDate() != null) {
            campaign.setEndDate(rs.getDate("end_date").toLocalDate());
        }
        campaign.setGoal(rs.getBigDecimal("goal"));
        campaign.setManagerId(rs.getInt("manager_id"));
        campaign.setImgURL(rs.getString("image_url"));
        campaign.setFunding(rs.getBigDecimal("funding"));
        campaign.setDescription(rs.getString("description"));
        campaign.setisPublic(rs.getBoolean("is_public"));
        return campaign;
    }
}
