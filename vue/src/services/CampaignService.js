import axios from 'axios';

const baseUrl = axios.create({
  baseURL: "http://localhost:9000"
});

export default {

  getFeaturedCampaigns() {
    return axios.get('/campaigns');
  },

  getUserCampaigns(){
    return axios.get('/campaigns/user-campaigns');
  },

  // backup plan for get users campaigns
  // getUserCampaigns(userId){
  //   return axios.get(`/campaigns/user-campaigns/${userId}`);
  // },
  getCampaignById(campaignId){ 
    return axios.get(`/campaigns/${campaignId}`);
  },
  updateCampaign(campaign, campaignId){ //not sure if works yet, other frontend issues getting in the way
    return axios.put(`/campaigns/${campaignId}`, campaign);
  },
  createCampaign(campaign) { 
    return axios.post('/campaigns', campaign);
  },
  deleteCampaign(campaignId){
    return axios.delete(`/campaigns/${campaignId}`);
  },
  searchCampaigns(searchTerm){
    return axios.get(`campaigns/search?searchTerm=${searchTerm}`);
  },

  
  createDonation(donation){
    return axios.post('/donations', donation);
  },
  getDonationsByCampaignId(campaignId){
    return axios.get(`/donations/campaign/${campaignId}`);
  },
  getDonationsByDonorId(donorId){
    return axios.get(`/donations/donor/${donorId}`);
  },

  getFeaturedTags(){
return axios.get(`/featured`);
  },
  getTagById(id){
return axios.get(`/tag/${id}`);
  },
  getCampaignsByTag(tag){
    return axios.get(`/campaigns/tag?tag=${tag}`);
  },
  getTagsByCampaignId(campaignId){
    return axios.get(`/tags/${campaignId}`);
  },
  getTags(){
    return axios.get('/tags');
  },

  getUsernameByManagerId(managerId){
    return axios.get(`campaigns/username/${managerId}`)
  },
  getUsernameByDonorId(donorId){
    return axios.get(`donations/username/${donorId}`)
  }
};


//Centralize all API interactions related to campaigns

