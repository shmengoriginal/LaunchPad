<template>
<div id="app">
  <div class="shell">
    <div class="bar" :style="{ width: fundingWidth + '%'}">
      <!-- <span>${{ funding }}</span> -->
    </div>
  </div>
</div>
</template>

<script>
export default{
    props: ['funding', 'goal'],
    computed: {
      fundingWidth(){
        if(this.funding >= this.goal){
          return `${100}`;
        }else{
          return `${(this.funding/this.goal)*100}`;
        }
      }
    }
}
</script>

<style scoped>
#app {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 3%;
  margin-bottom: 3%;
}

.shell {
  height: .6rem;
  width: 100%;
  border: 1px solid #db8605;
  border-radius: 13px;
  padding: 1px;
}

.bar {
  background: linear-gradient(to right, #f5d314, #e0860f);
  height: 100%;
  border-radius: 9px;
}
</style>