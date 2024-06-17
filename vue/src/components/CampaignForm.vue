<template>
    <!-- STILL NEEDS WORK -->
<div class="container">
      <!-- We probably dont need the @submit in the form tag but this works so not gonna touch it now -->
    <form @submit.prevent="addCampaign">
      <!-- <h1 >Campaign Form</h1> -->
        <div>
            <label for="title" class="form-label">What will you call your campaign? </label>
            <input class="form-input" type="text" id="title"  required placeholder="Campaign title here" v-model="editedCampaign.title" />
        </div>
        <div>
            <label for="goal" class="form-label" >What is your starting goal? (Currency is in U.S. dollars $)</label>
            <input class="form-input" type="number" id="goal" required placeholder="eg. 2000" v-model="editedCampaign.goal"/>
        </div>
        <div>
            <label for="description" class="form-label">Describe your campaign: </label>
            <textarea class="form-input" id="description" rows="6" cols="50" required placeholder="We're raising money to fund our amazing project!" v-model="editedCampaign.description"></textarea>
        </div>
        <div>
            <label for="description" class="grey">Describe your campaign: </label>
            <textarea id="description" rows="6" cols="50" required v-model="editedCampaign.description"></textarea>
        </div>
        <div>
            <label for="tags" class="grey">Enter tags separated by a comma: </label>
            <input type="text" id="tags"/>
            <!-- THIS NEEDS TO BE DONE STILL, NOT WORKING, needs vmodel for tag table?  -->
        </div>
        <div>
            <label for="imgURL">Upload an image: </label>
        <!-- <div>
            <label for="imgURL" class="form-label">Upload an image: </label>
            <input type="file" id="imgURL" accept="image/*" :src="editedCampaign.imgURL"/>
        </div> -->
        <div>
            <label for="endDate" class="form-label">When will the fundraiser end?</label>
            <input type="date" id="endDate" required  v-model="editedCampaign.endDate" class="form-input" />
        </div>

        <div class="tag-selection">
            <label class="form-label">Add some tags to your campaign! (Optional)</label>
            <select class="form-input" v-model="tagSelected" @change="addTag">
              <option disabled value="">Select a tag to add</option>
              <option v-for="tag in allTags" :key="tag.id" :value="tag">{{ tag.description }}</option>
            </select>
        </div>

        <div class="current-tags" v-show="editedCampaignTags">
          <!-- <p>Current campaign tags:</p> -->
          <span v-for="tag in editedCampaignTags" :key="tag.id" @click="removeTag(tag)">x {{ tag.description }}</span>
        </div>

        <label for="isPublic" class="form-label">Is this campaign going to be public or private?</label>
        <div class="button-group" >

          <div class="choice">
            <input class="radio_input" type="radio" id="isPublic" :value="true" v-model="editedCampaign.isPublic"/> 
            <label class="radio_label" for="isPublic">Public</label>
          </div>

          <div class="choice">
            <input class="radio_input" type="radio" id="notPublic" :value="false" checked v-model="editedCampaign.isPublic"/> 
            <label class="radio_label" for="notPublic">Private</label>
          </div>

        </div>
        <input type="submit" @click.prevent="submitForm" value="Create Campaign"/>
        <input type="cancel" @click.prevent="cancelForm" value="Cancel"/>
    </form>
</div>
</template>


<script>
import campaignService from '../services/CampaignService.js';
import { mapActions } from 'vuex';

export default {
  props: ['campaign', 'allTags', 'campaignTags'], //bug with props when the content passed is a webcall, shows up in html, is undefined in javascript. dunno what to do about that
  data() {
    return {
        editedCampaign: {
            campaignId: this.campaign ? this.campaign.campaignId : 0,
            title:  this.campaign ? this.campaign.title : '',
            endDate: this.campaign ? this.campaign.endDate : '',
            goal: this.campaign ? this.campaign.goal : '',
            managerId: this.campaign ? this.campaign.managerId : this.$store.state.user.id,
            imgURL: this.campaign ? this.campaign.imgURL : '',
            funding: this.campaign ? this.campaign.funding : 0,
            description: this.campaign ? this.campaign.description : '',
            isPublic: this.campaign ? this.campaign.isPublic : false
        },
      editedCampaignTags: this.campaignTags,
      tagSelected: null
    };
  },
  methods: {
    addTag(){
      if(this.tagSelected && !this.editedCampaignTags.includes(this.tagSelected)){
        
        this.editedCampaignTags.push(this.tagSelected);
        console.log("adding tag");
        console.log(this.editedCampaignTags);
        this.tagSelected = null;
      }
    },
    removeTag(tag){
      const tagIndex = this.editedCampaignTags.indexOf(tag);
      if(tagIndex !== -1){
        this.editedCampaignTags.splice(tagIndex, 1);
        console.log("removing tag");
        console.log(this.editedCampaignTags);
      }
    },
    updateFormData(campaignInfo) {
      console.log(campaignInfo);
      this.editedCampaign = {
            campaignId: campaignInfo ? campaignInfo.campaignId : 0,
            title:  campaignInfo ? campaignInfo.title : '',
            endDate: campaignInfo ? campaignInfo.endDate : '',
            goal: campaignInfo ? campaignInfo.goal : '',
            managerId: campaignInfo ? campaignInfo.managerId : this.$store.state.user.id,
            imgURL: campaignInfo ? campaignInfo.imgURL : '',
            funding: campaignInfo ? campaignInfo.funding : 0,
            description: campaignInfo ? campaignInfo.description : '',
            isPublic: campaignInfo ? campaignInfo.isPublic : false
        }
    },
    updateCampaignTagData(campaignTagsInfo){
      console.log(campaignTagsInfo);
      this.editedCampaignTags = campaignTagsInfo ? campaignTagsInfo : [];
    },
    submitForm() {
      // Do client-side form validation 
      if (!this.validateForm()) {
        //Form isn't valid, user must update and submit again.
        return;
      }
      // Check for add or edit
      if (this.editedCampaign.campaignId === 0) {
        
        // Do an add, then navigate Dashboard on success.
        // For errors, call handleErrorResponse
        campaignService.createCampaign(this.editedCampaign).then(response => {
          if(response.status === 201){
            console.log("worked");
            this.$router.push({ name: 'dashboard'}); //need to change route to registered, anon is ok for now
          }
        }).catch(error => {
          this.handleErrorResponse(error, 'adding');
        });

      } else {
        
        // Do an edit, then navigate back to Campaign Details on success
        // For errors, call handleErrorResponse
        campaignService.updateCampaign(this.editedCampaign, this.editedCampaign.campaignId).then(response => {
          if(response.status === 200){
            this.$router.push({ name: 'campaignDetails', params: {id: this.campaign.campaignId} }); //probably same issues w timing, used campaign instead of editedcampaign for router push
          }
        }).catch(error => {
          this.handleErrorResponse(error, 'updating');  
        });
      }
    },
    cancelForm() {
      this.$router.back();
    },
    handleErrorResponse(error, verb) {
      if (error.response) {
        if (error.response.status == 404) {
          this.$router.push({name: 'NotFoundView'});
        } else {
          this.$store.commit('SET_NOTIFICATION',
          `Error ${verb} message. Response received was "${error.response.statusText}".`);
        }
      } else if (error.request) {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} message. Server could not be reached.`);
      } else {
        this.$store.commit('SET_NOTIFICATION', `Error ${verb} message. Request could not be created.`);
      }
    },
    validateForm() {
      let title = '';

      this.editedCampaign.title = this.editedCampaign.title.trim();
      if (this.editedCampaign.title.length === 0) {
        title += 'The new campaign must have a title. ';
      }

      if (title.length > 0) {
        this.$store.commit('SET_NOTIFICATION', title);
        return false;
      }
      return true;
    }
  }
};

</script>


<style scoped>

h1{
    text-align: center;
    display: block;
    position: relative; 
    max-width: 90%;
    font-size: calc(7vw);
}

.container {
    /* display: flex;
    flex-direction: column;
    justify-content: center;
    height: auto; */
    /* padding: 1rem; */
    padding-top: 3rem;
}
.button-group {
    display: flex;
    justify-content: space-evenly;
    margin-bottom: 20px;
}

.choice{
  padding-top: 0;
  display: inline-flex;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 0 4px 5px rgba(228, 102, 0, 0.25);
  background: #f8cf90;
}

.radio_input{
  /* not the most accessible thing to do */
  display: none;
}
.radio_label{
  padding: 8px 14px;
  /* padding-top: 0; */
  font-size: 1em;
  font-weight: bold;
  color:rgb(168, 62, 13);
  background: #f8cf90;
  cursor: pointer;
}

.radio_input:checked + .radio_label{
  background: #ecaa47;

}

form input[type="file"]{
    margin: 20px;
    text-align: left;
}
form input[type="submit"], input[type="cancel"]{
  margin: auto;
  margin-bottom: 2rem;
  /* padding: 5px; */
  font-size: large;
  background-color: #e7a325;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  text-align: center;
  font-size: large;
}

input[type="cancel"]{
  background-color: #e35e0b;

}

form div:not(.button-group):not(.current-tags) {
  margin: 2rem 0 2rem 0;
  display: flex;
  flex-direction: column;
  border-radius: 4px;
}
.current-tags{
  margin: auto;
  margin-top: 0;
  margin-bottom: 2rem;
  border-radius: 5px;
}

.current-tags span{
  color: rgb(184, 113, 6);
  margin: 1%;
  padding-top: 1%;
  padding-bottom: 1%;
  padding-inline: 2%;
  background-color: #fae098;
  border-radius: 10px;
  border: 1px solid #db8605;
}
.current-tags span:hover{
  color: rgb(184, 62, 6);
  background-color: #e7b872;

}

.form-label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #d35e0b;
  font-size: 1.1em;
}

.form-input {
  padding: 10px;
  border: 1px solid #dfb128;
  border-radius: 5px;
  font-size: 1em;
  width: 100%;
  box-sizing: border-box;
  transition: border-color 0.3s ease-in-out;
}

.form-input:focus {
  border-color: #4CAF50;
  outline: none;
  box-shadow: 0 0 5px rgba(76, 175, 80, 0.5);
}

/* FOR PHONE ONLY VIEW:  */
@media (max-width: 767px) {
form input[type="submit"]{
    width: 100%;
    height: 50px;
}

form input[type="cancel"]{
    width: 100%;
    height: 50px;
}

}
</style>