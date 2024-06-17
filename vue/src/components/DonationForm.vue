<template>
  <div>
<button class="back" @click="$router.back()">Back</button>
  <div class="donation-form">
    <h1>Donation Form</h1>
    <div class="form-container">
      <form @submit.prevent="amountAlert">
        <div class="form-group">
          <label for="donationAmount"><span>What amount are you donating?</span> <span>(Currency is in U.S. dollars $)</span></label>
          <input type="number" id="donationAmount" name="donationAmount" placeholder="What amount are you donating ($0.00)?" required v-model="donation.amount">
        </div>
        <button type="submit" class="submit-btn">Donate</button>
        
      </form>
    </div>
  </div>
</div>
</template>
  
<script>
  // import { mapGetters, mapMutations } from 'vuex';
  import campaignService from '../services/CampaignService';
  
  export default {
    props: [],
    data() {
      return {
        // donationAmount: 0,
        campaign: {},
        donation: {
          donationId: 0,
          donorId: this.$store.state.user.id,
          campaignId: this.$route.params.campaignId,
          amount: 0, //I have no clue why amount doesn't return null in backend but donationAmount does.. but whatever it works
          // dateTime: ""
          dateTime: Date.now()
        },
        isReadyToSubmit: false
      };
    },
  //   computed: {
  //     ...mapGetters(['campaign'])
  //   },
    methods: {
  //     ...mapMutations(['setCampaign']),
      amountAlert(){
        if(this.campaign.funding + this.donation.amount > this.campaign.goal && !this.isReadyToSubmit){
          const newAmount = this.campaign.goal - this.campaign.funding;
          if (confirm(`That amount exceeds the goal. The most you can donate is $${newAmount}. Proceed?`)){
            this.donation.amount = newAmount;
            this.isReadyToSubmit = true;
          }
        }else{
          this.submitForm();
        }
      },
      submitForm() {
        
        //need to create donation and then get campaign by id, then update campaign to adjust funding
        // this.donation.dateTime = Date.now();
        campaignService.createDonation(this.donation).then(response => {
          if(response.status === 201){
            console.log("worked");
          }
        }).catch(error => {
          console.log(`something went wrong with creating a new donation. Response status: ${error.response.status}`);
        });

        // console.log(this.campaign.funding);
        // console.log(this.campaign);
        this.campaign.funding += this.donation.amount;
        // console.log(this.campaign.funding);
        campaignService.updateCampaign(this.campaign, this.campaign.campaignId).then(response => {
          if(response.status === 200){
            // console.log(this.campaign);
            this.$router.push({name: 'campaignDetails', params: {id: this.$route.params.campaignId}});
          }
        }).catch(error => {
          console.log(`something went wrong with updating funds of the campaign. Response status: ${error.response.status}`);
        });
  //       const campaignData = {
  //         title: this.title,
  //         request: this.request,
  //         manager: this.manager,
  //         description: this.description
  //       };
  //       this.setCampaign(campaignData);
  //       this.$router.push('/campaign-voting');
      },
      cancelForm() {
        this.$router.back();
      },
      setCampaign(response){ //OK oh my god, from now on set local variables with methods for web calls
        this.campaign = response.data;
      }
    },
    created(){//probably should pass this in from a view but idk
      campaignService.getCampaignById(this.donation.campaignId).then(response => {
        if(response.status === 200){
          this.setCampaign(response);
        }
      });
    }
  }
</script>
  
<style scoped>
  .donation-form{
    margin: 3%;
    margin-top: 50%;
  }

  h1{
    text-align: center;
  }
  .form-group{
    margin-top: 3rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  }

  form{
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2rem;
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
    /* margin-bottom: 2rem; */
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
  