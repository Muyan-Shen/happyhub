<template>
<div class="userInfo">
  <el-container>
    <el-header>
      <top-header/>
    </el-header>
    <el-main>
      <div class="q">
      <side/>
      </div>
      <div class="w">
        <div style="border: 1px solid #efefef;width: 700px;height: 34px; display: flex;align-items: center;">
          <span style="margin-left: 20px;color:	DarkGray; ">个人信息</span>
        </div>
        <br>
        <div style="border-bottom: 2px solid #ed0a75;width: 700px">
          <div style="background-color: #ed0a75;
    color: white;width: 100px;height: 20px;
    font-weight: 700;margin-left: 10px; border-radius: 2px;font-size: small;
    display: flex;align-items: center;justify-content: center;">基本资料</div>
        </div>
        <el-form :model="form" label-width="120px" style="margin-left: 80px;">
          <br>
          <el-form-item>
            <img :src="profileStore.profile.photoUrl" style="border-radius: 50%;width: 100px;height: 100px;" alt="">
          </el-form-item>
          <el-form-item label="用户名：">
            <label style="width: 200px;height: 25px; padding: 0 0 10px 0">{{user.username}}</label>
          </el-form-item>
          <el-form-item label="邮箱：">
            <el-input v-model="user.email" style="width: 200px;height: 25px;" />
          </el-form-item>
          <el-form-item label="用户创建时间：">
            <label style="width: 200px;height: 25px; padding: 0 0 10px 0">{{user.createdAt}}</label>
          </el-form-item>
          <br>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" style="margin-left: -80px;">修改信息</el-button>
            <el-button type="primary" @click="dialogFormVisible=true">修改密码</el-button>
          </el-form-item>
        </el-form>
            <el-button @click="logout" style="margin-left: 600px;margin-top: -700px;">注销账号</el-button>
        <el-dialog class="custom-dialog" v-model="dialogFormVisible" title="修改密码">
          <el-form :model="form" class="oo">
          <el-form-item label="请输入旧密码">
            <el-input type="password" v-model="password3" style="width: 200px;height: 25px;" />
          </el-form-item>
          <el-form-item label="请输入新密码">
            <el-input type="password" v-model="password1" style="width: 200px;height: 25px;"/>
          </el-form-item>
          <el-form-item label="请确认新密码">
            <el-input type="password" v-model="password2" style="width: 200px;height: 25px;"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePassword()">
              确定
            </el-button>
            <el-button @click="dialogFormVisible = false">取消</el-button>
          </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </el-main>
  </el-container>

</div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import Side from "../component/side.vue";
import {useProfileStore} from "../../stores/useProfile.js";
import {onMounted, ref,getCurrentInstance} from "vue";
import router from "../../config/router.config.js";

const profileStore = useProfileStore();
const user=ref(profileStore.profile);
const dialogFormVisible = ref(false)
const $http = getCurrentInstance().appContext.config.globalProperties.$http
const password1=ref('');
const password2=ref('');
const password3=ref('');
const onSubmit = () => {
  $http.post("/user/update",user.value).then(()=>{
    location.reload();
  })
}
const logout=()=>{
  $http.get("/user/logout").then(()=>{
    router.push("/login")
  })
}

const updatePassword=()=>{
  if (password1.value!==password2.value){
    alert("两次密码不一致");
    return;
  }
  if (password3.value!=='1'){
    alert("密码错误");
    return;
  }
  user.value.password=password1.value;
  $http.post("/user/update",user.value).then(()=>{
    password1.value='';
    password2.value='';
    password3.value='';
    dialogFormVisible.value=false
  })
}
onMounted(()=>{
  $http.get("/user/getById?id="+profileStore.profile.id).then((resp)=>{
    user.value=resp.data;
    console.log(user.value)
  })
})
</script>

<style scoped lang="scss">
.q{
  width: 403px;
  float: left;
}
.w{
  float: left;
}
</style>