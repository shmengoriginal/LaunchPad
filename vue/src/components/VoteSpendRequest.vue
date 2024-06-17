<template>
    <div class="vote-spend-request" v-if="spendRequest">
      <h1>Vote on Spend Request</h1>
      <p>Amount: {{ spendRequest.amount }}</p>
      <p>Reason: {{ spendRequest.reason }}</p>
      <button @click="vote('yes')">Yes</button>
      <button @click="vote('no')">No</button>
      <p>Yes Votes: {{ yesVotes }}</p>
      <p>No Votes: {{ noVotes }}</p>
    </div>
</template>
  
<script>
  export default {
    data() {
      return {
        spendRequest: null,
        yesVotes: 0,
        noVotes: 0,
      };
    },
    created() {
      const storedRequest = localStorage.getItem('spendRequest');
      if (storedRequest) {
        this.spendRequest = JSON.parse(storedRequest);
      }
    },
    methods: {
      vote(type) {
        if (type === 'yes') {
          this.yesVotes++;
        } else if (type === 'no') {
          this.noVotes++;
        }
      },
    },
  };
</script>
  
<style scoped>
  .vote-spend-request {
    padding: 2rem;
  }
  
  .vote-spend-request button {
    margin-right: 1rem;
  }
</style>
  