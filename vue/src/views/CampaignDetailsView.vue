<template>
    <div>
    <!-- Connection works -->
    <SiteHeader />
    <!-- <div>{{ isUserAnon }}, {{ isUserRegistered }}</div> -->
    <!-- I think this was meant to be the component not the view, so changed the names -->
    <!-- <CampaignDetailsAnonView /> -->
    <CampaignDetailsAnon v-if="isUserAnon" :campaign="campaign" />
    <CampaignDetailsRegistered v-if="isUserRegistered" :campaign="campaign" :campaignId="this.$route.params.id"/>
</div>
    <!-- <CampaignDetailsAnon v-if="isUserAnon" :campaign="campaign" /> -->
    <!-- <CampaignDetailsRegistered :campaign="campaign" :campaignId="this.$route.params.id" :managerName="managerName" :donations="donations"/>
    <VoteSpendRequest /> -->
</template>


<script>
// import CampaignDetailsAnonView from '../components/CampaignDetailsForm.vue';
import CampaignDetailsAnon from '../components/CampaignDetailsAnon.vue';
import SiteHeader from '../components/SiteHeader.vue';
import campaignService from '../services/CampaignService.js';
import CampaignDetailsRegistered from '../components/CampaignDetailsRegistered.vue';
import VoteSpendRequest from '../components/VoteSpendRequest.vue';

export default{
    
    components: {
    // CampaignDetailsAnonView,
    // CampaignDetailsAnon,
    CampaignDetailsRegistered,
    SiteHeader,
    VoteSpendRequest
},
    data() {
        return {
            campaign: {
                campaignId: 0,
                title: "",
                endDate: '0000-00-00',
                goal: 0.00,
                managerId: 0,
                imgURL: "",
                funding: 0.00,
                description: "",
                isPublic: false
            },
            managerName: 'N/A',
            donations: [],
            isLoading: true
        };
    },
    computed: { //so we actually dont need these, can just check if the token is empty or not oops
        isUserAnon(){
            return JSON.stringify(this.$store.state.user) === "{}";
        },
        isUserRegistered(){
            return JSON.stringify(this.$store.state.user) != "{}";
        }
    },
    created() {
    //   const id = this.$route.params.id; //for debugging
      campaignService
      .getCampaignById(this.$route.params.id)
      .then(response => {
        if(response.status === 200){
            this.campaign = response.data;
            
            campaignService.getUsernameByManagerId(this.campaign.managerId).then(response => {
            if(response.status === 200){
            this.managerName = response.data;
            // console.log(this.managerName); 
            this.isLoading = false;
            }
            });
        }
        
      });

      campaignService.getDonationsByCampaignId(this.$route.params.id).then(response => {
      if(response.status === 200){
        this.donations = response.data;
        this.donations.forEach( donation =>{
          campaignService.getUsernameByDonorId(donation.donorId).then(response => {
          if(response.status === 200){
              donation.name = response.data;
              this.isLoading = false;
            }
          });
        });        
      }
    });
  }
}
</script>

