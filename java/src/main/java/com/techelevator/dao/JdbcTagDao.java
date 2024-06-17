package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Campaign;
import com.techelevator.model.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTagDao implements TagDao{
    private final JdbcTemplate jdbcTemplate;
    public JdbcTagDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Tag createTag(String tag) {
        Tag newTag = null;
        String sql = "INSERT INTO tag(tag_description) VALUES (?) RETURNING tag_id";
        try{
            Integer newTagId = jdbcTemplate.queryForObject(sql, Integer.class,tag);
            if(newTagId != null){
                newTag = getTagById(newTagId);
            }else {
                throw new IllegalStateException("Failed to create campaign.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return newTag;
    }

    @Override
    public Tag getTagById(int id) {
        String sql = "SELECT * FROM tag WHERE tag_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return mapRowToTag(results);
        } else {
            return null;
        }
    }

    @Override
    public List<Tag> getTagsByCampaignId(int id) {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tag JOIN campaign_tag ON tag.tag_id = campaign_tag.tag_id WHERE campaign_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while (results.next()) {
                Tag tag = mapRowToTag(results);
                tags.add(tag);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return tags;

    }
    @Override
    public int deleteTag(int id){
        int rowCount = 0;
        String sql = "DELETE FROM tag WHERE tag_id = ?";
        try {
            rowCount = jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return rowCount;
    }

    @Override
    public List<Tag> getFeaturedTags(){
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT t.tag_description, t.tag_id, COUNT(ct.tag_id) AS tag_count FROM tag t JOIN campaign_tag ct ON t.tag_id = ct.tag_id GROUP BY t.tag_description, t.tag_id ORDER BY tag_count DESC LIMIT 5";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Tag tag = mapRowToTag(results);
                tags.add(tag);
            }
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return tags;}

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM tag";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            tags.add( mapRowToTag(results));
        }
        return tags;
    }

    private Tag mapRowToTag(SqlRowSet rs) {
        Tag tag = new Tag();
        tag.setDescription(rs.getString("tag_description"));
        tag.setId(rs.getInt("tag_id"));
        return tag;
    }

}
