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
                                    class="el-button"
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
            <el-container>
                <el-aside>
                    <el-menu
                            active-text-color="#ffd04b"
                            background-color="#545c64"
                            class="el-menu-vertical-demo"
                            :collapse="isCollapse"
                            :router="true"
                            :unique-opened="false"
                            :collapse-transition="true"
                            text-color="#fff"
                    >
                        <template v-for="m1 of serverMenus">
                            <el-sub-menu v-if="m1.children" :key="m1.id" :index="m1.routePath">
                                <template #title>
                                    <el-icon>
                                        <component :is="m1.icon"/>
                                    </el-icon>
                                    <span>
                                            {{ m1.title?m1.title:"" }}
                                        </span>
                                </template>
                                <el-menu-item v-for="m2 of m1.children" :key="m2.id" :index="m2.routePath">
                                    <template #title>
                                        <el-icon>
                                            <component :is="m2.icon"/>
                                        </el-icon>
                                        <span>
                                            {{ m2.title?m2.title:"" }}
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
                                        {{ m1.title?m1.title:"" }}
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
import {ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {serverMenus} from "../../config/route.config.js"

const isCollapse = ref(true)

function change() {
    isCollapse.value = !isCollapse.value;
}

const route = useRoute();
const defaultActive = ref(route.path);
const router = useRouter();

router.afterEach((to) => {
    defaultActive.value = to.path;
})


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

          .el-button {
            border: none;
            font-size: 30px;
            height: 50px;
            width: 150px;
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