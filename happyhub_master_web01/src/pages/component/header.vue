<template>
  <div class="header">
    <div class="headerLeft">
      <el-image src="LOGO_v2.png" fit="cover"/>
      <a href="/#/home">首页</a>
      <a href="/#/eventList">分类</a>
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
      <el-button v-if="!userId"
                 type="info"
                 @click="jumpToLogin">
        <el-icon style="margin-right: 5px">
          <UserFilled/>
        </el-icon>
        登录/注册
      </el-button>
      <el-image v-if="userId" src="vite.svg" index="/userInfo" @click="jumpToUser"/>
    </div>
  </div>
</template>

<script setup>
import router from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";
import {UserFilled,Search} from "@element-plus/icons-vue";
import {getCurrentInstance, ref, onMounted, reactive} from "vue";

const keyword = ref('');
const profileStore = useProfileStore();
const userId = profileStore.profile.id;
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
const jumpToUser = () => {
  router.push("/userInfo")
}
const jumpToLogin = ()=>{
  router.push("/login")
}
onMounted(()=>{
})
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
    margin-left: 8%;
    display: flex;
    align-items: center;
    justify-content: center;
    .el-image{
      height: 45px;
      padding: 1px;
      margin-top: 5px;
    }
    a{
      margin-left: 15px;

      color: #fff;
      font-weight: 750;
      text-decoration: none;
    }
    a:hover{
      color: pink;
    }
  }

  .headRight {
    margin-left: auto;
    margin-right: 4%;
    align-items: center;
    display: flex;
    .searchBox{
      height: 18%;
      margin-right: 8%;
      box-shadow: 1px 1px 1px #ffffff;

      border-radius: 3px;
      .el-button{
        height: 99%;
        color: white;
        background-color: pink;
        border-radius: 0px 3px 3px 0px;
        box-shadow: hotpink 1px 1px 1px;
      }
    }
    .el-button {

      background-color: #fdb5c1;
      border: none;
      box-shadow: 1px 1px 1px #ffffff;
    }
  }
}
</style>