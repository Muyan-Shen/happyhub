<template xmlns="http://www.w3.org/1999/html">
  <div class="eventList">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <el-container class="main">
          <el-aside class="card">
            <div class="cards">
              <el-card v-for="event of eventList" @click="jumpToEventInfo(event.id)">
                <el-container>
                  <el-aside width="170px">
                    <div class="postDiv">
                      <el-image v-if="event.photoUrl" :src="event.photoUrl"/>
                      <el-image v-if="!event.photoUrl" src="/event/photo03.jpg"/>
                    </div>
                  </el-aside>
                  <el-scrollbar>
                    <el-main>
                      <span class="title">【{{ event.location }}】{{ event.title }}</span><br>
                      <el-image src="/event/calendar.png" class="calendar"/>
                      <span class="timeShow" v-if="event.startTime">
                {{ event.startTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
                &emsp;-&emsp;
                {{ event.endTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
                  </span>
                    </el-main>
                  </el-scrollbar>
                </el-container>
              </el-card>
            </div>
            <el-pagination background
                           layout="prev,pager,next"
                           v-model:page-size="pageInfo.limit"
                           v-model:current-page="pageInfo.page"
                           :total="pageInfo.total"
                           @current-change="eventList"/>
          </el-aside>
          <el-scrollbar>
          <el-main>
            <el-container class="sidebar">
              <el-header class="recommend">
                您可能还喜欢
              </el-header>
              <el-main>
                <el-card class="card1" v-for="event of eventLists" @click="jumpToEventInfo(event.id)">
                    <div class="postDiv1">
                      <el-image v-if="event.photoUrl" :src="event.photoUrl" style="height: 100%"/>
                      <el-image v-if="!event.photoUrl" src="/event/photo03.jpg" style="width: 100%"/>
                    </div>
                    <template #footer>
                      <span class="title1">{{ event.title }}</span><br>
                      <span class="timeShow1" v-if="event.startTime">
                {{ event.startTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
                &emsp;-&emsp;
                {{ event.endTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
              </span>
                    </template>
                </el-card>
              </el-main>
            </el-container>
          </el-main>
          </el-scrollbar>
        </el-container>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";
import router from "../../config/router.config.js";

const profileStore = useProfileStore();
const eventList = ref([]);
const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const eventLists=ref([]);
const pageInfo = reactive({
  page: 1,
  limit: 16,
  total: 0
})

const getEventLists = () => {
  $http.get('/event/getThree').then(resp=> {
    eventLists.value=resp.data;
  })
}

// 在渲染时加载活动列表，从pinia中获得
onMounted(() => {
  eventList.value = profileStore.eventList;
  getEventLists();
  console.log(eventLists.value)
})

const jumpToEventInfo = (eventId) => {
  console.log(eventId)
  router.push({
    name: "eventInfo",
    params: {
      eventId: eventId
    }
  })
}


</script>

<style scoped lang="scss">
.eventList {
  height: 100%;

  .el-main {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    height: 100%;
    margin-left: 10%;
    //margin-right: 30%;


    .card{
      width: 70%;
      height: 100%;
    }
    .main{
      height: 100%;
      margin-top: 20px;
      margin-left: 20px;
      padding: 0;

      .el-main{
        margin-left: 0;
      }

      .sidebar{
        margin-left: 0;
        width: 150px;
        border: 1px solid #eee;

        .el-main{
          padding: 0;

          .card1{
            width: 100%;
            height: 100%;

            .title1{
              height: 28px;
              font-size: 15px;
              color: #333;
              display: block;
            }

            .postDiv1{
              width: 98px;
              height: 132px;
            }

            .timeShow1{
              color: #999;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              font-size: 12px;
            }
          }
        }

        .recommend{
          background-color: #f5f5f5;
          height: 37px;
          padding: 5px;
        }
      }
    }

    .cards {
      height: 100%;
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      margin-top: 15px;

      .el-card {
        width: 100%;
        height: 250px;

        .el-side {
          width: 170px;
          height: 230px;
        }

        .postDiv {
          width: 153px;
          height: 230px;
        }

        .calendar {
          width: 16px;
          height: 16px;
        }

        .el-main {
          padding: 0;
          margin-left: 0;

          .title{
            display: block;
          }

          .timeShow {
            font-size: 14px;
          }
        }
      }
    }
  }
}
</style>