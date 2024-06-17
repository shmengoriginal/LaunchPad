<template>
    <div v-if="campaign.pic">
      <img src="../assets/Launchpad-logo-full.png" alt="Launchpad Logo">
    </div>
    <div>
      <h1>Campaign Request Form</h1>
    </div>
    <div class="form-container">
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <input v-model="title" type="text" id="title" name="title" placeholder="Title of Campaign" required>
        </div>
        <div class="form-group">
          <input v-model="request" type="text" id="request" name="request" placeholder="Campaign Request" required>
        </div>
        <div class="form-group">
          <input v-model="manager" type="text" id="manager" name="manager" placeholder="Campaign Manager" required>
        </div>
        <div class="form-group">
          <input v-model="description" type="text" id="description" name="description" placeholder="Description Request" required>
        </div>
        <button type="submit" class="submit-btn">Create Request</button>
      </form>
    </div>
</template>
  
<script>
  import { mapGetters, mapMutations } from 'vuex';
  
  export default {
    data() {
      return {
        title: '',
        request: '',
        manager: '',
        description: ''
      };
    },
    computed: {
      ...mapGetters(['campaign'])
    },
    methods: {
      ...mapMutations(['setCampaign']),
      submitForm() {
        const campaignData = {
          title: this.title,
          request: this.request,
          manager: this.manager,
          description: this.description
        };
        this.setCampaign(campaignData);
        this.$router.push('/campaign-voting');
      }
    }
  };
</script>
  
<style>
</style>
  