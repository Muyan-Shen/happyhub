<template>
  <div class="service">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="main-content" >
            <el-card class="box-card" shadow="hover">
                <template #header>
                    <div class="card-header">
                        客服妹妹
                    </div>
                </template>

                <div class="chat-frame">
                    <ul v-infinite-scroll="load" class="infinite-list" style="overflow: auto">
                        <li v-for="i in count" :key="i" class="infinite-list-item">{{ i }}</li>
                    </ul>
                    <robot  :info="robotInfo"></robot>
                    <user  :info="userInfo" :userSrc="profileStore.profile.photoUrl"></user>
                </div>
                <template #footer>
                    <div class="input-field">
                      <input1 @sendData="clickEven"></input1>
                    </div>
                </template>
            </el-card>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from "../component/header.vue";
import Input1 from "./input1.vue";
import {ref} from "vue";
import Robot from "./robot.vue";
import User from "./user.vue";
import {useProfileStore} from "../../stores/useProfile.js";
const profileStore = useProfileStore();

const robotInfo = ref("傻逼吗你")
const userInfo = ref("你确实傻逼")
//---------------------------发送信息(子组件回传)
const sendData=ref('')
const clickEven=(val)=> {
    sendData.value = val.input;
}
</script>

<style scoped lang="scss">
.service{
  height: 100%;

}
.main-content{
  background-image: url('https://picsum.photos/2560/1600/?random');
  background-size: cover;
  height: 100vh;
  display: flex;
}
.box-card{
  margin: auto;
  height: 80vh;
  width: 100vh;
  display: list-item;

  .card-header{
    height: 2vh;
  }
  .chat-frame{
    height: 50vh;

  }
  .input-field{
    height: 5vh;
  }
}

</style>