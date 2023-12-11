<template>
  <div class="header">
    <div class="headerLeft">
      <el-image src="LOGO_v2.png" fit="cover"/>
      <a href="/#/home">首页</a>
      <a href="/#/eventList">分类</a>
      <el-dropdown is="nameDropDown" @visible-change="false">
        <span id="username" class="nameText">
          {{ city }}
          <el-icon style="height: 25px">
              <CaretBottom/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-scrollbar max-height="200px" style="padding: 7px">
            <p v-for="city of citys" @click="" class="pCity">{{ city }}</p>
          </el-scrollbar>
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
      <el-image v-if="userId" src="vite.svg" index="/userInfo" @click="jumpToUser"/>
    </div>
  </div>
</template>

<script setup>
import router from "../../config/router.config.js";
import {useProfileStore} from "../../stores/useProfile.js";
import {UserFilled, Search, CaretBottom} from "@element-plus/icons-vue";
import {getCurrentInstance, ref, onMounted, reactive} from "vue";
import axios from "axios";

const keyword = ref('');
const profileStore = useProfileStore();
const userId = profileStore.profile.id;
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const city = ref('')
const citys = ref(['长沙', '郑州', '沈阳', '青岛', '厦门', '宁波',
  '佛山', '昆明', '合肥', '福州', '济南', '大连', '无锡', '太原', '哈尔滨',
  '常州', '南宁', '南昌', '长春', '石家庄', '贵阳', '南通', '温州', '海口',
  '珠海', '泉州', '东莞', '银川', '绍兴', '嘉兴', '台州', '包头', '呼和浩特',
  '惠州', '烟台', '中国澳门', '金华', '阜阳', '中国香港', '中山', '兰州',
  '乌鲁木齐', '扬州', '赣州', '宝鸡', '廊坊', '西宁', '盐城', '芜湖', '徐州',
  '鞍山', '连云港', '柳州', '吉林', '洛阳', '宜昌', '景德镇', '六安', '南阳',
  '汕头', '泰州', '威海', '湛江', '遵义', '滨州', '湖州', '九江', '莆田',
  '潍坊', '大庆', '东营', '鄂尔多斯', '荆州', '开封', '秦皇岛', '清远',
  '肇庆', '镇江', '淄博', '衢州', '东方', '桂林', '邯郸', '济宁', '江门',
  '锦州', '丽水', '绵阳', '日照', '遂宁', '唐山', '孝感', '宜宾', '漳州', '株洲'])
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
const jumpToUser = () => {
  router.push("/userInfo")
}
const jumpToLogin = () => {
  router.push("/login")
}
onMounted(() => {
  axios.get('https://api.ipify.org/?format=json').then(resp => {
    console.log(resp)
    ip.value = resp.data.ip;
    axios.get('https://restapi.amap.com/v3/ip?key=0106a49472396d64819af96dd0e9e3db&ip=' + ip.value).then(resp => {
      console.log(resp)
      city.value = resp.data.city;
    })
  })
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
  }
}
</style>