<template>
    <div>
    <div class="campaign-card" v-for="campaign in campaigns" :key="campaign.campaignId">
        <!-- <router-link v-bind:to="{ name: 'campaignDetails', params: {id: campaign.campaignId} }"> -->
            <campaign-card :campaign="campaign" :isDashboard="isDashboard"
            @delete-complete="bubbleUp"
            />
        <!-- </router-link> -->
    </div>
</div>
</template>

<script>
import CampaignCard from '../components/CampaignCard.vue';
export default{
    methods: {
        bubbleUp() {
           this.$emit('delete-complete');
        }
    },  
    components: {
        CampaignCard
    },
    props: ['campaigns', 'isDashboard'] //I have campaigns coming in as a prop bc the campaigns depends on the page, anon vs registered
}
</script>