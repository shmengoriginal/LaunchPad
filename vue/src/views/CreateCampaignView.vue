<!-- add header here  -->
<template>
    <div>
    <site-header />
    
    <body>
        <!-- SHOULD BE OK -->
        
        <h1 class="campaign-form-h1">Create a campaign</h1>
        <!-- <div class="loading" v-if="isLoading">
            <p>Loading...</p>
        </div> -->
        <campaign-form :campaign="campaign" :allTags="allTags" :campaignTags="campaignTags"/>
    </body>
</div>
</template>

<script>
import CampaignForm from '../components/CampaignForm.vue';
import SiteHeader from '../components/SiteHeader.vue';
import campaignService from '../services/CampaignService';

export default{
    components: {
        CampaignForm,
        SiteHeader
    },
    data(){
        return{
            campaign: {
                campaignId: 0,
                title: "",
                endDate: "",
                goal: "",
                managerId: this.$store.state.user.id,
                imgURL: "",
                funding: 0,
                description: "",
                isPublic: false
            },
            allTags: [],
            campaignTags: []
        }
    },
    methods: {
        getAllTags(){
        campaignService.getTags().then(response => {
          if(response.status === 200){
            this.allTags = response.data;
          }
        });
      }
    },
    created() {
      this.getAllTags();
    }
}
</script>

<style scoped>
body{
    margin: .7rem;
}
</style>
