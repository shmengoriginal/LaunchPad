<template> 
  <div class="container">
  <header>
    <router-link v-bind:to="{ name: 'home' }" class="logo">
      <img src="../assets/Launchpad-logo-full.png" alt="LaunchPad">
    </router-link>
  </header>
  <div id="login" class="login-inputs">
    <form v-on:submit.prevent="login">
      <h1 >Enter Your Username And Password</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Log in</button>
      <p>
      <router-link v-bind:to="{ name: 'register' }" class="link">Need an account? Sign up here</router-link></p>
    </form>
  </div>
</div>

</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<!-- <style scoped>
.container{
  display: flex;
  position: relative; 
  padding: 1rem;
  align-items: center;
}

.container img{
  width: 70%;
  height: auto;
  display: flex;
}

h1{
  text-align: center;
  position: relative; 
  top: -600px;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  font-size: 1.5rem;
}


.form-input-group {
  margin-bottom: 1rem;
  position: relative; 
  top: -600px;
  font-size: 1rem;
}

input[type="text"] {
height: 10px;
width: 300px;
border: 1px solid #cdcdcd;
}

input[type="password"] {
height: 29px;
width: 320px;
border: 1px solid #cdcdcd;
border-radius: 5px;
padding: 0.1rem;
}

label {
  margin-right: 0.6rem;
}

button{
  fill: #23CF68;
  color: white;
  padding: 15px;
  font-size: large;
  border-color: #23CF68;
  position: relative; 
  top: -590px;
  border-radius: 5px;
}

header{
  align-self: center;
}

header img{
    width: 20%;
    display: flex;
    align-content: top;
    justify-content: center;
  }

.link{
  display: flex;
  text-align: center;
  font-style: italic;
  justify-content: space-evenly;
  position: relative; 
  top: -580px;
}

@media (max-width: 767px) {
button{
    width: 100%;
    background-color:#23CF68;
  
}

}
</style> -->

<style scoped>

.container{
  margin: 7%;
  margin-top: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

header{
  margin: auto;
  margin-bottom: 2rem;
  height: auto;
  transform: scale(1.1);
  display: flex;
  justify-content: center;
}

header img{
  width: 150px;
  height: auto;
}

h1{
  text-align: center;
  font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-size: 1.3rem;
  margin-bottom: 2rem;
}

.form-input-group {
  margin-bottom: 1rem;
  font-size: 1rem;
}

form input[type="text"],
form input[type="password"],
form textarea {
    width: 93%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

label{
    display: inline-block;
    clear: left;
    width: 250px;
    text-align: left; /*Change to right here if you want it close to the inputs*/
    font-family: sans-serif,'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
}

input {
  display: inline-block;
}

button{
  /* fill: #23CF68; */
  /* color: white; */
  padding: 10px;
  font-size: large;
  /* border-color: #23CF68; */
  border-radius: 5px;
  max-width: 60%;
  height: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
  margin: 0 auto;
}

header{
  align-self: center;
}

#register{
display: flex;
justify-content: center;
align-content: center;
margin-top: 30px;
}

.link{
  margin: 1rem;
  display: flex;
  text-align: center;
  font-style: italic;
  justify-content: center;
}

@media (max-width: 767px) {
button{
    width: 100%;
    height: 50%;
    /* background-color:#23CF68; */
  
}
}
</style>