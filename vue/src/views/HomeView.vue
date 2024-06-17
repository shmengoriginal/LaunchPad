<template>
  <div>
  <!-- CONTENT SHOULD BE GOOD -->
  <site-header />
  <div class="home">
    
    <main class="content">
      <h3>Search For a Campaign</h3>
      <!-- <div class="search-container">
        <input type="text" id="search" v-model="searchText" />
        <button @click="searchCampaigns">Search</button>
      </div> -->

      <div class="search-container">
        <input 
          type="text" 
          id="search" 
          v-model="searchText" 
          class="search-input" 
          placeholder="Search"
          @keyup.enter="searchCampaigns"
          @click="handleIconClick"
        />
        <button class="search-button" @click="searchCampaigns">
          <img src="../assets/search-icon.svg" alt="Search Icon" />
        </button>
      </div>

      <div class="tags">
        <span v-for="featuredTag in featuredTags" v-bind:key="featuredTag.id" @click="getCampaignByTag(featuredTag.description)">{{ featuredTag.description }}</span>
      </div>
      
      <button class="get-started" @click="this.$router.push({ name: 'createCampaign' })">Get Started</button>
      <h3>Browse Featured Campaigns</h3> 
      
    </main>
    <div>
        <campaign-list :campaigns="campaigns" :isDashboard="false" />
      </div>
    <body>
      <!-- {{ this.$store.state.user }} -->
      <!-- Testing routing, delete later, things don't show up but I think it's because no data is reachable atm -->
      <!-- <div id="nav">
        <router-link v-bind:to="{ name: 'campaignDetails', params: {id: 1} }">Details test</router-link>&nbsp;|&nbsp;
        <router-link v-bind:to="{ name: 'voting' }" v-if="this.$store.state.token != ''">Voting test</router-link>&nbsp;|&nbsp;
        <router-link v-bind:to="{ name: 'view' }" v-if="this.$store.state.token != ''">view test</router-link>&nbsp;|&nbsp;
        <router-link v-bind:to="{ name: 'request' }" v-if="this.$store.state.token != ''">request test</router-link>
      </div> -->

      <!--
        
        Noted out this section of the code for now


      <h3>Search For a Campaign</h3>
      <div>
        <input type="text" id="search"  v-model="searchText"/>
        <button @click="searchCampaigns" >Search</button>
      </div>
      
      <button @click="this.$router.push( {name: 'createCampaign'} );">Get Started</button>
      <h3>Browse Featured Campaigns</h3> 
      <div>
        <campaign-list :campaigns="campaigns" :isDashboard="false"/>
      </div>


    Noted out this section of the code for now 
  
  -->
    </body>
  
  </div>
</div>
</template>

<script>
import CampaignList from '../components/CampaignList.vue'; 
import SiteHeader from '../components/SiteHeader.vue';
import campaignService from '../services/CampaignService.js';

export default {
  components: {
    SiteHeader,
    CampaignList
  },
  data(){
    return {
      campaigns: [], 
    //   isLoading: true
      searchText: "",
      featuredTags: [],
      tag:{},
    }
  },
  methods: {
    getFeaturedCampaigns() {
      campaignService.getFeaturedCampaigns().then(response => {
        this.campaigns = response.data;
        // this.isLoading = false;
      });
    },
    searchCampaigns(){//just doesnt work for some reason, figured it out, somethign with naming conflicts
      campaignService.searchCampaigns(this.searchText).then(response => {
        this.campaigns = response.data;
      });
      console.log(this.searchText);
      console.log(this.campaigns);
    },
    getFeaturedTags() {
      campaignService.getFeaturedTags().then(response => {
        this.featuredTags = response.data;
        // this.isLoading = false;
      });
    },
    getTagById(id){
      campaignService.getTagById(id).then(response =>{
        this.tag = response.data;
      });
    },
    getCampaignByTag(tag){
      campaignService.getCampaignsByTag(tag).then(response =>{
        this.campaigns = response.data;
        
      })
      .catch(error=>{console.log(error)});

    },
  },
  created() { //what does this do again
    this.getFeaturedCampaigns();
    this.getFeaturedTags();
  },
  computed: {
    // publicCampaigns(){ //from the fake store data, can delete later
    //   const filteredCampaigns = this.$store.state.campaigns.filter( campaign => {
    //     return campaign.isPublic === true;
    //   });
    //   return filteredCampaigns;
    // },
    // featuredCampaigns(){
    //   this.getFeaturedCampaigns();
    //   const test = this.campaigns; //for debugging
    //   return this.campaigns;
    // }
    
  }
};
</script> 

<style scoped>
.home{
    margin: 3%;
    margin-top: 2rem;
    margin-bottom: 2rem;;
  }

.content {
  font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
  padding-top: 1rem;
}

.tags{
  white-space: normal;
  width:auto;
  margin-bottom: 2rem;
  align-self: start;
  margin-left: 10%;
}
.tags span{
  color: rgb(184, 113, 6);
  margin: 2%;
  padding-top: 1%;
  padding-bottom: 1%;
  padding-inline: 3%;
  background-color: #fae098;
  border-radius: 20px;
  border: 1px solid #db8605;
}
.tags span:hover{
  color: rgb(184, 62, 6);
  background-color: #e7b872;
}

h3{
  width: 100%;
  font-size: 1.5rem;
}

.search-container {
  margin: 1rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 1rem;
  width: 100%;
  max-width: 300px; /* Adjust as necessary for mobile */
  
  height: 100%;
  width: 100%;
  border: 2px solid #369c07;
  border-radius: 5px;
  padding: .2rem;
}

.search-input {
  flex: 1;
  padding: 0.4rem;
  font-size: 0.9rem; /* Adjust font size for mobile */
  border: 1px solid #91b382;
  border-radius: 4px 0 0 4px; /* Rounded corners on the left */
  box-sizing: border-box;
}

.search-input::placeholder {
  color: #6ca15c; /* Adjust the placeholder text color if needed */
}

.search-input:focus {
  outline: none;
  border-color: #007bff;
}

.search-button {
  padding: 0.4rem;
  background-color: #c0deb7;
  color: white;
  border: none;
  border-radius: 0 4px 4px 0; /* Rounded corners on the right */
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.search-button img {
  width: 16px; /* Adjust the size of the icon */
  height: 16px;
}

.get-started {
  margin-top: 1rem;
  margin-bottom: 3rem;
  padding: 0.5rem 1rem; 
  font-size: 1.3rem; 
  /* font-weight: bold; */
  background-color: #f3880e;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  max-width: 310px; 
  height: 3rem
}
</style>