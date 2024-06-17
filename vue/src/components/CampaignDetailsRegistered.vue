<template>
  <!-- <SiteHeader /> -->
  <div class="campaign-page">
    <!-- <div class="logo-container" v-if="campaign.imgURL">
      <img class="logo" src="../assets/Launchpad-logo-full.png" alt="Place Holder">
    </div> -->

    <!-- <img class="campaign-img" src="../assets/PLACEHOLDER_LOGO.png" alt="Place Holder"> -->
    <img class="campaign-img" :src="campaignImage" alt="Campaign Image" />

    <ProgressBar class="progress" :funding="campaign.funding" :goal="campaign.goal"/>
    <!-- <p class="funding">${{ campaign.funding }} raised out of our ${{ campaign.goal }} GOAL!</p> -->
    <p class="funding">{{ goalMsg }}</p>

    <div class="container">
      <h1 class="title">{{ campaign.title }}</h1>
      <!-- <h2 class="subtitle">Fund this Project!</h2> -->
      <p class="organizer">Organizer: {{ managerName }}</p>
      <p class="description">{{ campaign.description }}</p>

      
      <button :class="{'donate-button' : !isGoalReached, 'locked-button': isGoalReached}" @click="goToDonateForm">Donate</button>
      <span class="lock-msg" v-show="isGoalReached">Goal was reached. No more donations!</span>

      <p class="timeline">Timeline: {{ daysLeft }} Days Left!</p>
      <p class="donation-info">If {{ requiredDonors }} people donate ${{ donationAmount }}, the campaign will be over.</p>
      <h1 class="donors-title">Thank You To Our Donors!</h1>
      <p class="top-donors">Top Donors:</p>
      <div v-for="donation in donations.slice(0,5)" :key="donation.donationId">
        <p class="donor">Donor {{ donation.name }} donated ${{ donation.amount }} on {{donationDateTime(donation.dateTime) }}</p>
      </div>
    </div>
  </div>
</template>
  
<script>
import { mapGetters } from 'vuex';
import campaignService from '../services/CampaignService';
import ProgressBar from './ProgressBar.vue';
import SiteHeader from './SiteHeader.vue';

export default {
  props: ['campaign', 'campaignId', 'managerName', 'donations'], //bc of timing BUGS adding in campaignId to props, campaign is null in JS despite having data accessible in html
  components: {
    ProgressBar,
    // SiteHeader
  },
  data(){
    return{
      isLoading: true,
      campaignImage: ''
      // donations: []
      // managerName: 'N/A'
    }
  },
  methods: {
    donationDateTime(dateTime){
      const timestamp = new Date(dateTime);
      //.toLocaleDateString is a built in function for converting timestamp day info to weekday word, month word ect eg. 10 => October
      return `${timestamp.toLocaleDateString('en-US', { weekday: 'long' })}, ${timestamp.toLocaleDateString('en-US', { month: 'long' })} ${this.dayWithSuffix(timestamp.getDate())} ${timestamp.getFullYear()}`;
    },
    dayWithSuffix(day){ //for getting stuff like 1st, 2nd, 11th ect.
      if(day === 11 || day === 12 || day === 13){
        return `${day}th`;
      }else{
        const onesDigit = day % 10;
        if(onesDigit === 1){
          return `${day}st`;
        }else if(onesDigit === 2){
          return `${day}nd`;
        }else if(onesDigit === 3){
          return `${day}rd`;
        }else{
          return `${day}th`;
        }
      }
    },
    goToDonateForm(){
      if(!this.isGoalReached){
        this.$router.push({name: 'donationForm', params:{campaignId:this.campaign.campaignId}});
      }
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
  computed: {
    // ...mapGetters(['campaign']),
    isGoalReached(){
      if(this.campaign.funding >= this.campaign.goal){
        return true;
      }
      return false;
    },
    goalMsg(){
      if(this.isGoalReached){
        return `Campaign goal of $${ this.campaign.goal } reached! :)`;
      }else{
        return `$${ this.campaign.funding } / $${ this.campaign.goal } Currently raised`; 
      }
    },
    daysLeft() {
      if (!this.campaign.endDate) {
        return '';
      }
      const targetDate = new Date(this.campaign.endDate);
      const currentDate = new Date();
      const timeDifference = targetDate - currentDate;
      const daysLeft = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));
      if (daysLeft > 0) {
        return daysLeft;
      } else if (daysLeft === 0) {
        return 'Today!';
      } else {
        return 'This date has passed.';
      }
    },
    progressValue() {
      if (!this.campaign.goal || !this.campaign.funding) {
        return 0;
      }
      return Math.min((this.campaign.funding / this.campaign.goal) * 100, 100);
    },
    requiredDonors() {
      const amountNeeded = this.campaign.goal - this.campaign.funding;
      if (amountNeeded <= 0) {
        return 0;
      }
      return Math.ceil(amountNeeded / this.donationAmount);
    },
    donationAmount() {
      const baseDonation = 5;
      const increment = 5;
      let dynamicDonation = baseDonation;
      
      while ((this.campaign.goal / dynamicDonation) > 1000 && dynamicDonation < 100) {
        dynamicDonation += increment;
      }
      
      return dynamicDonation;
    }
  },
  created(){
    this.campaignImage = this.getRandomImage();
  }
  // created(){ //brute force way ig
  //   campaignService.getDonationsByCampaignId(this.campaignId).then(response => {
  //     if(response.status === 200){
  //       this.donations = response.data;
  //       this.donations.forEach( donation =>{
  //         campaignService.getUsernameByDonorId(donation.donorId).then(response => {
  //         if(response.status === 200){
  //             donation.name = response.data;
  //             this.isLoading = false;
  //           }
  //         });
  //       });
        
  //     }
  //   });
  //   // campaignService.getUsernameByManagerId(this.campaign.managerId).then(response => {
  //   //     if(response.status === 200){
  //   //       this.managerName = response.data;
  //   //       console.log(this.managerName); 
  //   //     }
  //   // });
  // }
};
</script>

<style scoped>
.campaign-img{
  width: 100%;
  border-radius: 10px;
}
.campaign-page:not(.progress):not(.campaign-img) {
  margin: 2rem 0 2rem 0;
  padding: 1rem;
  background-color: #ffeec6;
  box-shadow: 0 -20px 8px #ffeec6;
  /* font-family: Arial, sans-serif; */
}

.container {
  background: rgb(255, 255, 245);
  padding: 1.5rem;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-top: 20px; /* Add margin to prevent overlap with header */
  height: auto;
}

.title {
  font-size: 2em;
  color: #ec6809;
}

.subtitle {
  font-size: 1.5em;
  color: #666;
}

.organizer, .goal, .description, .funding, .timeline, .donation-info, .campaign-impact, .top-donors, .breakdown-title {
  font-size: 1em;
  margin: 10px 0;
  color: #d33d3d;
  font-style: italic;
  font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
}

.funding{
  color:#168e1c;
  font-weight: bold;
}

.donate-button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 10px 0;
  font-size: 1em;
  font-weight: bold;
}

.donate-button:hover {
  background-color: #45a049;
}

.locked-button{
  background-color: #4b764d;
  color: rgb(200, 207, 192);
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 10px 0;
  font-size: 1em;
  text-decoration: line-through;
  text-decoration-thickness: 3px;
  font-weight: bold;
}

.lock-msg{
  font-size: .8rem;
  margin-left: 5px;
}

.vote-button {
  width:100%;
  box-sizing: border-box;
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 10px 0;
  font-size: 1em;
}

.vote-button:hover {
  background-color: #4CAF50;
}

.donors-title {
  font-size: 1.5em;
  margin-top: 20px;
  color: #ec6403;
}

.donation {
  background: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
  margin: 5px 0;
}



/* progress {
  width: 100%;
  margin: 10px 0;
} */
</style>
