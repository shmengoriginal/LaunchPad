<template>
  <!-- NEED TO MAKE SURE I CAN SEE CURRENT USERS CAMPAIGNS ONCE CREATE CAMPAIGNS WORKS-->
  <site-header />
    <div class="dashboard">
    <body>
      <button @click="$router.push( { name: 'createCampaign'} )">Start a new campaign</button>
      <h3>Your Campaigns</h3> 
    </body>
      <div>
        <campaign-list :campaigns="campaigns" :isDashboard="true"
          @delete-complete="refreshList"
        />
      </div>
    
  </div>
</template>

<script>
import CampaignList from '../components/CampaignList.vue'; 
import SiteHeader from '../components/SiteHeader.vue';
import campaignService from '../services/CampaignService.js';

export default{
    components: {
        SiteHeader,
        CampaignList
    },
    data(){
      return {
        campaigns: [] //not really sure just using this isnt working
        //   isLoading: true
      }
    },
  methods: {
    refreshList() {
        console.log('something should happen here');
        this.callUserCampaigns();
    },
    callUserCampaigns() { //I made this a diff name from getUserCampaigns just in case, probably doesnt matter
      //principal works now yay
      campaignService.getUserCampaigns().then(response => {
        this.campaigns = response.data;
        // this.isLoading = false;
      }).catch( error => {
        console.log(error);
      })
      if(this.campaigns.length === 0){
        this.campaigns = [{
          campaignId: 0,
          title: "No Campaigns created",
          endDate: "",
          goal: 0.00,
          managerId: 0,
          imgURL: "",
          funding: 0.00,
          description: "",
          isPublic: false
        }];
      }
    },
    
  },
  created() {
    this.callUserCampaigns();
  }
    // computed: {
    // userCampaigns(){
    //   const filteredCampaigns = this.$store.state.campaigns.filter( campaign => {
    //     return campaign.campaignManagerId === this.$store.state.user.id;
    //   });
    //   return filteredCampaigns;
    // },
    // userCampaigns(){
    //   this.callUserCampaigns();
    //   const test = this.campaigns; //for debugging
    //   // if(this.campaigns.length === 0){
    //   //   return [{
    //   //     campaignId: -1,
    //   //     title: "No Campaigns created",
    //   //     endDate: "",
    //   //     goal: 0.00,
    //   //     managerId: 8,
    //   //     imgURL: "",
    //   //     funding: 0.00,
    //   //     description: "",
    //   //     isPublic: false
    //   //   }]
    //   // }
    //   return this.campaigns;
    // }
  // }
}
</script>

<style scoped>
  .dashboard{
    margin: 3%;
    margin-top: 2rem;
  }
  body{
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  button{
    margin-top: 1rem;
    margin-bottom: 1rem;
    padding: 0.5rem 1rem; 
    font-size: 1.3rem; 
    /* font-weight: bold; */
    background-color: #f3880e;
    width: 100%;
    max-width: 310px; 
    height: 3rem;
  }
  
  h3{
    /* width: 100%; */
    font-size: 1.5rem;
  }
</style>