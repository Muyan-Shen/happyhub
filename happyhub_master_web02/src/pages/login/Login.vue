<template>
  <div class="login">
    <el-form :model="user"
             :rules="rules"
             ref="accountForm"
             @submit="onLogin($event,$refs.accountForm)">
      <el-form-item>
        <h1>权限管理系统</h1>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" clearable>请输入用户名</el-input>
      </el-form-item>
      <el-form-item label="密&emsp;码" prop="passwordHash">
        <el-input v-model="user.passwordHash" show-password clearable>请输入密码</el-input>
      </el-form-item>
      <el-form-item>
        <div class="btns">
          <el-button type="primary" native-type="submit">Login</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { addServerRoutes,resetRouters,serverMenus } from "../../config/route.config.js"
import { reactive,getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import { ElLoading } from "element-plus";
import { useProfileStore } from "@/stores/useProfile.js";

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

const onLogin = async (e, form) => {
    e.preventDefault(); // 阻止默认刷新界面
    try {
        await form.validate(async (valid) => {
            if (valid) {
                // 提交表单
                let service = ElLoading.service({
                    lock: true,
                    text: 'Loading...',
                    background: 'rgba(0, 0, 0, 0.4)'
                });

                try {
                    const loginResponse = await $http.post('/user/login2', user);
                    if (loginResponse.code === 200) { // 登录成功
                        resetRouters();

                        // 保存登录状态，跳转到首页
                        profileStore.login(loginResponse.token, loginResponse.user.roles, loginResponse.user.roles.permissions, loginResponse.account);
                        const menuResponse = await $http.get('/menu/' + loginResponse.user.username);
                        // 存储菜单
                        serverMenus.value = menuResponse.data.menuTree;
                        // 添加路由
                        addServerRoutes(menuResponse.data.routeList);
                        // 跳转主页
                        router.push({ name: 'home' });
                    }
                } catch (error) {
                    console.error(error);
                } finally {
                    service.close();
                }
            }
        });
    } catch (e) {
        console.error(e);
    }
}

</script>

<style scoped lang="scss">
.login{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("https://picsum.photos/1920/1080/?random");
  background-size: cover;
  background-position: center;

  .el-form{
    min-width: 420px;
    padding: 15px;
    border-radius: 10px;
    box-shadow: 0 0 10px #ccc;
    background-color: rgba(255,255,255,0.4);
    backdrop-filter: blur(2px);
    .el-form-item{
      h1{
        text-align: center;
        font-size: 36px;
        font-weight: bold;
        flex: 100%;
      }
      .btns{
        flex: 1;
        text-align: right;
      }
    }
  }
}
</style>