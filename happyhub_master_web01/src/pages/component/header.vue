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
          v-model="keyword"
          clearable
      >
        <template #append>
          <el-button type="primary" @click="OnSearch($event)">
            搜索
          </el-button>
        </template>
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
import {getCurrentInstance, ref} from "vue";

const keyword = ref('');
const profileStore = useProfileStore();
// const userId = 1001;
const userId = profileStore.userId;
const $http = getCurrentInstance().appContext.config.globalProperties.$http;

const OnSearch = (e)=>{
  e.preventDefault();
  $http.get('/event/getAll?keyword='+keyword.value).then(resp=>{
    // console.log(typeof resp)
    if (resp.data){
      profileStore.saveEventList(Array.from(resp.data));
      router.push("/eventList")
    }
  })
}
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
  box-shadow: 2px 4px 2px #c2bfc1;

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
      height: 18%;
      margin-right: 4%;

      border-radius: 3px;
      .el-button{
        height: 99%;
        color: white;
        background-color: pink;
        border-radius: 0px 3px 3px 0px;
        box-shadow: #cccccc 1px 1px 1px;
      }
    }
    .el-button {

      margin-bottom: 2%;
      background-color: #fdb5c1;
      border: none;
    }
  }
}
</style>