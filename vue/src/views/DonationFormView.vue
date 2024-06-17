<site-header />
<template>
    <SiteHeader />
    <DonationForm />
</template>

<script>
import DonationForm from '../components/DonationForm.vue';
import SiteHeader from '../components/SiteHeader.vue';


export default{
    components: {
    SiteHeader,
    DonationForm
}

}
</script>
