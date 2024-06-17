<site-header />
<template>
    <SiteHeader />
    <CampaignDetailsRegistered />
    <VoteSpendRequest />
</template>

<script>
import CampaignDetailsRegistered from '../components/CampaignDetailsRegistered.vue';
import SiteHeader from '../components/SiteHeader.vue';
import VoteSpendRequest from '../components/VoteSpendRequest.vue';


export default{
    components: {
    CampaignDetailsRegistered,
    SiteHeader,
    VoteSpendRequest
}

}
</script>
