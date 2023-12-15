<template>
  <div class="layout">
    <el-container>
      <el-header>
        <top-header/>
      </el-header>
      <el-main>
        <div class="carousel">
          <el-carousel>
            <el-carousel-item v-for="p of photos">
              <el-image :src=p fit="cover" style="width: 100%;"/>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="cards">
          <el-card v-for="event of profileStore.eventList" @click="jumpToEventInfo(event.id)">
            <div class="postDiv">
              <el-image v-if="event.photoUrl" :src="event.photoUrl" style="height: 75%"/>
              <el-image v-if="!event.photoUrl" src="/event/photo03.jpg" style="width: 100%"/>
            </div>
            <template #footer>
              <span>{{ event.title }}</span><br>
              <span class="timeShow" v-if="event.startTime">
                {{ event.startTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
                &emsp;-&emsp;
                {{ event.endTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
              </span>
            </template>
          </el-card>
        </div>
        <el-pagination background
                       layout="prev,pager,next"
                       v-model:page-size="pageInfo.limit"
                       v-model:current-page="pageInfo.page"
                       :total="pageInfo.total"
                       @current-change="getEventLists"/>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import router from '../../config/router.config.js'
import {reactive, onMounted, getCurrentInstance, ref} from "vue";
import {useProfileStore} from "../../stores/useProfile.js";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const profileStore = useProfileStore();
const photos = reactive(["post.png", "post.png", "post.png"])
const pageInfo = reactive({
  page: 1,
  limit: 16,
  total: 0
})

const jumpToEventInfo = (eventId) => {
  console.log(eventId)
  router.push({
    name:"eventInfo",
    params:{
      eventId:eventId
    }
  })
}
const getEventLists = () => {
  $http.get('/event/getAll', {"pageNum": pageInfo.page, "limit": pageInfo.limit}).then(resp => {
    profileStore.eventList = resp.data.list;
  })
}
onMounted(() => {
  getEventLists();
})
</script>

<style scoped lang="scss">
.layout {
  height: 100%;

  .el-main {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
    height: 100%;
    margin-left: 10%;
    margin-right: 10%;

    .carousel {
      width: 100%;
      height: 282px;
    }

    .cards {
      height: 100%;
      width: 100%;
      display: flex;
      flex-wrap: wrap;

      margin-top: 15px;

      .el-card {
        width: 23%;
        margin: 8px;

        .postDiv {
        }

        .timeShow {
          font-size: 10px;
        }
      }

      .el-card:hover {
        box-shadow: #b4b3b3 0px 4px 5px 5px;
      }
    }
  }
}
</style>