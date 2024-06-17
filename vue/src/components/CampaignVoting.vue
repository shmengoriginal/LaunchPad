<template>
    <div>
      <div v-if="campaign.imgURL">
        <img src="../assets/Launchpad-logo-full.png" alt="Launchpad Logo">
      </div>
      <div>
        <h1>Completed Campaign</h1>
      </div>
      <div v-if="campaign.imgURL">
        <img src="../assets/PLACEHOLDER_LOGO.png" alt="Place Holder">
      </div>
      <div class="container">
        <h1>{{ campaign.title }}</h1>
        <h2>{{ campaign.request }}</h2>
        <h3>{{ campaign.manager }}</h3>
        <p>{{ campaign.description }}</p>
      </div>
      <div class="voting-container">
        <label class="voting-option">
          <input type="radio" name="vote" value="yes">
          <img src="../assets/thumbsup.png" alt="Yes">
          <span>Yes</span>
        </label>
        <label class="voting-option">
          <input type="radio" name="vote" value="no">
          <img src="../assets/thumbsdown.png" alt="No">
          <span>No</span>
        </label>
        <progress id="voteYes" value="70" max="100">70%</progress>
        <progress id="voteNo" value="30" max="100">30%</progress>
      </div>
    </div>
</template>
  
<script>
    // import { mapGetters } from 'vuex';

    export default {
      data(){
        
      },
      computed: {
      // ...mapGetters(['campaign'])
      }
    };
</script>
  
<style>
</style>
  