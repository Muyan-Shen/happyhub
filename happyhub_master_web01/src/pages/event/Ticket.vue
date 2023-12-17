<template>
  <el-container>
    <el-header>
      <top-header/>
    </el-header>
    <el-main>
      <div class="ticketShow">
        <el-card class="ticketMessage" v-model="ticket">
          <div class="ticketTitle">
            <el-icon>
              <DocumentRemove/>
            </el-icon>
            电子门票
          </div>
          <div>活动标题：{{ ticket.title }}</div>
          <div>用户名：{{ ticket.username }}</div>
          <div>档位：{{ ticket.gear }}档</div>
          <div>座位号：{{ ticket.seatNumber }}</div>
          <div>方向：{{ ticket.direction }}</div>
          <div>排数：第{{ ticket.row }}排</div>
          <div>列数：第{{ ticket.col }}列</div>
          <el-divider></el-divider>
          <el-button @click="returnHome">完成</el-button>
        </el-card>

      </div>
    </el-main>
  </el-container>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {useRoute} from "vue-router";
import {nextTick, onBeforeMount, onMounted, ref} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";
import {DocumentRemove} from "@element-plus/icons-vue";
import router from "../../config/router.config.js";

const route = useRoute();
const ticket = ref({
  "title": '',
  "username": '',
  "gear": '',
  "seatNumber": '',
  "direction": '',
  "row": '',
  "col": ''
})

const useProfileStore1 = useProfileStore();

const getTicket = () => {
  ticket.value = useProfileStore1.ticket
  useProfileStore1.ticket = ''
  nextTick(()=>{
    useProfileStore1.ticket = ''
  })


}


onMounted(() => {
  getTicket();

})

const returnHome=()=>{
  router.push({
    name: 'home',
  })
}
</script>

<style scoped lang="scss">
.ticketShow {
  justify-content: center;
  align-items: center;
  margin-right: 200px;
  margin-left: 200px;
  padding: 0;
  height: 100%;

  .ticketMessage {
    .ticketTitle {
      background-color: hotpink;
      margin-bottom: 10px;
      font-size: 25px;
      font-weight: bold;
      text-align: center;
    }
  }
}
</style>