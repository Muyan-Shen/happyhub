<template>
    <div class="layout">
        <el-container>
            <el-header>
                <i class="iconfont icon-a-zu44"/>
                <span>权限管理系统</span>
            </el-header>
            <el-row>
                <el-col :span="24">
                    <div class="col">
                        <div class="button">
                            <el-button
                                    class="but1"
                                    :color="blue"
                                    @click="change">
                                <el-icon v-if="isCollapse">
                                    <Expand/>
                                </el-icon>
                                <el-icon v-else>
                                    <Fold/>
                                </el-icon>
                            </el-button>
                        </div>

                        <div class="items-center">
                            <div class="postcard">
                                <el-button type="primary"
                                           :circle="true"
                                           @click="drawer = true"
                                           :icon="Postcard">

                                </el-button>
                                <el-drawer v-model="drawer"
                                           title="I am the title"
                                           :with-header="false">
                                    <span>Hi there!</span>
                                </el-drawer>
                            </div>
                            <div class="screenfull">
                                <el-button
                                        @click="handleFullScreen"
                                        :circle="true"
                                        :icon="isFullscreen ? 'Exit-FullScreen' : 'FullScreen'"/>
                            </div>
                            <div class="mr-3">
                                <el-avatar
                                        :size="96"

                                        src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
                            </div>

                        </div>

                        <!--                        <div class="broad">-->
                        <!--                            <el-breadcrumb class="breadcrumb" separator="/">-->
                        <!--                                <transition-group name="breadcrumb">-->
                        <!--                                    <el-breadcrumb-item-->
                        <!--                                            v-for="(item, index) in breadcrumbData"-->
                        <!--                                            :key="item.path"-->
                        <!--                                    >-->

                        <!--                                        &lt;!&ndash; 不可点击项 &ndash;&gt;-->
                        <!--                                        <span v-if="index === breadcrumbData.length - 1"-->
                        <!--                                              class="no-redirect">{{ item.meta.title }}</span>-->
                        <!--                                        &lt;!&ndash; 可点击项 &ndash;&gt;-->
                        <!--                                        <a v-else class="redirect"-->
                        <!--                                           @click.prevent="onLinkClick(item)">{{ item.meta.title }}</a>-->
                        <!--                                    </el-breadcrumb-item>-->
                        <!--                                </transition-group>-->
                        <!--                            </el-breadcrumb>-->
                        <!--                        </div>-->
                        <!--                    </div>-->
                    </div>
                </el-col>
            </el-row>
            <el-divider/>
            <el-container>
                <el-aside>
                    <el-menu
                            active-text-color="#ffd04b"
                            background-color="#545c64"
                            class="el-menu-vertical-demo"
                            :collapse="isCollapse"
                            :router="true"
                            :unique-opened="true"
                            :collapse-transition="true"
                            text-color="#fff"
                    >
                        <template v-for="m1 of serverMenus">
                            <el-sub-menu v-if="m1.children" :key="m1.id" :index="m1.id">
                                <template #title>
                                    <el-icon>
                                        <component :is="m1.icon"/>
                                    </el-icon>
                                    <span>
                                            {{ m1.title ? m1.title : "" }}
                                        </span>
                                </template>
                                <el-menu-item v-for="m2 of m1.children" :key="m2.id" :index="m2.routePath">
                                    <template #title>
                                        <el-icon>
                                            <component :is="m2.icon"/>
                                        </el-icon>
                                        <span>
                                            {{ m2.title ? m2.title : "" }}
                                        </span>
                                    </template>
                                </el-menu-item>
                            </el-sub-menu>
                            <el-menu-item v-else :key="m1.id" :index="m1.routePath">
                                <template #title>
                                    <el-icon>
                                        <component :is="m1.icon"/>
                                    </el-icon>
                                    <span>
                                        {{ m1.title ? m1.title : "" }}
                                    </span>
                                </template>
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

<script setup>
import {onMounted, onUnmounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {serverMenus} from "../../config/route.config.js"
import screenfull from 'screenfull'
import {Postcard} from "@element-plus/icons";

const isCollapse = ref(true)

function change() {
    isCollapse.value = !isCollapse.value;
}

const route = useRoute();
const defaultActive = ref(route.path);
const router = useRouter();
const drawer = ref(false)


router.afterEach((to) => {
    defaultActive.value = to.path;
})


// 是否全屏
const isFullscreen = ref(false)

function handleFullScreen() {
    screenfull.toggle()
}

// // 生成数组数据
// const breadcrumbData = ref([])
// const getBreadcrumbData = () => {
//
//     breadcrumbData.value = route.matched.filter(
//         item => item.meta && item.meta.title
//     )
//
// }
// // 监听路由变化时触发
// watch(
//     route,
//     () => {
//         getBreadcrumbData()
//     },
//     {
//         immediate: true
//     }
// )
//
// // 处理点击事件
// const onLinkClick = item => {
//     router.push(item.path)
// }
const change1 = () => {
    isFullscreen.value = screenfull.isFullscreen
}
onMounted(() => {
    screenfull.on('change1', change1)
})
// 删除侦听器
onUnmounted(() => {
    screenfull.off('change1', change1)
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

    .iconfont {
      font-size: 36px;
      margin-right: 10px;
    }
  }

  .el-container {
    height: 100%;

    .el-divider--horizontal {
      margin: 1px 0;
    }

    .el-row {
      height: 50px;
      width: 100%;

      .col {
        height: 50px;
        width: 100%;
        display: flex;

        .button {
          width: 150px;
          text-align: center;

          .but1 {
            border: none;
            font-size: 30px;
            height: 50px;
            width: 150px;
          }
        }

        .items-center {
          width: 100%;
          display: flex;
          justify-content: right; //水平
          align-items: center; //垂直
          .postcard {
            margin-right: 20px;
          }

          .screenfull {
            margin-right: 20px;
          }

          .mr-3 {

            margin-right: 20px;
          }
        }

        //.broad {
        //  display: flex;
        //  justify-content: left; //水平
        //  margin-left: 10px;
        //  align-items: center; //垂直
        //  height: 100%;
        //  width: 100%;
        //
        //  .no-redirect {
        //
        //    font-size: 20px;
        //    margin-top: 5px;
        //    text-align: center;
        //  }
        //}
      }

    }

    .el-aside {
      text-align: center;
      height: 100%;
      width: auto;
      background-color: #545c64;


    }

    .el-main {
      height: 100%;
      background-color: #fff;
    }

    .el-menu-item {
      i {
        margin: 5px;
      }
    }
  }
}
</style>