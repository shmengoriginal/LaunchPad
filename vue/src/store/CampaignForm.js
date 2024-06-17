import { createStore as createVuexStore } from 'vuex';

export function createStore(token, user) {
  return createVuexStore({
    state: {
      token: token || '',
      user: user || {},
      campaign: {
        title: '',
        goal: '',
        about: '',
        pic: '',
        visibility: 'public'
      }
    },
    mutations: {
      setCampaign(state, campaign) {
        state.campaign = campaign;
      }
    },
    actions: {
      updateCampaign({ commit }, campaign) {
        commit('setCampaign', campaign);
      }
    },
    getters: {
      campaign: state => state.campaign
    }
  });
}

