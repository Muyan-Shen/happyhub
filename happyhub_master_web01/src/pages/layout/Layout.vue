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
          <el-card v-for="event of eventList">
            <div class="postDiv">
              <el-image v-if="event.photoUrl" :src="event.photoUrl" style="height: 75%"/>
              <el-image v-if="!event.photoUrl" src="/event/photo03.jpg" style="width: 100%"/>
            </div>
            <template #footer>
              <span>{{ event.title }}</span><br>
              <span class="timeShow">
                {{ event.startTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
                &emsp;-&emsp;
                {{ event.endTime.toString().replace(/T/g, ' ').replace(/:\d{2}$/, '') }}
              </span>
            </template>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import topHeader from '../component/header.vue'
import router from '../../config/router.config.js'
import {reactive, onMounted, getCurrentInstance, ref} from "vue";
import {formatter} from "element-plus";
import {formatDate} from "@vueuse/shared";
import {Timer} from "@element-plus/icons-vue";

const $http = getCurrentInstance().appContext.config.globalProperties.$http;
const eventImage = "piglin.gif"
const eventList = ref([])
const photos = reactive(["post.png", "post.png", "post.png"])

const jumpToEventInfo = () => {
  router.push("/eventInfo")
}
onMounted(() => {
  $http.get('/event/getAll', {}).then(resp => {
    eventList.value = resp.data;
    console.log(eventList)
  })
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
    border: red 1px solid;
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
          border: red solid 1px;
        }
        .timeShow{
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