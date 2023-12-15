<template>
  <div class="header">
    <div class="headerLeft">
      <el-image src="LOGO_v2.png" fit="cover"/>
      <a href="/#/home">首页</a>
      <a href="/#/eventList">分类</a>
      <el-dropdown is="nameDropDown">
        <span id="username" class="nameText">
          <el-icon style="height: 15px"><LocationInformation /></el-icon>
          {{ city }}
          <el-icon style="height: 25px">
              <CaretBottom/>
          </el-icon>
        </span>
        <template #dropdown>
          <div class="dropdownList" style="width: 240px">
            <el-scrollbar max-height="200px" style="padding: 7px">
              <el-button v-for="city of citys" @click="searchByCity($event,city)"
                         style="margin: 5px;
                                padding: 4px;
                                border: none">
                {{ city }}
              </el-button>
            </el-scrollbar>
          </div>
        </template>
      </el-dropdown>
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
      <el-image v-if="userId"
                :src="profileStore.profile.photoUrl"
                index="/userInfo"
                @click="jumpToUser"/>
      <div class="service" @click="jumpToService">
        <el-icon :color="`#fff`" :size="30"><Headset /></el-icon>
        <a>客 服</a>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";
import {UserFilled, Search, CaretBottom, LocationInformation, Headset} from "@element-plus/icons-vue";
import {getCurrentInstance, ref, onMounted, reactive} from "vue";
import axios from "axios";

const keyword = ref('');
const profileStore = useProfileStore();
const userId = profileStore.profile.id;
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const instance = getCurrentInstance();
const city = ref('')
const citys = ref([])
const ip = ref('')
const OnSearch = (e) => {
  e.preventDefault();
  $http.get('/event/getAll?keyword=' + keyword.value).then(resp => {
    // console.log(typeof resp)
    if (resp.data) {
      profileStore.saveEventList(Array.from(resp.data));
      router.push("/eventList")
    }
  })
}
const searchByCity = (e, city) => {
  e.preventDefault();
  instance.emit("GetCity",city)
}
const jumpToUser = () => {
  router.push("/userInfo")
}
const jumpToLogin = () => {
  router.push("/login")
}
const jumpToService = ()=>{
  router.push("/service")
}
onMounted(() => {
  axios.get('https://api.ipify.org/?format=json').then(resp => {
    ip.value = resp.data.ip;
    axios.get('https://restapi.amap.com/v3/ip?key=0106a49472396d64819af96dd0e9e3db&ip=' + ip.value).then(resp => {
      city.value = resp.data.city;
    })
  })
  $http.get('/event/getCity').then(resp => {
    citys.value = resp.data;
  })
})
</script>

<style scoped lang="scss">
.header {
  height: 100%;
  width: 100%;

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

    .nameText {
      color: #fff;
      font-size: 16px;
      font-weight: 750;
      margin-left: 15px;
    }

    .nameText:hover {
      color: #ffd5dd;
    }

    .nameText:focus-visible {
      outline: none;
    }

    .el-image {
      height: 45px;
      padding: 1px;
      margin-top: 5px;
    }

    a {
      margin-left: 15px;

      color: #fff;
      font-weight: 750;
      text-decoration: none;
    }

    a:hover {
      color: pink;
    }
  }

  .headRight {
    margin-left: auto;
    margin-right: 4%;
    align-items: center;
    display: flex;

    .searchBox {
      height: 18%;
      margin-right: 8%;
      box-shadow: 1px 1px 1px #ffffff;

      border-radius: 3px;

      .el-button {
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
    .el-image{
      width: 18%;
      border-radius: 50%;
      transition: all 0.3s ease;
    }
    .el-image:hover{
      width: 24%;
      box-shadow: hotpink 0px 0px 0px 4px;
      transition: all 0.3s ease;
    }
    .service{
      margin-left: 5%;
      padding: 2px;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      justify-content: center;
      border-radius: 20%;
      transition: all 0.3s ease;
      a{
        font-size: 12px;
        font-weight: 750;
        color: white;
      }
    }
    .service:hover{
      background-color: pink;
      transition: all 0.3s ease;
    }
  }
}
</style>