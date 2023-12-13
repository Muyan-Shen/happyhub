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
                                    class="expandButton"
                                    :style="expandButtonStyle"
                                    @click="change">
                                <el-icon v-if="isCollapse">
                                    <DArrowRight/>
                                </el-icon>
                                <el-icon v-else>
                                    <DArrowLeft/>
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
                                           :with-header="false">
                                    <el-descriptions title="个人信息"
                                                     :size='large'
                                                     :column="1">
                                        <el-descriptions-item label="用户名">kooriookami</el-descriptions-item>
                                        <el-descriptions-item label="性别">男</el-descriptions-item>
                                        <el-descriptions-item label="年龄">22</el-descriptions-item>
                                        <el-descriptions-item label="联系方式">18100000000</el-descriptions-item>
                                        <el-descriptions-item label="邮箱地址">504142151@qq.com</el-descriptions-item>
                                        <el-descriptions-item label="身份">
                                            <el-tag size="small">Admin</el-tag>
                                        </el-descriptions-item>
                                    </el-descriptions>
                                    <div class="dialog">
                                        <el-button text @click="dialogFormVisible = true">
                                            修改信息
                                        </el-button>
                                        <el-dialog v-model="dialogFormVisible" title="信息修改">
                                            <el-form :model="form">
                                                <el-form-item label="用户名"
                                                              :label-width="formLabelWidth">
                                                    <el-input v-model="form.username"
                                                              autocomplete="off"/>
                                                </el-form-item>
                                                <el-form-item label="性别"
                                                              :label-width="formLabelWidth">
                                                    <el-select v-model="form.sex"
                                                               placeholder="Please select a zone">
                                                        <el-option label="男" value="shanghai"/>
                                                        <el-option label="女" value="beijing"/>
                                                    </el-select>
                                                </el-form-item>
                                                <el-form-item label="年龄"
                                                              :label-width="formLabelWidth">
                                                    <el-input type="number"
                                                              v-model="form.age"
                                                              maxlength="2" />
                                                </el-form-item>
                                                <el-form-item label="联系方式" :label-width="formLabelWidth">
                                                    <el-input type="tel"
                                                              v-model="form.phone"
                                                              maxlength="11" />
                                                </el-form-item>
                                                <el-form-item label="邮箱地址" :label-width="formLabelWidth">
                                                   <el-input type="email"
                                                             v-model="form.email"/>
                                                </el-form-item>
                                            </el-form>
                                        </el-dialog>
                                    </div>
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
import {getCurrentInstance, onMounted, onUnmounted, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {serverMenus} from "../../config/route.config.js"
import screenfull from 'screenfull'
import {Postcard} from "@element-plus/icons";
import { useProfileStore } from "@/stores/useProfile.js";


const isCollapse = ref(true)
const expandButtonStyle = ref('')

function change() {
    isCollapse.value = !isCollapse.value;
    if (!isCollapse.value) {
        expandButtonStyle.value = "padding-left: 120px;padding-right:30px"
    } else {
        expandButtonStyle.value = "padding-left: 15px"
    }
}

const route = useRoute();
const defaultActive = ref(route.path);
const router = useRouter();
const drawer = ref(false)


router.afterEach((to) => {
    defaultActive.value = to.path;
})

//----------------------------------------------设置全屏-------------------------------------------
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

//----------------------------------------------------------对话框
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const profileStore = useProfileStore();


const form = reactive({
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})
console.log(profileStore.AllRoles)
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
    display: flex;
    align-items: center;
    justify-content: flex-start;

    span {
      font-size: 28px;
      font-weight: bold;
      font-style: italic;
      -webkit-box-reflect: below -5px linear-gradient(transparent 50%, #d7d5d5)
    }

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
          text-align: center;

          .expandButton {
            border: none;
            font-size: 30px;
            height: 50px;
            width: 64px;
            transition: all 0.3s ease;
            --el-button-hover-text-color: none;
            --el-button-hover-bg-color: #ccc;
            --el-button-active-bg-color: #ccc;
          }
        }

        .items-center {
          width: 100%;
          display: flex;
          justify-content: right; //水平
          align-items: center; //垂直
          .postcard {
            margin-right: 20px;
            .dialog{
              text-align: center;
            }
          }

          .screenfull {
            margin-right: 20px;
          }

          .mr-3 {
            margin-right: 20px;

            .el-avatar {
              border: #303133 3px solid;
            }
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
      box-shadow: 5px 5px 5px 0px #ccc inset;
    }

    .el-menu-item {
      i {
        margin: 5px;
      }
    }
  }
}
</style>