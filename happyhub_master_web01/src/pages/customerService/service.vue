<template>
    <div class="service">
        <el-container>
            <el-header>
                <top-header />
            </el-header>
            <el-main>
                <div class="main-content">
                    <el-card class="box-card" shadow="hover">
                        <template #header>
                            <div class="card-header">
                                客服妹妹
                            </div>
                        </template>

                        <div class="chat-box-header">
                            <el-link @click="get_history" target="_blank">聊天记录</el-link>
                        </div>

                        <div class="chat-frame">
                            <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
                                <li v-for="msg of messageList" :key="msg.index" class="infinite-list-item">
                                    <robot v-if="msg.role==='bot'" :info="msg.content"></robot>
                                    <user v-else :info="msg.content" :userSrc="profileStore.profile.photoUrl"></user>
                                </li>
                            </ul>
                        </div>

                        <div class="input-field">
                            <input1 @sendData="clickEven" />
                        </div>

                    </el-card>
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script setup>
import topHeader from "../component/header.vue";
import Input1 from "./input1.vue";
import {getCurrentInstance, reactive, ref} from "vue";
import Robot from "./robot.vue";
import User from "./user.vue";
import {useProfileStore} from "../../stores/useProfile.js";
import {ElMessage} from "element-plus";
const profileStore = useProfileStore();
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
let messageList = ref([])

//---------------------------发送信息(子组件回传)
const clickEven = async (val) => {
    if (!val.input.trim()) return;
    try {
        const resp = await $http.post('/message/send?msg='+val.input);

        if (resp.code !== 200) {
            ElMessage.error('获取数据出错');
            return;
        }
        messageList.value=resp.data;
        console.log(messageList)
    } catch (error) {
        ElMessage.error('发送消息失败: ' + error.message);
    }
};
//--------------------------加载当前会话
const load = async () => {
    await $http.get('/message/get').then((resp) => {
        messageList.value=resp.data;
    }).catch((error) => {
        ElMessage.error("加载出错", error);
    });
}
//--------------------------获取历史信息
const get_history = async () => {
    await $http.get('/message/history', {
        withCredentials: true
    }).then((resp) => {
        messageList.value = resp.data;

    }).catch((error) => {
        ElMessage.error("获取历史信息错误", error);
    });
}
</script>

<style scoped lang="scss">
.service {
  height: 100%;
  display: flex;
  flex-direction: column;
  background-image: url('https://picsum.photos/2560/1600/?random');
  background-size: cover;
  background-position: center;
}

.el-header {
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 3;
}

.el-main {
  padding-top: 60px;
  flex: 1;
}

.main-content {
  flex-grow: 1;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 20px;
}

.box-card {
  max-height: calc(100vh - 80px)/2;
  width: 60vw;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0,0,0,.3);
  overflow: hidden;
}

.card-header,
.input-field {
  background-color: #fff;
  padding: 15px 20px;
}

.card-header {
  font-size: 1.25rem;
  font-weight: bold;
}

.chat-frame {
  height: 50vh;
  flex-grow: 1;
  overflow-y: auto;
  padding: 0 15px;
}

.infinite-list-item {
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,.1);
  padding: 10px;
  list-style: none;
  margin-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}


.input-field {
  margin-top: auto;
  box-shadow: 0 -2px 10px rgba(0,0,0,.2);
}
</style>