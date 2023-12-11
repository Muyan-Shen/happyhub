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
                        <template v-for="m1 of serverMenus">
                            <el-sub-menu v-if="m1.children" :key="m1.id" :title="m1.title">
                                <template #icon></template>
                                <el-menu-item v-for="m2 of m1.children" :key="m2.id">
                                    <template #icon></template>
                                    <router-link v-if="m2.routePath" :to="m2.routePath">{{m2.title}}</router-link>
                                    <span v-else>{{m2.title}}</span>
                                </el-menu-item>
                            </el-sub-menu>
                            <el-menu-item v-else :key="m1.id">
                                <template #icon></template>
                                <router-link v-if="m1.routePath" :to="m1.routePath">{{m1.title}}</router-link>
                                <span v-else>{{m1.title}}</span>
                            </el-menu-item>
                        </template>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<!--<el-menu-item index="/account">-->
<!--<i class="iconfont icon-account"/>-->
<!--<span>账号管理</span>-->
<!--</el-menu-item>-->
<!--<el-menu-item index="/role">-->
<!--<i class="iconfont icon-new-role"/>-->
<!--<span>角色管理</span>-->
<!--</el-menu-item>-->
<!--<el-menu-item index="/permission">-->
<!--<i class="iconfont icon-permission-manage"/>-->
<!--<span>权限管理</span>-->
<!--</el-menu-item>-->
<script setup>
import {ref} from "vue";
import {useRouter,useRoute} from "vue-router";
import { serverMenus } from "../../config/route.config.js"
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