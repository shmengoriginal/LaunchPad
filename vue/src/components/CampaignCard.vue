<template>
    <!-- NEED TO SHOW BUTTONS WHEN ITS DASHBOARD -->
    <div :class = "{'card': !isGoalReached, 'goal-reached': isGoalReached}">
      <div class="flex-row">
        <router-link v-bind:to="{ name: 'campaignDetails', params: { id: campaign.campaignId } }" class="card-image">
          <!-- <img src="../assets/Launchpad-logo.png" alt="Campaign Image" /> -->
          
          <img :src="campaignImage" alt="Campaign Image" />
          <!-- <img data-v-7377613c="" src="../assets/campaignPictures/clothes.jpg" alt="Campaign Image"> -->
        </router-link>


        <div class="info">
          
          <p class="title">{{ campaign.title }}</p>
          <p class="manager">Managed by {{ managerName }}</p>
          <p class="description">{{ campaign.description }}</p>
          
          <!-- <div class="tags" v-for="tag in tags" :key="tag.id">
            <span>{{ tag.description }}</span>
          </div> -->

          <transition name="fade">
            <p class="error-msg" v-if="errorMsg != ''">{{ errorMsg }}</p>
          </transition>
        </div>
      </div>
      <ProgressBar class="progress" :funding="campaign.funding" :goal="campaign.goal"/>
      <p class="funding">{{goalMsg}}</p>
      <div v-show="isDashboard" class="dashboard-buttons">
        <button @click="$router.push({ name: 'editCampaign', params: { id: campaign.campaignId } })">Edit</button>
        <button @click="deleteCampaign(); toastPopUp();">Delete</button>
        <button @click="$router.push({ name: 'CreateSpendRequest'})">Create Spend Request</button>
        <!-- <router-link to="/create-spend-request" class="button-link">Create Spend Request</router-link> -->
      </div>
      
    </div> 
</template>

<!-- <script>
import campaignService from '../services/CampaignService.js';
import ProgressBar from './ProgressBar.vue';

export default {
  components: {
    ProgressBar
  },
  data() {
    return {
      errorMsg: '',
      donations: [],
      managerName: 'N/A',
      campaignImage: '',
  
    };
  },
  props: ['campaign', 'isDashboard'],
  methods: {
    deleteCampaign() { // for now shouldn't work, uncomment and work on after endpoint is set
      const donations = this.donations;
      console.log(donations.length);
      if (donations.length > 0) { //we need to be able to get donors associated with a campaign id
        this.errorMsg = 'Cannot delete campaign';
        this.$store.commit('SET_NOTIFICATION', 'Cannot delete campaigns that have donors!');
      } else {
        if (confirm("Are you sure you want to delete this campaign? This action cannot be undone.")) {
          campaignService.deleteCampaign(this.campaign.campaignId).then(response => {
            if (response.status === 204) {
              this.$emit('delete-complete');
            }
          })
          .catch(error => {
            if (error.response) {
              this.$store.commit('SET_NOTIFICATION', `Error deleting campaign. Response received was "${error.response.statusText}".`);
            } else if (error.request) {
              this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Server could not be reached.');
            } else {
              this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Request could not be created.');
            }
          });
        }
      }
    },
    getDonations() {
      campaignService.getDonationsByCampaignId(this.campaign.campaignId).then(response => {
        if (response.status === 200) {
          this.donations = response.data;
        }
      });
      return [];
    },
    getRandomImage() {
      
      const images = [
        '../../public/campaignPictures/school.jpg',
        '../../public/campaignPictures/art.jpg',
        '../../public/campaignPictures/clothes.jpg',
        '../../public/campaignPictures/elephants.jpg',
        '../../public/campaignPictures/food.jpg',
        '../../public/campaignPictures/medicalsupplies.jpg',
        '../../public/campaignPictures/waterwell.jpg',
      ];
      const randomIndex = Math.floor(Math.random() * images.length);
      console.log(images[randomIndex]);
      return images[randomIndex];
    }
  },
  created() {
    campaignService.getDonationsByCampaignId(this.campaign.campaignId).then(response => {
      if (response.status === 200) {
        this.donations = response.data;
      }
    });

    campaignService.getUsernameByManagerId(this.campaign.managerId).then(response => {
      if (response.status === 200) {
        this.managerName = response.data;
      }
    });

    // Set a random campaign image
    this.campaignImage = this.getRandomImage();
  }
};
</script>  -->



<!-- The line script file below this line is the original script file.  -->

<script>
import campaignService from '../services/CampaignService.js';
import ProgressBar from './ProgressBar.vue';
export default{
    components: {
      ProgressBar
    },
    data(){
      return{
        errorMsg: '',
        donations : [],
        managerName: 'N/A',
        tags: [],
        campaignImage: ''
      }
    },
    props: ['campaign', 'isDashboard'],
    computed: {
      isGoalReached(){
        if(this.campaign.funding >= this.campaign.goal){
          return true;
        }else{
          return false; 
        }
      },
      goalMsg(){
        if(this.campaign.funding >= this.campaign.goal){
          return `Goal $${ this.campaign.goal } reached! :)`;
        }else{
          return `$${ this.campaign.funding } / $${ this.campaign.goal } Currently raised`; 
        }
      }
    },
    methods: {
      toastPopUp(){
        if(this.errorMsg != ''){
          setTimeout(() => {
            this.resetToast();
          }, 1500);
        }
      },
      resetToast(){
        this.errorMsg = '';
      },
      deleteCampaign() { // for now shouldn't work, uncomment and work on after endpoint is set
      const donations = this.donations;
          if(donations.length > 0){//we need to be able to get donors associated with a campaign id
            this.errorMsg = 'Cannot delete a campaign backed by donors';
            this.$store.commit(
                'SET_NOTIFICATION', 'Cannot delete campaigns that have donors!');
          }else{
              if (confirm("Are you sure you want to delete this campaign? This action cannot be undone.")) {
              campaignService.deleteCampaign(this.campaign.campaignId).then(response => {
                  if (response.status === 204) {
                      this.$emit('delete-complete');
                  }
              })
              .catch(error => {
                  if (error.response) {
                      this.$store.commit('SET_NOTIFICATION',
                      `Error deleting campaign. Response received was "${error.response.statusText}".`);
                  } else if (error.request) {
                      this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Server could not be reached.');
                  } else {
                      this.$store.commit('SET_NOTIFICATION', 'Error deleting campaign. Request could not be created.');
                  }
              });
          }
          }
          
      },
      getDonations(){
        campaignService.getDonationsByCampaignId(this.campaign.campaignId).then(response => {
          if(response.status === 200){
            this.donations = response.data;
          }
        });
        return [];
      },
      getRandomImage() {
        const images = [
          '../../public/campaignPictures/school.jpg',
          '../../public/campaignPictures/art.jpg',
          '../../public/campaignPictures/clothes.jpg',
          '../../public/campaignPictures/elephants.jpg',
          '../../public/campaignPictures/food.jpg',
          '../../public/campaignPictures/medicalsupplies.jpg',
          '../../public/campaignPictures/waterwell.jpg',
        ];
        const randomIndex = Math.floor(Math.random() * images.length);
        // console.log(images[randomIndex]);
        return images[randomIndex];
      }
    }, 
    created() {
      campaignService.getDonationsByCampaignId(this.campaign.campaignId).then(response => {
            if(response.status === 200){
              this.donations = response.data;
            }
          });

      campaignService.getUsernameByManagerId(this.campaign.managerId).then(response => {
        if(response.status === 200){
          this.managerName = response.data;
        }
      });

      campaignService.getTagsByCampaignId(this.campaign.campaignId).then(response => {
        if(response.status === 200){
          this.tags = response.data;
        }
      });
      this.campaignImage = this.getRandomImage();
    }
}
</script>

<style scoped>
body {
  padding: 0;
}

.card:not(.progress) {
  border: 2px solid #f2a44a;
  border-radius: 10px;
  width: 100%;
  margin: 20px auto;
  
  background-color: #fffbec;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: auto; /* Standard height for all cards */
}

.goal-reached:not(.progress) {
  border: 2px solid #9dd085;
  border-radius: 10px;
  width: 100%;
  margin: 20px auto;
  color: #168e1c;
  background-color: #dcf9a9;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  height: auto; /* Standard height for all cards */
}

.progress{
  margin: .5rem 1rem;
}

.funding{
  color:#168e1c;
  margin: .5rem 1rem;
  margin-bottom: 1rem;
  /* padding: 1rem; */
  font-size: 0.8rem;
  font-weight: bold;
}

.flex-row{
  display: flex;
  flex-direction: row;
  height: 80%;
}

.card-image {
  width: 40%;
  height: 150px;
}

.card-image img {
  width: 100%;
  height: 90%;
  border-radius: 10px;
  margin: 1rem;
  /* overflow: hidden; */
  object-fit: cover; 
}

.info:not(.progress) {
  width: 70%;
  padding: 0.5rem; /* Reduced padding */
  margin-left: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: start;
  margin-top: .5rem;;
  /* justify-content: space-between; */
  /* align-items: flex-start; */
}

.fade-leave-active{
  transition: opacity .5s;
}
.fade-leave-to{
  opacity: 0;
}
.error-msg{
  color: red;
  font-size: 0.9rem;
  margin-top: 1rem;
  /* align-self: end; */
  font-weight: bold;
  margin-right: 1rem;
}

.manager,
.description{
  margin: 0; /* Remove default margin */
  padding: 0.25rem 0; /* Add small padding for spacing */
  font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-size: 0.8rem;
  font-style: oblique;
}

.title {
    font-weight: bolder;
    font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
}

.dashboard-buttons {
  margin: 1rem;
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem; /* Adjust margin for spacing */
  /* width: 100%; */
  height: 3rem;
}

/* .dashboard-buttons button {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  flex: 1;
  margin: 0 0.05rem;
} */

.dashboard-buttons button{
  padding: 0.6rem;
  /* font-size: .8rem; */
  font-size: .9rem;
  font-weight: bold;
  /* background-color: #e7a325; */
  background-color: #f0b81f;

  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  flex: 1; 
  margin: 0 0.07rem; 
  text-align: center; 
  text-decoration: none; 
  display: inline-block; 
}

.dashboard-buttons button:hover {
  background-color: #b35f00; /* Add hover effect */
}
/* .dashboard-buttons button:last-child {
  background-color: #ff9900;
  color: white;
} */

.tags{
  display: flex;
  flex-direction: row;
}

@media (min-width: 600px) {
  .card {
    height: 200px; /* Maintain the same height for larger screens */
  }
}
</style>