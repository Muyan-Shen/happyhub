<template>
  <div class="header">
    <span>我是头部！</span>
    <div class="userHead">
      <el-image src="vite.svg" index="/userInfo" @click="jumpTo"/>

    </div>
  </div>
</template>

<script setup>
import router from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";

const profileStore = useProfileStore();
const userId = 1001;
// const userId = profileStore.userId;
const jumpTo = ()=>{
  router.push("/userInfo")
}
</script>

<style scoped lang="scss">
.header{
  height: 100%;
  width: 100%;
  border: 1px red solid;
  display: flex;
  justify-content: center;
  align-items: center;
  .userHead{
    margin-left: auto;
    margin-right: 4%;
  }
}
</style>