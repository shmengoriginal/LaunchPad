import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import DashboardView from '../views/DashboardView.vue';
import CreateCampaignView from '../views/CreateCampaignView.vue';
import MenuView from '../views/MenuView.vue';
import CampaignDetailsView from '../views/CampaignDetailsView.vue';
import ViewYourCampaignsView from '../views/ViewYourCampaignsView.vue';
import CampaignVotingView from '../views/CampaignVotingView.vue';
import CampaignRequestFormView from '../views/CampaignRequestFormView.vue';
import EditCampaignView from '../views/EditCampaignView.vue';
import DonationFormView from '../views/DonationFormView.vue';
import CreateSpendRequestView from '../views/CreateSpendRequestView.vue';

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */
const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/menu",
    name: "menu",
    component: MenuView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/dashboard",
    name: "dashboard",
    component: DashboardView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/createCampaign",
    name: "createCampaign",
    component: CreateCampaignView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/campaign/:id",
    name: "campaignDetails",
    component: CampaignDetailsView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/editCampaign/:id",
    name: "editCampaign",
    component: EditCampaignView,
    meta: {
      requiresAuth: true
    }
  },
  //testing new views
  {
    path: "/view",
    name: "view",
    component: ViewYourCampaignsView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/voting",
    name: "voting",
    component: CampaignVotingView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/donationForm/:campaignId",
    name: "donationForm",
    component: DonationFormView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/request",
    name: "request",
    component: CampaignRequestFormView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/create-spend-request',
    name: 'CreateSpendRequest',
    component: CreateSpendRequestView,
    meta: {
      requiresAuth: true
    }
  },
  // {
  //   path:"./voteSpendRequest",
  //   name:"voteSpendRequest",
  //   component: VoteSpendRequest,
  //     meta: {
  //       requiresAuth: true
  //     }
  // },
  {
    path: "/login",
    name: "login",
    component: LoginView,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/logout",
    name: "logout",
    component: LogoutView,
    meta: {
      requiresAuth: true
    }
  },
  {
    path: "/register",
    name: "register",
    component: RegisterView,
    meta: {
      requiresAuth: false
    }
  }
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes
});

router.beforeEach((to) => {

  // Get the Vuex store
  const store = useStore();

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return {name: "login"};
  }
  // Otherwise, do nothing and they'll go to their next destination
});

export default router;
