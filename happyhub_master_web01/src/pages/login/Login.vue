<template>
  <div class="login">
    <el-container>
      <el-header>
        <el-image src="LOGO.png"/>
      </el-header>
      <el-main>
        <el-tabs type="border-card">
          <el-tab-pane label="用户登录">
            <el-form :model="user"
                     :rules="rules"
                     ref="accountForm"
                     @submit="onLogin($event,$refs.accountForm)">
              <el-form-item prop="username">
                <el-input v-model="user.username"
                          placeholder="请输入账号"
                          clearable>
                  <template #prepend>
                    <el-icon>
                      <User/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="user.passwordHash"
                          placeholder="请输入密码"
                          show-password
                          clearable>
                  <template #prepend>
                    <el-icon>
                      <Lock/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item>
                <div class="btns">
                  <el-button type="primary" native-type="submit">登录</el-button>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="用户注册">
            <el-form :model="user"
                     :rules="rules"
                     ref="regForm"
                     @submit="onRegister($event,$refs.regForm)">
              <el-form-item prop="username">
                <el-input v-model="user.username"
                          placeholder="请输入账号"
                          clearable>
                  <template #prepend>
                    <el-icon>
                      <User/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="user.passwordHash"
                          placeholder="请输入密码"
                          show-password
                          clearable>
                  <template #prepend>
                    <el-icon>
                      <Lock/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item>
                <div class="btns">
                  <el-button type="primary" native-type="submit">注册</el-button>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-main>
      <el-footer>

      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import {Lock, User} from "@element-plus/icons-vue";
import {reactive, getCurrentInstance} from "vue";
import {useRouter} from "vue-router";
import {ElLoading, ElMessage} from "element-plus";
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
    {required: true, message: "请输入用户名", trigger: "blur"},

  ],
  passwordHash: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {
      pattern: /^(?=.*\d)(?=.*[a-zA-Z]).{1,16}$/,
      message: '密码必须由1-16位数字、字母组成',
      trigger: 'blur',
    },
  ]
})

const onLogin = (e, form) => {
  e.preventDefault();//阻止默认刷新界面
  form.validate((valid) => {
    if (valid) {
      // 提交表单
      let service = ElLoading.service({
        lock: true,
        text: 'Loading...',
        background: 'rgba(0, 0, 0, 0.4)'
      })
      $http.post('/user/login', user,).then(res => {
        if (res.user){
          ElMessage.success({
            message: "登录成功",
            duration: 750
          })
          // 保存登录状态，跳转到首页
          profileStore.login(res.token, res.user.roles, res.user.roles.permissions, res.user)
          router.push({name: 'home'})
        }
      }).finally(() => {
        service.close();
      })
    }
  })
}
const onRegister = (e, form) => {
  e.preventDefault();//阻止默认刷新界面
  form.validate((valid) => {
    if (valid) {
      // 提交表单
      let service = ElLoading.service({
        lock: true,
        text: 'Loading...',
        background: 'rgba(0, 0, 0, 0.4)'
      })
      $http.post('/user/register', user).then(res => {
        if (res.msg === "注册成功") {
          ElMessage.success({
            message: "注册成功，请登录",
            duration: 750
          })
          router.push("/login")
        }
      }).finally(() => {
        service.close();
      })
    }
  })
}
</script>

<style scoped lang="scss">
.login {
  height: 100%;
  display: flex;

  .el-header {
    height: 10%;

    .el-image {
      height: 75%;

      margin-left: 8%;
      margin-top: 1%;
    }
  }

  .el-main {
    background-color: #1a093d;
    background-image: url("PostMap.jpg");
    background-repeat: no-repeat;
    background-position: 12% 60%;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    .el-tabs {
      min-width: 330px;
      padding: 0px;
      margin-left: auto;
      margin-right: 10%;

      box-shadow: 0 0 10px #ccc;
      background-color: rgba(255, 255, 255, 1);
      backdrop-filter: blur(2px);

      .el-tab-pane {
        .el-form {
          .el-form-item {
            margin-bottom: 8%;

            .el-input {
              height: 110%;
              border-radius: 0px;
            }

            .btns {
              flex: 1;
              text-align: right;
              margin-top: 3%;

              .el-button {
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
  }
}
</style>