<template>
  <div class="login">
    <el-container>
      <el-header>
        <el-image src="LOGO.png"/>
      </el-header>
      <el-main>
        <el-form :model="user"
                 :rules="rules"
                 ref="accountForm"
                 @submit="onLogin($event,$refs.accountForm)">
          <el-form-item>
            <a>账号登录</a>
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="user.username"
                      clearable>
              <template #prepend>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="user.passwordHash"
                      show-password
                      clearable>
              <template #prepend>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <div class="btns">
              <el-button type="primary" native-type="submit">登录</el-button>
            </div>
          </el-form-item>
        </el-form>
      </el-main>
      <el-footer>

      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import {Lock, User} from "@element-plus/icons-vue";
import {reactive,getCurrentInstance} from "vue";
import {useRouter} from "vue-router";
import {ElLoading} from "element-plus";
import {useProfileStore} from "@/stores/useProfile.js";

const user = reactive({
  username: "",
  passwordHash: ""
})
const router = useRouter();
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const profileStore = useProfileStore();

const rules = reactive({
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"}
  ],
  passwordHash: [
    {required: true, message: "请输入密码", trigger: "blur"}
  ]
})

const onLogin = (e,form)=>{
  e.preventDefault();//阻止默认刷新界面
  form.validate((valid) => {
    if (valid) {
      // 提交表单
      let service = ElLoading.service({
        lock:true,
        text:'Loading...',
        background:'rgba(0, 0, 0, 0.4)'
      })
      $http.post('/user/login2',user).then(res=>{
        // 保存登录状态，跳转到首页
        profileStore.login(res.token,res.roles,res.permissions,res.account)
        router.push({name:'home'})
      }).finally(()=>{
        service.close();
      })
    }
  })
}
</script>

<style scoped lang="scss">
.login{
  height: 100%;
  display: flex;
  .el-header{
    height: 10%;
    .el-image{
      height: 75%;

      margin-left: 8%;
      margin-top: 1%;
    }
  }
  .el-main{
    background-color: #1a093d;
    background-image: url("PostMap.jpg");
    background-repeat: no-repeat;
    background-position: 12% 60%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .el-form{
      min-width: 280px;
      padding: 20px;
      margin-left: auto;
      margin-right: 10%;
      box-shadow: 0 0 10px #ccc;
      background-color: rgba(255,255,255,1);
      backdrop-filter: blur(2px);
      .el-form-item{
        margin-bottom: 8%;
        a{
          color: #7e51a6;
          text-align: center;
          font-size: 28px;
          font-weight: bold;
          flex: 100%;
        }
        .el-input{
          height: 110%;
          border-radius: 0px;
        }
        .btns{
          flex: 1;
          text-align: right;
          margin-top: 3%;
          .el-button{
            width: 100%;
            height: 36px;
            background-color: #ff1c74;

            font-size: 16px;
            font-weight: 750;
          }
        }
      }
    }
  }
}
</style>