<site-header />
<template>
    <SiteHeader />
    <CampaignVoting/>
</template>

<script>
import CampaignVoting from '../components/CampaignVoting.vue';
import SiteHeader from '../components/SiteHeader.vue';


export default{
    components: {
    CampaignVoting,
    SiteHeader
}

}
</script>
