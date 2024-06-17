import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    campaign: {
      title: '',
      request: '',
      manager: '',
      description: ''
    }
  },
  mutations: {
    setCampaign(state, campaignData) {
      state.campaign = campaignData;
    }
  },
  getters: {
    campaign: state => state.campaign
  }
});
