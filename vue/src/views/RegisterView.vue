<template>
  <div id="register" class="container">
    <header>
      <router-link v-bind:to="{ name: 'home' }" class="logo">
        <img src="../assets/Launchpad-logo-full.png" alt="LaunchPad">
      </router-link>
    </header>
    
    <div id="register" class="login-inputs">
      <form v-on:submit.prevent="register">
        <h1>Create Account</h1>
        <div role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>
        <div class="form-input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required autofocus />
        </div>
        <div class="form-input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="form-input-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
        </div>
        <button type="submit">Create Account</button>
        <p><router-link v-bind:to="{ name: 'login' }" class="link">Already have an account? Log in here</router-link></p>
      </form>
    </div>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

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
  margin-bottom: 1rem;
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

/* #register{
display: flex;
justify-content: center;
align-content: center;
margin-top: 30px;
} */

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


