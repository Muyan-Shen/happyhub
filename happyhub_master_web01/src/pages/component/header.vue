<template>
  <div class="header">
    <div class="headerLeft">
      <span>我是LOGO!</span>
    </div>
    <div class="headRight">
      <el-input
          placeholder="关键字搜索"
          class="searchBox"
          :prefix-icon="Search"
          clearable
      >

      </el-input>
      <el-button v-if="userId < 0" type="info" @click="">
        <el-icon style="margin-right: 5px">
          <UserFilled/>
        </el-icon>
        登录/注册
      </el-button>
      <el-image v-if="userId > 0" src="vite.svg" index="/userInfo" @click="jumpTo"/>
    </div>
  </div>
</template>

<script setup>
import router from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";
import {UserFilled,Search} from "@element-plus/icons-vue";

const profileStore = useProfileStore();
// const userId = 1001;
const userId = profileStore.userId;
const jumpTo = () => {
  router.push("/userInfo")
}
</script>

<style scoped lang="scss">
.header {
  height: 100%;
  width: 100%;

  border: 0px red solid;
  border-radius: 5px;
  background-color: hotpink;
  box-shadow: 2px 4px 2px #b4b0b3;

  display: flex;
  justify-content: center;
  align-items: center;

  .headerLeft {
    margin-left: 4%;
  }

  .headRight {
    margin-left: auto;
    margin-right: 4%;

    display: flex;
    .searchBox{
      height: 20%;
      margin-right: 2%;
    }
    .el-button {
      height: 20%;

      margin-bottom: 2%;
      background-color: pink;
      border: none;
    }
  }
}
</style>