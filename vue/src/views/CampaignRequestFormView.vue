<site-header />
<template>
    <SiteHeader />
    <CampaignRequestForm />
</template>

<script>
import CampaignRequestForm from '../components/CampaignRequestForm.vue';
import SiteHeader from '../components/SiteHeader.vue';


export default{
    components: {
        CampaignRequestForm,
        SiteHeader
}

}
</script>