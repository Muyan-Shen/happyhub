<template>
  <div class="layout">
    <el-container>
      <el-header>
        <i class="iconfont icon-a-zu44"/>
        <span>权限管理系统</span>
      </el-header>
      <el-container>
        <el-aside>
          <el-menu
              active-text-color="#ffd04b"
              background-color="#545c64"
              class="el-menu-vertical-demo"
              :router="true"
              :default-active="defaultActive"
              text-color="#fff"
          >
            <el-menu-item index="/home/account">
              <i class="iconfont icon-account"/>
              <span>账号管理</span>
            </el-menu-item>
            <el-menu-item index="/home/role">
              <i class="iconfont icon-new-role"/>
              <span>角色管理</span>
            </el-menu-item>
            <el-menu-item index="/home/permission">
              <i class="iconfont icon-permission-manage"/>
              <span>权限管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {useRouter,useRoute} from "vue-router";
const route = useRoute();
const defaultActive = ref(route.path);
const router = useRouter();
router.afterEach((to)=>{
  defaultActive.value = to.path;
})
</script>

<style scoped lang="scss">
.layout {
  height: 100%;

  .el-header {
    background-color: #303133;
    color: #fff;
    font-size: 36px;
    font-weight: bold;
    .iconfont{
      font-size: 36px;
      margin-right: 10px;
    }
  }

  .el-container {
    height: 100%;

    .el-aside {
      height: 100%;
      background-color: #545c64;
    }

    .el-main {
      height: 100%;
      background-color: #fff;
    }

    .el-menu-item{
      i{
        margin: 5px;
      }
    }
  }
}
</style>