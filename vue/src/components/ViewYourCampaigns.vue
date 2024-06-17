<template>
    <div class="container">
      <h6>Your Campaigns</h6>
      <button type="button">Start A New Campaign</button>
      <div v-if="campaign.pic">
        <img src="../assets/PLACEHOLDER_LOGO.png" alt="Place Holder">
      </div>
      <h1>{{ campaign.title }}</h1>
      <p>Description: {{ campaign.about }}</p>
      <progress id="CampaignProgress" value="50" max="100"></progress>
      <button type="button">Edit Campaign</button>
      <button type="button">Delete Campaign</button>
    </div>
</template>
  
<script>
  import { mapGetters } from 'vuex';
  
  export default {
    computed: {
      ...mapGetters(['campaign'])
    }
  };
</script>
  
<style>



</style>