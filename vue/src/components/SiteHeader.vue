<template>
  <header class="site-header">
    <router-link v-bind:to="{ name: 'home' }" class="logo">
      <img src="../assets/Launchpad-logo-full.png" alt="LaunchPad" />
    </router-link>
    <nav class="nav-buttons">
      <button @click="$router.push({ name: 'dashboard' })" class="menu-button">Manage</button>
      &nbsp;
      &nbsp;
      <button v-if="this.$store.state.token != ''" @click="logout" class="logout-button">
        Logout
      </button>
      &nbsp;
      &nbsp;
      <button v-if="this.$store.state.token === ''" class="login-button" @click="this.$router.push({ name: 'createCampaign' })">
        Login
      </button>
    </nav>
  </header>
</template>

<script>
export default {
  name: "SiteHeader",
  methods: {
    logout() {
      this.$router.push({ name: 'logout' });
    }
  }
};
</script>

<style scoped>

.site-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  /* background: linear-gradient( rgb(249, 249, 186),#ffc677); */
  /* background-color: rgb(249, 252, 228); */
  background-color: rgb(249, 249, 186);
  box-shadow: 0 5px 20px #f5d278;

  /* border-bottom: 2px solid rgb(240, 111, 6); */
  /* position: fixed;  */
  width: 100%; 
  top: 0; 
  z-index: 1000; 
  box-sizing: border-box; 
}

.logo {
  flex: 1; 
}

.logo img {
  max-width: 100%;
  height: auto;
}

.nav-buttons {
  display: flex;
  flex: 2; 
  justify-content: flex-end; 
}

.menu-button, .logout-button, .login-button {
  background-color: rgb(252, 152, 22);
  color: white;
  /* font-weight: bolder; */
  font-family: sans-serif,'Gill Sans', 'Gill Sans MT', 'Calibri', 'Trebuchet MS', sans-serif;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  border-radius: 4px;
  cursor: pointer;
}

@media (min-width: 600px) {
  .site-header {
    padding: 1rem 2rem;
  }

  .menu-button, .logout-button, .login-button {
    padding: 0.75rem 1.5rem;
    font-size: 1.2rem;
  }
}
</style>
