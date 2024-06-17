<template>
    <!-- STILL NEEDS WORK -->
    <site-header />
    <body>
      <h1 class="campaign-form-h1">Edit Campaign</h1>
      <div class="loading" v-if="isLoading">
        <p>Loading...</p>
      </div>
      <campaign-form v-show="!isLoading" :campaign="campaign" :allTags="allTags" :campaignTags="campaignTags" ref="editForm"/>
    </body>
  </template>
  
  <script>
  
  import campaignService from '../services/CampaignService';
  import CampaignForm from '../components/CampaignForm.vue';
  import SiteHeader from '../components/SiteHeader.vue';

  
  export default {
    components: {
        CampaignForm,
        SiteHeader
    },
    data() {
      return {
        campaign: {},
        allTags: [],
        campaignTags: [],
        isLoading: true
      }
    },
    methods: {
        //I did this in a wacky way, can move method content into created(), refactor later if there's time
      getCampaign(id) {
        campaignService.getCampaignById(id)
          .then(response => {
            this.campaign = response.data;
            this.$refs.editForm.updateFormData(this.campaign);
            this.isLoading = false;
          })
          .catch(error => {
            if (error.response) {
              if (error.response.status == 404) {
                this.$router.push({name: 'NotFoundView'});
              } 
              else {
                this.$store.commit('SET_NOTIFICATION',
                `Error getting message. Response received was "${error.response.statusText}".`);
              }
            } else if (error.request) {
              this.$store.commit('SET_NOTIFICATION', `Error getting message. Server could not be reached.`);
            } else {
              this.$store.commit('SET_NOTIFICATION', `Error getting message. Request could not be created.`);
            }
          })
      },
      getAllTags(){
        campaignService.getTags().then(response => {
          if(response.status === 200){
            this.allTags = response.data;
          }
        });
      },
      getCampaignTags(id){
        campaignService.getTagsByCampaignId(id).then(response => {
          if(response.status === 200){
            this.campaignTags = response.data;
            this.$refs.editForm.updateCampaignTagData(this.campaignTags);
          }
        });
      }
    },
    created() {
      console.log(this.$route.params.id);
      this.getCampaign(this.$route.params.id);
      this.getAllTags();
      this.getCampaignTags(this.$route.params.id);

    }
  };
  </script>
  
  <style scoped>
    body{
      margin: .7rem;
    }
  </style>
  