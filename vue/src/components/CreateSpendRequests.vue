<template>
  <button class="back" @click="$router.back()">Back</button>
    <div class="create-spend-request">
      <h1>Create Spend Request</h1>
      <form @submit.prevent="createSpendRequest">
        <div>
          <label for="amount">Amount:</label>
          <input type="number" v-model="amount" required />
        </div>
        <div>
          <label for="reason">Reason:</label>
          <input type="text" v-model="reason" required />
        </div>
        <button type="submit">Create Request</button>
      </form>
    </div>
</template>
  
<script>
  export default {
    data() {
      return {
        amount: '',
        reason: '',
      };
    },
    methods: {
      createSpendRequest() {
        // Logic to save the request to a backend or state management system
        // For simplicity, using local storage in this example
        localStorage.setItem('spendRequest', JSON.stringify({ amount: this.amount, reason: this.reason }));
        alert('Spend request created successfully');
        this.amount = '';
        this.reason = '';
      },
    },
  };
</script>
  
<style scoped>
  .create-spend-request{
    margin: 3%;
    margin-top: 50%;
  }

  h1{
    text-align: center;
    margin-bottom: 2rem;
  }

  form{
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  }

  form label{
    display: flex;
    flex-direction: column;
    font-size: large;
  }

  form input{
    padding: 10px;
    border: 1px solid #dfb128;
    border-radius: 5px;
    font-size: 1em;
    width: 100%;
    box-sizing: border-box;
    transition: border-color 0.3s ease-in-out;
  }

  form input:focus {
  border-color: #4CAF50;
  outline: none;
  box-shadow: 0 0 5px rgba(76, 175, 80, 0.5);
}

  button{
    margin: auto;
    margin-bottom: 2rem;
    padding: .5rem 1rem;
    font-size: large;
    background-color: #e7a325;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    text-align: center;
    font-size: large;
  }

  .back{
    margin-left: 1rem;
    margin-top: 2rem;
    padding-top: .3rem;
    padding-bottom: .3rem;
  }
</style>
  